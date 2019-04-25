package com.example.yfsl.smartrefreshlayout_demo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.caimuhao.rxpicker.bean.ImageItem;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassicalRefreshActivity extends AppCompatActivity implements MessageAdapter.SelectItem{
    private ImageButton back_btn;//返回按钮
    private SmartRefreshLayout smartRefresh;//刷新控件
    private RecyclerView recyclerView;
    private ImageButton choosePicRecyclerView;
    private EditText content_et;
    private String content;
    private List<Message> messageList = new ArrayList<>();//实体类对象集合

    private EditText editText;

    private int TAKE_PHOTO = 1;

    /*
        private List<InspectListData> listDatas = new ArrayList<>();
        listDatas = 通过网络请求到的数据
        for(int i = 0;i<listDatas.size();i++){
            String PITEM_CONTENT = listDatas.get(i).getPITEM_CONTENT();
        }
        messageList.add(PITEM_CONTENT);

        for(InspectListData listData : listDatas){
            String PITEM_CONTENT = listData.getPITEM_CONTENT();
        }
        messageList.add(PITEM_CONTENT);
     */

    private Message message;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

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
            message = new Message("第"+i+"条消息");
            message.setPATITEM_ID(i+1);
            messageList.add(message);
        }
    }

    private void initView() {
        back_btn = findViewById(R.id.back_btn);
        smartRefresh = findViewById(R.id.smartRefresh);
        recyclerView = findViewById(R.id.recyclerView);
//        choosePicRecyclerView = findViewById(R.id.choosepic_btn);
        content_et = findViewById(R.id.content_et);
//
//        btn1 = findViewById(R.id.btn1);
//        btn2 = findViewById(R.id.btn2);
    }

    private void initData() {
        //返回键监听
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","DIANJI:"+content);
                getData();
                finish();
//                onKeyDown(4,)
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
        MessageAdapter messageAdapter = new MessageAdapter(messageList,this);
        //recyclerview绑定适配器  实现recyclerview和适配器的互通 从而实现recyclerview和数据的互通 所以说适配器是用来传递数据的
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (preBackExitPage()) {
                return true;
            }
//            finishWithAnim();
        } else if (keyCode == KeyEvent.KEYCODE_MENU)
            return true;
        else if (keyCode == KeyEvent.KEYCODE_HOME)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    private boolean preBackExitPage() {
        saveInfo();
        return false;
    }

    private void saveInfo() {
        if (messageList == null) return;
        ContentValues values = new ContentValues();
        values.put(InspectionEntryTable.TASK_ID,message.getTASK_ID());
        values.put(InspectionEntryTable.NODE_ID,message.getNODE_ID());
        List<Boolean> status = new ArrayList<>();
        List<List<ImageItem>> img = new ArrayList<>();
        for (int i = 0;i<messageList.size();i++){
            boolean single_status = message.isPATITEM_FLAG();
            status.add(single_status);
        }
//        img.add(choosePicRecyclerView.getChooseDatas());
        values.put(InspectionEntryTable.FLAG_STATUS,GsonUtil.toJson(status));
        values.put(InspectionEntryTable.INSPECT_IMG,GsonUtil.toJson(img));
        values.put(InspectionEntryTable.INSPECT_DESCRIBE,GsonUtil.toJson(content));
        AirPortDbManager.getInstance().saveInspectionEntryData(values);
        Log.e("TAG","数据："+values);
    }

    private void getData(){
        List<Boolean> status = new ArrayList<>();
        List<List<ImageItem>> img = new ArrayList<>();
        for (int i = 0;i<messageList.size();i++){
            boolean single_status = messageList.get(i).isPATITEM_FLAG();
            status.add(single_status);
        }
        Log.e("TAG","STATUS:"+status.toString());
//        img.add(choosePicRecyclerView.getChooseDatas());
        Map<String,String> map = new HashMap<>();
        map.put("status",status.toString());
        map.put("img",img.toString());
        map.put("des",content);
        Log.e("TAG","数据："+map);
    }

    @Override
    public void select(View view, String str) {
        editText = (EditText) view;
        Log.e("TAG","edittext："+editText);
        Log.e("TAG","select方法中的view:"+view);
        Log.e("TAG","edittext："+str);
        content = str;
        Log.e("TAG","content:"+content);
    }

    @Override
    public void click(View view, int position) {
        Log.e("TAG","click方法中的view："+view);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //开始  传入intent 和requestCode  在onActivityResult中根据requestCode做相应的处理
            startActivityForResult(intent,TAKE_PHOTO);
        });
    }

    @Override
    public void packFlag(Map<Integer,Boolean> flagList) {

        /**
         * Map集合的遍历！！！！！！！！
         */
        Log.e("TAG","flaglist:"+flagList);
        for (Integer key : flagList.keySet()){
            boolean value = flagList.get(key);
            Log.e("TAG","最后一次："+value);
        }
//        for (int i = 0;i<flagList.size();i++){
//            Message message = messageList.get(i);
//            boolean flag3 = flagList.get(message.getPATITEM_ID());
//            Log.e("TAG","再来一次："+i+flag3);
//        }
//        for (Map flag : flagList){
//
//        }
//        boolean flag =  flagList.get(message.getPATITEM_ID());
//        Log.e("TAG","按ID取的："+flag);
//        for (int a = 0;a<flagList.size();a++){
//           boolean flag2 = flagList.get(a);
//            Log.e("TAG","遍历取的："+flagList.get(a));
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    savePicToSdcard(data);
                    Log.e("TAG", "data是：" + data.toString());
                    break;
                default:
                    break;
            }
        }
    }

    //保存照片到系统图库
    private void savePicToSdcard(Intent data){
        //将传入的Intent类型数据转换为bitmap位图
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //创建存放图片的文件目录
        File appDir = new File(Environment.getExternalStorageDirectory(),"AirPort");
        if (!appDir.exists()){
            appDir.mkdir();
        }
        String filename = System.currentTimeMillis() + ".jpg";
        //创建存放图片的文件（文件名后缀文件格式）
        File file = new File(appDir,filename);
        try {
            //创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //将图片插入到系统图库（file.getAbsolutePath()这个路径）
            MediaStore.Images.Media.insertImage(this.getContentResolver(),file.getAbsolutePath(),filename,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //发动广播  通知图库更新显示图片（更新的位置由第二个参数决定 Uri.parse("file://"+file.getAbsolutePath()) 保存在哪个路径就刷新那个）
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+file.getAbsolutePath())));
    }
}
