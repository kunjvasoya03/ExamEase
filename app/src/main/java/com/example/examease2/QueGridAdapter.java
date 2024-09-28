package com.example.examease2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class QueGridAdapter extends BaseAdapter {

    int noOfQue;
    int status;
    Context context;
    public QueGridAdapter(int noOfQue,Context context) {
        this.noOfQue = noOfQue;

        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNoOfQue() {
        return noOfQue;
    }

    public void setNoOfQue(int noOfQue) {
        this.noOfQue = noOfQue;
    }

    @Override
    public int getCount() {
        return noOfQue;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView==null)
        {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.que_grid_item,parent,false);
        }
        else
        {
            view = convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(context instanceof QuestionsActivity)
                 {
                     ((QuestionsActivity)context).goToQue(position);
                 }
            }
        });

        TextView quetv=view.findViewById(R.id.que_no);
        quetv.setText(String.valueOf(position+1));

        status=DBQuery.g_queModelList.get(position).getStatus();
        if(status==DBQuery.NOT_VISITED)
        {
            quetv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),R.color.grey)));
        }
        else if(status==DBQuery.UNANSWERED)
        {
            quetv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),R.color.red)));
        }
        else if(status==DBQuery.ANSWERED)
        {
            quetv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),R.color.green)));
        }
        else if(status==DBQuery.REVIEW)
        {
            quetv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(),R.color.yellow)));
        }

        return view;
    }
}
