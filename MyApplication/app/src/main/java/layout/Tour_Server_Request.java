package layout;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by conor on 06/04/2016.
 *      Class requests a connection from the server using volley. Parses a JSONObject and returns
 *      a string containing the information from the PHP file.
 *          - In this case, a list of the tournament games from the server.
 *      Needs to be passed the context of the current view.
 *
 *      Class works in the background using ASyncTask, and needs to know the class that
 *      created it.
 *      Volley creates a request to the server that needs to be added to a queue.
 *          - This follows the volley standard.
 *
 *      @GetApi()
 *      @param:     Context:        Pass the current view context by calling "this" /
 *                                  "getContext()".
 *                  DataListener:   An interface that responds only when the server
 *                                  request is complete. In this case, create
 *                                  "new Tour_Server_Request.DataListener()" and
 *                                  pass it to the constructor.
 *
 *      @return:    A listener interface that returns the data once it becomes available.
 *                  In the form of a Tournament_Data class, containing all of the
 *                  relevant data pertaining to Tournaments.
 */
public class Tour_Server_Request {

    private Tournament_Data[] tour_Data;
    public void GetApi (final Context context, final DataListener dataListener) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    int tId, tNumPlayers, tMaxP, tMinP, tFee;
                    String tName, tGame, tRegion, tTimestamp;
                    JSONObject json = null;

                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    tour_Data = new Tournament_Data[jsonArray.length()];

                    for(int i = 0; i < length; i++) {
                        json = jsonArray.getJSONObject(i);
                        tId = json.getInt("id");
                        tName = json.getString("tour_name");
                        tGame = json.getString("tour_game");
                        tRegion = json.getString("tour_region");
                        tTimestamp = json.getString("tour_start_timestamp");
                        tNumPlayers = json.getInt("num_players");
                        tMaxP = json.getInt("max_p");
                        tMinP = json.getInt("min_p");
                        tFee = json.getInt("entry_fee");

                        tour_Data[i] = new Tournament_Data(tId, tName, tGame, tRegion, tTimestamp, tNumPlayers, tMaxP, tMinP, tFee);


                    }
                    dataListener.onDataReceived(tour_Data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ApiConnector apiConnector = new ApiConnector(responseListener);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(apiConnector);
    }

    public interface DataListener {

        void onDataReceived(Tournament_Data[] tournament_data);
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
