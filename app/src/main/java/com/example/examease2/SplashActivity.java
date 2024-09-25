package com.example.examease2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=2000;
    Animation topAnim;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        ImageView logo = findViewById(R.id.logo);
        logo.setAnimation(topAnim);
        mAuth = FirebaseAuth.getInstance();
        DBQuery.g_firestore= FirebaseFirestore.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                user = mAuth.getCurrentUser();
                if (user != null) {
                    DBQuery.loadHome(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            intent = new Intent(SplashActivity.this, MainActivity.class);

                        }

                        @Override
                        public void onFailiure() {
                            Toast.makeText(SplashActivity.this, "couldn't load in splash",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    intent = new Intent(SplashActivity.this, Login2.class);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
            }, SPLASH_SCREEN);

    }
}