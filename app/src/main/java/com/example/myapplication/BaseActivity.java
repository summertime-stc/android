package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.viewmodel.ActivityCollector;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;


public abstract class BaseActivity extends AppCompatActivity {
    public TextView tv_title_time;
    public TextView tv_title_right;
    public TextView tv_middle;

    public Context mContext;

    protected View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        LitePal.initialize(getApplication());
        LitePal.getDatabase();

    }



    public void update(String titleTime, String titleMiddle, String titleWendu) {
        tv_title_time = findViewById(R.id.title_time);
        tv_title_right =findViewById(R.id.title_right);
        tv_middle= findViewById(R.id.title_middle);

        if (tv_title_time != null)
            tv_title_time.setText(titleTime);
        if (tv_title_right != null)
            tv_title_right.setText(titleWendu);
    }

//
//    @Override
//    public void setContentView(int layoutResID) {
//        super.setContentView(layoutResID);
//        ((ViewGroup) getWindow().getDecorView()).addView(rootView);
//    }
    @Override
    protected void onStart() {
        super.onStart();

    }


}
