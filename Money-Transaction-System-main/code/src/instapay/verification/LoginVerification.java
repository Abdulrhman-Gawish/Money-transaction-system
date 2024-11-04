package instapay.verification;

import instapay.user.UserDatabase;

import java.util.Scanner;


public class LoginVerification {

    public boolean confirmCredentials(UserDatabase database,String username, String password){
        return database.validateUser(username, password);
    }
}
