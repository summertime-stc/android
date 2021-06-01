package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.receiver.MyReceiver;
import com.example.myapplication.service.MyService;

public class Main5Activity extends AppCompatActivity {

    private CheckBox checkBox;
    private ImageView imageView;
    static Main5Activity main5Activity=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            Toast.makeText(this,"该设备不支持蓝牙设备",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"该设备支持蓝牙设备",Toast.LENGTH_SHORT).show();
        }

        init();

        //图片框架
//        RequestOptions requestOptions=new RequestOptions()
//                .placeholder(R.drawable.tab1)
//                .error(R.drawable.tab1)
//                .fallback(R.drawable.tab1)
//                .override(100,100);

        Glide.with(this)
                .load(R.drawable.jpg1)
                .placeholder(R.drawable.tab1)
                .error(R.drawable.tab1)
                .fallback(R.drawable.tab1)
                .override(100,100)
                .transform(new CircleCrop())
                .into(imageView);


        //注册动态广播接收者
//        MyReceiver myReceiver;
//        myReceiver = new MyReceiver();
//        IntentFilter filter=new IntentFilter();
//        filter.addAction("Intent.ACTION_SCREEN_OFF");
//        registerReceiver(myReceiver,filter);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()==true){
                    System.out.println("关闭服务-------");
                    checkBox.setChecked(true);

                    //后台服务关闭
                    stopService(new Intent(Main5Activity.main5Activity, MyService.class));


                }
                else{
                    System.out.println("开启服务-------");
                    checkBox.setChecked(false);

                    //后台服务关闭
                    startService(new Intent(Main5Activity.main5Activity, MyService.class));



                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        checkBox=findViewById(R.id.checkBox);
        imageView=findViewById(R.id.imageView5);
        main5Activity=this;

    }
}