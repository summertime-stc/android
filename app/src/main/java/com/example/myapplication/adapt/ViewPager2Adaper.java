package com.example.myapplication.adapt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


import java.util.List;

public class ViewPager2Adaper extends RecyclerView.Adapter<ViewPager2Adaper.ViewPageViewHolder> {

    private List<String> list;

    public ViewPager2Adaper(List<String> list) {
        this.list=list;
    }


    @NonNull
    @Override
    public ViewPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpage2, parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewPageViewHolder holder, int position) {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewPageViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView tv;
        public ViewPageViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.cy1);
            tv=itemView.findViewById(R.id.textView8);
        }
    }
}

