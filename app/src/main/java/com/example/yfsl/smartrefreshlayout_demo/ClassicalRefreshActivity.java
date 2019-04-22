package com.example.yfsl.smartrefreshlayout_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ClassicalRefreshActivity extends AppCompatActivity {
    private ImageButton back_btn;//返回按钮
    private SmartRefreshLayout smartRefresh;//刷新控件
    private RecyclerView recyclerView,choose_pic_recycler;
    private List<Message> messageList = new ArrayList<>();//实体类对象集合
//
//    private Button btn1,btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_classical);

        initView();//初始化空间 ->找到控件
        initData();//初始化数据 ->实现功能
        initMessage();//初始化实体类
    }

    private void initMessage() {
        for (int i = 0;i<25;i++){
            Message message = new Message("第"+i+"条消息");
            messageList.add(message);
        }
    }

    private void initView() {
        back_btn = findViewById(R.id.back_btn);
        smartRefresh = findViewById(R.id.smartRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        choose_pic_recycler = findViewById(R.id.choose_pic_recycler);

//
//        btn1 = findViewById(R.id.btn1);
//        btn2 = findViewById(R.id.btn2);
    }

    private void initData() {
        //返回键监听
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下拉刷新监听
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefresh.finishRefresh();
            }
        });
        //加载更多监听
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefresh.finishLoadMore();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        //设置item组件垂直向下分布
        recyclerView.setLayoutManager(layoutManager);
        //创建适配器对象  传入实体类对象集合 实现了数据和适配器的互通
        MessageAdapter messageAdapter = new MessageAdapter(messageList);
        //recyclerview绑定适配器  实现recyclerview和适配器的互通 从而实现recyclerview和数据的互通 所以说适配器是用来传递数据的
        recyclerView.setAdapter(messageAdapter);
    }
}
