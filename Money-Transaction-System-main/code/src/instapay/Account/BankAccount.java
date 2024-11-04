package instapay.Account;

import instapay.Bank.AlexBank;
import instapay.Bank.Bank;
import instapay.Bank.QNB;
import instapay.user.User;
import instapay.user.UserDatabase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class BankAccount extends InstapayAccount {

    private Bank bank;
    private String accountNumber;
    private Scanner input = new Scanner(System.in);

    public BankAccount(String phoneNumber, String handle,String serial ,Bank bank) {
        super(phoneNumber, handle);
        this.bank = bank;
        this.accountNumber = serial;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public Bank getBank() {
        return bank;
    }
    @Override
    public double inquireBalance(String credentials) {
        int min = 100000, max = 999999;
        Random random = new Random();
        return random.nextDouble(max - 100000) + min;
    }



    public void addMoney(double amount) {
        this.bank.addBalance(amount);
    }


    public boolean deductMoney(double amount) {
        return this.bank.deductBalance(this.accountNumber,amount);
    }

    @Override
    public boolean transferMoney(String type, double amount, String credentials, UserDatabase database) {
        return switch (type) {
            case "Instapay" -> transferToInstapay(credentials, amount, database);
            case "Wallet" -> transferToWallet(credentials, amount);
            case "Bank" -> transferToBankAccount(credentials, amount, "QNB");
            default -> false;
        };
    }

    public boolean payBill(double money){
        return deductMoney(money);
    }

    public boolean transferToBankAccount(String accountNumber, double amount, String bankName) {
        Bank transferBank = null;
        switch (bankName) {
            case "QNB" -> transferBank = new QNB();
            case "Alex" -> transferBank = new AlexBank();
            default -> {
                return false;
            }
        }
        if(!transferBank.accountExists(accountNumber)){
            return false;
        }
        if(this.deductMoney(amount)){
            transferBank.addBalance(amount);
            return true;
        }
        return false;
    }

    public String getAccountType(){
        return "Bank";
    }

    public  String getCredentials(){
        return bank.getSerial();
    }
}
