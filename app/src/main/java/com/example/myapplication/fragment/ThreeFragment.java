package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapt.ImageTitleAdapter;
import com.example.myapplication.bean.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;


/**

 * create an instance of this fragment.
 */
public class ThreeFragment extends Fragment {

    private View root;

    @BindView(R.id.banner)
    Banner banner;

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
        ButterKnife.bind(root);
        System.out.println("--------data:");
        Bundle bundle = getArguments();
        String data = bundle.getString("DATA");

        System.out.println("--------data:"+data);


        banner=root.findViewById(R.id.banner);


//        banner自带适配器
//        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
//            @Override
//            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
//                //图片加载自己实现
//                Glide.with(holder.itemView)
//                        .load(data.imageUrl)
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                        .into(holder.imageView);
//
//
//            }
//        })
//                .addBannerLifecycleObserver(this)//添加生命周期观察者
//                .setIndicator(new CircleIndicator(root.getContext()));
        ImageTitleAdapter adapter=new ImageTitleAdapter(DataBean.getTestData());
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(adapter)
                .setBannerGalleryEffect(20,0)
                .setIndicator(new CircleIndicator(root.getContext()));

        adapter.updateData(DataBean.getTestData3());

        return root;
    }
}