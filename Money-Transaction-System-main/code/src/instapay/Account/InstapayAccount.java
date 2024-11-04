package instapay.Account;
import instapay.user.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import instapay.wallet.*;


public abstract class InstapayAccount {


    protected String phoneNumber;
    protected String handle;
    public InstapayAccount(String phoneNumber,String handle){
        this.phoneNumber = phoneNumber;
        this.handle = handle;
    }

    /*return random balance value until real API is implemented*/
    public abstract double inquireBalance(String credentials);
    public abstract boolean payBill(double money);

    public abstract String getCredentials();
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean transferToInstapay(String instapayHandle, double amount, UserDatabase database){
        ArrayList<User> users = database.getUsers_array_list();
        if (users==null)
            return false;

        User transferedTo = null;
        for (User user:users) {
            if (Objects.equals(user.getAccount().handle, instapayHandle)){
                transferedTo = user;
                break;
            }
        }
        if (transferedTo==null)
            return false;
        if(this.deductMoney(amount)){
            transferedTo.getAccount().addMoney(amount);
            return true;
        }
        return false;
    }
    public boolean transferToWallet(String phoneNum,double amount){
        Scanner input = new Scanner(System.in);
        if(!this.deductMoney(amount)) {
            return false;
        }
        WalletProvider walletProvider = null;
        System.out.println("choose wallet provider");
        System.out.println("1) CIB Wallet");
        System.out.println("2) Fawry");
        System.out.println("3) Vodafone Cash");
        int choice = 0;
        choice = input.nextInt();
        while (choice < 1 || choice > 3) {
            System.out.println("invalid choice");
            System.out.println("Enter your choice");
            choice = input.nextInt();
        }
        input.nextLine();
        switch (choice) {
            case 1 -> {
                walletProvider = new CIBWallet();
            }
            case 2 -> {
                walletProvider  = new Fawry();
            }
            case 3 -> {
                walletProvider  = new VodafoneCash();
            }
        }
        if(!walletProvider.walletExists(phoneNum)){
            return false;
        }
        walletProvider.releaseMoney(amount,phoneNum);
        return true;
    }

    public abstract void addMoney(double amount);

    public abstract boolean deductMoney(double amount);

    public abstract boolean transferMoney(String type, double amount, String credentials, UserDatabase database);

    public abstract String getAccountType();
}

