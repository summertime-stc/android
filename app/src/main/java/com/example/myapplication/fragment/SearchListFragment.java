package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapt.CityAdapter;
import com.example.myapplication.bean.City;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchListFragment extends Fragment {

    private View root;
    List<City> cityList;

//    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (root==null){
            root=inflater.inflate(R.layout.fragment_search_list,container,false);
        }
        ButterKnife.bind(root);
        Bundle bundle = getArguments();
        String data = bundle.getString("DATA");
        System.out.println("-------------data"+data);
        JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();

        cityList=new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            City city=new City(jsonArray.get(i).getAsJsonObject().get("name").getAsString(),jsonArray.get(i).getAsJsonObject().get("id").getAsString());
            cityList.add(city);
        }

        recyclerView = root.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        CityAdapter cityAdapter = new CityAdapter(cityList,root.getContext());
        recyclerView.setAdapter(cityAdapter);
        return root;
    }
}