package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.objects.blockObj;
import com.example.utils.DBUtil;

import android.database.Cursor;

public class tb_BlockDAO {
	
	public List<blockObj> getBlockList(DBUtil util,Integer siteId)  
    {  
        List<blockObj> p=new ArrayList<blockObj>();  
        Cursor cursor=util.rawQuery("select * from tb_block where site_id=?", new String[]{siteId.toString()});  
        while(cursor.moveToNext())  
        {  
        	blockObj obj=new blockObj(); 
        	obj.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
        	obj.setBlockId(cursor.getInt(cursor.getColumnIndex("block_id")));
        	obj.setBlockName(cursor.getString(cursor.getColumnIndex("block_name")));
        	obj.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            p.add(obj);  
        }  
        cursor.close();  
        return p;  
    } 
}
