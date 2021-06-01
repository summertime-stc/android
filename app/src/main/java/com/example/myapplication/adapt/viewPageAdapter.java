package com.example.myapplication.adapt;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class viewPageAdapter extends PagerAdapter {


private List<View> listView;
public viewPageAdapter(List<View>listView ){

        this.listView =listView;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
// TODO Auto-generated method stub
        ((ViewPager)container).addView(listView.get(position));
//        Log.i("instantiateItem", "position="+position);
        return listView.get(position);
        }

@Override
public void destroyItem(View container, int position, Object object) {
// TODO Auto-generated method stub
//        Log.i("destroyItem", "position="+position);
        ((ViewGroup)container).removeView(listView.get(position));
        }


@Override
public int getCount() {
// TODO Auto-generated method stub
        return listView.size();
        }


@Override
public boolean isViewFromObject(View arg0, Object arg1) {
// TODO Auto-generated method stub


        return arg0 == arg1;
        }

        }
