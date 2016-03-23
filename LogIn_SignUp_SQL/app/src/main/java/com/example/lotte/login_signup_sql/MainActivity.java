package com.example.lotte.login_signup_sql;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText etusername, etpassword;
    LocalDatabase localDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etusername = (EditText)findViewById(R.id.TFusername);
        etpassword = (EditText)findViewById(R.id.TFpassword);

        localDatabase = new LocalDatabase(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onSignUpClick(View v){

        Intent i = new Intent(MainActivity.this, SignUp.class);
        startActivity(i);
    }

    public void onLogInClick(View v){

        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();

        Contact contact = new Contact(username, password);
        Log.i("custom_check", contact.username);
        authenticate(contact);
    }

    private void authenticate(Contact contact){

        ServerRequest serverRequest = new ServerRequest(MainActivity.this);
        serverRequest.fetchDataInBackground(contact, new GetUserCallBack() {
            @Override
            public void done(Contact returnedcontact) {

                if(returnedcontact == null){
                    //show error message
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Username and Password don't match!");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }else{
                    //Log User in
                    localDatabase.storeData(returnedcontact);
                    localDatabase.setUserLoggedIn(true);

                    Intent i = new Intent(MainActivity.this, DisplayInfo.class);
                    startActivity(i);

                }
            }
        });
    }

}
