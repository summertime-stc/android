package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.globalvaries.GlobalVaries;
import com.example.myapplication.utils.ScreenBrightness;
import com.example.myapplication.viewmodel.ProcessViewModel;


public class FirstFragment extends Fragment {

    private View root;
    private SeekBar seekBar;
    private TextView textView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (root==null){
            root=inflater.inflate(R.layout.fragment_first,container,false);
        }

        init();
        final ProcessViewModel processViewModel=new ViewModelProvider(MainActivity.mainActivity,new ViewModelProvider.AndroidViewModelFactory(MainActivity.mainActivity.getApplication())).get(ProcessViewModel.class);

        seekBar.setProgress(processViewModel.getProcess().getValue());
        textView.setText(processViewModel.getProcess().getValue().toString());

        processViewModel.getProcess().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                seekBar.setProgress(i);
                textView.setText(processViewModel.getProcess().getValue().toString());
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processViewModel.getProcess().setValue(progress);
                ScreenBrightness.setScreenBrightness(getActivity(),progress);
                Log.d("TAG", "fragment: "+ScreenBrightness.getScreenBrightness(getActivity()));
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final String s= GlobalVaries.getTelNum();

        return root;
    }

    private void init() {
        seekBar=root.findViewById(R.id.seekBar);
        textView=root.findViewById(R.id.textView);
    }




}
