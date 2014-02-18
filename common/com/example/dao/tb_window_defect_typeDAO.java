package com.example.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.example.objects.windowDefectTypeObj;
import com.example.objects.windowObj;
import com.example.utils.DBUtil;

public class tb_window_defect_typeDAO {
	public List<windowDefectTypeObj> getWindowDefectTypeList(DBUtil util) {
		List<windowDefectTypeObj> p = new ArrayList<windowDefectTypeObj>();
		Cursor cursor = util.rawQuery(
				"select * from tb_window_defect_type where status=0  ",
				new String[] {  });
		while (cursor.moveToNext()) {
			windowDefectTypeObj obj = new windowDefectTypeObj();
			obj.setWindowDefectTypeId(cursor.getInt(cursor.getColumnIndex("window_defect_type_id")));
			obj.setCode(cursor.getString(cursor.getColumnIndex("code")));
			obj.setWindowDefectTypeName(cursor.getString(cursor
					.getColumnIndex("window_defect_type_name")));
			//obj.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
			obj.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			p.add(obj);
		}
		cursor.close();
		return p;
	}
	
	public void InsertDataForTest(DBUtil util){
		util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("insert into tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,description,lock_key,status,created_user_id,created_date,updated_user_id,updated_date,user_id) values (?,?,?,?,?,?,?,datetime('now', 'localtime'),?,datetime('now', 'localtime'),1)");
	           
	             Object[] bindArgs1 = {1,"Broken","Broken","","100",0,1,1};
	             util.execSQL(sql_insert.toString(),bindArgs1);
	             Object[] bindArgs2 = {2,"Miss-Parts","Miss-Parts","","100",0,1,1};
	             util.execSQL(sql_insert.toString(),bindArgs2);
	             Object[] bindArgs3 = {3,"Dent-Mark","Dent-Mark","","100",0,1,1};
	             util.execSQL(sql_insert.toString(),bindArgs3);
	             Object[] bindArgs4 = {4,"Others","Others","","100",0,1,1};
	             util.execSQL(sql_insert.toString(),bindArgs4);
	             util.setTransactionSuccessful();
	        } finally{
	        	util.endTransaction();
			}
	}
}


