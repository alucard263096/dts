package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.example.objects.areaObj;
import com.example.utils.DBUtil;

public class tb_AreaDAO {
	
	public List<areaObj> getAreaList(DBUtil util,Integer siteId,Integer flatTypeId)  
    {  
        List<areaObj> p=new ArrayList<areaObj>();  
        Cursor cursor=util.rawQuery("select * from tb_area where site_id= ? and flat_type_id=?", new String[]{siteId.toString(),flatTypeId.toString()});  
        while(cursor.moveToNext())  
        {  
        	areaObj area=new areaObj(); 
        	area.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
        	area.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
        	area.setFlatTypeId(cursor.getInt(cursor.getColumnIndex("flat_type_id")));
        	area.setAreaName(cursor.getString(cursor.getColumnIndex("area_name")));
        	area.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            p.add(area);  
        }  
        cursor.close();  
        return p;  
    }  
}

