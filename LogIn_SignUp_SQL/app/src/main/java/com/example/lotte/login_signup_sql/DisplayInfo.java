package com.example.lotte.login_signup_sql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lotte on 18.03.2016.
 */
public class DisplayInfo extends Activity {

    LocalDatabase localDatabase;

    TextView tvusername, tvemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_info);

        tvusername = (TextView)findViewById(R.id.TVuname);
        tvemail = (TextView)findViewById(R.id.TVemail);

        localDatabase = new LocalDatabase(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(authenticate() == true){
            displayContactDetails();
        }else {
            Intent i = new Intent(DisplayInfo.this, MainActivity.class);
            startActivity(i);
        }
    }

    private boolean authenticate(){
        return localDatabase.getUserLoggedIn();
    }

    private  void displayContactDetails(){
        Contact contact = localDatabase.getLoggedInUser();
        tvusername.setText(contact.username);
        tvemail.setText(contact.email);
    }


    public void onLogOutClick (View v){

        localDatabase.clearData();
        localDatabase.setUserLoggedIn(false);

        Intent i = new Intent(DisplayInfo.this, MainActivity.class);
        startActivity(i);

    }
}
