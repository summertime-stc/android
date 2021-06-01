package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragment.FirstFragment;
import com.example.myapplication.fragment.SecFragment;
import com.example.myapplication.globalvaries.GlobalVaries;

public class FragmentActivity2 extends AppCompatActivity {

    private Button bt1;
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);

        bt1=findViewById(R.id.button6);
        bt2=findViewById(R.id.button9);

        replaceFragment(new FirstFragment(),bt1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FirstFragment(),bt1);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SecFragment(),bt2);
            }
        });
    }

    private void replaceFragment(Fragment fragment, Button bt) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transation = fragmentManager.beginTransaction();
        transation.replace(R.id.frag3,fragment);
        transation.addToBackStack(null);

        GlobalVaries.setTelNum(bt.getText().toString());

        transation.commit();
    }
}
