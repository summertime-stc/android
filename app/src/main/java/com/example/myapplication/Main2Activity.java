package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bean.MessageWrap;
import com.example.myapplication.room.FruitRepository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Main2Activity extends AppCompatActivity {

    private EditText et1;
    private EditText et2;
    private String data;
    private FruitRepository fruitRepository;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EventBus.getDefault().register(this);

        fruitRepository=new FruitRepository(this);

        Intent intent = getIntent();
        data = intent.getStringExtra("id");
        System.out.println(data+"11111");

//        FruitDbhelper dbHelper = new FruitDbhelper(Main2Activity.this,"stu_db",null,1);
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
//        List<Fruit> fruitList=new ArrayList<>();
//
//        Cursor cursor = db.query("fruit_table", new String[]{"_id","col1","col2","col3"}, "_id=?", new String[]{data}, null, null, null);
//        while(cursor.moveToNext()){
//            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
//            String col1 = cursor.getString(cursor.getColumnIndex("col1"));
//            String col2 = cursor.getString(cursor.getColumnIndex("col2"));
//            String col3 = cursor.getString(cursor.getColumnIndex("col3"));
//            System.out.println("query2------->" + "id："+id+ "姓名："+col1+" "+"年龄："+col2+" "+"性别："+col3);
//
//            et1=findViewById(R.id.et3);
//            et1.setText(col1);
//
//            et2=findViewById(R.id.et4);
//            et2.setText(col2);
//
//        }
//        //关闭数据库
//        db.close();
        Fruit fruit=fruitRepository.getFruitById(Integer.valueOf(data));
        et1=findViewById(R.id.et3);
        et1.setText(fruit.getName());

        et2=findViewById(R.id.et4);
        et2.setText(fruit.getName1());

        tv=findViewById(R.id.textView10);
    }

    public void tomain(View view) {
        startActivity(new Intent(this, com.example.myapplication.Main3Activity.class));
    }

    public void act2to3(View view) {
//        FruitDbhelper dbHelper = new FruitDbhelper(Main2Activity.this,"stu_db",null,1);
//        //得到一个可写的数据库
//        SQLiteDatabase db =dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        et1=findViewById(R.id.et3);
//        et2=findViewById(R.id.et4);
//        cv.put("col1",et1.getText().toString());
//        cv.put("col2",et2.getText().toString());
//        //where 子句 "?"是占位符号，对应后面的"1",
//
//        String whereClause="_id=?";
//        String [] whereArgs = {data};
//
//        //参数1 是要更新的表名
//        //参数2 是一个ContentValeus对象
//        //参数3 是where子句
//
//        db.update("fruit_table", cv, whereClause, whereArgs);
        et1=findViewById(R.id.et3);
        et2=findViewById(R.id.et4);

        Fruit fruit1=new Fruit(Integer.valueOf(data),et1.getText().toString(),et2.getText().toString(),0);

        System.out.println("wwwwwwwwww"+fruit1.toString());
        fruitRepository.updateFruit(fruit1);
        startActivity(new Intent(this, com.example.myapplication.Main3Activity.class));
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageWrap message) {
        tv.setText(message.message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
