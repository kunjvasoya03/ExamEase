package com.example.examease2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreTV,timeTV,totalQTV,correctQTV,wrongQTV,unattemptedQTV;
    Button leaderB,reAttemptB,viewAnsB;
    long time_taken;
    int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.score_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        loadData();

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAttempt();
            }
        });

        saveResult();

    }
    void reAttempt(){
        for (int i=0;i<DBQuery.g_queModelList.size();i++){
            DBQuery.g_queModelList.get(i).setSelectedAns(-1);
            DBQuery.g_queModelList.get(i).setStatus(DBQuery.NOT_VISITED);
        }
        Intent intent=new Intent(ScoreActivity.this,StartTestActivity.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }


    void init(){
        scoreTV=findViewById(R.id.score);
        timeTV=findViewById(R.id.time);

        totalQTV=findViewById(R.id.totalQ);
        wrongQTV=findViewById(R.id.wrongQ);
        correctQTV=findViewById(R.id.corectQ);
        unattemptedQTV=findViewById(R.id.unattemptedQ);
        leaderB=findViewById(R.id.leaderB);
        reAttemptB=findViewById(R.id.reapptemptB);
        viewAnsB=findViewById(R.id.viewAnsB);
    }

    void loadData(){
        int correctQ=0,wrongQ=0,unattemptedQ=0;

        for (int i=0;i<DBQuery.g_queModelList.size();i++){
            if(DBQuery.g_queModelList.get(i).getSelectedAns()==-1){
                unattemptedQ++;
            }
            else {
                if(DBQuery.g_queModelList.get(i).getSelectedAns()==DBQuery.g_queModelList.get(i).getAns()){
                    correctQ++;
                }else {
                    wrongQ++;
                }
            }
        }
        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptedQ));
        totalQTV.setText(String.valueOf(DBQuery.g_queModelList.size()));
        finalScore=(correctQ*100)/DBQuery.g_queModelList.size();
        scoreTV.setText(String.valueOf(finalScore));
        time_taken=getIntent().getLongExtra("TIME_TAKEN",0);

        String time=String.format("%02d:%02d min", TimeUnit.MILLISECONDS.toMinutes(time_taken),
                TimeUnit.MILLISECONDS.toSeconds(time_taken)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time_taken)));

        timeTV.setText(time);
    }
    void saveResult()
    {
        DBQuery.saveResult(finalScore, new MyCompleteListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailiure() {
                Toast.makeText(ScoreActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}