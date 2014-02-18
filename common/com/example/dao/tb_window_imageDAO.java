package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.database.Cursor;

import com.example.objects.defect_imageObj;
import com.example.utils.DBUtil;

public class tb_window_imageDAO {
	
	public List<defect_imageObj> getWindowImageList(DBUtil util,Integer windowId)  
    {  
        List<defect_imageObj> p=new ArrayList<defect_imageObj>();  
        Cursor cursor=util.rawQuery("select * from tb_window_image where window_id= ? and image_type=1", new String[]{windowId.toString()});  
        while(cursor.moveToNext())  
        {  
        	defect_imageObj image=new defect_imageObj(); 
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("window_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("window_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
            p.add(image);  
        }  
        cursor.close();  
        return p;  
    }  
	
	public int addWindowImage(DBUtil util,defect_imageObj dImageObj){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	int defectImageId=0;
	        	Cursor cursor = util.rawQuery("select max(window_image_id) AS maxId from tb_window_image",null);  
	             if(cursor.moveToFirst())
	            	 defectImageId  = cursor .getInt(cursor.getColumnIndex("maxId"))+1; 
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("INSERT INTO tb_window_image(window_image_id,window_id,lock_key,image_type,pic,status,created_user_id,created_date,updated_user_id,updated_date) ");
	             sql_insert.append(" VALUES( ?,?,?,?,?,?,?,datetime('now', 'localtime'), ?, datetime('now', 'localtime'))");
	             Object[] bindArgs = {defectImageId,dImageObj.getDefectId(),System.currentTimeMillis(),dImageObj.getImageType(),dImageObj.getPic(),dImageObj.getStatus(),dImageObj.getCreatedUserId(),dImageObj.getUpdatedUserId()};
	             
	             util.execSQL(sql_insert.toString(),bindArgs);
	             result=defectImageId;
	             util.setTransactionSuccessful();
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}
	
	public int updateWindowImage(DBUtil util,defect_imageObj dImageObj){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("UPDATE  tb_window_image set pic=?, updated_user_id=?,updated_date=datetime('now', 'localtime') where window_image_id=? ");
	             Object[] bindArgs = {dImageObj.getPic(),dImageObj.getUpdatedUserId(),dImageObj.getDefectImageId()};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             util.setTransactionSuccessful();
	             result=dImageObj.getDefectImageId();
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}
	
	public int DeleteDefectImage(DBUtil util,int defectImageId){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("DELETE FROM  tb_window_image  where window_image_id=? ");
	             Object[] bindArgs = {defectImageId};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             util.setTransactionSuccessful();
	             result=1;
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}
	
	public defect_imageObj getDefectImage(DBUtil util,Integer windowImageId)  
    {  
		defect_imageObj image=new defect_imageObj();  
        Cursor cursor=util.rawQuery("select * from tb_window_image where window_image_id= ? ", new String[]{windowImageId.toString()});  
        if(cursor.moveToFirst())  
        {   
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("window_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("window_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
        }  
        cursor.close();  
        return image;  
    } 
	
	public defect_imageObj getPreviousDefectImage(DBUtil util,Integer defectImageId,Integer windowId)  
    {  
		defect_imageObj image=null;  
        Cursor cursor=util.rawQuery("select * from tb_window_image where window_image_id< ? and window_id=? and image_type=1 order by window_image_id desc ", new String[]{defectImageId.toString(),windowId.toString()});  
        if(cursor.moveToFirst())  
        {   image=new defect_imageObj();
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("window_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("window_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
        }  
        cursor.close();  
        return image;  
    } 
	
	public defect_imageObj getNextDefectImage(DBUtil util,Integer defectImageId,Integer windowId)  
    {  
		defect_imageObj image=null; 
        Cursor cursor=util.rawQuery("select * from tb_window_image where window_image_id> ? and window_id=? and image_type=1 order by window_image_id ", new String[]{defectImageId.toString(),windowId.toString()});  
        if(cursor.moveToFirst())  
        {   image=new defect_imageObj(); 
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("window_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("window_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
        }  
        cursor.close();  
        return image;  
    }
	

	public void createTable(DBUtil util)  
    {  
		
        Cursor cursor=util.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='tb_window_image'; ", new String[]{});  
        boolean have=false;
        if(cursor.moveToFirst())  
        {   have=true;
        }  
        //if(have==false){
        //	util.execSQL("CREATE TABLE tb_window_image ([window_image_id] int NOT NULL  ,[window_id] int NOT NULL,[image_type] smallint NOT NULL,[pic] image NOT NULL,[lock_key] timestamp NOT NULL,[status] smallint NOT NULL,[created_user_id] int NOT NULL,[created_date] datetime NOT NULL,[updated_user_id] int NOT NULL,[updated_date] datetime NOT NULL);",  new String[]{});
        //}
        cursor.close();  
    }
	
}
