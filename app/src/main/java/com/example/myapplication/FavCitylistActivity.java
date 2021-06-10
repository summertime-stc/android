package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;


import com.example.myapplication.adapt.CityAdapter;
import com.example.myapplication.bean.City;

import org.litepal.LitePal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavCitylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Cursor cursor = null;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    static FavCitylistActivity favCitylistActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //关闭标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_fav_citylist);
        ButterKnife.bind(this);

        favCitylistActivity=this;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("我的收藏");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------------------");
                startActivity(new Intent(FavCitylistActivity.favCitylistActivity,WeatcherActivity.class));
            }
        });

        //加载list列表数据
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<City> cityList = new ArrayList<>();
        cursor = LitePal.findBySQL("select * from City");// group by mydatetime

        if ((cursor != null) && (cursor.moveToFirst())) {
            // 循环遍历cursor 
            do {
                City city = new City(cursor.getColumnIndex("id"), cursor.getString(cursor.getColumnIndex("cityname")), cursor.getString(cursor.getColumnIndex("citycode")));
                cityList.add(city);
            } while (cursor.moveToNext());
            // 关闭
            cursor.close();
            CityAdapter cityAdapter = new CityAdapter(cityList, this);
            recyclerView.setAdapter(cityAdapter);

        }
    }
}