package com.example.examease2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    List <TestModel> testModelList;
    Toolbar toolbar;
    RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar=findViewById(R.id.toolbar2);
        recyclerView=findViewById(R.id.testrecyclerview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        int index=getIntent().getIntExtra("INDEX",0);
        getSupportActionBar().setTitle(HomeFragment.homeModelList.get(index).getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        loadTestData();


        TestAdapter testAdapter=new TestAdapter(testModelList);
        recyclerView.setAdapter(testAdapter);







    }
    void loadTestData(){
        testModelList=new ArrayList<>();

        testModelList.add(new TestModel("1",50,20) );
        testModelList.add(new TestModel("2",50,20) );
        testModelList.add(new TestModel("3",50,20) );
        testModelList.add(new TestModel("4",50,20) );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}