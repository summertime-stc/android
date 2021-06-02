package com.example.myapplication.adapt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.bean.DataBean;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义布局，图片+标题
 */

public class ImageTitleAdapter extends BannerAdapter<DataBean, ImageTitleAdapter.ImageTitleHolder> {

    public ImageTitleAdapter(List<DataBean> mDatas) {
        super(mDatas);
    }

    //更新数据
    public void updateData(List<DataBean> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ImageTitleHolder onCreateHolder(ViewGroup parent, int viewType) {
        //未设置监听
//        return new ImageTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title, parent, false));

        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.banner_image_title,parent,false);
        final ImageTitleAdapter.ImageTitleHolder holder = new ImageTitleAdapter.ImageTitleHolder(view);

        //给Image组件设置监听器
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                DataBean dataBean = mDatas.get(position-1);
                Log.d("", "  -----"+dataBean.imageId+"  "+dataBean.imageUrl+"  "+dataBean.title+"  "+dataBean.imageRes+"  "+dataBean.viewType);
            }
        });
        return holder;
    }

    @Override
    public void onBindView(ImageTitleHolder holder, DataBean data, int position, int size) {
//        holder.imageView.setImageResource(data.imageRes);
        holder.title.setText(data.title);

        Glide.with(holder.itemView)
                .load(data.imageUrl)
                .placeholder(R.drawable.loading)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
    }

    static class ImageTitleHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;

        public ImageTitleHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.bannerTitle);
        }
    }

}
