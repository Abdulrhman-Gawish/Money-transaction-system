package instapay.wallet;

public class VodafoneCash extends WalletProvider{
    @Override
    public boolean verifyPhoneNumber(String phoneNum) {
        return true;
    }
}
