package lab2team1;

/* 
Project: Deadlock
Purpose Details: Create an application that makes an
intentional deadlock between threads upon runtime.
In this example, both Emma and John are trying to add
money to each other's accounts at the same exact time.
This will cause a deadlock because the accounts are 
resources. John has his account open and tries to open Emma's.
However, Emma is simultaneously in her account at the same time John is
trying to access her account which causes a deadlock because 
the account can only be opened one at a time.
Course: IST 411
Author: Team 3
Date Developed: 5/21/19
Last Date Changed:
Revision:
 */

public class Lab2Team1 {

    /*
    Threads allow multiple processes to run simultaneously.
    They execute independently of each other and are good 
    for helping your program to multitask. 
    This program causes a deadlock where a thread becomes locked
    because it's waiting for resources from another thread.
     */
    
    public static void main(String[] args) {
        System.out.println("Look I made a Thread!");
        Account emma = new Account("Emma", 300);
        Account john = new Account("John", 200);
       
        Runnable addToAccountEmma = new Runnable() {
            public void run() {
                synchronized (emma) {
                    try {
                        //Waiting to give the first thread a head start.
                        System.out.println("Emma: I'm in my account at the same time John is!");
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        ex.getMessage();
                    }
                    //This block will never be reached because John's account is already open
                    synchronized (john) {
                        System.out.println("Emma is trying to access John's account!");
                        john.setBalance(100);
                        System.out.println(john.getBalance());
                    }

                }
            }
        };
        
           Runnable addToAccountJohn = new Runnable() {
            public void run() {
                synchronized (john) {
                    System.out.println("John: I'm in my account at the same time Emma is!");
                    System.out.println("I won't be able to access her bank account while she's using it.");
                    synchronized (emma) {
                        System.out.println("Need Emma");
                        emma.setBalance(500);
                        System.out.println(emma.getBalance());
                    }

                }
            }
        };

        Thread t1 = new Thread(addToAccountEmma);
        Thread t2 = new Thread(addToAccountJohn);

        t1.start();
        t2.start();

    }

}
