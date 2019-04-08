package com.example.yfsl.smartrefreshlayout_demo;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 适配器 给recyclerview传递数据
 * 继承自RecyclerView.Adapter
 * 泛型MessageAdapter.ViewHolder  ViewHolder是MessageAdapter中的一个内部类
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> mMessageList;

    /**
     * 创建viewholder
     * 找到布局 ->创建ViewHolder对象 传入布局
     * @param viewGroup
     * @param i
     * @return 返回ViewHolder对象
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 绑定viewholder
     * 拿到实体类对象 ->通过get/set方法传数据  将数据展示在item中
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Message mes = mMessageList.get(i);
        viewHolder.message.setText(mes.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    /**
     * 内部类ViewHolder继承自RecyclerView.ViewHolder
     * 用来找到item控件
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView message;

        /**
         * 有参构造方法
         * @param view item空间所在的布局
         */
        public ViewHolder(@NonNull View view) {
            super(view);
            message = view.findViewById(R.id.message);
        }
    }

    /**
     * 适配器有参构造方法  创建适配器对象的时候传入参数（实体类对象集合）
     * 实现将实体类和适配器绑定起来
     * @param messageList 实体类对象集合
     */
    public MessageAdapter(List<Message> messageList) {
        mMessageList = messageList;
    }
}
