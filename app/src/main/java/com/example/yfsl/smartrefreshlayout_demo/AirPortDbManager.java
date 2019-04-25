package com.example.yfsl.smartrefreshlayout_demo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class AirPortDbManager {
    private static AirPortDbManager instance;
    private SQLiteDatabase readDatabase;
    private SQLiteDatabase writeDatabase;
//    private AirPortDbHelper mHelper;

//    private AirPortDbManager() {
//        mHelper = new AirPortDbHelper(AirPortApplication.getInstance());
//        readDatabase = mHelper.getReadableDatabase();
//        writeDatabase = mHelper.getWritableDatabase();
//    }

    public static AirPortDbManager getInstance() {
        if (instance == null) {
            synchronized (AirPortDbManager.class) {
                if (instance == null)
                    instance = new AirPortDbManager();
            }
        }
        return instance;
    }

    public void saveInspectionEntryData(ContentValues values) {
        int taskId = (int) values.get(InspectionEntryTable.TASK_ID);
        int nodeId = (int) values.get(InspectionEntryTable.NODE_ID);
        int update = writeDatabase.update(InspectionEntryTable.TABLE_NAME, values,
                InspectionEntryTable.TASK_ID + "=? and " + InspectionEntryTable.NODE_ID + "=?",
                new String[]{String.valueOf(taskId), String.valueOf(nodeId)});
        if (update <= 0) {//数据库中没有这样的数据
            writeDatabase.insert(InspectionEntryTable.TABLE_NAME, null, values);
        }
    }
}
