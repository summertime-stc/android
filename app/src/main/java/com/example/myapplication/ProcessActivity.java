package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.bean.MessageWrap;
import com.example.myapplication.bean.ProcessValue;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ProcessActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        EventBus.getDefault().register(this);
        init();

    }

    private void init() {
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.textView11);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProcessValue processValue) {
        progressBar.setProgress(Integer.valueOf(processValue.process));
        textView.setText(processValue.process+"%");

        if ("100".equals(processValue.process)){
            startActivity(new Intent(ProcessActivity.this,wifiActivity2.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}