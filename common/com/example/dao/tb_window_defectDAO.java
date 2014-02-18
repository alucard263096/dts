package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.example.objects.windowDefectObj;
import com.example.objects.windowDefectTypeObj;
import com.example.utils.DBUtil;

public class tb_window_defectDAO {
	public List<windowDefectObj> getWindowDefectList(DBUtil util,int windowId,int windowTypeId) {
		List<windowDefectObj> p = new ArrayList<windowDefectObj>();
		Cursor cursor = util.rawQuery(
				"select * from tb_window_defect where  window_id=? order by round_no ",
				new String[] {String.valueOf(windowId) });
		while (cursor.moveToNext()) {
			windowDefectObj obj = new windowDefectObj();
			
			
			obj.setWindowDefectId(cursor.getInt(cursor.getColumnIndex("window_defect_id")));
			obj.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
			obj.setFlatId(cursor.getInt(cursor.getColumnIndex("flat_id")));
			obj.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
			obj.setWindowId(cursor.getInt(cursor.getColumnIndex("window_id")));
			obj.setWindowTypeId(cursor.getInt(cursor.getColumnIndex("window_type_id")));
			obj.setWindowDefectTypeId(cursor.getInt(cursor.getColumnIndex("window_defect_type_id")));
			
			obj.setRoundNo(cursor.getInt(cursor.getColumnIndex("round_no")));
			obj.setXcoor(cursor.getInt(cursor.getColumnIndex("x_coor")));
			obj.setYcoor(cursor.getInt(cursor.getColumnIndex("y_coor")));
			
			obj.setDesc(cursor.getString(cursor.getColumnIndex("description")));
			obj.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
			
			obj.SetIsUpdate();
			
			p.add(obj);
		}
		cursor.close();
		return p;
	}

	public void saveWindowDefect(DBUtil util,tb_window_defectDAO dao,
			List<windowDefectObj> defectPointList, int currentWindowType,boolean checkIsSelect) {
		// TODO Auto-generated method stub
		util.beginTransaction();
        try {
        	
        	String insert_sql="insert into tb_window_defect " +
        			"(window_defect_id,site_id,flat_id,area_id,window_id,window_type_id,window_defect_type_id,round_no,x_coor,y_coor,description,status,created_user_id,created_date,updated_user_id,updated_date,org_status)" +
        			" values " +
        			"(?,?,?,?,?,?,?,?,?,?,?,?,?,datetime('now', 'localtime'), ?, datetime('now', 'localtime'),0)";
        	String update_Sql="update tb_window_defect set status=?,x_coor=?,y_coor=?,updated_date=datetime('now', 'localtime'),round_no=?" +
        			"where window_defect_id=?";
        	String delete_sql="delete from tb_window_defect where window_defect_id=?";
        	for(windowDefectObj defect : defectPointList){
        		if(checkIsSelect==true){
        			if(defect.GetSelected()==false){
        				continue;
        			}
        		}
        		if(defect.getWindowTypeId()==currentWindowType){
        				if(defect.IsUpdate()){
    	        			Object[] bindArgs = {defect.getStatus(),defect.getXcoor(),defect.getYcoor(),defect.getRoundNo(),defect.getWindowDefectId()};
    	                    util.execSQL(update_Sql, bindArgs);
    	                    Log.i("defect", "update");
    	        		}
    	        		else{
    	        			int defectId=getMaxDefectId(util);
    	        			defect.setWindowDefectId(defectId);
    	    	             Object[] bindArgs = {defect.getWindowDefectId(),defect.getSiteId(),defect.getFlatId(),defect.getAreaId(),defect.getWindowId(),defect.getWindowTypeId(),defect.getWindowDefectTypeId(),
    	    	            		 defect.getRoundNo(),defect.getXcoor(),defect.getYcoor(),
    	    	            		 defect.getDesc(),defect.getStatus(),defect.getCreatedUserId(),defect.getUpdatedUserId()};
    	                     util.execSQL(insert_sql, bindArgs);
    	                     defect.SetIsUpdate();
    		                    Log.i("defect", "insert");
    	        		}
        			}
        		}
            util.setTransactionSuccessful();
        } finally{
        	util.endTransaction();
		}
	}
	
	public boolean checkExists(DBUtil util,int window_defect_id){
		boolean haveData=false;
    	Cursor cursor = util.rawQuery("select 1  from tb_window_defect where window_defect_id="+String.valueOf(window_defect_id),null);  
         if(cursor.moveToFirst())
        	 haveData=true;
         
         cursor.close();
         
         return haveData;
	}

    public int getMaxDefectId(DBUtil util){
		int defectId=0;
    	Cursor cursor = util.rawQuery("select max(window_defect_id) AS maxId from tb_window_defect",null);  
         if(cursor.moveToFirst())
        	 defectId  = cursor .getInt(cursor.getColumnIndex("maxId")); 
         cursor.close();
         return defectId+1;
    }

}
