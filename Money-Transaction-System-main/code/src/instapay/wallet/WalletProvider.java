package instapay.wallet;

import java.util.Random;

public abstract class WalletProvider {
    public abstract boolean verifyPhoneNumber(String phoneNum);
    public double getBalance(String phoneNumber){
        int min = 100000, max = 999999;
        Random random = new Random();
        return random.nextDouble(max - 100000) + min;
    }
    public boolean deductBalance(String phoneNumber,double amount){
        return true;
    }

    public void releaseMoney(double amount, String phoneNum) {
        return;
    }


    public boolean walletExists(String phoneNum) {
        return true;
    }
}
