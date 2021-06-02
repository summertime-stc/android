package com.example.myapplication.adapt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fruit;
import com.example.myapplication.R;
import com.example.myapplication.globalvaries.GlobalVaries;

import java.util.List;


public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<com.example.myapplication.Fruit> mFruitList;
    private Context context;
    public static PopupWindow popupWindow;


    public List<Fruit> getmFruitList() {
        return mFruitList;
    }

    public void setmFruitList(List<com.example.myapplication.Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    //内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        TextView fruitName1;

        public ViewHolder(View view){
            super(view);
            fruitView = view;
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.fruit_name);
            fruitName1 = view.findViewById(R.id.fruit_name1);
        }
    }

    public FruitAdapter(List<com.example.myapplication.Fruit> mFruitList, Context context) {
        this.mFruitList = mFruitList;
        this.context = context;
    }

    public FruitAdapter(List<com.example.myapplication.Fruit> fruitList){
        mFruitList = fruitList;
    }

    //以下是三个RecyclerView.Adapter的方法的重写
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fruit_item,parent,false);

//        return new ViewHolder(view);

        final ViewHolder holder = new ViewHolder(view);


        //给整个View设置监听器
        holder.fruitView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                com.example.myapplication.Fruit fruit = mFruitList.get(position);
                //TODO
                Log.d("", "被单个点了: "+fruit.getId());

                //适配器里跳转
                Intent intent = new Intent(context, com.example.myapplication.Main2Activity.class);
                intent.putExtra("id", fruit.getId().toString());
                context.startActivity(intent);
                Log.d("", "被点了: ");


            }
        });

        //长按事件
        holder.fruitView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //popupwindow

                //activity中加载layout
                //View popupview=activity.getLayoutInflater().inflate(R.layout.ppw1,null);

                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                GlobalVaries.setTelNum(fruit.getId().toString());

                //适配器里加载layout
                LayoutInflater nflater = LayoutInflater.from(context);
                View popupview = nflater.inflate(R.layout.ppw1,null);

//                Button bt1=popupview.findViewById(R.id.bt7);


                popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
                popupWindow.showAsDropDown(v);

                Log.d("", "被长按点了: ");
                return true;
            }
        });


        //给Image组件设置监听器
        holder.fruitImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Fruit fruit = mFruitList.get(position);
//                //TODO
//                Log.d("", "被单个点了: "+fruit.getId());
//
//                //适配器里跳转
//                Intent intent = new Intent(context, Main2Activity.class);
//                intent.putExtra("id", fruit.getId().toString());
//                context.startActivity(intent);
                Log.d("", "被单个图片点了: ");
            }
        });

        holder.fruitName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO
                Log.d("", "被单个名字点了: ");
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitName1.setText(fruit.getName1());
        holder.fruitImage.setImageResource(fruit.getImageId());
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }



}