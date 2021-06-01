package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.fragment.FirstFragment;
import com.example.myapplication.fragment.FourFragment;
import com.example.myapplication.fragment.OneFragment;
import com.example.myapplication.fragment.ThreeFragment;
import com.example.myapplication.fragment.TwoFragment;
import com.example.myapplication.globalvaries.GlobalVaries;

public class NewActivity extends AppCompatActivity {

    private ImageView iv1,iv2,iv3,iv4;
    private FragmentManager fragmentManager;
    private Fragment f1,f2,f3,f4;

    public static final String KEY_ONE_FRAGMENT = "one_fragment";
    public static final String KEY_TWO_FRAGMENT = "two_fragment";
    public static final String KEY_THREE_FRAGMENT = "three_fragment";
    public static final String KEY_FOUR_FRAGMENT = "four_fragment";
    public static final String KEY_INDEX = "index";
    private String s;


    //重写onSaveInstanceState方法，解决fragmentadd方法重叠问题
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(f1 != null){
            getSupportFragmentManager().putFragment(outState,KEY_ONE_FRAGMENT,f1);
        }
        if(f2 != null){
            getSupportFragmentManager().putFragment(outState,KEY_TWO_FRAGMENT,f2);
        }
        if(f3 != null){
            getSupportFragmentManager().putFragment(outState,KEY_THREE_FRAGMENT,f3);
        }
        if(f4 != null){
            getSupportFragmentManager().putFragment(outState,KEY_FOUR_FRAGMENT,f4);
        }
        outState.putString(KEY_INDEX,s);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new);

        init();
        if(savedInstanceState != null){
            f1 = (OneFragment)getSupportFragmentManager().getFragment(savedInstanceState,KEY_ONE_FRAGMENT);
            f2 = (TwoFragment)getSupportFragmentManager().getFragment(savedInstanceState,KEY_TWO_FRAGMENT);
            f3 = (ThreeFragment)getSupportFragmentManager().getFragment(savedInstanceState,KEY_THREE_FRAGMENT);
            f4 = (FourFragment)getSupportFragmentManager().getFragment(savedInstanceState,KEY_FOUR_FRAGMENT);
            s = savedInstanceState.getString(KEY_INDEX,"1");
        }else{
            showonefragment();
        }

        if (s==null){
            s="1";
        }
        System.out.println("------:"+s);

        if ("1".equals(s)){
            iv1.setSelected(true);
        }
        else if ("2".equals(s)){
            iv2.setSelected(true);
        }
        else if ("3".equals(s)){
            iv3.setSelected(true);
        }
        else{
            iv4.setSelected(true);
        }


//        replaceFragment(new OneFragment());




        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllfalse();
                iv1.setSelected(true);
                showonefragment();
                s="1";

//                replaceFragment(oneFragment);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllfalse();
                iv2.setSelected(true);
                showtwofragment();
                s="2";
//                replaceFragment(new TwoFragment());
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllfalse();
                iv3.setSelected(true);
                showthreefragment();
                s="3";
//                replaceFragment(new ThreeFragment());
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllfalse();
                iv4.setSelected(true);
                showfourfragment();
                s="4";
//                replaceFragment(new FourFragment());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setAllfalse() {
        iv1.setSelected(false);
        iv2.setSelected(false);
        iv3.setSelected(false);
        iv4.setSelected(false);
    }

    private void showonefragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(f1 == null){
            f1 = new OneFragment();
            transaction.add(R.id.fragment1,f1);
        }

        hideAllFragement();
        transaction.show(f1);
        transaction.commit();
    }

    private void showtwofragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(f2 == null){
            //利用构造函数传参
            f2 = new TwoFragment("stcstc");
            transaction.add(R.id.fragment1,f2);
        }

        hideAllFragement();
        transaction.show(f2);
        transaction.commit();
    }

    private void showthreefragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(f3 == null){
            f3 = new ThreeFragment();
            transaction.add(R.id.fragment1,f3);
        }

        hideAllFragement();
        transaction.show(f3);
        transaction.commit();

        Bundle bundle = new Bundle();
        bundle.putString("DATA","ssssss");//这里的values就是我们要传的值

        f3.setArguments(bundle);
    }

    private void showfourfragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(f4 == null){
            f4 = new FourFragment();
            transaction.add(R.id.fragment1,f4);
        }

        hideAllFragement();
        transaction.show(f4);
        transaction.commit();
    }

    private void hideAllFragement() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(f1 != null){
            transaction.hide(f1);
        }
        if(f2 != null){
            transaction.hide(f2);
        }
        if(f3 != null){
            transaction.hide(f3);
        }
        if(f4 != null){
            transaction.hide(f4);
        }

        transaction.commit();
    }


    private void init() {
        iv1=findViewById(R.id.imageView);
        iv2=findViewById(R.id.imageView2);
        iv3=findViewById(R.id.imageView3);
        iv4=findViewById(R.id.imageView4);
        fragmentManager=getSupportFragmentManager();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transation = fragmentManager.beginTransaction();
        transation.replace(R.id.fragment1,fragment);
//        transation.addToBackStack(null);
        transation.commit();
    }
}