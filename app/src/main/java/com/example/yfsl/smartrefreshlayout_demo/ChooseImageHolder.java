package com.example.yfsl.smartrefreshlayout_demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ChooseImageHolder extends RecyclerView.ViewHolder {
    public ImageView itemPic;
    public ImageView detailPic;
    public RelativeLayout removePic;

    public ChooseImageHolder(View itemView) {
        super(itemView);
        itemPic = itemView.findViewById(R.id.choose_pic_item_iv);
        removePic = itemView.findViewById(R.id.choose_remove_pic);
        detailPic = itemView.findViewById(R.id.choose_pic_item_detail_iv);
    }
}
