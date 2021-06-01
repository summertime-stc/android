package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.adapt.ViewPager2Adaper;
import com.example.myapplication.navigator.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private View view1;
    private List<String> list;
    private TextView tv;
    private MagicIndicator magicIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标签栏
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        setContentView(R.layout.activity_main6);
        
        init();

//        LayoutInflater inflater = getLayoutInflater();
//        for (int i = 0; i < 3; i++) {
//            view1 = inflater.inflate(R.layout.viewpage2, null);
//            viewList.add(view1);
//            tv = (TextView) view1.findViewById(R.id.textView8);
//            tv.setText("违规违纪给我今儿个i晋文公即位几位结构较为关键哦i微结构鸡尾酒攻击为国际我今儿购物i结果");
//        }
        list=new ArrayList<>();
        for (int i = 0; i < 3; i++){
            list.add(getStringFromAssetsFile(this,"stringhelp"+(i+1)));

//            图片
//            img_help.setImageBitmap(getImageFromAssetsFile(this,"imagehelp"+(i+1)+".jpg"));
        }

        ViewPager2Adaper viewPager2Adaper=new ViewPager2Adaper(list);
        viewPager2.setAdapter(viewPager2Adaper);

        List<String> ls=new ArrayList<>();
        ls.add("1");
        ls.add("2");
        ls.add("3");

        //自带的Navigator
//        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//
//            @Override
//            public int getCount() {
//                return list == null ? 0 : list.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, int index) {
//                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
////                list.get(index)
//                clipPagerTitleView.setText(ls.get(index));
//                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
//                clipPagerTitleView.setClipColor(Color.BLACK);
//
//                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        viewPager2.setCurrentItem(index);
//                    }
//                });
//
//                return clipPagerTitleView;
//            }
//
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                return null;    // 没有指示器，因为title的指示作用已经很明显了
//            }
//        });
//        magicIndicator.setNavigator(commonNavigator);


        ScaleCircleNavigator navigator = new ScaleCircleNavigator(this);
        navigator.setCircleCount(ls.size());
        navigator.setNormalCircleColor(Color.BLACK);
        navigator.setSelectedCircleColor(Color.CYAN);
        navigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                viewPager2.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(navigator);

//        滑动监听
      viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              super.onPageScrolled(position, positionOffset, positionOffsetPixels);
              magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);

          }

          @Override
          public void onPageSelected(int position) {
              super.onPageSelected(position);
              magicIndicator.onPageSelected(position);

          }

          @Override
          public void onPageScrollStateChanged(int state) {
              super.onPageScrollStateChanged(state);
              magicIndicator.onPageScrollStateChanged(state);

          }
      });

    }


    //读取assets文件
    private String getStringFromAssetsFile(Context context,String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //读取图片
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void init() {
        viewPager2=findViewById(R.id.vp2);
        magicIndicator = (MagicIndicator) findViewById(R.id.bottom_indicator);
    }


    public void backtomain(View view) {
        startActivity(new Intent(Main6Activity.this,MainActivity.class));
    }
}