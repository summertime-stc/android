package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.adapt.ViewPagerAdaper;
import com.example.myapplication.adapt.viewPageAdapter;
import com.example.myapplication.fragment.SecFragment;
import com.example.myapplication.fragment.TestFragment;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.util.ArrayList;
import java.util.List;


public class wifiActivity2 extends AppCompatActivity {

 /*
 　 wifiManager.WIFI_STATE_DISABLED (1)
    wifiManager..WIFI_STATE_ENABLED (3)
　  wifiManager..WIFI_STATE_DISABLING (0)
　　wifiManager..WIFI_STATE_ENABLING  (2)
  */


    private Button bt1;
    WifiManager wifiManager;
    private List<ScanResult> WifiList;
    private WifiInfo wifiInfo;
    private List WifiConfiguration;
    private ScanResult scanResult;
    private StringBuffer stringBuffer = new StringBuffer();
    private ViewPager2 vp2;
    private NoScrollViewPager viewPager;
    private LayoutInflater inflater;
    private static final int REQUEST_CODE_SCAN_ONE = 0X01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //关闭标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wifi);
        init();
        wifiManager.setWifiEnabled(true);
        inflater = getLayoutInflater();

        vp2=findViewById(R.id.vp3);
        viewPager=(NoScrollViewPager)findViewById(R.id.vp22);


        List<View> pages = new ArrayList<>();
        View v1 = inflater.inflate(R.layout.fragment_test, null);
        pages.add(v1);
        View v2 = inflater.inflate(R.layout.fragment_test, null);
        pages.add(v2);

        PagerAdapter adapter = new viewPageAdapter(pages);
        viewPager.setAdapter(adapter);
        viewPager.setEnabled(false);
        viewPager.setNoScroll(false);

