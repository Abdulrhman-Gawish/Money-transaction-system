package instapay.wallet;

public class Fawry extends WalletProvider{
    @Override
    public boolean verifyPhoneNumber(String phoneNum) {
        return true;
    }
}
