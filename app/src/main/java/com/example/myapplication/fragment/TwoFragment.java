package com.example.myapplication.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapt.ViewPagerAdaper;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment {

    public String s;
    private MagicIndicator magicIndicator;
    private ViewPager2 viewPager2;
    private Fragment f1,f2;

    public TwoFragment(String s) {
        this.s = s;
    }
    public TwoFragment() {}

    private View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root==null){
            root=inflater.inflate(R.layout.fragment_two,container,false);
        }
        magicIndicator = root.findViewById(R.id.magic_indicator);
        viewPager2=root.findViewById(R.id.vp3);

        f1 = new ListFragment();
        f2 = new SecFragment();
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(f1);
        fragments.add(f2);
        ViewPagerAdaper viewPagerAdaper=new ViewPagerAdaper(getChildFragmentManager(),getLifecycle(),fragments);

        viewPager2.setAdapter(viewPagerAdaper);


        List<String> list=new ArrayList<>();
        list.add("测试1");
        list.add("测试2");

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
        return root;
    }
}