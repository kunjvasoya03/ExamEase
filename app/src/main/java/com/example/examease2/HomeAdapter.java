package com.example.examease2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {

    List<HomeModel> homeModelList;

    public HomeAdapter(List<HomeModel> homeModelList) {
        this.homeModelList = homeModelList;
    }

    @Override
    public int getCount() {
        return homeModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView;

        if(view==null)
        {
            myView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item_layout,viewGroup,false);
        }
        else
        {
            myView=view;
        }
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),TestActivity.class);
                intent.putExtra("INDEX",i);
                view.getContext().startActivity(intent);
            }
        });

        TextView homeName=myView.findViewById(R.id.home_name);
        ImageView homeImage=myView.findViewById(R.id.home_img);
        homeName.setText(homeModelList.get(i).getName());
        homeImage.setImageResource(homeModelList.get(i).getImg_id());
        return myView;
    }

}
