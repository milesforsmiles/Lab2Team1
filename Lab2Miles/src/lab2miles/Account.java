
package lab2miles;


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
through the threads I have running and catch the deadlock.
Course: IST 411
Author: Andrea Miles
Date Developed: 5/21/19
Last Date Changed: NA
Revision: NA
 */

public class Account {
    private String name;
    private int balance;
    
    
    Account(String name, int balance){
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }  

    @Override
    public String toString() {
        return "Account{" + "name=" + name + ", balance=" + balance + '}';
    }
    
}
