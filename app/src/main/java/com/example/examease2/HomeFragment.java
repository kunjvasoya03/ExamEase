package com.example.examease2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    GridView gridView;
   public static List<HomeModel> homeModelList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        gridView=view.findViewById(R.id.home_grid);

        homeModelList.clear();
        homeModelList.add(new HomeModel("1","Android",5));
        homeModelList.add(new HomeModel("2","Android",5));
        homeModelList.add(new HomeModel("3","Android",5));
        homeModelList.add(new HomeModel("4","Android",5));

        HomeAdapter homeAdapter=new HomeAdapter(homeModelList);
        gridView.setAdapter(homeAdapter);

        return view;
    }
}
