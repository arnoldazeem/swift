package com.example.adabooazeem.swift;


public class Utils {
	
	//Email Validation pattern
	public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";


	public static final String LOGIN_URL = "http://192.168.2.105/swift/login.php";

    public static final String SIGN_URL = "http://192.168.2.105/swift/signup.php";

	//Keys for email and password as defined in our $_POST['key'] in login.php
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";

    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_BUSINESS = "business";


    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_LOCATION = "location";

    public static final String KEY_SOCIAL = "social";



	//If server response is equal to this that means login is successful
	public static final String LOGIN_SUCCESS = "success";


	public static final String signup_SUCCESS = "successful";

	//Keys for Sharedpreferences
	//This would be the name of our shared preferences
	public static final String SHARED_PREF_NAME = "myloginapp";

	//This would be used to store the email of current logged in user
	public static final String EMAIL_SHARED_PREF = "email";

	//We will use this to store the boolean in sharedpreference to track user is loggedin or not
	public static final String LOGGEDIN_SHARED_PREF = "loggedin";



	//Fragments Tags
	public static final String Login_Fragment = "Login_Fragment";
	public static final String SignUp_Fragment = "SignUp_Fragment";
	public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";

	private static final String ROOT_URL = "http://192.168.101.1/Android/Api.php?apicall=";

	public static final String URL_REGISTER = ROOT_URL + "signup";
	public static final String URL_LOGIN= ROOT_URL + "login";
	
}
