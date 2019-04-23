package com.example.yfsl.smartrefreshlayout_demo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        if (i == ITEM_FOOT3){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer3,viewGroup,false);
            return new FooterView3Holder(view);
        }
        if (i == ITEM_FOOT4){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_footer4,viewGroup,false);
            return new FooterView4Holder(view);
        }
        if (i == ITEM_FOOT5){
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
//            int buttonId = ((ViewHolder) viewHolder).qualified_status_group.getCheckedRadioButtonId();
//            String PATITEM_FLAG = buttonId == R.id.qualified ? "合格" : "不合格";
//            message.setPATITEM_FLAG(TextUtils.equals(PATITEM_FLAG,"合格"));
//            boolean FLAG = message.isPATITEM_FLAG();
            message.setPATITEM_ID(i);
            Log.e("TAG","message"+i+"的ID为："+message.getPATITEM_ID());
//            Log.e("TAG","此项合格:"+FLAG);

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
                }
            });


//            String flag = getFlag((ViewHolder) viewHolder);
//            message.setPATITEM_FLAG(TextUtils.equals(flag,""));
//            boolean FLAG = message.isPATITEM_FLAG();
//            Log.e("TAG","此项合格:"+FLAG);
        }

        if (viewHolder instanceof FooterView2Holder){
//            FooterView5Holder footerView5Holder = new FooterView5Holder(viewHolder.itemView);
            ((FooterView2Holder) viewHolder).entry_work_order_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG","输出的："+getDescribe.toString());
                    Log.e("TAG",flagList.toString());
                }
            });
        }

        if (viewHolder instanceof FooterView5Holder){
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
                }
            });
            Message message2 = mMessageList.get(i-mHeader-mFoot4-mMessageList.size());
            setDescribe = ((FooterView5Holder) viewHolder).inspectDec.getText().toString();
            message2.setPATITEM_DESCRIBE(setDescribe);
            Log.e("TAG","输入的："+setDescribe);
            getDescribe = message2.getPATITEM_DESCRIBE();
            Log.e("TAG","拿到的："+getDescribe);
        }
//        if (viewHolder instanceof FooterView3Holder){
//            RecyclerView choosePicRcv = ((FooterView3Holder) viewHolder).choosePicRecyclerView;
//            choosePicRcv.setLayoutManager(new GridLayoutManager(mContext,4));
//            ChooseImageAdapter chooseImageAdapter = new ChooseImageAdapter((width - 3 * MeasureUtil.dip2px(mContext, 5)) / 4);
//            choosePicRcv.setAdapter(chooseImageAdapter);
//        }
    }

//    private String getFlag(ViewHolder vh){
//        int buttonId = vh.qualified_status_group.getCheckedRadioButtonId();
//        if (buttonId == R.id.qualified){
//            return "合格";
//        }
//        if (buttonId == R.id.un_qualified){
//            return "不合格";
//        }
//        return "";
//    }

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
//        ChoosePicRecyclerView choosePicRecyclerView;
        public FooterView3Holder(@NonNull View itemView) {
            super(itemView);
//            choosePicRecyclerView = itemView.findViewById(R.id.choose_pic_recycler);
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
}
