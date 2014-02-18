package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;


import com.example.utils.DBUtil;

public class tb_Defect_RemarkDAO {
	public String[] getRemarkArray(DBUtil util)  
    {  
        List<String> p=new ArrayList<String>();  
        Cursor cursor=util.rawQuery("select * from tb_defect_remark where status= 0", new String[]{});  
        while(cursor.moveToNext())  
        {  
            p.add(cursor.getString(cursor.getColumnIndex("description")));  
        }  
        cursor.close();  
        return  (String[])p.toArray(new String[p.size()]);  
    }  
}
