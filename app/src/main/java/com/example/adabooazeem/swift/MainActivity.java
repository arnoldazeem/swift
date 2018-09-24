package com.example.adabooazeem.swift;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.kosalgeek.android.md5simply.MD5;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected static EditText clientname;
    protected static EditText alternativename;

    protected static EditText locationpickup;
    protected static EditText locationdelivery;

    protected static EditText deliverydate;

    protected static Button request;

    public String spinner_value;

    String getclientname;
    String getalternativename;
    String getlocationpickup;
    String getlocationdelivery;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        clientname = (EditText) findViewById(R.id.client_name);
        alternativename = (EditText) findViewById(R.id.alternative_name);
        locationpickup = (EditText) findViewById(R.id.location_pickup);
        locationdelivery = (EditText) findViewById(R.id.location_delivery);

        deliverydate = (EditText) findViewById(R.id.delivery_date);



        request = (Button) findViewById(R.id.send_request);

        String[] PICKSPINNERLIST = {"TUESDAY", "FRIDAY"};


        request.setOnClickListener(this);
       // pickmonth.setOnClickListener(this);

        //setSupportActionBar(toolbar);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
        //    }
       // });


        //MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinnerpick);
        //spinner.setItems("Pickup Time","30mins", "1hour", "2hours");
        //spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

        //    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
       //         Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
        //    }
       // });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PICKSPINNERLIST);

        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);


        materialDesignSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                spinner_value= adapterView.getItemAtPosition(position).toString();


                if (spinner_value.equals("TUESDAY")) {

                    deliverydate.setText("WEDNESDAY", TextView.BufferType.EDITABLE);

                } else {

                    deliverydate.setText("SUNDAY", TextView.BufferType.EDITABLE);

                }

            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action) {
            return true;
        }

        if (id == R.id.close) {

            logout();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME, getApplication().MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Utils.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Utils.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainActivity.this, Authentication.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
         AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }



    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.send_request:
                // it was the first button
              //  TimePicker mTimePicker = new TimePicker();
              //  mTimePicker.show(getFragmentManager(), "Select time");
                confirmAlert();
                break;


            //case R.id.pickmonth:
                // it was the first button
              //  DatePicker mDatePicker = new DatePicker();
              //  mDatePicker.show(getFragmentManager(), "Select date");

              //  break;*/

        }

    }


    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            //pickdate.setText("Selected Time: " + String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
        }
    }


    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }



        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {

           // pickmonth.setText("Selected Time: " + String.valueOf(i) + " : " + String.valueOf(i1) + " : " + String.valueOf(i2));

        }
    }




    private void confirmAlert() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
     builder.setTitle("Confirm dialog demo !");
     builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
     builder.setCancelable(false);
     builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've choosen to delete all records", Toast.LENGTH_SHORT).show();

                checkValidation();
            }
        });

     builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

     builder.show();
     }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        getclientname = clientname.getText().toString();
        getalternativename = alternativename.getText().toString();
        getlocationpickup = locationpickup.getText().toString();
        getlocationdelivery = locationdelivery.getText().toString();


        // Check if all strings are null or not
        if (getclientname.equals("") || getclientname.length() == 0
                || getalternativename.equals("") || getalternativename.length() == 0
                || getlocationpickup.equals("") || getlocationpickup.length() == 0
                || getlocationdelivery.equals("") || getlocationdelivery.length() == 0)


        Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT)
                .show();


            // Check if email id valid or not
        //else if (!m.find())
         //   new CustomToast().Show_Toast(this, view,
          //          "Your Email Id is Invalid.");
        else {
            Toast.makeText(this, "Do SignUp.", Toast.LENGTH_SHORT)
                    .show();

            try {


                //signup();

            }catch (Exception E){
                E.printStackTrace();
            }

        }

    }

}
