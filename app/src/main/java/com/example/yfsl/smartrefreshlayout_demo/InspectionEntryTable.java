package com.example.yfsl.smartrefreshlayout_demo;

/**
 * 巡检录入信息存入数据库的存储表
 */
public class InspectionEntryTable {
    //存储表表名
    public static final String TABLE_NAME = "table_name";
    //任务id
    public static final String TASK_ID = "task_id";
    //节点id
    public static final String NODE_ID = "node_id";
    //是否合格
    public static final String FLAG_STATUS = "flag_status";
    //巡检说明
    public static final String INSPECT_DESCRIBE = "inspect_describe";
    //巡检图片
    public static final String INSPECT_IMG = "inspect_img";
    //创建巡检录入信息表的sql
    public static final String CREATE_IET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_ID + " int, "
            + NODE_ID + " int, "
            + FLAG_STATUS + " text, "
            + INSPECT_DESCRIBE + " text, "
            + INSPECT_IMG + " text"
            + ");";
}
