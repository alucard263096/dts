package com.example.utils;

import java.io.IOException;

import android.content.Context;

public class BaseDB extends DBUtil {

	public BaseDB(Context ctx)  {
		super(ctx, "base.db", 1);
		// TODO Auto-generated constructor stub
//		try{
//			mDbHelper.createDataBase();
//		} catch (IOException e) {
//            throw new Error("数据库创建失败");
//        }
	}

	public void DeleteDB(){
		mDbHelper.deleteDataBase();
	}
	
	public boolean CheckDbExists(){
		boolean canOpen= super.CheckDbExists();
		if(canOpen){
			String sql="select 1 from tb_user where 1=2";
			try{

				super.open();
				super.execSQL(sql, new Object[]{});
				super.close();
				
				return true;
			}catch(Exception e){
				
			}
		}
		return false;
	}
	
	
}
