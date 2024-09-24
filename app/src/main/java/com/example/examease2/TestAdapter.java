package com.example.examease2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>
{
    List<TestModel> testlist;

    // Constructor to pass the list of test items
    public TestAdapter(List<TestModel> testlist) {
        this.testlist = testlist;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        int progress = testlist.get(position).getTopscore();
        holder.setData(position, progress);
    }

    // Make sure getItemCount() returns the correct size of the list
    @Override
    public int getItemCount() {
        return testlist.size();  // Return the size of the testlist
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView testnum, topscore;
        ProgressBar testprogress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testnum = itemView.findViewById(R.id.testnum);
            topscore = itemView.findViewById(R.id.testscore);
            testprogress = itemView.findViewById(R.id.testprogressBar);
        }

        // Update UI elements with data
        void setData(int position, int progress) {
            testnum.setText("Test no: " + (position + 1));  // Test number starts from 1
            topscore.setText(String.valueOf(progress) + "%");
            testprogress.setProgress(progress);  // Set the progress bar
        }
    }
}
