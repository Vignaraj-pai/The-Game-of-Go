package com.example.thegameofgo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public void customLogoutDialog() {
        // creating custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);

        // setting content view to dialog
        dialog.setContentView(R.layout.logout_dialog);

        // getting reference of TextView
        TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.textViewYes);
        TextView dialogButtonNo = (TextView) dialog.findViewById(R.id.textViewNo);

        // click listener for No
        dialogButtonNo.setOnClickListener(v -> {
            // dismiss the dialog
            dialog.dismiss();

        });
        // click listener for Yes
        dialogButtonYes.setOnClickListener(v -> {
            // dismiss the dialog and exit the exit
            SharedPreferences preferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", "Guest");
            editor.putBoolean("isloggedin", false);
            editor.commit();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            dialog.dismiss();
        });

        // show the exit dialog
        dialog.show();
    }

    public void customExitDialog() {
        // creating custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);

        // setting content view to dialog
        dialog.setContentView(R.layout.exit_dialog);

        // getting reference of TextView
        TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.textViewYes);
        TextView dialogButtonNo = (TextView) dialog.findViewById(R.id.textViewNo);

        // click listener for No
        dialogButtonNo.setOnClickListener(v -> {
            // dismiss the dialog
            dialog.dismiss();

        });
        // click listener for Yes
        dialogButtonYes.setOnClickListener(v -> {
            // dismiss the dialog and exit the exit
            dialog.dismiss();
            finish();
        });

        // show the exit dialog
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        // calling the function
        customExitDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        Boolean isLoggedIn = preferences.getBoolean("isloggedin", false);
        String logUsername = preferences.getString("username", "Guest");

        if(!isLoggedIn){
            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(i);
        }

        Button set_btn = (Button) findViewById(R.id.options);
        Button exit = (Button) findViewById(R.id.Exit);

        TextView logInfo = (TextView) findViewById(R.id.LoginInfo);
        logInfo.setText(logUsername);

        set_btn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        });

        //for online play, check isLoggedIn, if false, redirect to signup

        exit.setOnClickListener(view -> customLogoutDialog());
    }
}
