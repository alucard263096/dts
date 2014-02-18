package com.example.utils;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {

	private static  Context mCtx;
	protected DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private String mDatabaseName;
	private int mVersion;
	
	protected DBUtil(Context ctx,String databaseName,int version){
		this.mCtx=ctx;
		this.mDatabaseName=databaseName;
		this.mVersion=version;
		mDbHelper=new DatabaseHelper(mCtx,mDatabaseName,mVersion);
	}
	protected DBUtil(Context ctx,String path,String databaseName,int version){
		this.mCtx=ctx;
		this.mDatabaseName=databaseName;
		this.mVersion=version;
		mDbHelper=new DatabaseHelper(mCtx,path,mDatabaseName,mVersion);
	}
	
	
	public DBUtil open() throws SQLException{
		mDb=mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void beginTransaction() throws SQLException{
		mDb.beginTransaction();
	}
	
	public void endTransaction() throws SQLException{
		mDb.endTransaction();
	}
	
	public void setTransactionSuccessful() throws SQLException{
		mDb.setTransactionSuccessful();
	}
	
	public void close(){
		mDbHelper.close();
	}
	
	public Cursor rawQuery(String sql, String[] selectionArgs) throws SQLException{
	  return mDb.rawQuery(sql, selectionArgs);
	}
	
	public void execSQL(String sql, Object[] bindArgs) throws SQLException{
		  mDb.execSQL(sql, bindArgs);
	}
	
	public void deleteTable(String tablename)
	{
		deleteTable(tablename, null, null);
	}
	
	public void deleteTable(String tablename, String where, String[] whereArgs)
	{
		mDb.delete(tablename, where, whereArgs);
	}
	
	public long getDatabaseLength(){
		return mDbHelper.getDatabaseLength();
	}
	
	public void CreateDB(byte[] buffer) {
		try{
			mDbHelper.createDataBase(buffer);
		} catch (Exception e) {
            throw new Error("数据库创建失败");
        }
		
	}

	public void CreateDB(List<byte[]> albuffer) {
		try{
			mDbHelper.createDataBase(albuffer);
		} catch (Exception e) {
            throw new Error("数据库创建失败");
        }
		
	}
	
	public boolean CheckDbExists(){
		return mDbHelper.checkDataBase();
	}
	public void CreateDBOnAsset(){
		try{
		mDbHelper.createDataBase();
	} catch (IOException e) {
        throw new Error("数据库创建失败");
    }
	}
}