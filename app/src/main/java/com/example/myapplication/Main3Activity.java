package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.adapt.FruitAdapter;
import com.example.myapplication.globalvaries.GlobalVaries;
import com.example.myapplication.viewmodel.FruitViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.adapt.FruitAdapter.popupWindow;

public class Main3Activity extends BaseActivity {

    private FruitAdapter fruitAdapter;
    private RecyclerView recyclerView;
    private List<Fruit> fruitList;
    private TextView tv1;
    static Main3Activity main3Activity;
    FruitViewModel fruitViewModel;

//    private TextView tv_title_time;
//    private TextView tv_title_right;
//    private TextView tv_middle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        System.out.println("--------------------------main3------------------------------");
        init();


//        tv_title_time=findViewById(R.id.title_time);
//        tv_title_right = findViewById(R.id.title_right);
//        tv_middle= findViewById(R.id.title_middle);
//
//        tv_title_time.setText("111");
//        tv_title_right.setText("111");
//        tv_middle.setText("111");



        fruitList=new ArrayList<>();

//        FruitDbhelper dbHelper = new FruitDbhelper(Main3Activity.this,"stu_db",null,1);
//        //得到一个可写的数据库
//        SQLiteDatabase db =dbHelper.getWritableDatabase();
//        //参数1：表名
//        //参数2：要想显示的列
//        //参数3：where子句
//        //参数4：where子句对应的条件值
//        //参数5：分组方式
//        //参数6：having条件
//        //参数7：排序方式
//
//        Cursor cursor = db.query("fruit_table", new String[]{"_id","col1","col2","col3"}, null,null, null, null, null);
//        while(cursor.moveToNext()){
//            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
//            String col1 = cursor.getString(cursor.getColumnIndex("col1"));
//            String col2 = cursor.getString(cursor.getColumnIndex("col2"));
//            String col3 = cursor.getString(cursor.getColumnIndex("col3"));
//            System.out.println("query------->" + "id："+id+ "姓名："+col1+" "+"年龄："+col2+" "+"性别："+col3);
//            if (col3==null){
//                Fruit fruit = new Fruit(id,col1,col2, R.drawable.jpg1);
//                fruitList.add(fruit);
//            }
//            else{
//                Fruit fruit = new Fruit(id,col1,col2, R.drawable.jpg1);
//                fruitList.add(fruit);
//            }
//        }
//        //关闭数据库
//        db.close();

        //加载list列表数据
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        fruitAdapter = new FruitAdapter(fruitList,this);
        recyclerView.setAdapter(fruitAdapter);

        fruitViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(FruitViewModel.class);

        fruitViewModel.getAllFruitsLive().observe(this, new Observer<List<Fruit>>() {
            @Override
            public void onChanged(List<Fruit> fruits) {
                fruitAdapter.setmFruitList(fruits);
                fruitAdapter.notifyDataSetChanged();
            }
        });

        //SwipeRefreshLayout功能介绍
//        final SwipeRefreshLayout swip_refresh_layout=findViewById(R.id.swipeLayout);
//        swip_refresh_layout.setColorSchemeResources(R.color.colorPrimary);
//        swip_refresh_layout.setProgressBackgroundColorSchemeColor(R.color.colorPrimaryDark);
//        swip_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {

//                fruitAdapter.setmFruitList(fruitList);
//                fruitAdapter.notifyDataSetChanged();
//                swip_refresh_layout.setRefreshing(false);
//            }
//        });

        RefreshLayout mRefreshLayout = findViewById(R.id.refreshLayout);
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

    }

    //初始化
    private void init() {
        main3Activity=this;
        tv1=findViewById(R.id.title_middle);
    }

    public void bt8(View view) {

        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        Fruit fruit=new Fruit(1,"stcstc","stcstc", R.drawable.jpg1);
        fruitList.add(fruit);
        fruitAdapter.notifyItemInserted(fruitList.size());
        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        popupWindow.dismiss();
    }

    public void bt7(View view){
        GlobalVaries.getTelNum();
        System.out.println("     -----------------          "+GlobalVaries.getTelNum());

        //dbhelper删除
//        FruitDbhelper dbHelper = new FruitDbhelper(this,"stu_db",null,1);
//        //得到一个可写的数据库
//        SQLiteDatabase db =dbHelper.getWritableDatabase();
//        String sql = "delete from fruit_table where _id = "+GlobalVaries.getTelNum();
//        //执行SQL语句
//        db.execSQL(sql);
        Fruit fruit=new Fruit(Integer.valueOf(GlobalVaries.getTelNum()));
        fruitViewModel.DelFruit(fruit);

//        int position = 9999999;
//        for(int i=0;i<fruitList.size();i++){
//            System.out.println("index:"+i+"----"+fruitList.get(i).getId()+"--"+GlobalVaries.getTelNum());
//            if(fruitList.get(i).getId().toString().equals(GlobalVaries.getTelNum())){
//                position=i;
//            }
//        }
//        System.out.println("position:"+position);
//        if (position!=9999999){
//            fruitList.remove(position);
//            fruitAdapter.notifyItemRemoved(position);
//        }

        popupWindow.dismiss();

    }

    @Override
    protected void onPause() {
        super.onPause();

        //返回动画
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

}
