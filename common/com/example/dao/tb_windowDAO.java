package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.objects.windowDefectObj;
import com.example.objects.windowObj;
import com.example.utils.DBUtil;

public class tb_windowDAO {

	public List<windowObj> getWindowList(DBUtil util,Integer areaId, Integer siteId,
			Integer flatTypeId) {
		List<windowObj> p = new ArrayList<windowObj>();
		Cursor cursor = util.rawQuery(
				"select * from tb_area_window a "
				      +" INNER JOIN tb_window w on a.window_id=w.window_id "
						+ " where area_id= ? and flat_type_id=? and a.site_id=?",
				new String[] { areaId.toString(), flatTypeId.toString(),
						siteId.toString() });
		
		
		
		while (cursor.moveToNext()) {
			windowObj window = new windowObj();
			window.setWindowId(cursor.getInt(cursor.getColumnIndex("window_id")));
			window.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
			window.setWindowName(cursor.getString(cursor
					.getColumnIndex("window_name")));
			window.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
			window.setDesc(cursor.getString(cursor.getColumnIndex("description")));
			
			//List<windowDefectObj> lstDefect= 	windowdefectDAO.getWindowDefectList(util, window.getWindowId(), 0);
			//window.setDefectPointList(lstDefect);
			p.add(window);
		}
		cursor.close();
		return p;
	}
	
	public void RefreshDefectList(DBUtil util,windowObj window){
		
		tb_window_defectDAO windowdefectDAO=new tb_window_defectDAO();
		
		List<windowDefectObj> lstDefect= 	windowdefectDAO.getWindowDefectList(util, window.getWindowId(), 0);
		window.setDefectPointList(lstDefect);
	}
	
	public void UpdateWindowFlowForTest(DBUtil util,byte[] img){
		 util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("UPDATE  tb_window set pic=?");
	             Object[] bindArgs = {img};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             util.setTransactionSuccessful();
	        } finally{
	        	util.endTransaction();
			}
	}
	
}
