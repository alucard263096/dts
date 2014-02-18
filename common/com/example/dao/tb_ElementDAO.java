package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.objects.areaObj;
import com.example.objects.elementObj;
import com.example.utils.DBUtil;

public class tb_ElementDAO {

	public List<elementObj> getElementList(DBUtil util,Integer areaId, Integer siteId,
			Integer flatTypeId,Integer elementTypeId) {
		List<elementObj> p = new ArrayList<elementObj>();
		Cursor cursor = util
				.rawQuery(
						"select * from tb_area_element a "
								+ " INNER join tb_element  e on a.element_id=e.element_id "
								+ " where area_id= ? and flat_type_id=? and a.site_id=? and a.element_type_id=?",
						new String[] { areaId.toString(),
								flatTypeId.toString(), siteId.toString(),elementTypeId.toString() });
		while (cursor.moveToNext()) {
			elementObj element = new elementObj();
			element.setElementId(cursor.getInt(cursor
					.getColumnIndex("element_id")));
			element.setSiteId(cursor.getInt(cursor.getColumnIndex("site_id")));
			element.setElementTypeId(cursor.getInt(cursor
					.getColumnIndex("element_type_id")));
			element.setElementName(cursor.getString(cursor
					.getColumnIndex("element_name")));
			element.setTradeId(cursor.getInt(cursor.getColumnIndex("trade_id")));
			element.setDesc(cursor.getString(cursor.getColumnIndex("description")));
			p.add(element);
		}
		cursor.close();
		return p;
	}
}
