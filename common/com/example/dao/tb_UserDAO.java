package com.example.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.utils.DBUtil;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class tb_UserDAO {

	public List<String> getUserByLoginName(DBUtil util,String loginName) {
		List<String> list = new ArrayList<String>();
		try {

			String sql = "select * from tb_user where login_name='" + loginName
					+ "'";
			Cursor cur = util.rawQuery(sql, new String[] {});
			while (cur.moveToNext()) {
				for (int i = 0; i <= cur.getColumnCount() - 1; i++) {
					list.add(cur.getString(i)); // user_id
				}
			}
			cur.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void updateLastSyncDate(DBUtil util,int user_id){
		
			String sql="update tb_user set last_sync_date=datetime('now', 'localtime') where user_id=?";
			try{
				util.execSQL(sql, new Object[]{user_id});
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public String getUserLastSyncDate(DBUtil util,int user_id) {

		String strDate="1990 1-1 0:0:0";
		try {
			String sql = "select last_sync_date from tb_user where user_id=" + String.valueOf(user_id);
			Cursor cur = util.rawQuery(sql, new String[] {});
			while (cur.moveToNext()) {
				strDate=	cur.getString(0);
			}
			cur.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strDate;
	}
	
}
