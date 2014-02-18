package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.example.objects.flatObj;
import com.example.utils.DBUtil;

public class tb_flatDAO {
	
	public String getTitleString(DBUtil util,Integer flatId)  
    {  
		String titleString="";
		try {

			String sql = "select f.\"description\" AS flat_name,fo.floor_name,b.block_name,s.site_name from tb_flat f INNER JOIN tb_floor fo on f.floor_id=fo.floor_id INNER JOIN tb_block b on f.block_id=b.block_id INNER JOIN tb_site s on f.site_id=s.site_id where flat_id= ?";
			Cursor cur = util.rawQuery(sql, new String[] {flatId.toString()});
			if(cur.moveToFirst()) {
			    titleString=cur.getString(cur.getColumnIndex("site_name"))+", "+cur.getString(cur.getColumnIndex("block_name"))+", "+cur.getString(cur.getColumnIndex("floor_name"))+", "+cur.getString(cur.getColumnIndex("flat_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return titleString;  
    }  
	
	public List<flatObj> getFlatList(DBUtil util,Integer siteId,Integer blockId,Integer floorId)  
    {  
        List<flatObj> p=new ArrayList<flatObj>();  
        Cursor cursor=util.rawQuery("select * from tb_flat where   site_id=? and block_id=? and floor_id=?", new String[]{siteId.toString(),blockId.toString(),floorId.toString()});  
        while(cursor.moveToNext())  
        {  
        	flatObj obj=new flatObj(); 
        	obj.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
        	obj.setBlockId(cursor.getInt(cursor.getColumnIndex("block_id")));
        	obj.setFloorId(cursor.getInt(cursor.getColumnIndex("floor_id")));
        	obj.setFlatId(cursor.getInt(cursor.getColumnIndex("flat_id")));
        	obj.setFlatTypeId(cursor.getInt(cursor.getColumnIndex("flat_type_id")));
        	obj.setFlatName(cursor.getString(cursor.getColumnIndex("flat_code")));
        	obj.setDesc(cursor.getString(cursor.getColumnIndex("description")));
            p.add(obj);  
        }  
        cursor.close();  
        return p;  
    } 
}