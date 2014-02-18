package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.example.objects.floorObj;
import com.example.utils.DBUtil;

public class tb_FloorDAO {
	public List<floorObj> getFloorList(DBUtil util,Integer siteId,Integer blockId)  
    {  
        List<floorObj> p=new ArrayList<floorObj>();  
        Log.i("siteId", String.valueOf(siteId));  
        Log.i("blockId", String.valueOf(blockId));
        Cursor cursor=util.rawQuery("select * from tb_floor where  site_id=? and block_id=?", new String[]{siteId.toString(),blockId.toString()});  
        while(cursor.moveToNext())  
        {  
        	floorObj obj=new floorObj(); 
        	obj.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
        	obj.setBlockId(cursor.getInt(cursor.getColumnIndex("block_id")));
        	obj.setFloorId(cursor.getInt(cursor.getColumnIndex("floor_id")));
        	obj.setFloorName(cursor.getString(cursor.getColumnIndex("floor_name")));
        	obj.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            p.add(obj);  
        }  
        cursor.close();  
        return p;  
    } 
}
