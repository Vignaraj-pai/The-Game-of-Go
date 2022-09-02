package com.example.thegameofgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = (EditText) findViewById(R.id.username_login);
        EditText password = (EditText) findViewById(R.id.password);
        TextView warning = (TextView) findViewById(R.id.warning);

        Button login = (Button) findViewById(R.id.loginBtn);

        login.setOnClickListener(v -> {
            login.setBackgroundResource(R.drawable.login_btn_bg_pressed);

            String susername = username.getText().toString();
            String spassword = password.getText().toString();

            DocumentReference newPlayerRef = db.collection("players").document(susername);
            newPlayerRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            //player p = document.toObject(player.class);
                            String comparePassword = document.get("password").toString();
                            if(spassword.equals(comparePassword)){
                                SharedPreferences preferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username", susername);
                                editor.putBoolean("isloggedin", true);
                                editor.commit();

                                finish();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else {
                                warning.setText(R.string.wrongdetails);
                            }
                        } else {
                            warning.setText(R.string.wrongdetails);
                        }
                    } else {
                        warning.setText("get failed");
                    }
                }
            });

            login.setBackgroundResource(R.drawable.login_btn_bg);
        });
    }
}