        viewPager.setCurrentItem(0);

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments=getfragments();
        ViewPagerAdaper viewPagerAdaper=new ViewPagerAdaper(getSupportFragmentManager(),getLifecycle(),fragments);
        vp2.setUserInputEnabled(false);
        vp2.setAdapter(viewPagerAdaper);


    }

    private ArrayList<Fragment> getfragments() {
        ArrayList<Fragment> fragments=new ArrayList<>();

        TestFragment fragment=new TestFragment();
//
//        TextView vt=fragment.getActivity().findViewById(R.id.textView12);
//        vt.setText("fragment1:tv1");
//        TextView vt1=fragment.getActivity().findViewById(R.id.textView14);
//        vt1.setText("fragment1:tv2");
//        TextView vt2=fragment.getActivity().findViewById(R.id.textView15);
//        vt2.setText("fragment1:tv3");
        fragments.add(new TestFragment());

        fragments.add(new TestFragment());

        fragments.add(new TestFragment());
        return fragments;
    }

    private void init() {
        bt1=findViewById(R.id.button13);
        //1.WifiManager实例化
        wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        Log.d("TAG", "进入："+wifiManager.isWifiEnabled());

        if (!wifiManager.isWifiEnabled()) {
            bt1.setText("打开wifi");
        }
        else{
            bt1.setText("关闭wifi");
        }
    }


    public void wifiswitch(View view) {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            bt1.setText("关闭wifi");
            Log.d("TAG", wifiManager.isWifiEnabled()+"  wifiswitch: 开启了wifi     "+ change());

            wifiManager.startScan();
            WifiList = wifiManager.getScanResults();
            wifiInfo = wifiManager.getConnectionInfo();
            if (stringBuffer != null) {
                stringBuffer = new StringBuffer();
            }
            stringBuffer = stringBuffer.append("Wifi名").append(" ").append("Wifi地址").append(" ")
                    .append("Wifi频率").append("").append("Wifi信号")
                    .append("\n");
            if (WifiList != null) {
                for (int i = 0; i < WifiList.size(); i++) {
                    scanResult = WifiList.get(i);
                    stringBuffer = stringBuffer.append(scanResult.SSID).append(" ")
                            .append(scanResult.BSSID).append(" ")
                            .append(scanResult.frequency).append(" ")
                            .append(scanResult.level).append("\n");

                    Log.d("TAG", stringBuffer.toString());
                }
                stringBuffer = stringBuffer.append
                        ("-----------------------------------------------").append("\n");
                Log.d("TAG", stringBuffer.toString());
                stringBuffer = stringBuffer.append("当前Wifi—BSSID").append(":").append(wifiInfo.getBSSID()).append("\n")
                        .append("当前wifi—HiddenSSID").append(": ").append(wifiInfo.getHiddenSSID()).append("\n")
                        .append("当前Wifi—IpAddress").append(": ").append(wifiInfo.getIpAddress()).append("\n")
                        .append("当前Wifi—LinkSpeed").append(": ").append(wifiInfo.getLinkSpeed()).append("\n")
                        .append("当前Wifi—MacAddress").append(": ").append(wifiInfo.getMacAddress()).append("\n")
                        .append("当前Wifi—Network ID").append(": ").append(wifiInfo.getNetworkId()).append("\n")
                        .append("当前Wifi—RSSI").append(": ").append(wifiInfo.getRssi()).append("\n")
                        .append("当前Wifi—SSID").append(": ").append(wifiInfo.getSSID()).append("\n")
                        .append("-----------------------------------------------").append("\n")
                        .append("全部打印出关于本机Wifi信息").append(": ").append
                                (wifiInfo.toString());
                Log.d("TAG", stringBuffer.toString());
            }
            //stringBuffer=stringBuffer.append("-----------------------------------------------").append("\n");
            //textView.setText()
        }
        else{
            wifiManager.setWifiEnabled(false);
            bt1.setText("打开wifi");
            Log.d("TAG", wifiManager.isWifiEnabled()+"   wifiswitch: 关闭了wifi     "+change());

        }
    }

    public void wifiscan(View view) {
        wifiManager.startScan();
        WifiList = wifiManager.getScanResults();
        wifiInfo = wifiManager.getConnectionInfo();
        if (stringBuffer != null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer = stringBuffer.append("Wifi名").append(" ").append("Wifi地址").append(" ")
                .append("Wifi频率").append("").append("Wifi信号")
                .append("\n");
        if (WifiList != null) {
            for (int i = 0; i < WifiList.size(); i++) {
                scanResult = WifiList.get(i);
                stringBuffer = stringBuffer.append(scanResult.SSID).append(" ")
                        .append(scanResult.BSSID).append(" ")
                        .append(scanResult.frequency).append(" ")
                        .append(scanResult.level).append("\n");
                Log.d("TAG", stringBuffer.toString());            }
            stringBuffer = stringBuffer.append
                    ("-----------------------------------------------").append("\n");
            Log.d("TAG", stringBuffer.toString());
            stringBuffer = stringBuffer.append("当前Wifi—BSSID").append(":").append(wifiInfo.getBSSID()).append("\n")
                    .append("当前wifi—HiddenSSID").append(": ").append(wifiInfo.getHiddenSSID()).append("\n")
                    .append("当前Wifi—IpAddress").append(": ").append(wifiInfo.getIpAddress()).append("\n")
                    .append("当前Wifi—LinkSpeed").append(": ").append(wifiInfo.getLinkSpeed()).append("\n")
                    .append("当前Wifi—MacAddress").append(": ").append(wifiInfo.getMacAddress()).append("\n")
                    .append("当前Wifi—Network ID").append(": ").append(wifiInfo.getNetworkId()).append("\n")
                    .append("当前Wifi—RSSI").append(": ").append(wifiInfo.getRssi()).append("\n")
                    .append("当前Wifi—SSID").append(": ").append(wifiInfo.getSSID()).append("\n")
                    .append("-----------------------------------------------").append("\n")
                    .append("全部打印出关于本机Wifi信息").append(": ").append
                            (wifiInfo.toString());
            Log.d("TAG", stringBuffer.toString());        }
    }


    public String change() {
        String temp = null;
        switch (wifiManager.getWifiState()) {
            case 0:
                temp = "wifi正在关闭ING";
                break;
            case 1:
                temp = "wifi已经关闭";
                break;
            case 2:
                temp = "wifi正在打开ING";
                break;
            case 3:
                temp = "wifi已经打开";
                break;
            default:
                break;
        }
        return temp;
    }

    public void tonew(View view) {
        startActivity(new Intent(wifiActivity2.this,NewActivity.class));
    }

    public void scan(View view) {
        ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE, new HmsScanAnalyzerOptions.Creator().create());
    }

    /**
     * Event for receiving the activity result.
     *
     * @param requestCode Request code.
     * @param resultCode Result code.
     * @param data        Result.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        //Default View
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj != null) {
                Intent intent = new Intent(this, ScanResultActivity.class);
                intent.putExtra("SCAN_RESULT", obj);
                startActivity(intent);
            }
            //MultiProcessor & Bitmap
        }
    }

    public void toweather(View view) {
        startActivity(new Intent(wifiActivity2.this,WeatcherActivity.class));
    }
}