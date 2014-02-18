package com.example.utils;

import java.io.File;
import java.io.IOException;

import com.example.objects.StaticVar;

import android.content.Context;

public class BackUpDB extends DBUtil {
	String backup_filename;
	String ori_db_name;
	
	public BackUpDB(Context ctx,String db_name)  
	{
		super(ctx,StaticVar.DB_Backup_PATH, db_name + StaticVar.DB_Backup_filename_suffix, 1);
		
		this.ori_db_name = db_name;
		this.backup_filename = db_name + StaticVar.DB_Backup_filename_suffix;
		
//		try{
//			mDbHelper.createDataBase();
//		} catch (IOException e) {
//            throw new Error("数据库创建失败");
//        }
	}
	
	public void doBackUp(){
		
		// Check working DB exist
		File dbf = new File(StaticVar.DB_Working_PATH + ori_db_name);
		if(dbf.exists()==false){
			return;
		}
   
		// Delete Backup DB if exist
		File bedbf=new File(StaticVar.DB_Backup_PATH + backup_filename);
		if(bedbf.exists()){
			bedbf.delete();
		}
		
		// Create backup directory if not exist
		File dir = new File(StaticVar.DB_Backup_PATH);
		if(!dir.exists()){
			dir.mkdirs();
		}
   
		try {
			// Copy working DB to Backup DB
			ToolUtil.copyFile(dbf, bedbf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getFile(){
		// Get Backup DB
		File bedbf = new File(StaticVar.DB_Backup_PATH + backup_filename);
		return bedbf;
	}
}
