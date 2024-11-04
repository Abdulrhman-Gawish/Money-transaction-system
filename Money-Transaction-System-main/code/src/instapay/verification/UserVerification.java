package instapay.verification;

import instapay.Account.BankAccount;
import instapay.Account.InstapayAccount;
import instapay.Account.WalletAccount;
import instapay.Bank.AlexBank;
import instapay.Bank.Bank;
import instapay.Bank.QNB;
import instapay.user.User;
import instapay.user.UserDatabase;
import instapay.wallet.CIBWallet;
import instapay.wallet.Fawry;
import instapay.wallet.VodafoneCash;
import instapay.wallet.WalletProvider;

import java.util.Random;
import java.util.Scanner;

public class UserVerification {
    private String username;
    private String password;
    private String phoneNumber;
    private String instapayHandle;
    protected Scanner input = new Scanner(System.in);

    public String confirmUsername(UserDatabase database) {
        while (true) {
            System.out.println("please enter your username:");
            System.out.println("NOTE: This username must be unique, username is permanent and can't be Changed");
            username = input.nextLine();
            if (database.searchUsername(username)) {
                System.out.println("This username is already taken");
            } else {
                System.out.println("This username is valid :)");
                return username;
            }
        }
    }

    public String confirmPassword() {
        //        regex for a strong password with min length 8 and max length 20 that strong password criteria
        String password_regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-_+=()])(?=\\S+$).{8,20}$";
        while (true) {
            System.out.println("please enter password\n");
            System.out.println("NOTE: Password must be at least 8 characters and strong (contains at least: 1 lowercase letter, 1 uppercase letter,1 number and 1 symbol)\n");
            password = input.nextLine();
            if (password.matches(password_regex)) {
                System.out.println("Password is Valid :)");
                return password;
            } else {
                System.out.println("this password is not strong enough");
            }
        }
    }

    public String confirmPhone(UserDatabase database) {
        String phone_regex = "^01[0125]\\d{8}$";
        while (true) {
            System.out.println("Please enter your phone number");
            phoneNumber = input.nextLine();

            /*
            if the input number matches the regex
            and the number is not present in the database
            then this number is valid for a user.
            */
            if (phoneNumber.matches(phone_regex) && !database.searchPhoneNumber(phoneNumber)) {
                System.out.println("Phone Number is Valid! :)");
                return phoneNumber;
            } else {
                System.out.println("This is phone number is not valid\n");
            }
        }
    }

    public String confirmHandle(UserDatabase database) {
        while (true) {
            System.out.println("please enter your new InstaPay Handle");
            System.out.println("your account will end with @instapay and start with this handle (example: john123@instapay)");
            instapayHandle = input.nextLine();
            if (database.searchHandle(instapayHandle)) {
                System.out.println("This username is already taken :(");
            } else {
                System.out.println("This handle is valid! :)");
                return instapayHandle;
            }
        }
    }

    public boolean verifyOTP(String phoneNumber) {
        Random random = new Random();
        int max = 999999, min = 100000;
        int otp = random.nextInt(max - min) + min;
        System.out.println(otp);
        System.out.println("Code has been sent successfully to your phone number!");
        System.out.println("Enter the code: ");
        int in = input.nextInt();

        while (in != otp) {
            System.out.println("Code isn't right! a new verification code has been sent to your device.");
            otp = random.nextInt(max - min) + min;
            System.out.println(otp);
            System.out.println("Enter the code: ");
            in = input.nextInt();
        }
        System.out.println("Account verified successfully! :)");

        return true;
    }

    public User makeUserType() {
        User user = null;
        InstapayAccount account = null;
        System.out.println("1.add a bank\n2.add a wallet");
        int choice = input.nextInt();
        while (choice != 1 && choice != 2){
            System.out.println("Invalid choice, try again!");
            choice = input.nextInt();
        }
        input.nextLine();
        switch (choice) {
            case 1 -> {
                Bank bank = null;
                while (true) {
                    System.out.println("Choose your bank: ");
                    System.out.println("1-AlexBank\n2-QNB");
                    int bankChoice = input.nextInt();
                    if (bankChoice == 1) {
                        bank = new AlexBank();
                        break;
                    } else if (bankChoice == 2) {
                        bank = new QNB();
                        break;
                    } else {
                        System.out.println("Invalid bank choice");
                    }
                }
                input.nextLine();
                System.out.println("Enter Bank Account Number: ");
                String serial = input.nextLine();
                while (!bank.verifySerial(serial)) {
                    System.out.println("Invalid Serial");
                    System.out.println("Enter Bank Account Number: ");
                    serial = input.nextLine();
                }

                account = new BankAccount(phoneNumber,instapayHandle, serial, bank);
            }
            // bank account verification
            // check with api
            case 2 -> {
                WalletProvider wallet = null;
                while (true) {
                    System.out.println("Choose your wallet provider: ");
                    System.out.println("1-Vodafone Cash\n2-Fawry\n3-CIB Wallet");
                    int walletChoice = input.nextInt();
                    input.nextLine();
                    if (walletChoice == 1) {
                        wallet = new VodafoneCash();
                        break;
                    } else if (walletChoice == 2) {
                        wallet = new Fawry();
                        break;
                    } else if (walletChoice == 3) {
                        wallet = new CIBWallet();
                        break;
                    } else {
                        System.out.println("Invalid wallet provider");
                    }
                }

                account = new WalletAccount(phoneNumber, instapayHandle, wallet);
                break;
            }
            // Ewallet verification
            //check with api
        }
        user = new User(phoneNumber, username, password, instapayHandle, account);

        System.out.println("Account Created Successfully!");
        System.out.println("this is your new instapay handle: "+instapayHandle+"@instapay\n");
        return user;

    }
}


