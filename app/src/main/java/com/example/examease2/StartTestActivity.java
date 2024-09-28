package com.example.examease2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartTestActivity extends AppCompatActivity {
    TextView catName,testNo,totalQue,bestScore,time;
    Button startTest;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        init();

        DBQuery.loadQuestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                setData();

            }

            @Override
            public void onFailiure() {
                Toast.makeText(StartTestActivity.this, "que is not loaded yet", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void init()
    {
        catName=findViewById(R.id.st_cat_name);
        testNo=findViewById(R.id.st_test_no);
        totalQue=findViewById(R.id.st_total_que);
        bestScore=findViewById(R.id.st_best_sore);
        time=findViewById(R.id.st_time);
        startTest=findViewById(R.id.strat_test_btn);
        backBtn=findViewById(R.id.st_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTestActivity.this.finish();
            }

            });
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartTestActivity.this,QuestionsActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
    void setData()
    {
        catName.setText(DBQuery.g_homeModelList.get(DBQuery.g_selected_cat_index).getName());
        testNo.setText("Test No : "+(DBQuery.g_selected_test_index+1));
        totalQue.setText(String.valueOf(DBQuery.g_queModelList.size()));
        bestScore.setText(String.valueOf(DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTopscore()));
        time.setText(String.valueOf(DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTime()));


    }

}