package DatabaseRequestTournaments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import layout.Tournaments;

/**
 * Created by Lotte on 23.03.2016.
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
        //TO DO: first just implement fetchData as we just want to implement a searchrequest first
            //later we can add storeData so you could add tournaments in the app
        /**
         *     public void storeDataInBackground(Tournaments tournament , GetUserCallBack callback)
        {
                 progressDialog.show();
                 new StoreDataAsyncTask(tournament, callback).execute();


     }
     *
     */


        public void fetchDataInBackground(Tournament tournament , GetUserCallBack callback)
        {
            progressDialog.show();
            new FetchDataAsyncTask(tournament, callback).execute();


        }

       //TO DO: class storeData was here, add if necessary

        public class FetchDataAsyncTask extends AsyncTask<Void , Void , Tournament>
        {
            Tournament tournaments;
            GetUserCallBack callback;

            public FetchDataAsyncTask(Tournament tournaments , GetUserCallBack callback)
            {
                this.tournaments = tournaments;
                this.callback=callback;
            }


            @Override
            /**
             *  this should be a search request for the filters "game" and "region"
             *  TO DO: implement if case

             */
            protected Tournament doInBackground(Void... voids) {


                HttpParams httpRequestParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpRequestParams , CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpRequestParams , CONNECTION_TIMEOUT);

                HttpClient client = new DefaultHttpClient(httpRequestParams);
                HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchAll.php");

                Tournament returnedTournament = null;
                try {

                    HttpResponse httpResponse = client.execute(post);

                    HttpEntity entity = httpResponse.getEntity();
                    String result = EntityUtils.toString(entity);

                    Log.i("test", result);
                    JSONArray jsonArray = new JSONArray(result);

                    //TO DO: either give back JsonArray or Array of Tournament objects (probably better)
                    /*JSONArray jsonArray = new JSONArray(result);
                    returnedTournament = null;


                    if(jsonArray.length() == 0)
                    {
                        returnedTournament = null;

                    }
                    else
                    {
                        String name,game,region;
                        name = null;
                        game = null;
                        region=null;

                        for (int i=0; i<jsonArray.length(); ++i){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if(jsonObject.has("name"))
                                name = jsonObject.getString("name");
                            if(jsonObject.has("game"))
                                game = jsonObject.getString(("game"));
                            if(jsonObject.has("region"))
                                region =jsonObject.getString("region");

                            returnedTournament = new Tournament(name, game , region);
                        }





                    }

                     */


                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                return returnedTournament;
            }
            @Override
            protected void onPostExecute(Tournament returnedTournament) {
                progressDialog.dismiss();
                callback.done(returnedTournament);
                super.onPostExecute(returnedTournament);
            }

        }
    }

