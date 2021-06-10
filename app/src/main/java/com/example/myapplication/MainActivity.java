package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.adapt.SimpleArrayAdapter;
import com.example.myapplication.bean.Fruit1;
import com.example.myapplication.bean.Title;
import com.example.myapplication.receiver.MyReceiver;
import com.example.myapplication.receiver.ScreenReceiver;
import com.example.myapplication.thread.ProcessThread;
import com.example.myapplication.thread.TimeThread;
import com.example.myapplication.utils.IniFileold;
import com.example.myapplication.utils.ScreenBrightness;
import com.example.myapplication.viewmodel.ProcessViewModel;
import com.example.myapplication.viewmodel.TitleViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private TextView tv,tv2,tvtime;
    private EditText et,et1;
    private ProgressBar pb;
    private ProcessViewModel processViewModel;
    private Spinner spinner;
    private LinkedList<String> usrsList = new LinkedList<>();
    private String usr;
    private ConstraintLayout constraintLayout;
    private ImageView imageView;
    private Title title;
    private ProgressDialog progressDialog;
    private ImageView iv1,iv2,iv3,iv4,flagiv;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                System.out.println("收到消息，处理事件");
//                tv.setText("收到消息，处理事件");
                closeProgressDialog();
            }
        }
    };

    public static MainActivity mainActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标签栏
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //去掉标签栏
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        System.out.println("--------------------------main------------------------------");
//        location();

        Fruit1 fruit1=new Fruit1(1,"ss","ddd");
        fruit1.save();

        Cursor cursor = null;
        cursor = LitePal.findBySQL("select * from Fruit1");
        System.out.println("----------------结果显示---------------"+cursor.getCount());


        setContentView(R.layout.activity_main);
        mainActivity=this;

        init();
        showProgressDialog("请稍等…");
        //开启标题栏时间线程
        startTitleThread();

        new Thread() {
            @Override
            public void run() {
                super.run();
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA
                for (int i=0;i<3;i++){
                    try {
                        sleep(1000);
                        System.out.println(5-i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //发送消息
                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
                System.out.println("发送消息");
            }
        }.start();

        setSpinner();
        processViewModel.getProcess().setValue(ScreenBrightness.getScreenBrightness(this));

        sendRequestWithHttpURLConnection();

        //接受系统锁屏广播需动态注册
        ScreenReceiver screenReceiver;
        screenReceiver = new ScreenReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenReceiver,filter);

        SharedPreferences sp = getSharedPreferences("dddd", Context.MODE_PRIVATE);
        et.setText(sp.getString("stcKey","dault"));
        Log.e("TAG", "亮度: "+ScreenBrightness.getScreenBrightness(this));
        iv1.setSelected(true);
        flagiv=iv1;

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv1.setSelected(true);
                flagiv=iv1;
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv2.setSelected(true);
                flagiv=iv2;
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv3.setSelected(true);
                flagiv=iv3;
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv4.setSelected(true);
                flagiv=iv4;
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onContinueClick(10,3000)) {
                    Log.d("", "显示: ");
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    /**
     * @param len  需要监听几次点击事件数组的长度就为几
     * @param time 规定的时间
     * @return
     */
    private static long[] mClicks = null;
    public static boolean onContinueClick(int len, long time) {
        if (mClicks == null) {
            mClicks = new long[len];
        }
        //将mClicks数组内的所有记录的时间左移一个位置
        System.arraycopy(mClicks, 1, mClicks, 0, mClicks.length - 1);
        //获得当前系统已经启动的时间
        mClicks[mClicks.length - 1] = SystemClock.uptimeMillis();
        //点击的时间差小于time就认为是连续点击
        if ((mClicks[0] + time) >= SystemClock.uptimeMillis()) {
            mClicks = null;
            return true;
        }
        return false;
    }



    //设置spinner
    private void setSpinner() {

        usrsList.add("eee");
        usrsList.add("stc");
        usrsList.add("stc1");
        usrsList.add("stc2");
        usrsList.add("stc3");
        System.out.println(usrsList);

        //spinner适配器
        SimpleArrayAdapter type_Adapter1 = new SimpleArrayAdapter<String>(this, R.layout.spinner_showed_item, usrsList);
        spinner.setAdapter(type_Adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usr = usrsList.get(position);
                System.out.println("usrsList:"+usr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    void startTitleThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while ( true ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            TitleViewModel titleViewModel=new ViewModelProvider(MainActivity.mainActivity,new ViewModelProvider.AndroidViewModelFactory(MainActivity.mainActivity.getApplication())).get(TitleViewModel.class);
                            titleViewModel.getTitle().observe(MainActivity.mainActivity, new Observer<Title>() {
                                @Override
                                public void onChanged(Title i) {

                                    update( titleViewModel.getTitle().getValue().getTitleTime(),"",titleViewModel.getTitle().getValue().getTitleWendu());
                                }
                            });

                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time = dateFormatter.format(Calendar.getInstance().getTime());//获取当前时间
                            title.setTitleTime(time);
                            title.setTitleWendu("37°C");
                            titleViewModel.getTitle().setValue(title);
//                            System.out.println("time--------------------:"+time);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();

        et1.setText(processViewModel.getProcess().getValue().toString());
    }


    private void init() {
        tv=findViewById(R.id.tv1);
        tv2=findViewById(R.id.title_middle);
        tvtime=findViewById(R.id.title_time);

        pb=findViewById(R.id.pb1);
        et=findViewById(R.id.editText);
        et1=findViewById(R.id.editText2);
        spinner=findViewById(R.id.spinner);

        iv1=findViewById(R.id.imageView);
        iv2=findViewById(R.id.imageView2);
        iv3=findViewById(R.id.imageView3);
        iv4=findViewById(R.id.imageView4);

        constraintLayout=findViewById(R.id.cl);
        imageView=findViewById(R.id.imageView7);

        processViewModel=new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProcessViewModel.class);

        title=new Title();
    }

    public void bt3(View view) {

        //对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("对话框")
                .setMessage("今天天气怎么样")
                .setPositiveButton("记录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        tv.setText("按钮1");
//                        pb.setVisibility(View.VISIBLE);
                        startActivity(new Intent(MainActivity.this,Main4Activity.class));
                        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("查看记录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this,Main3Activity.class));
                        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
//                        finish();
                    }
                })
                .create()
                .show();
    }

    public void bt4(View view) {
//        tv.setText("按钮2");
//        pb.setVisibility(View.GONE);

        //popupwindow
        View popupview=getLayoutInflater().inflate(R.layout.ppw,null);

        Button bt1=popupview.findViewById(R.id.bt1);
        Button bt2=popupview.findViewById(R.id.button2);
        Button bt3=popupview.findViewById(R.id.button5);
        Button bt4=popupview.findViewById(R.id.button8);
        Button bt5=popupview.findViewById(R.id.button7);
        Button bt6=popupview.findViewById(R.id.button12);

        final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("按钮2");
                pb.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();

                startActivity(new Intent(MainActivity.this,Main5Activity.class));

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("按钮1");
                pb.setVisibility(View.VISIBLE);
                popupWindow.dismiss();

                //广播发送者
                //8以上安卓版本需发显示广播
                Intent intent=new Intent("com.example.demo1.BROADCAST");
                intent.setComponent(new ComponentName(MainActivity.this, MyReceiver.class));
                sendBroadcast(intent,"com.gaoxiang.permission.receive");
                System.out.println("发送广播");

                startActivity(new Intent(MainActivity.this,Main6Activity.class));
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv.setText("按钮3");
//                popupWindow.dismiss();
                popupWindow.dismiss();
                startActivity(new Intent(MainActivity.this,FragmentActivity.class));

            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv.setText("按钮3");
//                popupWindow.dismiss();
                popupWindow.dismiss();
                startActivity(new Intent(com.example.myapplication.MainActivity.this,FragmentActivity2.class));
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv.setText("按钮3");
//                popupWindow.dismiss();
                popupWindow.dismiss();
                startActivity(new Intent(com.example.myapplication.MainActivity.this,PageViewActivity.class));
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv.setText("按钮3");
//                popupWindow.dismiss();
                popupWindow.dismiss();
//                startActivity(new Intent(MainActivity.this,wifiActivity2.class));
                startActivity(new Intent(MainActivity.this,ProcessActivity.class));
                ProcessThread processThread=new ProcessThread();
                processThread.start();
            }
        });
    }




    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://devapi.qweather.com/v7/weather/now?key=35308ecb64154831af1c38a08d0622db&location=101010100").build();
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
                System.out.println("          sssssssssssssssssssssssssssssssssssssss"+response);
                Gson g = new Gson();
                JsonObject obj = g.fromJson(response, JsonObject.class);
                tv.setText( obj.get("now").getAsJsonObject().get("text").getAsString());
            }
        });
    }


    //保存sp
    public void button11(View view) {
        et=findViewById(R.id.editText);
        SharedPreferences sp = getSharedPreferences("dddd", Context.MODE_PRIVATE);
        sp.edit().putString("stcKey",et.getText().toString()).apply();
        processViewModel.getProcess().setValue(Integer.valueOf(et1.getText().toString()));
        ScreenBrightness.setScreenBrightness(this,Integer.valueOf(et1.getText().toString()));
        System.out.println(processViewModel.getProcess().getValue()+"++++++++++++++");

        //
        if (TimeThread.mPaused==false){
            TimeThread.mPaused=true;
        }
        else{
            TimeThread.mPaused=false;
            TimeThread.getInstance().myResume();
        }

        System.out.println("TimeThread.getInstance().getState():"+TimeThread.getInstance().getState());
    }



    @Override
    protected void onPause() {
        super.onPause();

    }


    /**
     * 显示进度对话框
     */
    private void showProgressDialog(String text) {
        closeProgressDialog();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(text);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
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
            return;
        }
        // 权限复验
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
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


//            // 因为这里 Geocoder对象的 getFromLocation 方法，源码说明中建议在工作线程执行 getFromLocation方法
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    try {
//                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                        result = geocoder.getFromLocation(latitude, longitude, 1);
//                        handler.sendEmptyMessage(WHAT_LOCATE);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
        }else {
//            ToastUtil.showToast(getContext(), "UHello 定位失败");
            System.out.println("location========null");
        }

    }


}
