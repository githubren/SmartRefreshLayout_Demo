package com.example.yfsl.smartrefreshlayout_demo;

import android.support.annotation.NonNull;

public class Message {
    private String message;
    private int PATITEM_ID;
    private boolean PATITEM_FLAG;
    private String PATITEM_DESCRIBE;
    private int TASK_ID;
    private int NODE_ID;

    public int getTASK_ID() {
        return TASK_ID;
    }

    public void setTASK_ID(int TASK_ID) {
        this.TASK_ID = TASK_ID;
    }

    public int getNODE_ID() {
        return NODE_ID;
    }

    public void setNODE_ID(int NODE_ID) {
        this.NODE_ID = NODE_ID;
    }

    public String getPATITEM_DESCRIBE() {
        return PATITEM_DESCRIBE;
    }

    public void setPATITEM_DESCRIBE(String PATITEM_DESCRIBE) {
        this.PATITEM_DESCRIBE = PATITEM_DESCRIBE;
    }

    public int getPATITEM_ID() {
        return PATITEM_ID;
    }

    public void setPATITEM_ID(int PATITEM_ID) {
        this.PATITEM_ID = PATITEM_ID;
    }

    public boolean isPATITEM_FLAG() {
        return PATITEM_FLAG;
    }

    public void setPATITEM_FLAG(boolean PATITEM_FLAG) {
        this.PATITEM_FLAG = PATITEM_FLAG;
    }

    /**
     * 有参构造方法
     * 创建实体类对象的时候传递参数
     * @param message 传递的数据
     */
    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
