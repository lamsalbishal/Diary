package com.example.bisha.diary;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SecretRegister extends AppCompatActivity {

    EditText newPassword,confirmPassword,oldPassword;
    Button btn;
    SharedPreferences sharedPreferences;
    String logFilepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_register);

        sharedPreferences = getSharedPreferences("dairyUser",0);

        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.newConfrimPassword);
        btn = (Button) findViewById(R.id.setPassword);

        btn.setEnabled(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        logFilepassword = sharedPreferences.getString("password","");
        if(logFilepassword == null)
        {
            oldPassword.setVisibility(View.INVISIBLE);
        }



    }

    public void insert()
    {
        String insoldPassword = oldPassword.getText().toString();
        String insnewPassword = newPassword.getText().toString();
        String insconfirmPassword = confirmPassword.getText().toString();

        if(logFilepassword == null)
        {
            if(insnewPassword.equals(insconfirmPassword))
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password",insconfirmPassword);
                editor.apply();
                Toast.makeText(this, "Saved Password", Toast.LENGTH_SHORT).show();
                finish();
            }else
            {
                Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show();
            }
        } else
        {
            if(logFilepassword.equals(insoldPassword))
            {
                if(insnewPassword.equals(insconfirmPassword))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password",insconfirmPassword);
                    editor.apply();
                    Toast.makeText(this, "Saved Password", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                {
                    Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show();
                }
            }else
            {
                Toast.makeText(this, "Soory old Password not Match", Toast.LENGTH_SHORT).show();
            }
        }

        btn.setEnabled(true);
    }
}
