package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.example.objects.areaElementWindowObj;
import com.example.utils.DBUtil;

public class tb_Area_ElementDAO {

	public List<areaElementWindowObj> getAreaElementList(DBUtil util,
			Integer areaId, Integer siteId, Integer flatTypeId) {

		Cursor cursor = null;
		List<areaElementWindowObj> p = null;
		try {
			p = new ArrayList<areaElementWindowObj>();
			cursor = util
					.rawQuery(
							"select * from tb_area_element a "
									+ " inner join tb_element_type  t on a.element_type_id=t.element_type_id "
									+ " where a.site_id= ? and flat_type_id=? and area_id=?",
							new String[] {siteId.toString(), 
									flatTypeId.toString(), areaId.toString() });
			Log.i("sql", "select * from tb_area_element a "
									+ " inner join tb_element_type  t on a.element_type_id=t.element_type_id "
									+ " where a.site_id= "+siteId.toString()+" and flat_type_id="+flatTypeId.toString()+" and area_id="+areaId.toString()+"");
			while (cursor.moveToNext()) {
				boolean canAdd = true;
				for (areaElementWindowObj o : p) {
					if (o.getElementTypeId() == cursor.getInt(cursor
							.getColumnIndex("element_type_id"))) {
						canAdd = false;
					}
				}
				if (canAdd) {
					areaElementWindowObj areaElement = new areaElementWindowObj();
					areaElement.setAreaId(cursor.getInt(cursor
							.getColumnIndex("area_id")));
					areaElement.setSiteId(cursor.getInt(cursor
							.getColumnIndex("site_id")));
					areaElement.setFlatTypeId(cursor.getInt(cursor
							.getColumnIndex("flat_type_id")));
					areaElement.setName(cursor.getString(cursor
							.getColumnIndex("element_type_name")));
					areaElement.setElementTypeId(cursor.getInt(cursor
							.getColumnIndex("element_type_id")));
					areaElement.setId(cursor.getInt(cursor
							.getColumnIndex("element_id")));
					areaElement.setIsWindow(false);
					p.add(areaElement);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return p;

	}
}
