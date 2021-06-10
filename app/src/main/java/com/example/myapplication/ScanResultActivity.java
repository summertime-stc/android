package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;

public class ScanResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标签栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_scan_result);

        HmsScan hmsScan = getIntent().getParcelableExtra(ScanUtil.RESULT);
        System.out.println("--------------obj.getOriginalValue():"+hmsScan.getOriginalValue()+"      hmsScan.getScanType():"+hmsScan.getScanType()+"       hmsScan.getScanTypeForm()"+hmsScan.getScanTypeForm());
    }
}