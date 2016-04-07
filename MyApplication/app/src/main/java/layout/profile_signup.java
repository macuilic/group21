package layout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johnmilsom.ggleaguesv1local.MainActivity;
import com.example.johnmilsom.ggleaguesv1local.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile_signup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile_signup#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * Fragment for Sign Up
 */


public class profile_signup extends Fragment implements View.OnClickListener{


    EditText etfirstname, etlastname, etusername, etemail, etpassword1, etpassword2;
    Button signup;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public profile_signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile_signup.
     */

    public static profile_signup newInstance(String param1, String param2) {
        profile_signup fragment = new profile_signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * method called when fragment is created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_signup, container, false);

        //fetch user data from text fields
        etusername = (EditText)view.findViewById(R.id.TFsignup_uname);
        etemail = (EditText)view.findViewById(R.id.TFsignup_email);
        etfirstname = (EditText)view.findViewById(R.id.TFsignup_fname);
        etlastname = (EditText)view.findViewById(R.id.TFsignup_lname);
        etpassword1 = (EditText)view.findViewById(R.id.TFsignup_pass1);
        etpassword2 = (EditText)view.findViewById(R.id.TFsignup_pass2);

        //set listener for sign up button
        signup = (Button) view.findViewById(R.id.Bsignup);
        signup.setOnClickListener(this);

        return view;
    }


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

    /**
     * Listener for the sign up button
     * @param v
     */
    public void onClick(View v) {
        String firstname = etfirstname.getText().toString();
        String lastname = etlastname.getText().toString();
        String username = etusername.getText().toString();
        String email = etemail.getText().toString();
        String pass1 = etpassword1.getText().toString();
        String pass2 = etpassword2.getText().toString();

        Contact contact;
        //check if passwords match
        if (pass1.equals(pass2)){

            contact = new Contact(firstname,lastname,username,email,pass1);
            ServerRequest serverRequest = new ServerRequest(getContext());
            serverRequest.storeDataInBackground(contact, new GetUserCallBack() {
                @Override
                public void done(Contact returnedcontact) {
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);

                }
            });
        }else{
            Toast.makeText(getContext(), "Passwords don't match!", Toast.LENGTH_LONG).show();

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
