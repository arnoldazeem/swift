package com.example.adabooazeem.swift;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Login_Fragment extends Fragment implements OnClickListener {
	private static View view;

	private static EditText emailid, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;
	private static CheckBox show_hide_password;
	private static LinearLayout loginLayout;
	private static Animation shakeAnimation;
	private static FragmentManager fragmentManager;

    private boolean loggedIn = false;

    RequestQueue requestQueue;
    String finals;
    String getEmailId,getPassword;
    DelayedProgressDialog progressDialog;

	public Login_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.login, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initiate Views
	private void initViews() {


		fragmentManager = getActivity().getSupportFragmentManager();
        //session = new SessionManager(getActivity().getApplicationContext());

		emailid = (EditText) view.findViewById(R.id.input_email);
		password = (EditText) view.findViewById(R.id.input_password);

		loginButton = (Button) view.findViewById(R.id.loginBtn);
		forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
		signUp = (TextView) view.findViewById(R.id.link_signup);
		show_hide_password = (CheckBox) view
				.findViewById(R.id.show_hide_password);

		loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);

        //progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleSmall);

         progressDialog = new DelayedProgressDialog();

	}



	// Set Listeners
	private void setListeners() {
		loginButton.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		signUp.setOnClickListener(this);

		// Set check listener over checkbox for showing and hiding password
		show_hide_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
							boolean isChecked) {

						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
																			// checkbox
																			// text
							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod
									.getInstance());// hide password

						}

					}
				});
	}



    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, getActivity().MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Utils.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			checkValidation();
			break;

		case R.id.forgot_password:

			// Replace forgot password fragment with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer,
							new ForgotPassword_Fragment(),
							Utils.ForgotPassword_Fragment).commit();
			break;

		case R.id.link_signup:
			// Replace signup frgament with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer, new SignUp_Fragment(),
							Utils.SignUp_Fragment).commit();
			break;
		}

	}



	// Check Validation before login
	private void checkValidation() {
		// Get email id and password
         getEmailId = emailid.getText().toString();
		 getPassword = password.getText().toString();

		// Check patter for email id
		Pattern p = Pattern.compile(Utils.regEx);

		Matcher m = p.matcher(getEmailId);

		// Check for both field is empty or not
		if (getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0) {
			loginLayout.startAnimation(shakeAnimation);
			new CustomToast().Show_Toast(getActivity(), view,
					"Enter both credentials.");

		}
		// Check if email id is valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");
		// Else do login and do your stuff
		else{

			//Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
			//		.show();
            try {

				login();

            }catch (Exception E){
                E.printStackTrace();
            }


        }

	}



	private void login(){

	    //Creating a string request
		StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.LOGIN_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

						//If we are getting success from server
						if(response.equalsIgnoreCase(Utils.LOGIN_SUCCESS)){
							//Creating a shared preference
							SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, getActivity().MODE_PRIVATE);

							//Creating editor to store values to shared preferences
							SharedPreferences.Editor editor = sharedPreferences.edit();

							//Adding values to editor
							editor.putBoolean(Utils.LOGGEDIN_SHARED_PREF, true);
							editor.putString(Utils.EMAIL_SHARED_PREF, getEmailId);

							//Saving values to editor
							editor.commit();

							//Starting profile activity
							Intent intent = new Intent(getActivity(), MainActivity.class);
							startActivity(intent);
						}else{
							//If the server response is not success
							//Displaying an error message on toast
							Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						//You can handle error here if you want
					}
				}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> params = new HashMap<>();
				//Adding parameters to request
				params.put(Utils.KEY_EMAIL, getEmailId);
				params.put(Utils.KEY_PASSWORD, getPassword);

				//returning parameter
				return params;
			}
		};

		//Adding the string request to the queue
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		requestQueue.add(stringRequest);
	}


}
