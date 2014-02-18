package com.example.dao;

import android.database.Cursor;

import com.example.utils.DBUtil;

public class tb_Sys_parameterDAO {
	
	public int getNumsOfPhotos(DBUtil util) {
		int result=0;
		try {

			String sql = "select * from tb_sys_parameter where parameter_id=?";
			Cursor cur = util.rawQuery(sql, new String[] {"3"});
			if(cur.moveToFirst()) {
			    result = cur.getInt(cur.getColumnIndex("int_value"));  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int getNumsOfSketchs(DBUtil util) {
		int result=0;
		try {

			String sql = "select * from tb_sys_parameter where parameter_id=?";
			Cursor cur = util.rawQuery(sql, new String[] {"5"});
			if(cur.moveToFirst()) {
			    result = cur.getInt(cur.getColumnIndex("int_value"));  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getNumsOfFloorPlan(DBUtil util) {
		int result=0;
		try {

			String sql = "select * from tb_sys_parameter where parameter_id=?";
			Cursor cur = util.rawQuery(sql, new String[] {"7"});
			if(cur.moveToFirst()) {
			    result = cur.getInt(cur.getColumnIndex("int_value"));  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
