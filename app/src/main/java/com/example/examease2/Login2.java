package com.example.examease2;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login2 extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView forgetPass,signUp;
    String Email,Password;
    FirebaseAuth mAuth;
    LinearLayout glayout;
    private static final int RC_SIGN_IN = 104;
    GoogleSignInClient mGoogleSignInClient;
    SignInCredential googleCredential;
    String idToken;
    BeginSignInRequest signInRequest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forgetPass=findViewById(R.id.forget_password);
        signUp=findViewById(R.id.signuptext);
        mAuth=FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Ensure you have set this in strings.xml
                .requestEmail()
                .build();
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);

         //googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
         //  idToken = googleCredential.getGoogleIdToken();


        glayout=findViewById(R.id.g_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData())
                {
                    login();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Login2.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        glayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
            }
        });
    }

    boolean validateData() {

        Email=email.getText().toString().trim();
        Password=password.getText().toString().trim();
        if(Email.isEmpty())
        {
            email.setError("Enter Email id");
            return false;
        }
        if(Password.isEmpty()){
            password.setError("Enter Password");
            return false;
        }
        return true;
    }

    void  login()
    {

        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(Login2.this, "Authentication Successfull",
                                    Toast.LENGTH_SHORT).show();
                            DBQuery.loadData(new MyCompleteListener() {
                                @Override
                                public void onSuccess() {
                                    Intent intent=new Intent(Login2.this,MainActivity.class);
                                    startActivity(intent);
                                    Login2.this.finish();
                                }

                                @Override
                                public void onFailiure() {
                                    Toast.makeText(Login2.this, "couldn't load in login",
                                            Toast.LENGTH_SHORT).show();

                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    void googleLogin()
    {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, you can get the account information
            String idToken = account.getIdToken();
            String email = account.getEmail();
            // You can now send the idToken or email to your backend for authentication
            firebaseAuthWithGoogle(idToken);
        } catch (ApiException e) {
            // Handle error, like displaying a message to the user
            Log.w("Google Sign-In", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void firebaseAuthWithGoogle (String idToken)
    {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(Login2.this,MainActivity.class);
                            startActivity(intent);
                            Login2.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
}