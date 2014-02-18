package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.database.Cursor;

import com.example.objects.defect_imageObj;
import com.example.utils.DBUtil;

public class tb_Defect_imageDAO {
	
	public List<defect_imageObj> getDefectImageList(DBUtil util,Integer defectId,Integer imageType)  
    {  
        List<defect_imageObj> p=new ArrayList<defect_imageObj>();  
        Cursor cursor=util.rawQuery("select * from tb_defect_image where defect_id= ? and image_type=?", new String[]{defectId.toString(),imageType.toString()});  
        while(cursor.moveToNext())  
        {  
            defect_imageObj image=new defect_imageObj(); 
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("defect_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("defect_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
            p.add(image);  
        }  
        cursor.close();  
        return p;  
    }  
	
	public int addDefectImage(DBUtil util,defect_imageObj dImageObj){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	int defectImageId=0;
	        	Cursor cursor = util.rawQuery("select max(defect_image_id) AS maxId from tb_defect_image",null);  
	             if(cursor.moveToFirst())
	            	 defectImageId  = cursor .getInt(cursor.getColumnIndex("maxId"))+1; 
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("INSERT INTO tb_defect_image(defect_image_id,defect_id,image_type,pic,status,created_user_id,created_date,updated_user_id,updated_date) ");
	             sql_insert.append(" VALUES( ?,?,?,?,?,?,datetime('now', 'localtime'), ?, datetime('now', 'localtime'))");
	             Object[] bindArgs = {defectImageId,dImageObj.getDefectId(),dImageObj.getImageType(),dImageObj.getPic(),dImageObj.getStatus(),dImageObj.getCreatedUserId(),dImageObj.getUpdatedUserId()};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             result=defectImageId;
	             util.setTransactionSuccessful();
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}
	
	public int updateDefectImage(DBUtil util,defect_imageObj dImageObj){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("UPDATE  tb_defect_image set pic=?, updated_user_id=?,updated_date=datetime('now', 'localtime') where defect_image_id=? ");
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
	             sql_insert.append("DELETE FROM  tb_defect_image  where defect_image_id=? ");
	             Object[] bindArgs = {defectImageId};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             util.setTransactionSuccessful();
	             result=1;
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}
	
	public defect_imageObj getDefectImage(DBUtil util,Integer defectImageId)  
    {  
		defect_imageObj image=new defect_imageObj();  
        Cursor cursor=util.rawQuery("select * from tb_defect_image where defect_image_id= ? ", new String[]{defectImageId.toString()});  
        if(cursor.moveToFirst())  
        {   
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("defect_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("defect_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
        }  
        cursor.close();  
        return image;  
    } 
	
	public defect_imageObj getPreviousDefectImage(DBUtil util,Integer defectImageId,Integer imageTypeId)  
    {  
		defect_imageObj image=null;  
        Cursor cursor=util.rawQuery("select * from tb_defect_image where defect_image_id< ? and image_type=? order by defect_image_id desc ", new String[]{defectImageId.toString(),imageTypeId.toString()});  
        if(cursor.moveToFirst())  
        {   image=new defect_imageObj();
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("defect_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("defect_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
        }  
        cursor.close();  
        return image;  
    } 
	
	public defect_imageObj getNextDefectImage(DBUtil util,Integer defectImageId,Integer imageTypeId)  
    {  
		defect_imageObj image=null; 
        Cursor cursor=util.rawQuery("select * from tb_defect_image where defect_image_id> ? and image_type=? order by defect_image_id ", new String[]{defectImageId.toString(),imageTypeId.toString()});  
        if(cursor.moveToFirst())  
        {   image=new defect_imageObj(); 
            image.setDefectImageId(cursor.getInt(cursor.getColumnIndex("defect_image_id")));
            image.setDefectId(cursor.getInt(cursor.getColumnIndex("defect_id")));
            image.setImageType(cursor.getInt(cursor.getColumnIndex("image_type")));
            image.setPic(cursor.getBlob(cursor.getColumnIndex("pic")));
        }  
        cursor.close();  
        return image;  
    }
}
