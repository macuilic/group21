package layout;

/**
 * Created by Lotte on 29.03.2016.
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
