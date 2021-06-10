package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.bean.City;
import com.example.myapplication.globalvaries.GlobalVaries;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.litepal.LitePal;
import org.litepal.crud.callback.SaveCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatcherActivity extends AppCompatActivity {


    @BindView(R.id.fruit_image_view)
    ImageView imageView;

    @BindView(R.id.imageView10)
    ImageView imageView1;

    @BindView(R.id.fruit_content_text)
    TextView textView;

    @BindView(R.id.textView19)
    TextView textView1;

    @BindView(R.id.textView21)
    TextView textView2;

    @BindView(R.id.textView13)
    TextView textView3;

    @BindView(R.id.textView17)
    TextView textView4;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.fb1)
    FloatingActionButton button;

    private Cursor cursor = null;

    static WeatcherActivity weatcherActivity;

    private Boolean COLLECT=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //关闭标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_weatcher);
        ButterKnife.bind(this);
        weatcherActivity=this;


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collsping_Toolbar);

//        //禁止侧滑
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------------------");
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        //监听
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Log.i("---", "滑动中");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.i("---", "打开");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.i("---", "关闭");
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.i("---", "状态改变");
            }
        });


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences sp = getSharedPreferences("dddd", Context.MODE_PRIVATE);

        GlobalVaries.setLocation(sp.getString("location","北京"));
        GlobalVaries.setLocationcode(sp.getString("locationcode","101010100"));

        //判断是否收藏
        cursor = LitePal.findBySQL("select * from City where cityname = ?",GlobalVaries.getLocation());
        System.out.println("----------------"+GlobalVaries.getLocation()+":"+cursor.getCount());
//        COLLECT=cursor.getCount()==0?false:true;
        if (cursor.getCount()==0){
            COLLECT=false;
        }
        else{
            COLLECT=true;
            button.setImageResource(R.drawable.ic_baseline_favorite_241);
        }
        cursor.close();

        collapsingToolbar.setTitle(GlobalVaries.getLocation());

        sendRequestWithHttpURLConnection(GlobalVaries.getLocationcode());
    }


    private void sendRequestWithHttpURLConnection(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url="https://devapi.qweather.com/v7/weather/now?key=35308ecb64154831af1c38a08d0622db&location="+s;
                    System.out.println("sssssssssssssssssssss1"+url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    String url2="https://devapi.qweather.com/v7/weather/3d?key=35308ecb64154831af1c38a08d0622db&location="+s;
                    System.out.println("sssssssssssssssssssss1"+url2);
                    request = new Request.Builder().url(url2).build();
                    response = client.newCall(request).execute();
                    String responseData2 = response.body().string();
                    showResponse(responseData,responseData2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response,final String response2) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里做UI操作，将结果显示到界面上
                System.out.println("          sssssssssssssssssssssssssssssssssssssss1"+response);
                System.out.println("          sssssssssssssssssssssssssssssssssssssss2"+response2);
                Gson gson=new Gson();
                JsonObject obj = gson.fromJson(response, JsonObject.class);
                JsonObject obj2 = gson.fromJson(response2, JsonObject.class);
                System.out.println("          sssssssssssssssssssssssssssssssssssssss"+obj.get("now").getAsJsonObject().get("text").getAsString());

                switch (obj.get("now").getAsJsonObject().get("text").getAsString()){
                    case "晴":
                        Glide.with(WeatcherActivity.weatcherActivity)
                                .load(R.drawable.sunday)
                                .centerCrop()
                                .into(imageView);

                        Glide.with(WeatcherActivity.weatcherActivity)
                                .load(R.drawable.sun)
                                .into(imageView1);
                        System.out.println("111111111");
                        break;
                    case "阴":
                        Glide.with(WeatcherActivity.weatcherActivity)
                                .load(R.drawable.sunday)
                                .centerCrop()
                                .into(imageView);

                        Glide.with(WeatcherActivity.weatcherActivity)
                                .load(R.drawable.cloud)
                                .into(imageView1);
                        System.out.println("111111111");
                        break;

                }

                textView.setText(obj.get("now").getAsJsonObject().get("temp").getAsString()+"℃");
                textView1.setText(obj.get("now").getAsJsonObject().get("windDir").getAsString());
                textView2.setText(obj.get("now").getAsJsonObject().get("humidity").getAsString()+"%");
                textView3.setText(obj2.get("daily").getAsJsonArray().get(0).getAsJsonObject().get("tempMax").getAsString()+"℃");
                textView4.setText(obj2.get("daily").getAsJsonArray().get(0).getAsJsonObject().get("tempMin").getAsString()+"℃");
            }
        });
    }

    public void choosecity(View view) {
        System.out.println("已点击选择城市");
        startActivity(new Intent(this,CitylistActivity.class));
    }

    public void toFavorite(View view) {
        System.out.println("收藏界面");
        startActivity(new Intent(this,FavCitylistActivity.class));
    }

    public void collect(View view) {
        if (COLLECT==false){
//            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_241));

            cursor = LitePal.findBySQL("select * from City where cityname = ?",GlobalVaries.getLocation());
            System.out.println("oooooooooooooooooooofalse:"+cursor.getCount());
            if (cursor.getCount()==0){
                new City(GlobalVaries.getLocation(),GlobalVaries.getLocationcode()).saveAsync().listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        button.setImageResource(R.drawable.ic_baseline_favorite_241);
                        COLLECT=true;
                        Toast.makeText(WeatcherActivity.weatcherActivity,"收藏成功",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            cursor.close();

        }
        else{
//            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
            LitePal.deleteAll(City.class, "cityname = ?", GlobalVaries.getLocation());
            button.setImageResource(R.drawable.ic_baseline_favorite_24);
            Toast.makeText(WeatcherActivity.weatcherActivity,"取消收藏",Toast.LENGTH_SHORT).show();
            COLLECT=false;
        }
    }
}