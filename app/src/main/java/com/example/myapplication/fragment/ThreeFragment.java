package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

/**

 * create an instance of this fragment.
 */
public class ThreeFragment extends Fragment {

    private View root;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root==null){
            root=inflater.inflate(R.layout.fragment_three,container,false);
        }

        System.out.println("--------data:");
        Bundle bundle = getArguments();
        String data = bundle.getString("DATA");

        System.out.println("--------data:"+data);

        textView=root.findViewById(R.id.tt3);
        textView.setText(data);
        return root;
    }
}