package com.cy.bear1.dbm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cy.common.bus.L;

/**
 * sqlite数据库建库建表类 in version
 */
public class DBOpenHelper extends SQLiteOpenHelper {


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    /**
     * 首次创建数据库时调用，执行建库建表的操作<br/>
     * sqlite 没有单独的boolean类型， 使用integer类型存储：0 - false，1 - true
     */
    public void onCreate(SQLiteDatabase db) {
        // 用户表
        db.execSQL( "create table if not exists lg_user_tb(_id integer primary key autoincrement, " +
                "name text not null, pwd text not null)");

        ContentValues cv = new ContentValues();
        cv.put("name", "admin");
        cv.put("pwd", "abc123");
        Long ret = db.insert("lg_user_tb", null, cv);
        Log.i(L.BUS, String.format("==================>>>>>> 初始化lg_user_tb的记录id=%s", ret));
    }

    @Override
    /**
     * 当版本改变时，自动执行
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}