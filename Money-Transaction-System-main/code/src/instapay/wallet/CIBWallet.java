package instapay.wallet;

public class CIBWallet extends WalletProvider{
    @Override
    public boolean verifyPhoneNumber(String phoneNum) {
        return true;
    }
}
