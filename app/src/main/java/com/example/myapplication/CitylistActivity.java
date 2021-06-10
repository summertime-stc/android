package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.adapt.CityAdapter;
import com.example.myapplication.adapt.FruitAdapter;
import com.example.myapplication.bean.City;
import com.example.myapplication.globalvaries.GlobalVaries;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    static CitylistActivity citylistActivity;


    @BindView(R.id.toolbar3)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //关闭标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_citylist);
        ButterKnife.bind(this);
        citylistActivity=this;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("城市选择");

//        toolbar.inflateMenu(R.menu.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------------------");
                startActivity(new Intent(CitylistActivity.citylistActivity,WeatcherActivity.class));
            }
        });
        //加载list列表数据
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<City> cityList=new ArrayList<>();
        Map<String,String> map=readKeyValueTxtToMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            cityList.add(new City(entry.getKey(),entry.getValue()));
        }
        CityAdapter cityAdapter = new CityAdapter(cityList,this);
        recyclerView.setAdapter(cityAdapter);


    }

    public Map readKeyValueTxtToMap() {
        //循环直至返回map
        while (true) {
            final HashMap keyValueMap = new HashMap();//保存读取数据keyValueMap
            //每一个循环读取一组key=value
            while (true) {
                try {
                    final InputStream open = this.getAssets().open(
                            "key_value.txt");
                    final byte[] readArray = new byte[open.available()];
                    open.read(readArray);
                    open.close();
                    final StringTokenizer allLine = new StringTokenizer(new String(readArray, "UTF-8"), "\r\n");//以"\r\n"作为key=value的分解标志
                    while (allLine.hasMoreTokens()) {
                        final StringTokenizer oneLine = new StringTokenizer(allLine.nextToken(), "=");//以"="作为分解标志
                        final String leftKey = oneLine.nextToken();//读取第一个字符串key
                        if (!oneLine.hasMoreTokens()) {
                            break;
                        }
                        final String rightValue = oneLine.nextToken();//读取第二个字符串value
                        keyValueMap.put(leftKey, rightValue);
                    }
                    return keyValueMap;
                } catch (IOException e) {
                    e.printStackTrace();
                    return keyValueMap;
                }
            }
        }
    }

    @Override//普通菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //返回true代表普通菜单显示
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
//                Toast.makeText(this, "我的", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CitylistActivity.citylistActivity,SearchCityActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void location(View view) {
        System.out.println("-----location");
        location();
    }
    
    
    /**
     * 定位获取当前城市
     */
    private void location() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//        String providerName = locationManager.getBestProvider(criteria, true);
//        String providerName = LocationManager.NETWORK_PROVIDER;
        String providerName = "";
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
            providerName = LocationManager.NETWORK_PROVIDER;
        }else if (providerList.contains(LocationManager.GPS_PROVIDER)){
            providerName = LocationManager.GPS_PROVIDER;
        }else {

            System.out.println("provider 获取失败");
//            Toast.makeText(citylistActivity, "定位权限未打开", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0x0002);

            return;
        }
        // 权限复验
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            System.out.println("权限未授权，请先授权UHello定位权限");
            return;
        }
        Location location = locationManager.getLastKnownLocation(providerName);
        if (location != null){
            final double longitude = location.getLongitude();// 经度
            final double latitude = location.getLatitude();// 纬度
            Log.d("TAG", "longitude = " + longitude);
            Log.d("TAG", "latitude = " + latitude);

            sendRequestWithHttpURLConnection(longitude,latitude);
        }else {
//            ToastUtil.showToast(getContext(), "UHello 定位失败");
            System.out.println("location========null");
            Toast.makeText(citylistActivity, "获取定位失败", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendRequestWithHttpURLConnection(double s1,double s2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url="https://geoapi.qweather.com/v2/city/lookup?key=35308ecb64154831af1c38a08d0622db&location="+s1+","+s2;
                    System.out.println("sssssssssssssssssssss1"+url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里做UI操作，将结果显示到界面上
                System.out.println("          sssssssssssssssssssssssssssssssssssssss1"+response);
                Gson gson=new Gson();
                JsonObject obj = gson.fromJson(response, JsonObject.class);
                GlobalVaries.setLocation(obj.get("location").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                GlobalVaries.setLocationcode(obj.get("location").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString());
                SharedPreferences sp = CitylistActivity.citylistActivity.getSharedPreferences("dddd", Context.MODE_PRIVATE);
                sp.edit().putString("location",obj.get("location").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString()).apply();
                sp.edit().putString("locationcode",obj.get("location").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString()).apply();

                System.out.println("-----=-=-=-=-"+obj.get("location").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                startActivity(new Intent(CitylistActivity.citylistActivity,WeatcherActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        System.out.println("------------回调-----------"+requestCode);

        if (requestCode == 0x0002) {
            if (verifyPermissions(grantResults)) {
                location();
            } else {
            }
        }
    }

    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}