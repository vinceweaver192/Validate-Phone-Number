package com.example.project1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneActivity extends AppCompatActivity {

    protected EditText input;
    protected String number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone);

        // Bind the interface elements to the corresponding fields
        input = (EditText) findViewById(R.id.editTextTextPersonName);

        // Listens for the user to enter a prompt into the text field
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                String x = input.getText().toString();
                //x = filterInput(x);
                // check the action ID for the end of the action through the soft key
                // enter
                if (actionId == EditorInfo.IME_ACTION_DONE)  { //&& event.getAction() != 4 && event.getAction() != 1) {
                    switchToMainActivity(x);
                    //Log.i("Activity1: ", "***TRUE***");
                    return true;
                }
                else {
                    //switchToMainActivity(x);
                    //Log.i("Activity1: ", "***FALSE***");
                    return false;
                }
            }

        });
    }

    public String filterInput(String theInput) {
        theInput = theInput.replaceAll("[^a-zA-Z0-9]", "");
        //Log.i("Activity1: ", "filter");

        return theInput;
    }

    public boolean checkEmptyInput(String theInput) {

        //theInput = theInput.replaceAll("[\\p{Punct}]", "");
        //theInput = theInput.replaceAll("[^a-zA-Z0-9]", "");

        // check for empty text
        if (theInput == null || theInput.isEmpty()) {
            //Log.i("Activity1: ", "the input is empty");
            return false;
        }
        //number = theInput;
        //Log.i("Activity1: ", "empty is not empty");
        return true;
    }

    public boolean checkLetterInput(String theInput) {
        // check for letters in text
        //Log.i("Activity1: ", "the letter input is: " + theInput);
        for (int i = 0; i < theInput.length(); i++) {
            if (Character.isLetter(theInput.charAt(i))) {
                return false;
            }
        }
        //Log.i("Activity1: ", "letter is true");
        return true;
    }

    public boolean checkLengthInput(String theInput) {
        // check for length of text
        theInput = filterInput(theInput);

        if(theInput.length() != 10){
            //Log.i("Activity1: ", "length is false");
            return false;
        }
        //Log.i("Activity1: ", "length is true");
        return true;
    }

//    private void switchToMainActivity() {
//        Intent i = new Intent(PhoneActivity.this, MainActivity.class) ;
//        startActivityForResult(i,1); ;
//    }

    public void switchToMainActivity(String theNumber) {
        Intent i = new Intent();
        //Log.i("Activity1: ", "switch to main activity HERE: " + theNumber);
        number = theNumber;
        if (checkEmptyInput(theNumber) && checkLengthInput(theNumber) && checkLetterInput(theNumber)) {

            number = filterInput(theNumber);
            //Log.i("Activity1: ", "the input number is ****: " + number);

            i.putExtra("number", number);
            setResult(RESULT_OK,i) ;
        }
        else {
            //Log.i("Activity1: ", "checks were not true SORRY: " + number);

            i.putExtra("number", number);
            setResult(RESULT_CANCELED,i);
        }
        //Log.i("Activity1: ", "the name before activity 1: " + number);

        finish();
    }

}