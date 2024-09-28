package com.example.examease2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    RecyclerView questionsView;
    TextView tvQuesId,timertv,catNametv;
    Button submitB,markB,clearselB;
    ImageButton prevQuesB,nextQuesB;
    ImageView questionListB;
    int questionId;
    QuestionsAdapter questionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        init();

        questionsAdapter=new QuestionsAdapter(DBQuery.g_queModelList);
        questionsView.setAdapter(questionsAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(linearLayoutManager);

        setSnapHelper();

        setClickListeners();

        startTimer();


    }

    void init(){

        questionsView=findViewById(R.id.questions_view);
        tvQuesId=findViewById(R.id.tv_questionid);
        timertv=findViewById(R.id.tv_time);
        catNametv=findViewById(R.id.qa_cat_name);
        submitB=findViewById(R.id.submitb);
       markB=findViewById(R.id.markB);
       clearselB=findViewById(R.id.clear_selB);
       prevQuesB=findViewById(R.id.prev_quesB);
       nextQuesB=findViewById(R.id.next_quesB);
       questionListB=findViewById(R.id.qa_list_gridb);

       questionId=0;
       tvQuesId.setText("1/"+ String.valueOf(DBQuery.g_queModelList.size()));
       catNametv.setText(DBQuery.g_homeModelList.get(DBQuery.g_selected_cat_index).getName());
    }

    public void setClickListeners(){


        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionId>0){
                    questionsView.smoothScrollToPosition(questionId-1);
                    questionId--;
                }
            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionId<DBQuery.g_queModelList.size()-1){
                    questionsView.smoothScrollToPosition(questionId+1);
                    questionId++;
                }
            }
        });
        clearselB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBQuery.g_queModelList.get(questionId).setSelectedAns(-1);
                questionsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void startTimer(){
        long totaltime=DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTime()*60*1000;
        CountDownTimer timer=new CountDownTimer(totaltime,1000) {
            @Override
            public void onTick(long remaningtime) {
                String time=String.format("%02d:%02d min", TimeUnit.MILLISECONDS.toMinutes(remaningtime),
                        TimeUnit.MILLISECONDS.toSeconds(remaningtime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remaningtime)));
                timertv.setText(time);

            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    public void setSnapHelper()
    {
        SnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsView);
        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view=snapHelper.findSnapView(recyclerView.getLayoutManager());
                questionId=recyclerView.getLayoutManager().getPosition(view);
                tvQuesId.setText(String.valueOf(questionId+1+"/"+String.valueOf(DBQuery.g_queModelList.size())));

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}