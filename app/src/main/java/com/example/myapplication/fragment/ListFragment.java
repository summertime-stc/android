package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Fruit;
import com.example.myapplication.R;
import com.example.myapplication.adapt.FruitAdapter;
import com.example.myapplication.viewmodel.FruitViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private FruitAdapter fruitAdapter;
    private List<Fruit> fruitList;
    FruitViewModel fruitViewModel;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root==null){
            root=inflater.inflate(R.layout.fragment_list,container,false);
        }

        fruitList=new ArrayList<>();

        recyclerView = root.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        fruitAdapter = new FruitAdapter(fruitList,root.getContext());
        recyclerView.setAdapter(fruitAdapter);

        fruitViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FruitViewModel.class);

        fruitViewModel.getAllFruitsLive().observe(this, new Observer<List<Fruit>>() {
            @Override
            public void onChanged(List<Fruit> fruits) {
                fruitAdapter.setmFruitList(fruits);
                fruitAdapter.notifyDataSetChanged();
            }
        });

        RefreshLayout mRefreshLayout = root.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                fruitList.add(new Fruit(3,"wdwd","wwd",R.drawable.jpg1));
//                fruitAdapter.setmFruitList(fruitList);

                fruitViewModel.insertFruit(new Fruit("wdwd","wwd",R.drawable.jpg1));

                fruitAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh(true);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                fruitList.add(new Fruit(3,"wd66wd","ww55d",R.drawable.jpg1));
//                fruitAdapter.setmFruitList(fruitList);

                fruitViewModel.insertFruit(new Fruit("wd66wd","ww55d",R.drawable.jpg1));

                mRefreshLayout.finishLoadMore(true);
                fruitAdapter.notifyDataSetChanged();

            }
        });


        return root;
    }
}