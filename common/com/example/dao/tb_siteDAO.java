package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.example.objects.siteObj;
import com.example.utils.DBUtil;

public class tb_siteDAO {
	public List<siteObj> getSiteList(DBUtil util)  
    {  
        List<siteObj> p=new ArrayList<siteObj>();  
        Cursor cursor=util.rawQuery("select * from tb_site", new String[]{});  
        while(cursor.moveToNext())  
        {  
        	siteObj obj=new siteObj(); 
        	obj.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
        	obj.setSiteName(cursor.getString(cursor.getColumnIndex("site_name")));
        	obj.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            p.add(obj);  
        }  
        cursor.close();  
        return p;  
    } 
}
