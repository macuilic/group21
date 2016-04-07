package layout;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lotte on 29.03.2016.
 * Local Database to track the Loged In User Data
 *
 */
public class LocalDatabase {



        public static final String SP_NAME = "UserDetails";
        SharedPreferences localDatabase;

    /**
     * Constructor
     * @param context
     */
        public  LocalDatabase (Context context){
            localDatabase = context.getSharedPreferences(SP_NAME, 0);
        }

    /**
     * Stores the data from the logged in user
     *
     * @param contact contains the information of the user, pulled from the database
     */
        public void storeData(Contact contact){
            SharedPreferences.Editor spEditor = localDatabase.edit();
            spEditor.putString("FirstName", contact.firstname);
            spEditor.putString("Email", contact.email);
            spEditor.putString("LastName", contact.lastname);
            spEditor.putString("Username", contact.username);
            spEditor.putString("Password", contact.password);

            spEditor.commit();


        }

    /**
     * Returns the currently logged in user
     * @return the contact data of the user
     */
        public Contact getLoggedInUser(){

            String uname = localDatabase.getString("Username", "");
            String email = localDatabase.getString("Email", "");
            String firstname = localDatabase.getString("FirstName", "");
            String lastname = localDatabase.getString("LastName", "");
            String password = localDatabase.getString("Password", "");

            Contact storedContact = new Contact(firstname,lastname,uname, email, password);
            return storedContact;


        }

        public void setUserLoggedIn (boolean loggedIn){

            SharedPreferences.Editor spEditor = localDatabase.edit();
            spEditor.putBoolean("loggedIn", loggedIn);
            spEditor.commit();

        }

        public boolean getUserLoggedIn (){

            return localDatabase.getBoolean("loggedIn", false);

        }

    /**
     * used to log out the user
     * clears all data
     */
        public  void clearData(){
            SharedPreferences.Editor spEditor = localDatabase.edit();
            spEditor.clear();
            spEditor.commit();

        }

    }


