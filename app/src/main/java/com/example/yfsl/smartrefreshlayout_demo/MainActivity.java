package com.example.yfsl.smartrefreshlayout_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button classicalRefresh_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_main);

        initView();//初始化空间
        initData();//初始化数据
    }

    private void initView() {
        classicalRefresh_btn = findViewById(R.id.classicalRefresh_btn);
    }

    private void initData() {
        //监听事件
        classicalRefresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象 传入两个互相跳转的页面
                Intent intent = new Intent(MainActivity.this,ClassicalRefreshActivity.class);
                //执行跳转操作
                startActivity(intent);
            }
        });
    }
}
