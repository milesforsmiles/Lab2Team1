package lab2miles;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/* 
Project: Fix the Deadlock
Purpose Details: Create an application that makes an
intentional deadlock between threads upon runtime.
In this example, both Emma and John are trying to add
money to each other's accounts at the same exact time.
John has his account open and tries to open Emma's.
However, Emma is simultaneously in her account at the same time John is
trying to access her account which causes a deadlock because 
the account can only be opened one at a time.
I detected the deadlock using a ThreadManagement factory to cycle
through the threads I have running and automatically catch the deadlock.

My solution to fix it involves making Emma's account "notify all" the other threads 
that are waiting to access that account to wake up. It then uses wait to suspend her 
account which fixes the deadlock and lets them set the balances in their accounts
to different values.

Course: IST 411
Author: Andrea Miles
Date Developed: 5/21/19
Last Date Changed: 5/28/19 
Revision: I added more comments to help the Teacher understand my solution better
because I was asked to send my program in as a zip file today.
 */
public class Lab2Miles {

    /*
    Threads allow multiple processes to run simultaneously.
    They execute independently of each other and are good 
    for helping your program to multitask and allow resources to run
    without sacrificing the usability of the program. 
    This program causes a deadlock where a thread becomes locked
    because it's waiting for resources from another thread.
     */
    
    public static void main(String[] args) {

        Account emma = new Account("Emma", 300);
        Account john = new Account("John", 200);

        Runnable addToAccountEmma = () -> {
            synchronized (emma) {

                System.out.println("Emma: I'm in my account at the same time John is!");

                /*
                This code notifies the thread that is trying to access John's account that Emma's account is in use.
                It then makes Emma's account wait it's turn and essentially unravels the deadlock.
                 */
                emma.notifyAll();
                try {
                    //releases the lock by telling the other thread to wait
                    emma.wait(50);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    Logger.getLogger(Lab2Miles.class.getName()).log(Level.SEVERE, null, e);
                }

                //This block will never be reached because John's account is already open
                synchronized (john) {

                    System.out.println("Emma is trying to access John's account!");
                    john.setBalance(100);
                    System.out.println(john.getBalance());

                }
            }
        };

        Runnable addToAccountJohn = () -> {

            synchronized (john) {
                System.out.println("John: I'm in my account at the same time Emma is!");

                synchronized (emma) {
                    System.out.println("Need Emma");
                    emma.setBalance(500);
                    System.out.println(emma.getBalance());

                }
            }
        };

        ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
        //Creating the threads
        Thread t1 = new Thread(addToAccountEmma);
        t1.setName("Thread One");
        Thread t2 = new Thread(addToAccountJohn);
        t2.setName("Thread Two");

        t1.start();

        t2.start();

        //timeForADeadLock();

        /*
        
        https://www.codota.com/code/java/classes/java.lang.management.ThreadMXBean
        All credit for finding dead locked code comes from the examples given on 
        codota.com. There were similar examples showing how to use a thread factory
        on stackover flow as well that followed the same general format.
        
         */
        
        long[] threadDeadlocks = tmx.findDeadlockedThreads();

        if (threadDeadlocks != null) {
            ThreadInfo[] info = tmx.getThreadInfo(threadDeadlocks, true, true);

            for (ThreadInfo ti : info) {
                System.out.println("The following threads are deadlocked: ");
                System.out.println("Thread name : " + ti.getThreadName());
                //This can go on and on
                //With the thread info about the state of the thread or which thread that blocked it.
                System.out.println("Who is blocking the thread " + ti.getLockName());
                System.out.println("Thread state: " + ti.getThreadState());
            }

        }

    }
}
    //Even without putting things to sleep, this program will eventually produce a deadlock.
    //I used this website https://stackoverflow.com/questions/3342651/i-get-exception-when-using-thread-sleepx-or-wait
    //to learn more about TimeUnits. I used this to speed things up and catch the deadlock sooner.
    //Uncomment this to see the deadlocked thread info from the tmx factory.

//    public static void timeForADeadLock() {
//        try {
//            TimeUnit.MILLISECONDS.sleep(200);
//        } catch (InterruptedException i) {
//            System.out.println(i.getMessage());
//        }
//
//    }
//}

