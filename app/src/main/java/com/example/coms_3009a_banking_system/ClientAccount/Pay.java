package com.example.coms_3009a_banking_system.ClientAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coms_3009a_banking_system.AsyncHTTPPost;
import com.example.coms_3009a_banking_system.R;

public class Pay extends AppCompatActivity {

    // initializing variables
    EditText recipient,amount,reference,paymentDate;
    Button pay;
    RadioButton bankA, bankB, bankC;
    RadioGroup radioGroupfrom;
    RadioButton radioButton;

    String From;
    String Amount;
    String To;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // assigning variables
        recipient = findViewById (R.id.recipient);
        amount = findViewById(R.id.amount);
        reference = findViewById(R.id.reference);
        paymentDate = findViewById(R.id.paymentDate);

        radioGroupfrom = (RadioGroup)findViewById(R.id.payRadioGroup_From);

        bankA = (RadioButton)findViewById(R.id.toBankA);
        bankB = (RadioButton)findViewById(R.id.toBankB);
        bankC = (RadioButton)findViewById(R.id.toBankC);

        //Get Email from previous page
        Intent intent = getIntent();
        Email = intent.getStringExtra("email");

        pay = (Button)findViewById(R.id.pay);

        //website to post to the php file
        //https://lamp.ms.wits.ac.za/home/s2143686/Account_activity.php

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get from which account you're paying from
                To = recipient.getText().toString();
                Amount = amount.getText().toString();

                //get selected radio button From
                int selected = radioGroupfrom.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selected);
                From = radioButton.getText().toString();

                ContentValues parameters = new ContentValues();
                parameters.put("Acc_Name", From);
                parameters.put("Pin", 12345);
                parameters.put("Activity", "Pay");
                parameters.put("Amount", Amount);
                parameters.put("To", To);
                parameters.put("Email", Email);

                AsyncHTTPPost asyncHttpPost = new AsyncHTTPPost("https://lamp.ms.wits.ac.za/home/s2143686/Account_activity.php",parameters){
                    @Override
                    protected void onPostExecute(String output) {
                        //Do the transfer and toast output
                        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();

                    }
                };
                asyncHttpPost.execute();
            }
        });

    }

    public void onRadioButtonClicked(View view) {
    }
}