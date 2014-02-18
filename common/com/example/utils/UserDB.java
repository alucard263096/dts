package com.example.utils;

import java.io.IOException;

import android.content.Context;

public class UserDB extends DBUtil {

	private String UserId="";
	public UserDB(Context ctx, String userId) {
		super(ctx, "user_"+userId+".db", 1);
		// TODO Auto-generated constructor stub
		UserId=userId;
		
		try{
			//mDbHelper.deleteDataBase();
			mDbHelper.createDataBase();
		} catch (Exception e) {
			//throw new Error("数据库创建失败");
		}
//        }
		
	}
	
	public void DeleteDB(){
		mDbHelper.deleteDataBase();
	}
	

	public boolean CheckDbExists(){
		boolean canOpen= super.CheckDbExists();
		if(canOpen){
			String sql="select 1 from tb_site where 1=2";
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
