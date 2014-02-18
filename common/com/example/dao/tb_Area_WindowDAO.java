package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.objects.areaElementWindowObj;
import com.example.utils.DBUtil;

public class tb_Area_WindowDAO {
	
	public List<areaElementWindowObj> getAreaWindowList(DBUtil util,Integer areaId,Integer siteId,Integer flatTypeId)  
    {  
        List<areaElementWindowObj> p=new ArrayList<areaElementWindowObj>();  
        Cursor cursor=util.rawQuery("select * from tb_area_window " +
        		                  " where area_id= ? and flat_type_id=? and site_id=?", new String[]{areaId.toString(),flatTypeId.toString(),siteId.toString()});  
        while(cursor.moveToNext())  
        {  
        	boolean canAdd = true;
			for (areaElementWindowObj o : p) {
				if (o.getAreaId() == cursor.getInt(cursor
						.getColumnIndex("area_id"))&&o.getSiteId() == cursor.getInt(cursor
								.getColumnIndex("site_id"))&&o.getFlatTypeId() == cursor.getInt(cursor
										.getColumnIndex("flat_type_id"))) {
					canAdd = false;
				}
			}
			if (canAdd) {
        	areaElementWindowObj areaElement=new areaElementWindowObj(); 
        	areaElement.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
        	areaElement.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
        	areaElement.setFlatTypeId(cursor.getInt(cursor.getColumnIndex("flat_type_id")));
        	areaElement.setName("Window Inspection");
        	areaElement.setId(cursor.getInt(cursor.getColumnIndex("window_id")));
        	areaElement.setIsWindow(true);
            p.add(areaElement);  
			}
        }  
        cursor.close();  
        return p;  
    }  
}
