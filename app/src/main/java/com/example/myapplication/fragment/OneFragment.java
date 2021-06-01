package com.example.myapplication.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapt.ViewPager2Adaper;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class OneFragment extends Fragment {
    private View root;

    private MagicIndicator magicIndicator;
    private ViewPager2 viewPager2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root==null){
            root=inflater.inflate(R.layout.fragment_one,container,false);
        }

        List<String> list=new ArrayList<>();
        list.add("测试1");
        list.add("测试2");
        list.add("测试3");
        list.add("测试4");
        list.add("测试5");
        list.add("测试6");
        list.add("测试7");
        list.add("测试8");

        magicIndicator = root.findViewById(R.id.magic_indicator);
        viewPager2=root.findViewById(R.id.vp3);

        List<String> list1=new ArrayList<>();
        for (int i = 0; i < 8; i++){
//            list1.add(getStringFromAssetsFile(root.getContext(),"stringhelp"+(i+1)));
            list1.add(getStringFromAssetsFile(root.getContext(),"stringhelp1"));
//            图片
//            img_help.setImageBitmap(getImageFromAssetsFile(this,"imagehelp"+(i+1)+".jpg"));
        }

        ViewPager2Adaper viewPager2Adaper=new ViewPager2Adaper(list1);
        viewPager2.setAdapter(viewPager2Adaper);


        final CommonNavigator commonNavigator = new CommonNavigator(root.getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {


            @Override
            public int getCount() {
                return list == null ? 0 : list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);

                clipPagerTitleView.setText("测试" + index);
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager2.setCurrentItem(index);
                    }
                });

                return clipPagerTitleView;
            }

            //此为tab指示器回调
            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);


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

        //设置给magicIndicatormagicIndicator.setNavigator(commonNavigator);

        return root;
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
}