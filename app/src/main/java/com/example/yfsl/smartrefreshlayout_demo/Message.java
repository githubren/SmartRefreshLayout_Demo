package com.example.yfsl.smartrefreshlayout_demo;

import android.support.annotation.NonNull;

public class Message {
    private String message;

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
