package instapay.verification;

import instapay.Account.InstapayAccount;
import instapay.bill.Bill;
import instapay.bill.ElectricityBill;
import instapay.bill.GasBill;
import instapay.bill.WaterBill;
import instapay.user.User;
import instapay.user.UserDatabase;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ProgramInterface {
    private UserDatabase database = new UserDatabase();
    private User activeUser = null;



    public void programRun() throws IOException {
        System.out.println("\t\tWelcome to instapay\n");
        Scanner input = new Scanner(System.in);

        int choice;
        while(true) {
            System.out.println("enter 1 for login\nenter 2 for register");
            choice = input.nextInt();
            if (choice==1 || choice==2)
                break;
            else
                System.out.println("Wrong choice choice");
        }
        switch (choice) {
            case 1 -> this.login();
            case 2 -> this.register();
        }
        while(true) {
            this.chooseOperation();
        }
    }
    private void register() throws IOException {

        String username, password, phone_number, email, instapayHandle;
        ProgramInterface programInterface = new ProgramInterface();


        UserVerification verification = new UserVerification();
        //the process of verifying each user attribute
        username = verification.confirmUsername(database);

        password = verification.confirmPassword();


        instapayHandle = verification.confirmHandle(database);

        phone_number = verification.confirmPhone(database);
        verification.verifyOTP(phone_number);

        User user = verification.makeUserType();

        activeUser = user;
        database.addUser(user);
    }

    private void login() {
        LoginVerification loginVerification = new LoginVerification();
        System.out.println();
        while (true){
            Scanner input = new Scanner(System.in);
            System.out.println("please enter your username: ");
            String username = input.nextLine();
            System.out.println("please enter your password: ");
            String password = input.nextLine();
            if (loginVerification.confirmCredentials(database, username, password)){
                System.out.println("Log in successful");
                break;
            }
            else {
                System.out.println("Username and password don't match\ntry again");
            }

        }
    }

    private void chooseOperation(){
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("1.Transfer money\n2.Inquire balance\n3.bill payment\n4.Exit");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> {
                    if (Objects.equals(activeUser.getAccountType(), "Bank")) {
                        bankAccountTransferMenu();
                    } else if (activeUser.getAccountType() == "Wallet") {
                        walletTransferMenu();
                    }
                }
                case 2 -> System.out.println("Your balance is: " + activeUser.inquireBalance());
                case 3 -> billMenu();
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void billMenu(){
        Bill bill = null;
        int choice;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("1.Gas\n2.Electricity\n3.Water");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 3)
                break;
            else
                System.out.println("Invalid choice");
        }
        input.nextLine();
        String paymentCode;
        switch (choice){
            case 1:
                bill = new GasBill();
                break;
            case 2:
                bill = new ElectricityBill();
                break;
            case 3:
                bill = new WaterBill();
        }
        while (true){
            System.out.println("Enter e-payment code");
            paymentCode = input.nextLine();
            if(!bill.verifyPaymentCode())
                System.out.println("This e-payment code doesn't exist");
            else
                break;
        }
        System.out.println("Your bill: "+bill.getBillPrice());
        if (activeUser.payBill(bill.getBillPrice()))
            System.out.println("Bill is paid successfully!!");
        else
            System.out.println("Something went wrong with you bill payment");
    }

    public void bankAccountTransferMenu(){
        Scanner transferType = new Scanner(System.in);
        System.out.println("1.Transfer to instapay account\n2.Transfer to bank account\n3.Transfer to Wallet:");
        int choice = transferType.nextInt();
        switch (choice){
            case 1:
                System.out.println("Enter the instapay handle of the account you want to transfer to: ");
                Scanner instapayHandle = new Scanner(System.in);
                String handle = instapayHandle.nextLine();
                System.out.println("Enter the amount you want to transfer: ");
                Scanner amount = new Scanner(System.in);
                double transferAmount = amount.nextDouble();
                if(activeUser.transferMoney("Instapay",transferAmount,handle, database)){
                    System.out.println("Transfer successful");
                }
                else {
                    System.out.println("Transfer failed");
                }
                break;
            case 2:
                System.out.println("Enter the bank account number you want to transfer to: ");
                Scanner bankAccountNumber = new Scanner(System.in);
                String accountNumber = bankAccountNumber.nextLine();
                System.out.println("Enter the amount you want to transfer: ");
                Scanner amount2 = new Scanner(System.in);
                double transferAmount2 = amount2.nextDouble();
                if(activeUser.transferMoney("Bank",transferAmount2,accountNumber, database)){
                    System.out.println("Transfer successful");
                }
                else {
                    System.out.println("Transfer failed");
                };
                break;
            case 3:
                System.out.println("Enter the phone number you want to transfer to: ");
                Scanner phoneNumber = new Scanner(System.in);
                String phoneNum = phoneNumber.nextLine();
                System.out.println("Enter the amount you want to transfer: ");
                Scanner amount3 = new Scanner(System.in);
                double transferAmount3 = amount3.nextDouble();
                if(activeUser.transferMoney("Wallet",transferAmount3,phoneNum, database)){
                    System.out.println("Transfer successful");
                }
                else {
                    System.out.println("Transfer failed");
                }
                break;

        }
    }

    public void walletTransferMenu(){
        Scanner transferType = new Scanner(System.in);
        System.out.println("1.Transfer to instapay account\n2.Transfer to Wallet");
        int choice = transferType.nextInt();
        switch (choice){
            case 1:
                System.out.println("Enter the instapay handle of the account you want to transfer to: ");
                Scanner instapayHandle = new Scanner(System.in);
                String handle = instapayHandle.nextLine();
                System.out.println("Enter the amount you want to transfer: ");
                Scanner amount = new Scanner(System.in);
                double transferAmount = amount.nextDouble();
                if(activeUser.transferMoney("Instapay",transferAmount,handle, database)){
                    System.out.println("Transfer successful");
                }
                else {
                    System.out.println("Transfer failed");
                }
                break;
            case 2:
                System.out.println("Enter the phone number you want to transfer to: ");
                Scanner phoneNumber = new Scanner(System.in);
                String phoneNum = phoneNumber.nextLine();
                System.out.println("Enter the amount you want to transfer: ");
                Scanner amount3 = new Scanner(System.in);
                double transferAmount3 = amount3.nextDouble();
                if(activeUser.transferMoney("Wallet",transferAmount3,phoneNum, database)){
                    System.out.println("Transfer successful");
                }
                else {
                    System.out.println("Transfer failed");
                };
                break;

        }
    }

}
