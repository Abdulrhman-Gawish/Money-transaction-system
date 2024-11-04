package instapay.bill;

import java.util.Random;

public abstract class Bill {
    protected String e_paymentCode;

    public String getE_paymentCode() {
        return e_paymentCode;
    }
    public double getBillPrice(){
        int min = 100, max = 1000;
        Random random = new Random();
        return random.nextDouble(max - 100) + min;
    }


    public abstract boolean verifyPaymentCode();
}
