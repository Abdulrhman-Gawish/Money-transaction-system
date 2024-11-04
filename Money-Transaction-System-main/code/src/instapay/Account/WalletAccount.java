package instapay.Account;
import instapay.wallet.WalletProvider;
import instapay.user.UserDatabase;

public class WalletAccount extends InstapayAccount {
    private WalletProvider walletProvider;

    public WalletAccount( String phoneNumber,String handle, WalletProvider walletProvider) {
        super( phoneNumber, handle);
        this.walletProvider = walletProvider;
    }

    public double inquireBalance(String credentials){
        return walletProvider.getBalance(this.phoneNumber);
    }

    public void addMoney(double amount) {
        this.walletProvider.releaseMoney(amount, this.phoneNumber);
    }

    public boolean deductMoney(double amount) {
        return this.walletProvider.deductBalance(this.phoneNumber,amount);
    }

    @Override
    public boolean transferMoney(String type, double amount, String credentials, UserDatabase database) {
        return switch (type) {
            case "Instapay" -> transferToInstapay(credentials, amount, database);
            case "Wallet" -> transferToWallet(credentials, amount);
            default -> false;
        };
    }

    public boolean payBill(double money){
        return deductMoney(money);
    }

    public String getAccountType(){
        return "Wallet";
    }


    public String getCredentials(){
        return phoneNumber;
    }

}
