package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adapt.ViewPagerAdaper;
import com.example.myapplication.fragment.FirstFragment;
import com.example.myapplication.fragment.SecFragment;

import java.util.ArrayList;

public class PageViewActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ImageView iv1,iv2,iv3,iv4,flagiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);

        viewPager2=findViewById(R.id.viewpage);
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecFragment());
        fragments.add(new FirstFragment());
        fragments.add(new SecFragment());
        ViewPagerAdaper viewPagerAdaper=new ViewPagerAdaper(getSupportFragmentManager(),getLifecycle(),fragments);

        viewPager2.setAdapter(viewPagerAdaper);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch(position){
                    case 0:
                        flagiv.setSelected(false);
                        iv1.setSelected(true);
                        flagiv=iv1;
                        break;
                    case 1:
                        flagiv.setSelected(false);
                        iv2.setSelected(true);
                        flagiv=iv2;
                        break;
                    case 2:
                        flagiv.setSelected(false);
                        iv3.setSelected(true);
                        flagiv=iv3;
                        break;
                    case 3:
                        flagiv.setSelected(false);
                        iv4.setSelected(true);
                        flagiv=iv4;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        iv1=findViewById(R.id.imageView);
        iv2=findViewById(R.id.imageView2);
        iv3=findViewById(R.id.imageView3);
        iv4=findViewById(R.id.imageView4);
        iv1.setSelected(true);
        flagiv=iv1;

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv1.setSelected(true);
                flagiv=iv1;
                viewPager2.setCurrentItem(0);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv2.setSelected(true);
                flagiv=iv2;
                viewPager2.setCurrentItem(1);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv3.setSelected(true);
                flagiv=iv3;
                viewPager2.setCurrentItem(2);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagiv.setSelected(false);
                iv4.setSelected(true);
                flagiv=iv4;
                viewPager2.setCurrentItem(3);
            }
        });
    }
}
