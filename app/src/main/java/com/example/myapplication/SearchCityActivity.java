package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.myapplication.bean.City;
import com.example.myapplication.fragment.FirstFragment;
import com.example.myapplication.fragment.FourFragment;
import com.example.myapplication.fragment.SearchListFragment;
import com.example.myapplication.fragment.SearchNullFragment;
import com.example.myapplication.fragment.SearchWaitFragment;
import com.example.myapplication.globalvaries.GlobalVaries;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchCityActivity extends AppCompatActivity {

    @BindView(R.id.city_name)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("城市搜索");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("---------"+editText.getText().toString());
                replaceFragment(new SearchWaitFragment());
                sendRequestWithHttpURLConnection(editText.getText().toString());

            }
        });

    }
    private void sendRequestWithHttpURLConnection(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url="https://geoapi.qweather.com/v2/city/lookup?key=35308ecb64154831af1c38a08d0622db&location="+s;
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


                System.out.println("404".equals(obj.get("code").getAsString())+"---"+obj.get("code").getAsString());
                if ("404".equals(obj.get("code").getAsString())){
                    replaceFragment(new SearchNullFragment());
                }
                else if("200".equals(obj.get("code").getAsString())){
                    SearchListFragment searchListFragment=new SearchListFragment();
                    replaceFragment(searchListFragment);

                    Bundle bundle = new Bundle();
                    bundle.putString("DATA",obj.get("location").getAsJsonArray().toString());//这里的values就是我们要传的值
                    searchListFragment.setArguments(bundle);
                }else{
                    replaceFragment(new SearchNullFragment());
                }

            }
        });
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transation = fragmentManager.beginTransaction();
        transation.replace(R.id.fragment3,fragment);
//        transation.addToBackStack(null);
        transation.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}