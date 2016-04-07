package layout;

/**
 * Created by Lotte on 29.03.2016.
 * Contact class containing user data.
 * Contact can be made two ways:
 *      By signing up, and providing the relevant information.
 *      By logging in in order to save data to the app.
 */
public class Contact {

    String firstname, lastname, username, email, password;

    public Contact(String firstname, String lastname, String username, String email, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public Contact(String username, String password){
        this.password = password;
        this.username = username;

    }
}
