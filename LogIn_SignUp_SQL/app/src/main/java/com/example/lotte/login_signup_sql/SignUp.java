package com.example.lotte.login_signup_sql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lotte on 18.03.2016.
 */
public class SignUp extends Activity  {

    EditText etfirstname, etlastname, etusername, etemail, etpassword1, etpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etusername = (EditText)findViewById(R.id.TFuname);
        etemail = (EditText)findViewById(R.id.TFemail);
        etfirstname = (EditText)findViewById(R.id.TFfirstname);
        etlastname = (EditText)findViewById(R.id.TFlastname);
        etpassword1 = (EditText)findViewById(R.id.TFpass1);
        etpassword2 = (EditText)findViewById(R.id.TFpass2);


    }

    public void onLogInClick(View v){
        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }

    public void onSignUpClick (View v){
        String firstname = etfirstname.getText().toString();
        String lastname = etlastname.getText().toString();
        String username = etusername.getText().toString();
        String email = etemail.getText().toString();
        String pass1 = etpassword1.getText().toString();
        String pass2 = etpassword2.getText().toString();

        Contact contact;

        if (pass1.equals(pass2)){

            contact = new Contact(firstname,lastname,username,email,pass1);
            ServerRequest serverRequest = new ServerRequest(this);
            serverRequest.storeDataInBackground(contact, new GetUserCallBack() {
                @Override
                public void done(Contact returnedcontact) {
                    Intent i = new Intent(SignUp.this, MainActivity.class);
                    startActivity(i);

                }
            });
        }else{
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_LONG).show();

        }

    }
}
