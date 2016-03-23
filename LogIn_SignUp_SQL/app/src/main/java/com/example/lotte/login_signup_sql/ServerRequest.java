package com.example.lotte.login_signup_sql;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Lotte on 18.03.2016.
 */
public class ServerRequest {


    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final String SERVER_ADDRESS = "http://ggleagues.site88.net/";

    public ServerRequest(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");

    }

    public void storeDataInBackground(Contact contact , GetUserCallBack callback)
    {
        progressDialog.show();
        new StoreDataAsyncTask(contact, callback).execute();


    }

    public void fetchDataInBackground(Contact contact , GetUserCallBack callback)
    {
        progressDialog.show();
        new FetchDataAsyncTask(contact, callback).execute();


    }

    public class StoreDataAsyncTask extends AsyncTask<Void , Void , Void>
    {
        Contact contact;
        GetUserCallBack callback;

        public StoreDataAsyncTask(Contact contact , GetUserCallBack callback)
        {
            this.contact = contact;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<NameValuePair> data_to_send = new ArrayList<>();
            data_to_send.add(new BasicNameValuePair("FirstName" , contact.firstname));
            data_to_send.add(new BasicNameValuePair("LastName", contact.lastname));
            data_to_send.add(new BasicNameValuePair("Email" , contact.email));
            data_to_send.add(new BasicNameValuePair("Username" , contact.username));
            data_to_send.add(new BasicNameValuePair("Password" , contact.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams , CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(data_to_send));
                client.execute(post);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            callback.done(null);

            super.onPostExecute(aVoid);
        }
    }

    public class FetchDataAsyncTask extends AsyncTask<Void , Void , Contact>
    {
        Contact contact;
        GetUserCallBack callback;

        public FetchDataAsyncTask(Contact contact , GetUserCallBack callback)
        {
            this.contact = contact;
            this.callback=callback;
        }


        @Override
        protected Contact doInBackground(Void... voids) {
            ArrayList<NameValuePair> data_to_send = new ArrayList<>();
            data_to_send.add(new BasicNameValuePair("Username" , contact.username));
            data_to_send.add(new BasicNameValuePair("Password" , contact.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams , CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams , CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            Contact returnedContact = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(data_to_send));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                Log.i("test", result);

                JSONObject jsonObject = new JSONObject(result);
                returnedContact = null;

                if(jsonObject.length() == 0)
                {
                    returnedContact = null;

                }
                else
                {
                    String firstname,lastname,email;
                    firstname = null;
                    lastname = null;
                    email=null;

                    if(jsonObject.has("firstname"))
                        firstname = jsonObject.getString("firstname");
                    if(jsonObject.has("lastname"))
                        lastname = jsonObject.getString(("lastname"));
                    if(jsonObject.has("email"))
                        email =jsonObject.getString("email");

                    returnedContact = new Contact(firstname, lastname , email , contact.username , contact.password);

                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return returnedContact;
        }
        @Override
        protected void onPostExecute(Contact returnedContact) {
            progressDialog.dismiss();
            callback.done(returnedContact);
            super.onPostExecute(returnedContact);
        }

    }
}