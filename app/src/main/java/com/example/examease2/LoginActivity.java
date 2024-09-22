package com.example.examease2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView forgetPass,signUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forgetPass=findViewById(R.id.forget_password);
        signUp=findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData())
                {
                    login();
                }
            }
        });
    }

    boolean validateData() {


        if(email.getText().toString().isEmpty())
        {
            email.setError("Enter Email id");
            return false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Enter Password");
            return false;
        }
        return true;
    }

    void  login(){

    }

}


