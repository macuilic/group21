package layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnmilsom.ggleaguesv1local.MainActivity;
import com.example.johnmilsom.ggleaguesv1local.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragement_profile_login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragement_profile_login#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class fragement_profile_login extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button onLogin;

    // TODO: Rename and change types of parameters
    EditText etusername;
    EditText  etpassword;

    LocalDatabase localDatabase;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragement_profile_login.
     */
    // TODO: Rename and change types and number of parameters
    public static fragement_profile_login newInstance(String param1, String param2) {
        fragement_profile_login fragment = new fragement_profile_login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public fragement_profile_login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDatabase = new LocalDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =  inflater.inflate(R.layout.fragment_fragement_profile_login, container, false);

        etusername = (EditText)view.findViewById(R.id.TFlogin_uname);
        etpassword = (EditText)view.findViewById(R.id.TFlogin_pass);
        onLogin = (Button) view.findViewById(R.id.Blogin);
        onLogin.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void authenticate(Contact contact){

        ServerRequest serverRequest = new ServerRequest(getContext());
        serverRequest.fetchDataInBackground(contact, new GetUserCallBack() {
            @Override
            public void done(Contact returnedcontact) {

                if (returnedcontact == null) {
                    //show error message
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Username and Password don't match!");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                } else {
                    //Log User in
                    localDatabase.storeData(returnedcontact);
                    localDatabase.setUserLoggedIn(true);

                    Intent i = new Intent(getContext(), Profile.class);
                    startActivity(i);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();

        Contact contact = new Contact(username, password);

        authenticate(contact);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}

