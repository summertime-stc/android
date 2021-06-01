package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private View root;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root==null){
            root=inflater.inflate(R.layout.fragment_test,container,false);
        }
        textView=root.findViewById(R.id.textView12);
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        String mess = null;
        if(bundle!=null){
            mess = bundle.getString("data");
        }
        System.out.println("================"+mess);
        textView.setText(mess);

        return root;
    }
}