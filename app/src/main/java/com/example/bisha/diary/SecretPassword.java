package com.example.bisha.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecretPassword extends AppCompatActivity {

    Button setPasswordMatch;
    EditText PasswordMatch;

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_password);

        preferences = getSharedPreferences("dairyUser",0);
        setPasswordMatch = (Button) findViewById(R.id.setPasswordMatch);
        PasswordMatch = (EditText) findViewById(R.id.PasswordMatch);

        setPasswordMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();

            }
        });
    }

    public void login()
    {

        String logPassword = PasswordMatch.getText().toString();
        String logFilepassword = preferences.getString("password","");

        if(!logPassword.isEmpty())
        {
            if(logPassword.equals(logFilepassword))
            {
                startActivity(new Intent(SecretPassword.this,SecretMessage.class));
                finish();
            } else
            {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
