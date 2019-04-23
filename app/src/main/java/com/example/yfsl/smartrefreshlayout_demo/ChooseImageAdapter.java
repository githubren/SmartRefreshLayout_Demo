package com.example.yfsl.smartrefreshlayout_demo;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.caimuhao.rxpicker.bean.ImageItem;

import java.util.List;

public class ChooseImageAdapter extends RecyclerView.Adapter<ChooseImageHolder>{
    private int itemHeight;
    private ChooseListener chooseListener;
    private RemoveListener removeListener;
    private List<ImageItem> mDatas;

    public ChooseImageAdapter(List<ImageItem> chooseDatas, int itemHeight) {
        this.mDatas = chooseDatas;
        this.itemHeight = itemHeight;
    }

    public void setRemoveListener(RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    public void setChooseListener(ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }

    @Override
    public ChooseImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_item_layout,parent,false);
        return new ChooseImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ChooseImageHolder holder, final int position) {
        holder.itemView.setOnClickListener(null);
        if (itemHeight != 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.itemPic.getLayoutParams();
            params.height = itemHeight;
            holder.itemPic.requestLayout();

            RelativeLayout.LayoutParams detailParams = (RelativeLayout.LayoutParams) holder.detailPic.getLayoutParams();
            detailParams.height = itemHeight;
            holder.detailPic.requestLayout();
        }
        final ImageItem item = mDatas.get(position);
        if (TextUtils.isEmpty(item.getPath())) {//+号

            Utils.setGone(holder.removePic, holder.detailPic);
            Utils.setVisible(holder.itemPic);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chooseListener != null)
                        chooseListener.choose(v, position);
                }
            });
        } else {//显示图片
            Utils.setVisible(holder.removePic, holder.detailPic);
            Utils.setGone(holder.itemPic);

            GlideUtil.loadChoosePic(holder.itemView.getContext(), item.getPath(), holder.detailPic);
            holder.removePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (removeListener != null)
                        removeListener.remove(v, position);
                }
            });
        }
        //放在最下面，上面代码有隐藏与显示，会被覆盖。
        if (item.getAddTime() == -1)
            Utils.setInvisible(holder.removePic);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
