package instapay.bill;

public class GasBill extends Bill{

    @Override
    public boolean verifyPaymentCode() {
        return true;
    }

}
