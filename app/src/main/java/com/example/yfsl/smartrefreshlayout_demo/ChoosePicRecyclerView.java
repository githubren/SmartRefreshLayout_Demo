package com.example.yfsl.smartrefreshlayout_demo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.caimuhao.rxpicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ChoosePicRecyclerView extends RecyclerView {
    private int defalutSpan = 4;
    private Context mContext;
    private int width;
    private ChooseImageAdapter adapter;
    private List<ImageItem> chooseDatas;
    private ChooseListener chooseListener;

    public ChoosePicRecyclerView(Context context) {
        this(context, null);
    }

    public ChoosePicRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChoosePicRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        //去掉滑动阴影
        setOverScrollMode(OVER_SCROLL_NEVER);
        initAttr(context, attrs);
        initRecycler(context);
    }

    private void initRecycler(Context context) {
        chooseDatas = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(context, defalutSpan);
        manager.setAutoMeasureEnabled(true);
        setLayoutManager(manager);
        setHasFixedSize(true);
        setNestedScrollingEnabled(false);
        addItemDecoration(new DividerGridItemDecoration(context, R.drawable.gird_equal_divider_height));
    }

    public void setChooseListener(ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    private void initAttr(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        initAdapter();
    }

    private void initAdapter() {
        if (adapter != null) return;
        chooseDatas.add(new ImageItem());
        adapter = new ChooseImageAdapter(chooseDatas, (width - 3 * MeasureUtil.dip2px(mContext, 5)) / defalutSpan);
        adapter.setChooseListener(chooseListener);
        adapter.setRemoveListener(new RemoveListener() {
            @Override
            public void remove(View view, int position) {
                chooseDatas.remove(position);
                int size = chooseDatas.size();
                if (!TextUtils.isEmpty(chooseDatas.get(size - 1).getPath()))
                    plus();
                adapter.notifyDataSetChanged();
            }
        });
        setAdapter(adapter);
    }

    /**
     * 默认1，因为有一个加号
     */
    public List<ImageItem> getChooseDatas() {
        return chooseDatas;
    }

    public void setDatas(List<ImageItem> mdatas) {
        if (mdatas == null || mdatas.isEmpty()) return;
        int size = chooseDatas.size();
        if (chooseDatas.size() >= 1)
            chooseDatas.remove(size - 1);
        chooseDatas.addAll(mdatas);
        plus();
        if (adapter == null)
            adapter = (ChooseImageAdapter) getAdapter();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    public void setDatas(ImageItem mdata) {
        if (mdata == null) return;
        int size = chooseDatas.size();
        if (chooseDatas.size() >= 1)
            chooseDatas.remove(size - 1);
        chooseDatas.add(mdata);
        plus();
        if (adapter == null)
            adapter = (ChooseImageAdapter) getAdapter();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * +号
     */
    private void plus() {
        if (chooseDatas.size() < 9) {
            chooseDatas.add(new ImageItem());
        }
    }
}
