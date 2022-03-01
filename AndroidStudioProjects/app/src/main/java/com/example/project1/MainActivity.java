package com.example.project1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button button1 ; 				// the "Up" button in the GUI
    protected Button button2 ;
    protected String theNumber;
    protected Boolean checkIfValid;


    // checks the activity result that was saved during the phone activity
    // and checks whether the activity result is OK or CANCELED.
    ActivityResultLauncher<Intent> startMainActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

        @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i("Activity1: ", "on activity ***RESULT***");
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent i = result.getData();
                        //Log.i("Activity1: ", "Returned result is: " + result.getResultCode());

                        checkIfValid = true;
                        theNumber = i.getStringExtra("number"); /* Storing the PersonName for later */
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Intent i = result.getData();
                        //Log.i("Activity1: ", "Returned result is: " + result.getResultCode());
                        checkIfValid = false;
                        theNumber = i.getStringExtra("number");

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Always do this
        super.onCreate(savedInstanceState);

        // Inflate the main layout (from the res folder)
        setContentView(R.layout.activity_main);

        // Bind the interface elements to the corresponding fields
        button1 = (Button) findViewById(R.id.buttonOne);
        button2 = (Button) findViewById(R.id.buttonTwo) ;

        // Set up listeners for the buttons and the slider;
        // Actual listeners are created below as instances of
        // anonymous classes
        button1.setOnClickListener(buttonOneListener) ;
        button2.setOnClickListener(buttonTwoListener) ;

    }

    // waits for user to click on button one and switches to phone activity
    public View.OnClickListener buttonOneListener = new View.OnClickListener() {

        // Called when up button is selected
        @Override
        public void onClick(View v) {
            switchToPhoneActivity();
        }
    };

    // waits for user to press button, this button is pressed after the user
    // has typed a phone number into activity 2 and will either prompt an
    // error message if phone # is invalid or will do an implicit intent to
    // the phone contact activity.
    public View.OnClickListener buttonTwoListener = new View.OnClickListener() {

        // Called when down Button is selected
        @Override
        public void onClick(View v) {
            //Log.i("Activity1: ", "reached button 2");
            openPhoneContact();
            //Log.i("Activity1: ", "after contact");
        }
    } ;

    // does the actual switch to the next activity
    private void switchToPhoneActivity() {
        Intent i = new Intent(MainActivity.this, PhoneActivity.class) ;
        startMainActivity.launch(i);
        //startActivityForResult(i,1); ;
    }

    // Opens the phone contact implicit intent if input is true or
    // prompts an error message if input is false
    public void openPhoneContact(){
        if (checkIfValid) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + theNumber));
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Entered an incorrect number \n '" + theNumber + "'";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}