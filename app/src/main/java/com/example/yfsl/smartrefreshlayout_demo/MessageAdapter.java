package com.example.yfsl.smartrefreshlayout_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 适配器 给recyclerview传递数据
 * 继承自RecyclerView.Adapter
 * 泛型MessageAdapter.ViewHolder  ViewHolder是MessageAdapter中的一个内部类
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> mMessageList;
    private String setDescribe;
    private String getDescribe;
    Map<Integer, Boolean> flagList = new HashMap<>();
    private Context mContext;

    private int TAKE_PHOTO = 1;

    private int editStart;
    private int editEnd;
    private long toastInterval;
    private final int INTERVAL = 3*1000;

    private final static int ITEM_HEADER=0;
    private final static int ITEM_CONTENT=1;
    private final static int ITEM_FOOT1=2;
    private final static int ITEM_FOOT2=3;
    private final static int ITEM_FOOT3=4;
    private final static int ITEM_FOOT4=5;
    private final static int ITEM_FOOT5=6;

    private int mHeader=1;
    private int mFoot1=1;
    private int mFoot2=1;
    private int mFoot3=1;
    private int mFoot4=1;
    private int mFoot5=1;

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
            Log.e("TAG","加载item_content");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message,viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        if (i == ITEM_HEADER){
            Log.e("TAG","加载头部");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header,viewGroup,false);
            return new HeaderViewHolder(view);
        }
        if (i == ITEM_FOOT1){
            Log.e("TAG","加载“报修图片”TextView");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer1,viewGroup,false);
            return new FooterView1Holder(view);
        }
        if (i == ITEM_FOOT2){
            Log.e("TAG","加载“提交”");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer2,viewGroup,false);
            return new FooterView2Holder(view);
        }
        if (i == ITEM_FOOT3){
            Log.e("TAG","加载ChoosePicRecyclerView");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer3,viewGroup,false);
            return new FooterView3Holder(view);
        }
        if (i == ITEM_FOOT4){
            Log.e("TAG","加载“巡检说明”TextView");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer4,viewGroup,false);
            return new FooterView4Holder(view);
        }
        if (i == ITEM_FOOT5){
            Log.e("TAG","加载edittext");
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer5_insdec,viewGroup,false);
            return new FooterView5Holder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != 0 && position == 0){
            return ITEM_HEADER;
        }
        if (mFoot4 != 0 && position == mMessageList.size()+mHeader){
            return ITEM_FOOT4;
        }
        if (mFoot5 != 0 && position == mMessageList.size()+mHeader+mFoot4){
            return ITEM_FOOT5;
        }
        if (mFoot1 != 0 && position == mMessageList.size()+mHeader+mFoot4+mFoot5){
            return ITEM_FOOT1;
        }
        if (mFoot2 != 0 && position == mMessageList.size()+mHeader+mFoot4+mFoot5+mFoot1+mFoot3){
            return ITEM_FOOT2;
        }
        if (mFoot3 != 0 && position == mMessageList.size()+mHeader+mFoot1+mFoot4+mFoot5){
            return ITEM_FOOT3;
        }
        return ITEM_CONTENT;
    }

    /**
     * 绑定viewholder
     * 拿到实体类对象 ->通过get/set方法传数据  将数据展示在item中
     * @param viewHolder
     * @param i
     */
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ViewHolder){//是普通item的时候  i从1开始 i=0是头部 （mHeader=1）
            //拿到普通item第一个位置的Message对象
            final Message message = mMessageList.get(i-mHeader);
            //设置内容
            ((ViewHolder)viewHolder).message1.setText(message.getMessage());
//            message.setPATITEM_ID(i);
            Log.e("TAG","message"+i+"的ID为："+message.getPATITEM_ID());

            ((ViewHolder) viewHolder).qualified_status_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.qualified){
                        message.setPATITEM_FLAG(true);
                    }
                    if (checkedId == R.id.un_qualified){
                        message.setPATITEM_FLAG(false);
                    }
                    Log.e("TAG","MESSAGE"+i+":"+message.isPATITEM_FLAG());
                    flagList.put(message.getPATITEM_ID(),message.isPATITEM_FLAG());
                    Log.e("TAG","FLAG:"+flagList);
                    SelectItem selectItem = (SelectItem) mContext;
                    selectItem.packFlag(flagList);
                }
            });
        }

        if (viewHolder instanceof FooterView5Holder){
            final Message message2 = mMessageList.get(i-mHeader-mFoot4-mMessageList.size());
            //给edittext设置tag 放置数据混乱 此处设置 在监听的afterTextChanged方法中进行判断
            ((FooterView5Holder) viewHolder).inspectDec.setTag(viewHolder.getLayoutPosition());
            ((FooterView5Holder) viewHolder).inspectDec.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((FooterView5Holder) viewHolder).content_number_tip_tv.setText(charSequence.length()+"/150");
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //判断控件edittext的tag和布局的位置是否相同
                    if ((Integer)((FooterView5Holder) viewHolder).inspectDec.getTag() == viewHolder.getLayoutPosition() && ((FooterView5Holder) viewHolder).inspectDec.hasFocus()){
                        editStart = ((FooterView5Holder)viewHolder).inspectDec.getSelectionStart();
                        editEnd = ((FooterView5Holder)viewHolder).inspectDec.getSelectionEnd();
                        if (editable.length()>150){
                            long l = System.currentTimeMillis();
                            if (l-toastInterval >= INTERVAL){
                                Toast.makeText(mContext, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                                toastInterval = l;
                            }
                            editable.delete(editStart-1,editEnd);
                            ((FooterView5Holder)viewHolder).inspectDec.setText(editable);
                            ((FooterView5Holder)viewHolder).inspectDec.setSelection(150);
                        }
                        setDescribe = editable.toString();
                        message2.setPATITEM_DESCRIBE(setDescribe);
                        getDescribe = message2.getPATITEM_DESCRIBE();
                        Log.e("TAG","输入的："+editable);
                        Log.e("TAG","拿到的："+editable);
                    }
                    SelectItem selectItem = (SelectItem) mContext;
                    selectItem.select(((FooterView5Holder) viewHolder).inspectDec,editable.toString());
                }
            });
        }

        if (viewHolder instanceof FooterView2Holder){
            viewHolder.setIsRecyclable(true);
            ((FooterView2Holder) viewHolder).entry_work_order_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG","输出的："+setDescribe);
                    Log.e("TAG",flagList.toString());
                }
            });
        }

        if (viewHolder instanceof FooterView3Holder){
            ImageButton choosePicBtn = ((FooterView3Holder) viewHolder).choosepic_btn;
            choosePicBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG","点击ChoosePicRecyclerView");
                    SelectItem clickItemChoosePic = (SelectItem) mContext;
                    clickItemChoosePic.click(((FooterView3Holder) viewHolder).choosepic_btn,i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size()+mHeader+mFoot1+mFoot2+mFoot3+mFoot4+mFoot5;
    }

    /**
     * 内部类ViewHolder继承自RecyclerView.ViewHolder
     * 用来找到item控件
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView message1;
        RadioGroup qualified_status_group;
        /**
         * 有参构造方法
         * @param view item空间所在的布局
         */
        public ViewHolder(@NonNull View view) {
            super(view);
            message1 = view.findViewById(R.id.message);
            qualified_status_group = view.findViewById(R.id.qualified_status_group);
        }
    }

    /**
     * 适配器有参构造方法  创建适配器对象的时候传入参数（实体类对象集合）
     * 实现将实体类和适配器绑定起来
     * @param messageList 实体类对象集合
     */
    public MessageAdapter(List<Message> messageList,Context context) {
        mMessageList = messageList;
        mContext = context;
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
        RelativeLayout entry_work_order_commit;
        public FooterView2Holder(View view) {
            super(view);
            entry_work_order_commit = view.findViewById(R.id.entry_work_order_commit);
        }
    }

    class FooterView3Holder extends RecyclerView.ViewHolder{
        ImageButton choosepic_btn;
        public FooterView3Holder(@NonNull View itemView) {
            super(itemView);
            choosepic_btn = itemView.findViewById(R.id.choosepic_btn);
        }
    }

    class FooterView4Holder extends RecyclerView.ViewHolder{

        public FooterView4Holder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class FooterView5Holder extends RecyclerView.ViewHolder{
        EditText inspectDec;
        TextView content_number_tip_tv;
        public FooterView5Holder(@NonNull View itemView) {
            super(itemView);
            inspectDec = itemView.findViewById(R.id.content_et);
            content_number_tip_tv = itemView.findViewById(R.id.content_number_tip_tv);
        }
    }

    protected interface SelectItem{
        void select(View view, String str);

        void click(View view,int position);

        void packFlag(Map<Integer,Boolean> flagList);
    }
}
