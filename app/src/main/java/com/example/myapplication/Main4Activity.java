package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bean.MessageWrap;
import com.example.myapplication.room.FruitRepository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

public class Main4Activity extends BaseActivity {

    private EditText et;
    private FruitRepository fruitRepository;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        EventBus.getDefault().register(this);
        fruitRepository=new FruitRepository(this);
        tv=findViewById(R.id.textView9);
    }

    public void bt5(View view) {
        boolean flag=true;
        et=findViewById(R.id.et1);
        String s1=et.getText().toString();

        et=findViewById(R.id.et2);
        String s2=et.getText().toString();

        if ("".equals(s1.trim())||s1==null){
            Toast.makeText(com.example.myapplication.Main4Activity.this, "第一个框不为空", Toast.LENGTH_SHORT).show();
            flag=false;
        }

        if ("".equals(s2.trim())||s2==null){
            Toast.makeText(com.example.myapplication.Main4Activity.this, "第二个框不为空", Toast.LENGTH_SHORT).show();
            flag=false;
        }

        if (flag==true){

//            FruitDbhelper dbHelper = new FruitDbhelper(Main4Activity.this,"stu_db",null,1);
//            //得到一个可写的数据库
//            SQLiteDatabase db =dbHelper.getWritableDatabase();
//            //生成ContentValues对象 //key:列名，value:想插入的值
//            ContentValues cv = new ContentValues();
//            //往ContentValues对象存放数据，键-值对模式
//            cv.put("col1", s1);
//            cv.put("col2", s2);
//            //调用insert方法，将数据插入数据库
//            db.insert("fruit_table", null, cv);
//            //关闭数据库
//            db.close();

            com.example.myapplication.Fruit fruit=new com.example.myapplication.Fruit();
            fruit.setName(s1);
            fruit.setName1(s2);

            fruitRepository.insertFruit(fruit);

            Toast.makeText(Main4Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Main3Activity.class));
            finish();

            System.out.println(s1+"  "+s2);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
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
