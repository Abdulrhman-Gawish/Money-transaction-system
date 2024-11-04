package instapay.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UserDatabase {
    private ArrayList<User> users_array_list = new ArrayList<>();

    public ArrayList<User> getUsers_array_list() {
        return users_array_list;
    }
    /*
    Hashmap for users' username and password
    this hashmap will be filled from  users_authentications.txt file
    */
//    private HashMap<String, ArrayList<String>> users_list= new HashMap<>();
    // file that contains users data
//    private final File users_file = new File("users_authentications.txt");

    /* Description:
     *   Add a new user to the users file after doing all verifications.
     *   Uses fileWriter to write the new user to users file
     */
    public void addUser(User user) throws IOException {
//
//        // file writer is created with the users text file in append mode
//        FileWriter writer = new FileWriter(users_file,true);
//        /*
//         * add username, password, instapay handle and phone number respectively
//         * in a new line in users file seperated with space
//         */
//        writer.write(user.getUsername()+" "+user.getPassword()+" "+user.getInstapayHandle()+" "+user.getPhoneNumber()+"\n");
//        writer.close();

        users_array_list.add(user);
        for (User u: users_array_list) {
            System.out.println();
        }
    }

    /* Description:
     *   find if this username already exists and return true if it does.
     *   used when user in creating a new username.
     */
    public boolean searchUsername(String username){
        // for using files
//        return users_list.containsKey(username);

//        using array list
        for (User user: users_array_list) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    /* Description:
     *   check if this phone number already exists.
     *   return true if it exists in the database.
     */
    public boolean searchPhoneNumber(String number){

        for (User user: users_array_list) {
            if (user.getPhoneNumber().equals(number))
                return true;
        }
        return false;
    }

    /* Description:
     *   check if this handle already exists.
     *   return true if it exists in the database.
     */
    public boolean searchHandle(String handle){

        for (User user: users_array_list) {
            if (user.getInstapayHandle().equals(handle))
                return true;
        }
        return false;
    }

    public boolean validateUser(String username, String password){
        for (User user:users_array_list) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password))
                return true;
        }
        return false;
    }

}
