package com.example.dts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import com.example.common.CommonEnum;
import com.example.common.ViewFlowAdapter;
import com.example.dao.tb_DefectDAO;
import com.example.dao.tb_Defect_RemarkDAO;
import com.example.dao.tb_Defect_imageDAO;
import com.example.dao.tb_Flat_ImageDAO;
import com.example.dao.tb_Sys_parameterDAO;
import com.example.dao.tb_window_imageDAO;
import com.example.objects.defectObj;
import com.example.objects.defect_imageObj;
import com.example.objects.flat_imageObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.FormatUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public  class WindowImageActivity extends ImageActivity {
	
	
	public void goBack() {

		WindowImageActivity.this.setResult(RESULT_OK); 
		WindowImageActivity.this.finish();
	}
	
	public void findViews() {
		super.findViews();
		
		
		btnRemarks = (Button) findViewById(R.id.btnRemarks);
		btnClearRemarks = (Button) findViewById(R.id.btnClearRemarks);
		btnFloorPlan = (Button) findViewById(R.id.btnFloorPlan);
		btnSketch = (Button) findViewById(R.id.btnSketch);
		btnRemarks.setVisibility(View.INVISIBLE);
		btnClearRemarks.setVisibility(View.INVISIBLE);
		btnFloorPlan.setVisibility(View.INVISIBLE);
		btnSketch.setVisibility(View.INVISIBLE);
		btnEditPhoto.setVisibility(View.INVISIBLE);
		
		image_type=1;
	}
	
	public void init() {
		view_title.setText(title + defectName);
		initViewFlow(CommonEnum.ImageType.DefectImage.getIndex());
	}
	

	public Bundle getBundleForAdapter(){
		Bundle bundle = new Bundle();
		bundle.putInt("KEY_FLAT_ID",flatId);
		bundle.putInt("KEY_SITE_ID",siteId);
		bundle.putInt("KEY_FLAT_TYPE_ID",flatTypeId);
		bundle.putInt("KEY_AREA_ID",areaId);
		bundle.putInt("KEY_ROUND_NO", roundNo);
		bundle.putString("KEY_TITLE", title);
		bundle.putInt("KEY_DEFECT_ID", defectId);
		bundle.putInt("KEY_ELEMENT_TYPE_ID", elementTypeId);
		bundle.putStringArray("KEY_MENU", menu);
		bundle.putInt("KEY_ELEMENT_ID", elementId);
		bundle.putString("KEY_DEFECT_NAME", defectName);
		bundle.putInt("KEY_DEFECT_TYPE_ID", defectTypeId);
		bundle.putInt("KEY_IMAGE_TABLE", 1);
		return bundle;
		
	};
	
	public List<List<defect_imageObj>> initImageList(int imageType) {
		List<List<defect_imageObj>> list = new ArrayList<List<defect_imageObj>>();
		DBUtil userdbutil = DBFactory.GetUserDB(WindowImageActivity.this);
		userdbutil.open();
		DBUtil util = DBFactory.GetBaseDB(WindowImageActivity.this);
		util.open();
		tb_window_imageDAO dao = new tb_window_imageDAO();
		tb_Sys_parameterDAO dao2 = new tb_Sys_parameterDAO();
		
		int numOfPhotos = 0;
		// get DB image
		List<defect_imageObj> defetList = new ArrayList<defect_imageObj>();
		defetList = dao.getWindowImageList(userdbutil, defectId);
		if (imageType == CommonEnum.ImageType.DefectImage.getIndex()) {
			numOfPhotos = dao2.getNumsOfPhotos(util);
		} else if (imageType == CommonEnum.ImageType.Skecth.getIndex()) {
			numOfPhotos = dao2.getNumsOfSketchs(util);
		} else {
			numOfPhotos=dao2.getNumsOfFloorPlan(util);
			/*if (defetList.size() <= 0) {
				List<flat_imageObj> flatImageList = fDao.getFlatImageList(util,
						flatId);
				for (flat_imageObj image : flatImageList) {
					defect_imageObj imageObj = new defect_imageObj();
					imageObj.setPic(image.getPic());
					imageObj.setImageType(imageType);
					defetList.add(imageObj);
				}
			}*/

		}

		util.close();
		userdbutil.close();
			Resources resource = getBaseContext().getResources();
			for (int i = defetList.size(); i < numOfPhotos; i++) {
				defect_imageObj imageObj = new defect_imageObj();
				imageObj.setPic(new FormatUtil().drawableToByte(resource
						.getDrawable(R.drawable.p_01)));
				imageObj.setImageType(imageType);
				defetList.add(imageObj);
			}

		List<defect_imageObj> tempList = new ArrayList<defect_imageObj>();
		for (int i = 0; i < defetList.size(); i++) {
			tempList.add(defetList.get(i));
			if (((i + 1) % PAGE_SIZE == 0) || i == defetList.size() - 1) {
				list.add(tempList);
				tempList = new ArrayList<defect_imageObj>();
			}
		}

		return list;
	}

	public int saveDefectImage(int defectImageId, byte[] pic) {
		int result = -1;
		defect_imageObj dImageObj = new defect_imageObj();
		dImageObj.setUserId(1);
		dImageObj.setCreatedUserId(1);
		dImageObj.setUpdatedUserId(1);
		dImageObj.setDefectId(defectId);
		dImageObj.setImageType(CommonEnum.ImageType.DefectImage.getIndex());
		dImageObj.setPic(pic);
		dImageObj.setStatus(0);
		DBUtil util = DBFactory.GetUserDB(WindowImageActivity.this);
		util.open();
		tb_window_imageDAO dao = new tb_window_imageDAO();
		if (defectImageId <= 0) {
			result = dao.addWindowImage(util, dImageObj);
		} else {
			result = dao.updateWindowImage(util, dImageObj);
		}
		util.close();
		Log.i("dbsize", String.valueOf(util.getDatabaseLength()));
		return result;
	}
	
	public void SaveDefect(){

		goBack();
		
	}
}
