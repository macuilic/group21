package layout;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by conor on 06/04/2016.
 */
public class Tour_Server_Request {

    public String tour;
    public String GetApi (Context context) {

        tour = "--";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                DataListener dataListener = null;
                try {

                    tour = null;
                    JSONObject jsonResponse = new JSONObject(response);

                    tour = jsonResponse.getString("tour_game");

                    dataListener.onDataReceived(tour);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ApiConnector apiConnector = new ApiConnector(responseListener);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(apiConnector);

        return tour;
    }

    public interface DataListener {

        void onDataReceived(String tour);
    }
    
    public class ApiConnector extends StringRequest {

        public static final int CONNECTION_TIMEOUT = 15000;
        public static final String SERVER_ADDRESS = "http://ggleagues.site88.net/Fetch_Tour_Data.php";
        private Map<String, String> params;

        public ApiConnector(Response.Listener<String> listener) {
            super(Request.Method.POST, SERVER_ADDRESS, listener, null);

        }


    }
}
