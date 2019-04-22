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
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> mMessageList;

    private final static int ITEM_HEADER=0;
    private final static int ITEM_CONTENT=1;
    private final static int ITEM_FOOT1=2;
    private final static int ITEM_FOOT2=3;

    private int mHeader=1;
    private int mFoot1=1;
    private int mFoot2=1;

    /**
     * 创建viewholder
     * 找到布局 ->创建ViewHolder对象 传入布局
     * @param viewGroup
     * @param i
     * @return 返回ViewHolder对象
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i ==ITEM_CONTENT){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message,viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        if (i == ITEM_HEADER){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header,viewGroup,false);
            return new HeaderViewHolder(view);
        }
        if (i == ITEM_FOOT1){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer1,viewGroup,false);
            return new FooterView1Holder(view);
        }
        if (i == ITEM_FOOT2){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer2,viewGroup,false);
            return new FooterView2Holder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != 0 && position<mHeader){
            return ITEM_HEADER;
        }
        if (mFoot1 != 0 && position>=mMessageList.size()+mHeader){
            return ITEM_FOOT1;
        }
        if (mFoot2 != 0 && position>=mMessageList.size()+mHeader+mFoot1){
            return ITEM_FOOT2;
        }
        return ITEM_CONTENT;
    }

    /**
     * 绑定viewholder
     * 拿到实体类对象 ->通过get/set方法传数据  将数据展示在item中
     * @param viewHolder
     * @param i
     */
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder){
            Message message = mMessageList.get(i-mHeader);
            ((ViewHolder)viewHolder).message.setText(message.getMessage());
        }
        if (viewHolder instanceof FooterView1Holder){
            
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size()+mHeader+mFoot1+mFoot2;
    }

    /**
     * 内部类ViewHolder继承自RecyclerView.ViewHolder
     * 用来找到item控件
     */
    class ViewHolder extends RecyclerView.ViewHolder{
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

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    class FooterView1Holder extends RecyclerView.ViewHolder {
        public FooterView1Holder(View view) {
            super(view);
        }
    }

    class FooterView2Holder extends RecyclerView.ViewHolder {
        public FooterView2Holder(View view) {
            super(view);
        }
    }
}
