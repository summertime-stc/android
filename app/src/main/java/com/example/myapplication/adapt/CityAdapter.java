package com.example.myapplication.adapt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CitylistActivity;
import com.example.myapplication.FavCitylistActivity;
import com.example.myapplication.Fruit;
import com.example.myapplication.R;
import com.example.myapplication.SearchCityActivity;
import com.example.myapplication.WeatcherActivity;
import com.example.myapplication.bean.City;
import com.example.myapplication.globalvaries.GlobalVaries;

import java.util.List;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<City> cityList;
    private Context context;
    public static PopupWindow popupWindow;


    public List<City> getCityList() {
        return cityList;
    }

    public void setmFruitList(List<Fruit> mFruitList) {
        this.cityList = cityList;
    }

    //内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{
        View cityView;
        TextView cityname;
        CardView cardView;

        public ViewHolder(View view){
            super(view);
            cityView = view;

            cityname = view.findViewById(R.id.city_name);
//            cardView=view.findViewById(R.id.)
        }
    }

    public CityAdapter(List<City> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
    }

    public CityAdapter(List<City> cityList){
        cityList = cityList;
    }

    //以下是三个RecyclerView.Adapter的方法的重写
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.city_item,parent,false);

//        return new ViewHolder(view);

        final ViewHolder holder = new ViewHolder(view);


        //给整个View设置监听器
        holder.cityView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                City city = cityList.get(position);
                //TODO
                Log.d("", "被单个点了: "+city.getCitycode());

                //适配器里跳转
//                Intent intent = new Intent(context, com.example.myapplication.Main2Activity.class);
//                intent.putExtra("id", fruit.getId().toString());
//                context.startActivity(intent);
                Log.d("", "被点了: ");

                GlobalVaries.setLocation(city.getCityname());
                GlobalVaries.setLocationcode(city.getCitycode());
                SharedPreferences sp = context.getSharedPreferences("dddd", Context.MODE_PRIVATE);
                sp.edit().putString("location",city.getCityname()).apply();
                sp.edit().putString("locationcode",city.getCitycode()).apply();
                Intent intent = new Intent(context, WeatcherActivity.class);
//                intent.putExtra("id", fruit.getId().toString());
                context.startActivity(intent);

                //适配器里调用finish()
                if (SearchCityActivity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    SearchCityActivity activity = (SearchCityActivity) context;
                    activity.finish();
                }

                if (CitylistActivity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    CitylistActivity activity = (CitylistActivity) context;
                    activity.finish();
                }

                if (FavCitylistActivity.class.isInstance(context)) {
                    // 转化为activity，然后finish就行了
                    FavCitylistActivity activity = (FavCitylistActivity) context;
                    activity.finish();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        City city = cityList.get(position);
        holder.cityname.setText(city.getCityname());
    }

    @Override
    public int getItemCount(){
        return cityList.size();
    }



}