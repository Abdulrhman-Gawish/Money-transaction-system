package instapay.user;

import instapay.Account.InstapayAccount;

import java.util.Objects;


public  class User {
    protected String mobileNumber;
    protected String username;
    protected String password;
    protected String instaPayHandle;
    protected InstapayAccount account;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getInstaPayHandle() {
        return instaPayHandle;
    }

    public InstapayAccount getAccount() {
        return account;
    }

    public User(String mobileNumber, String username, String password, String instapay_handle , InstapayAccount account) {
        this.account = account;
        this.mobileNumber = mobileNumber;
        this.username = username;
        this.password = password;
        this.instaPayHandle = instapay_handle;
    }

    protected String getUsername(){
        return username;
    }
    protected String getPassword(){
        return password;
    }
    protected String getPhoneNumber(){
        return mobileNumber;
    }
    protected String getInstapayHandle(){
        return instaPayHandle;
    }

    /*to be removed*/
    public void printUser(){
        System.out.println(this.username+" "+this.password+" "+this.mobileNumber+" "+this.instaPayHandle +"@instapay\n");
    }


    public boolean payBill(double money){
        return this.account.payBill(money);
    }

    public String getAccountType(){
        return this.account.getAccountType();
    }

    public boolean transferMoney(String transferType , double amount, String credentials,UserDatabase database){
        return this.account.transferMoney( transferType ,  amount,  credentials, database);
    }

    public double inquireBalance(){
            return this.account.inquireBalance(account.getCredentials());
    }
}
