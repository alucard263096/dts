package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.example.objects.flat_imageObj;
import com.example.utils.DBUtil;

public class tb_Flat_ImageDAO {

	public List<flat_imageObj> getFlatImageList(DBUtil util,Integer FlatId)  
    {  
        List<flat_imageObj> p=new ArrayList<flat_imageObj>();  
        Cursor cursor=util.rawQuery("select * from tb_flat_image where flat_id= ?", new String[]{FlatId.toString()});  
        while(cursor.moveToNext())  
        {  
        	flat_imageObj image=new flat_imageObj(); 
            image.setFlatImageId(cursor.getInt(cursor.getColumnIndex("flat_image_id")));
            image.setFlatId(cursor.getInt(cursor.getColumnIndex("flat_id")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
            p.add(image);  
        }  
        cursor.close();  
        return p;  
    }  
	
	public flat_imageObj getFlatImage(DBUtil util,Integer FlatId)  
    {  
    	flat_imageObj image=new flat_imageObj();  
        Cursor cursor=util.rawQuery("select * from tb_flat_image where flat_id= ?", new String[]{FlatId.toString()});  
        if(cursor.moveToFirst())  
        {  
            image.setFlatImageId(cursor.getInt(cursor.getColumnIndex("flat_image_id")));
            image.setFlatId(cursor.getInt(cursor.getColumnIndex("flat_id")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic"))); 
        }  
        cursor.close();  
        return image;  
    }
}
