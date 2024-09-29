package com.example.examease2;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText email,password,username,confirmpassword;
    Button signup;
    TextView logintext;
    FirebaseAuth mAuth;
    String Email,Password,Username,Confirmpassword;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        confirmpassword=findViewById(R.id.confirm_password);
        username=findViewById(R.id.username);
        logintext=findViewById(R.id.logintext);
        mAuth=FirebaseAuth.getInstance();
        //FirebaseUser user = mAuth.getCurrentUser();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData())
                {
                    signUp();
                }
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SignUpActivity.this,Login2.class);
                startActivity(i);
            }
        });
    }

    boolean validateData() {

        Email=email.getText().toString().trim();
        Password=password.getText().toString().trim();
        Confirmpassword=confirmpassword.getText().toString().trim();
        Username=username.getText().toString().trim();


        if(Username.isEmpty())
        {
            username.setError("Enter Username");
            return false;
        }

        if(Email.isEmpty())
        {
            email.setError("Enter Email id");
            return false;
        }
        if(Password.isEmpty()){
            password.setError("Enter Password");
            return false;
        }
        if(Password.length()<=6){
            Toast.makeText(this,"Enter Password must be greater than 6 character",Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(Confirmpassword.isEmpty()){
            confirmpassword.setError("Enter Confirm Password");
            return false;
        }
        if(Password.compareTo(Confirmpassword)!=0)
        {
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

    void  signUp() {

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this,"SIgnUp successfull",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            DBQuery.createuserData(Email,Username,new MyCompleteListener(){

                                @Override
                                public void onSuccess() {
                                    Intent intent=new Intent(SignUpActivity.this,Login2.class);
                                    startActivity(intent);
                                    SignUpActivity.this.finish();
                                }
                                @Override
                                public void onFailiure() {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });

    }

}


