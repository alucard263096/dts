package com.example.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.objects.StaticVar;
import com.example.objects.defectListObj;
import com.example.objects.defectObj;
import com.example.objects.elementObj;
import com.example.utils.DBUtil;

public class tb_DefectDAO {

	public List<defectListObj> getDefectList(DBUtil util,Integer areaId, Integer siteId,
			Integer flatId,Integer elementId,Integer roundNo) {
		List<defectListObj> p = new ArrayList<defectListObj>();
		Cursor cursor = util
				.rawQuery(
						"select e.site_id,d.defect_id,dt.defect_type_name,dt.defect_type_id,d.org_status,d.status,confirm_completion_date from tb_element e "
								+ " INNER join tb_element_defect_type et on e.element_id=et.element_id "
								+ " INNER JOIN tb_defect_type dt on  et.defect_type_id=dt.defect_type_id "
								+ " LEFT JOIN tb_defect d on e.element_id = d.element_id and d.site_id=? and d.flat_id=? and d.area_id=? and d.round_no=? and d.defect_type_id=dt.defect_type_id "
								+ " WHERE e.element_id=? ",
						new String[] { 
								siteId.toString(), flatId.toString(),areaId.toString(),roundNo.toString() ,elementId.toString()});
		while (cursor.moveToNext()) {
			defectListObj defect = new defectListObj();
			defect.setSitetId(cursor.getInt(cursor.getColumnIndex("site_id")));
			defect.setDefectId(cursor.getInt(cursor.getColumnIndex("defect_id")));
			defect.setName(cursor.getString(cursor.getColumnIndex("defect_type_name")));
			defect.setDefectTypeId(cursor.getInt(cursor.getColumnIndex("defect_type_id")));
			defect.setElementId(elementId);
			defect.setOrgStatus(cursor.getInt(cursor.getColumnIndex("org_status")));
			defect.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

			defect.setConfirmCompletionDate(cursor.getString(cursor.getColumnIndex("confirm_completion_date")));
			p.add(defect);
		}
		cursor.close();
		return p;
	}
	
	public defectObj getDefectById(DBUtil util,Integer defectId)  
    {  
		defectObj defect=new defectObj();
		try {
			String sql = "select * from tb_defect where defect_id=?";
			Cursor cur = util.rawQuery(sql, new String[] {defectId.toString()});
			if(cur.moveToFirst()) {
			    defect.setDetailDesc(cur.getString(cur.getColumnIndex("detail_desc")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return defect;  
    }  
	
	public int addDefect(DBUtil util,defectObj defect){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	
	        	int defectId=0;
	        	Cursor cursor = util.rawQuery("select max(defect_id) AS maxId from tb_defect",null);  
	             if(cursor.moveToFirst())
	            	 defectId  = cursor .getInt(cursor.getColumnIndex("maxId"))+1; 
	        	 StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("INSERT INTO tb_defect(defect_id,site_id,flat_id,area_id,element_type_id,element_id,defect_type_id,round_no,detail_desc,scheduled_completion_date,actual_completion_date,defect_form_ref,form_received_date,description,status,created_user_id,created_date,updated_user_id,updated_date,org_status) ");
	             sql_insert.append(" VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?, ?,datetime('now', 'localtime'), ?, datetime('now', 'localtime'),0)");
	             Object[] bindArgs = {defectId,defect.getSitetId(),defect.getFlatId(),defect.getAreaId(),defect.getElementTypeId(),defect.getElementId(),defect.getDefectTypeId(),defect.getRoundNo(),defect.getDetailDesc(),defect.getScheduledCompletionDate(),defect.getActualCompletionDate(),defect.getDefectFormRef(),defect.getFormReceivedDate(),defect.getDesc(),defect.getStatus(),defect.getCreatedUserId(),defect.getUpdatedUserId()};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             result=defectId;
	             util.setTransactionSuccessful();
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}
	
	public int updateDefect(DBUtil util,defectObj defect){
		int result=-1;
		 util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("UPDATE  tb_defect set detail_desc=?, updated_user_id=?,updated_date=datetime('now', 'localtime') where defect_id=? ");
	             Object[] bindArgs = {defect.getDetailDesc(),defect.getUpdatedUserId(),defect.getDefectId()};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             util.setTransactionSuccessful();
	             result=1;
	        } finally{
	        	util.endTransaction();
			}
	 
	        return result;
	}

	public void completeDefect(DBUtil util, int defectId) {
		// TODO Auto-generated method stub
		 util.beginTransaction();
	        try {
	        	StringBuffer sql_insert = new StringBuffer();
	             sql_insert.append("UPDATE  tb_defect set confirm_completion_date=datetime('now', 'localtime'), updated_user_id=?,updated_date=datetime('now', 'localtime') where defect_id=? ");
	             Object[] bindArgs = {StaticVar.USERID,defectId};
	             util.execSQL(sql_insert.toString(),bindArgs);
	             util.setTransactionSuccessful();
	        } finally{
	        	util.endTransaction();
			}
	}

	public void rectifyDefect(DBUtil util, int defectId) {
		// TODO Auto-generated method stub
		util.beginTransaction();
        try {
        	StringBuffer sql_insert = new StringBuffer();
             sql_insert.append("UPDATE  tb_defect set status = 1, updated_user_id=?,updated_date=datetime('now', 'localtime') where defect_id=? ");
             Object[] bindArgs = {StaticVar.USERID,defectId};
             util.execSQL(sql_insert.toString(),bindArgs);
             util.setTransactionSuccessful();
        } finally{
        	util.endTransaction();
		}
	}

	public void deleteDefect(DBUtil util, int defectId) {
		// TODO Auto-generated method stub
		util.beginTransaction();
        try {
        	StringBuffer sql_insert = new StringBuffer();
             sql_insert.append("delete from tb_defect  where defect_id=? ");
             Object[] bindArgs = {defectId};
             util.execSQL(sql_insert.toString(),bindArgs);
             util.setTransactionSuccessful();
        } finally{
        	util.endTransaction();
		}
	}

	
}

