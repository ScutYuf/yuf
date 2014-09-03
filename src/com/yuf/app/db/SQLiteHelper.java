package com.yuf.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * 实现对表的创建、更新、变更列名操作
 * 
 * 在Android 中针对少量数据的操作在SQLite操作实现相关功能功能
 * ，但是必须继承SQLiteOpenHelper，实现相关的功能。
 *  
 * 
 * @author longgangbai
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	public static final String TB_NAME = "orders";

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public SQLiteHelper(Context context) {
		super(context, "yuf", null, 2);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建新表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TB_NAME + "(" +
				Order.ID + " integer primary key," +
				Order.USERID + " integer," + 
				Order.ORDERDISHID + " integer," + 
				Order.ORDERPRICE+ " float," + 
				Order.ORDERTIME+ " varchar,"+
				Order.ORDERPAYMETHOD+ " varchar,"+
				Order.ORDERAMOUNT+ " integer "+
				")");
	}
	
	/**
	 * 当检测与前一次创建数据库版本不一样时，先删除表再创建新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
		onCreate(db);
	}
	
	/**
	 * 变更列名
	 * @param db
	 * @param oldColumn
	 * @param newColumn
	 * @param typeColumn
	 */
	public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
		try{
			db.execSQL("ALTER TABLE " +
					TB_NAME + " CHANGE " +
					oldColumn + " "+ newColumn +
					" " + typeColumn
			);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
