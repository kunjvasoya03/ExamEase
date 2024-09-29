package com.example.examease2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    RecyclerView questionsView;
    TextView tvQuesId,timertv,catNametv;
    Button submitB,markB,clearselB;
    ImageButton prevQuesB,nextQuesB,drawercloseB;
    ImageView questionListB;
    int questionId;
    QuestionsAdapter questionsAdapter;
    DrawerLayout drawer;
    GridView queListGrid;
    ImageView markImg;
    QueGridAdapter gridAdapter;
    CountDownTimer timer;
    Long time_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list_layout);

        init();

        questionsAdapter=new QuestionsAdapter(DBQuery.g_queModelList);
        questionsView.setAdapter(questionsAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(linearLayoutManager);

        gridAdapter=new QueGridAdapter(DBQuery.g_queModelList.size(),this);
        queListGrid.setAdapter(gridAdapter);


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
        drawer=findViewById(R.id.drawer_layout);
        drawercloseB=findViewById(R.id.drawercloseB);
        markImg=findViewById(R.id.mark_img);
        queListGrid=findViewById(R.id.que_list_grid);
       questionId=0;
       tvQuesId.setText("1/"+ String.valueOf(DBQuery.g_queModelList.size()));
       catNametv.setText(DBQuery.g_homeModelList.get(DBQuery.g_selected_cat_index).getName());

       DBQuery.g_queModelList.get(0).setStatus(DBQuery.UNANSWERED);
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
                if(DBQuery.g_queModelList.get(questionId).getStatus()!=DBQuery.REVIEW)
                {
                    DBQuery.g_queModelList.get(questionId).setStatus(DBQuery.UNANSWERED);
                }
                questionsAdapter.notifyDataSetChanged();
            }
        });

        questionListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawer.isDrawerOpen(GravityCompat.END)){
                    gridAdapter.notifyDataSetChanged();
                    drawer.openDrawer(GravityCompat.END);
                }

                drawercloseB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(drawer.isDrawerOpen(GravityCompat.END)){
                            drawer.closeDrawer(GravityCompat.END);
                        }
                    }
                });
            }
        });

        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(markImg.getVisibility()!=View.VISIBLE)
                {
                    markImg.setVisibility(View.VISIBLE);
                    DBQuery.g_queModelList.get(questionId).setStatus(DBQuery.REVIEW);


                }
                else {
                    markImg.setVisibility(View.GONE);
                    if(DBQuery.g_queModelList.get(questionId).getSelectedAns()!=-1)
                    {
                        DBQuery.g_queModelList.get(questionId).setStatus(DBQuery.ANSWERED);
                    }
                    else
                    {
                        DBQuery.g_queModelList.get(questionId).setStatus(DBQuery.UNANSWERED);
                    }
                }
            }
        });

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTest();
            }
        });
    }

    void submitTest(){
        AlertDialog.Builder builder=new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view=getLayoutInflater().inflate(R.layout.alert_dialog_layout,null);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button cancleB =view.findViewById(R.id.cancelB);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button confirmB =view.findViewById(R.id.confirmB);

        builder.setView(view);
        AlertDialog alertDialog=builder.create();

        cancleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                alertDialog.dismiss();
                Intent intent=new Intent(QuestionsActivity.this,ScoreActivity.class);
                long total_time=DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME_TAKEN",total_time-time_left);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });
        alertDialog.show();

    }

    void goToQue(int position)
    {
        questionsView.smoothScrollToPosition(position);
        if(drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);

        }
    }

    public void startTimer(){
        long totaltime=DBQuery.g_testModelList.get(DBQuery.g_selected_test_index).getTime()*60*1000;
         timer=new CountDownTimer(totaltime,1000) {
            @Override
            public void onTick(long remaningtime) {
                time_left=remaningtime;
                String time=String.format("%02d:%02d min", TimeUnit.MILLISECONDS.toMinutes(remaningtime),
                        TimeUnit.MILLISECONDS.toSeconds(remaningtime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remaningtime)));

                timertv.setText(time);

            }

            @Override
            public void onFinish() {
                Toast.makeText(QuestionsActivity.this,"your exam time is ended",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(QuestionsActivity.this,ScoreActivity.class);
                startActivity(intent);
                QuestionsActivity.this.finish();
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
                if(DBQuery.g_queModelList.get(questionId).getStatus()==DBQuery.NOT_VISITED)
                {
                    DBQuery.g_queModelList.get(questionId).setStatus(DBQuery.UNANSWERED);
                }
                if(DBQuery.g_queModelList.get(questionId).getStatus()==DBQuery.REVIEW)
                {
                    markImg.setVisibility(View.VISIBLE);
                }
                else{
                    markImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}