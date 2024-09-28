package com.example.examease2;

import static android.app.ProgressDialog.show;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionsModel> questionsModelList;
    int barColor;
    int cyanColor;

    public QuestionsAdapter(List<QuestionsModel> questionsModelList) {

        this.questionsModelList = questionsModelList;
    }


    @NonNull
    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.ViewHolder holder, int position) {

        holder.setDate(position);
    }

    @Override
    public int getItemCount() {
        return questionsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        Button a, b, c, d, prevSelecteB, nextSelectedB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            a = itemView.findViewById(R.id.optionA);
            b = itemView.findViewById(R.id.optionB);
            c = itemView.findViewById(R.id.optionC);
            d = itemView.findViewById(R.id.optionD);
            prevSelecteB = null;
        }

        void setDate(final int position) {
            question.setText(questionsModelList.get(position).getQue());
            a.setText(questionsModelList.get(position).getOptionA());
            b.setText(questionsModelList.get(position).getOptionB());
            c.setText(questionsModelList.get(position).getOptionC());
            d.setText(questionsModelList.get(position).getOptionD());


//            setBackgroundResource() applies a drawable, but color resources in Android can be applied as themes, causing theme-based colors to override.
//                    setBackgroundColor() expects a specific color value, and using ContextCompat.getColor() ensures that you're passing the actual resolved color value rather than a resource ID that can vary by theme.


            barColor = ContextCompat.getColor(itemView.getContext(), R.color.barcolor);
            cyanColor = ContextCompat.getColor(itemView.getContext(), R.color.cyan);
            setOption(a,1,position);
            setOption(b,2,position);
            setOption(c,3,position);
            setOption(d,4,position);
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(a, 1, position);
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(b, 2, position);
                }
            });
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(c, 3, position);
                }
            });
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(d, 4, position);
                }
            });

        }

        void selectOption(Button btn, int optionNo, int questionId) {
            if (prevSelecteB == null) {
                btn.setBackgroundColor(cyanColor);
                DBQuery.g_queModelList.get(questionId).setSelectedAns(optionNo);
                prevSelecteB = btn;
            } else {
                if (prevSelecteB.getId() == btn.getId()) {
                    btn.setBackgroundColor(barColor);
                    DBQuery.g_queModelList.get(questionId).setSelectedAns(optionNo);
                    prevSelecteB = btn;
                } else {
                    prevSelecteB.setBackgroundColor(barColor);
                    btn.setBackgroundColor(cyanColor);
                    DBQuery.g_queModelList.get(questionId).setSelectedAns(-1);
                    prevSelecteB = null;
                }
            }
        }

        void setOption(Button btn,int optionNo,int queId)
        {
            if(DBQuery.g_queModelList.get(queId).getSelectedAns()==optionNo)
            {
                btn.setBackgroundColor(cyanColor);
            }
            else
                btn.setBackgroundColor(barColor);
        }
    }
}