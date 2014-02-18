package com.example.dts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.CommonEnum;
import com.example.common.ViewFlowAdapter;
import com.example.dao.tb_DefectDAO;
import com.example.dao.tb_Defect_RemarkDAO;
import com.example.dao.tb_Defect_imageDAO;
import com.example.dao.tb_Sys_parameterDAO;
import com.example.objects.defectObj;
import com.example.objects.defect_imageObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.FormatUtil;

public  class ImageActivity extends Activity {

	protected ViewFlow viewFlow;
	protected int PAGE_SIZE = 5;
	protected List<List<defect_imageObj>> list;
	protected EditText editRemarks;
	protected ViewFlowAdapter adp;
	protected int siteId;
	protected Button btnRemarks;
	protected Button btnClearRemarks;
	protected Button btnEditPhoto;
	protected Button btnFloorPlan;
	protected Button btnSketch;
	protected Button btnSave;
	protected int flatId;
	protected int areaId;
	protected int flatTypeId;
	protected int defectTypeId;
	protected int roundNo;
	protected String menu[];
	protected String title;
	protected String defectName;
	protected TextView view_title;
	protected int elementTypeId;
	protected int elementId;
	protected int defectId;
	protected String[] standardRemarks;
	protected boolean waitDouble = true;
	protected static final int DOUBLE_CLICK_TIME = 350;
	protected int currentImageType=0;
	
	protected int image_type=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		getBundleData();
		findViews();
		init();
		setListensers();
		initViewFlow(currentImageType);
	}
	
	public void findViews() {
		view_title = (TextView) findViewById(R.id.txtTitle);
		btnRemarks = (Button) findViewById(R.id.btnRemarks);
		btnClearRemarks = (Button) findViewById(R.id.btnClearRemarks);
		editRemarks = (EditText) findViewById(R.id.editTxtRemarks);
		btnSave = (Button) findViewById(R.id.btnSaveClose);
		btnEditPhoto = (Button) findViewById(R.id.btnEditPhoto);
		btnFloorPlan = (Button) findViewById(R.id.btnFloorPlan);
		btnSketch = (Button) findViewById(R.id.btnSketch);
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
		bundle.putInt("KEY_IMAGE_TABLE", 0);
		return bundle;
		
	};
	public void init() {
		view_title.setText(title + defectName);
		

		DBUtil util = DBFactory.GetUserDB(ImageActivity.this);
		util.open();
		tb_Defect_RemarkDAO dao = new tb_Defect_RemarkDAO();
		standardRemarks = dao.getRemarkArray(util);
		if (defectId > 0) {
			tb_DefectDAO defectDAO = new tb_DefectDAO();
			defectObj defect = defectDAO.getDefectById(util, defectId);
			editRemarks.setText(defect.getDetailDesc());
		}
		util.close();
	}

	private void setListensers() {
		btnSave.setOnTouchListener(TouchButton);
		btnClearRemarks.setOnTouchListener(TouchButton);
		btnRemarks.setOnTouchListener(TouchButton);
		btnSave.setOnClickListener(btnSaveClick);
		btnClearRemarks.setOnClickListener(btnClearRemarksClick);
		btnRemarks.setOnClickListener(btnRemarksClick);
		btnEditPhoto.setOnClickListener(btnChangePhoto);
		btnFloorPlan.setOnClickListener(btnChangePhoto);
		btnSketch.setOnClickListener(btnChangePhoto);
	}

	public void getBundleData() {
		Bundle bundle = this.getIntent().getExtras();
		siteId = bundle.getInt("KEY_SITE_ID");
		flatId = bundle.getInt("KEY_FLAT_ID");
		flatTypeId = bundle.getInt("KEY_FLAT_TYPE_ID");
		areaId = bundle.getInt("KEY_AREA_ID");
		roundNo = bundle.getInt("KEY_ROUND_NO");
		elementTypeId = bundle.getInt("KEY_ELEMENT_TYPE_ID");
		menu = bundle.getStringArray("KEY_MENU");
		defectName = bundle.getString("KEY_DEFECT_NAME");
		title = bundle.getString("KEY_TITLE");
		elementId = bundle.getInt("KEY_ELEMENT_ID");
		defectId = bundle.getInt("KEY_DEFECT_ID");
		defectTypeId = bundle.getInt("KEY_DEFECT_TYPE_ID");
		currentImageType=bundle.getInt("KEY_IMAGE_TYPE");
		if(currentImageType<=0){
			currentImageType=CommonEnum.ImageType.DefectImage.getIndex();
		}
	}

	public void initViewFlow(int imageType) {
		if(imageType==CommonEnum.ImageType.DefectImage.getIndex()){
			btnEditPhoto.setBackgroundResource(R.drawable.bg_but_select);
			btnFloorPlan.setBackgroundResource(R.drawable.bg_but);
			btnSketch.setBackgroundResource(R.drawable.bg_but);
		}
		else if(imageType==CommonEnum.ImageType.FloorPlan.getIndex()){
			btnEditPhoto.setBackgroundResource(R.drawable.bg_but);
			btnFloorPlan.setBackgroundResource(R.drawable.bg_but_select);
			btnSketch.setBackgroundResource(R.drawable.bg_but);
		}
		else{
			btnEditPhoto.setBackgroundResource(R.drawable.bg_but);
			btnFloorPlan.setBackgroundResource(R.drawable.bg_but);
			btnSketch.setBackgroundResource(R.drawable.bg_but_select);
		}
		currentImageType=imageType;
		ImageView big_image = (ImageView) ImageActivity.this
				.findViewById(R.id.big_imageView);
		big_image.setImageDrawable(null);
		list = initImageList(imageType);
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		adp = new ViewFlowAdapter(ImageActivity.this, list,image_type);
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		// System.out.println("viewFlow==null?:" + viewFlow == null);
		// System.out.println("Adp==null?:" + adp == null);
		viewFlow.setAdapter(adp);

		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
	}

	public List<List<defect_imageObj>> initImageList(int imageType) {
		List<List<defect_imageObj>> list = new ArrayList<List<defect_imageObj>>();
		DBUtil userdbutil = DBFactory.GetUserDB(ImageActivity.this);
		userdbutil.open();
		DBUtil util = DBFactory.GetBaseDB(ImageActivity.this);
		util.open();
		tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
		tb_Sys_parameterDAO dao2 = new tb_Sys_parameterDAO();
		
		int numOfPhotos = 0;
		// get DB image
		List<defect_imageObj> defetList = new ArrayList<defect_imageObj>();
		defetList = dao.getDefectImageList(userdbutil, defectId, imageType);
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
			imageObj.setPic(new FormatUtil().drawableToByte(resource.getDrawable(R.drawable.p_01)));
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		initViewFlow(currentImageType);
		if (resultCode == RESULT_OK) {
			int Type = Integer.parseInt(String.valueOf(requestCode).substring(
					0, 1));
			if (Type == CommonEnum.PictureType.FromPhoto.getIndex()
					|| Type == CommonEnum.PictureType.FromCamera.getIndex()) {

				Bitmap smallBitmap = null;
				if (Type == CommonEnum.PictureType.FromPhoto.getIndex()) {

					ContentResolver resolver = getContentResolver();
					try {
						Uri originalUri = data.getData();
						Bitmap photo = MediaStore.Images.Media.getBitmap(
								resolver, originalUri);
						if (photo != null) {
							//smallBitmap = FormatUtil.zoomBitmap(photo, 710, 540);
							smallBitmap = FormatUtil.scaleBitmap(photo, 710, 540);
							photo.recycle();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					//Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/dts_image.jpg");
					//smallBitmap = FormatUtil.zoomBitmap(bitmap, 880, 540);
					//bitmap.recycle();
					
					smallBitmap = FormatUtil.decodeSampledBitmapFromFile(Environment.getExternalStorageDirectory() + "/dts_image.jpg", 880, 540);
					
					// FormatUtil.savePhotoToSDCard(smallBitmap,
					// Environment.getExternalStorageDirectory().getAbsolutePath(),
					// String.valueOf(System.currentTimeMillis()));
				}
				int position = Integer.parseInt(String.valueOf(requestCode).substring(1, String.valueOf(requestCode).length() - 1));
				int currentImageId = Integer.parseInt(String.valueOf(requestCode).substring(String.valueOf(requestCode).length() - 1));
				int imageViewId = Integer.parseInt(String.valueOf(position) + String.valueOf(currentImageId));
				ImageView iv_image = (ImageView) this.findViewById(imageViewId);
				iv_image.setImageBitmap(smallBitmap);
				ImageView big_image = (ImageView) this
						.findViewById(R.id.big_imageView);
				big_image.setImageBitmap(smallBitmap);
				// big_image.setVisibility(View.VISIBLE);
				list.get(position - 1).get(currentImageId).setPic(FormatUtil.bitmapToBytes(smallBitmap));
				int defectImageId = saveDefectImage(list.get(position - 1).get(currentImageId).getDefectImageId(), FormatUtil.bitmapToBytes(smallBitmap));
				list.get(position - 1).get(currentImageId).setDefectImageId(defectImageId);
				list.get(position - 1).get(currentImageId).setDefectId(defectId);
				((BaseAdapter) adp).notifyDataSetChanged();
			}
		}
		
	}

	public int saveDefectImage(int defectImageId, byte[] pic) {
		int result = -1;
		defectId = getDefectId();
		defect_imageObj dImageObj = new defect_imageObj();
		dImageObj.setUserId(1);
		dImageObj.setCreatedUserId(1);
		dImageObj.setUpdatedUserId(1);
		dImageObj.setDefectId(defectId);
		dImageObj.setImageType(CommonEnum.ImageType.DefectImage.getIndex());
		dImageObj.setPic(pic);
		dImageObj.setStatus(0);
		DBUtil util = DBFactory.GetUserDB(ImageActivity.this);
		util.open();
		tb_Defect_imageDAO dao = new tb_Defect_imageDAO();
		if (defectImageId <= 0) {
			result = dao.addDefectImage(util, dImageObj);
		} else {
			result = dao.updateDefectImage(util, dImageObj);
		}
		util.close();
		return result;
	}

	private int getDefectId() {
		if (defectId <= 0) {
			defectObj defect = new defectObj();
			defect.setDefectId(defectId);
			defect.setUserId(1);
			defect.setDefectType(CommonEnum.DefectType.Defect.getIndex());
			defect.setSitetId(siteId);
			defect.setFlatId(flatId);
			defect.setAreaId(areaId);
			defect.setElementTypeId(elementTypeId);
			defect.setElementId(elementId);
			defect.setDefectTypeId(defectTypeId);
			defect.setRoundNo(roundNo);
			defect.setDetailDesc(String.valueOf(editRemarks.getText()));
			defect.setScheduledCompletionDate(null);
			defect.setActualCompletionDate(null);
			defect.setDefectFormRef(1);
			defect.setFormReceivedDate(null);
			defect.setDesc("");
			defect.setStatus(0);
			defect.setCreatedUserId(1);
			defect.setUpdatedUserId(1);
			DBUtil util = DBFactory.GetUserDB(ImageActivity.this);
			util.open();
			tb_DefectDAO dao = new tb_DefectDAO();
			int result = dao.addDefect(util, defect);
			util.close();
			return result;
		} else {
			return defectId;
		}
	}

	private Button.OnClickListener btnSaveClick = new Button.OnClickListener() {
		public void onClick(View v) {
			SaveDefect();
		}
	};

	public void SaveDefect(){

		defectObj defect = new defectObj();
		defect.setDefectId(defectId);
		defect.setUserId(1);
		defect.setDefectType(CommonEnum.DefectType.Defect.getIndex());
		defect.setSitetId(siteId);
		defect.setFlatId(flatId);
		defect.setAreaId(areaId);
		defect.setElementTypeId(elementTypeId);
		defect.setElementId(elementId);
		defect.setDefectTypeId(defectTypeId);
		defect.setRoundNo(roundNo);
		defect.setDetailDesc(String.valueOf(editRemarks.getText()));
		defect.setScheduledCompletionDate(null);
		defect.setActualCompletionDate(null);
		defect.setDefectFormRef(1);
		defect.setFormReceivedDate(null);
		defect.setDesc("");
		defect.setStatus(0);
		defect.setCreatedUserId(1);
		defect.setUpdatedUserId(1);
		DBUtil util = DBFactory.GetUserDB(ImageActivity.this);
		util.open();
		int result = -1;
		tb_DefectDAO dao = new tb_DefectDAO();
		if (defectId > 0) {
			result = dao.updateDefect(util, defect);
		} else {
			result = dao.addDefect(util, defect);
		}
		util.close();
		goBack();
	}
	
	private Button.OnClickListener btnClearRemarksClick = new Button.OnClickListener() {
		public void onClick(View v) {
			editRemarks.setText("");
		}
	};

	private Button.OnClickListener btnRemarksClick = new Button.OnClickListener() {
		public void onClick(View v) {
			new AlertDialog.Builder(ImageActivity.this)
					.setTitle("Standard Remarks")
					.setItems(standardRemarks,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									try {
										Field field = dialog.getClass()
												.getSuperclass()
												.getDeclaredField("mShowing");
										field.setAccessible(true);
										field.set(dialog, false);
									} catch (Exception e) {
										e.printStackTrace();
									}
									if (waitDouble == true) {
										waitDouble = false;
										Thread thread = new Thread() {
											@Override
											public void run() {
												try {
													sleep(DOUBLE_CLICK_TIME);
													if (waitDouble == false) {
														waitDouble = true;

													}
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
											}
										};
										thread.start();
									} else {
										waitDouble = true;
										doubleClick(which);
									}
								}
							})
					.setNegativeButton("Close",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									try {

										Field field = dialog.getClass()
												.getSuperclass()
												.getDeclaredField("mShowing");

										field.setAccessible(true);

										field.set(dialog, true);

										dialog.dismiss();

									} catch (Exception e) {

										e.printStackTrace();
									}
								}
							}).show();
		}
	};

	private Button.OnClickListener btnChangePhoto = new Button.OnClickListener() {
		public void onClick(View v) {
			if (v.getId() == R.id.btnEditPhoto) {
				if(currentImageType!=CommonEnum.ImageType.DefectImage.getIndex()){
					initViewFlow(CommonEnum.ImageType.DefectImage.getIndex());
				}
				
			} else if (v.getId() == R.id.btnSketch) {
				if(currentImageType!=CommonEnum.ImageType.Skecth.getIndex()){
					initViewFlow(CommonEnum.ImageType.Skecth.getIndex());
				}	
			} else if (v.getId() == R.id.btnFloorPlan) {
				if(currentImageType!=CommonEnum.ImageType.FloorPlan.getIndex()){
					initViewFlow(CommonEnum.ImageType.FloorPlan.getIndex());
				}
			}
		}
	};

	private OnTouchListener TouchButton = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundResource(R.drawable.bg_but_select);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setBackgroundResource(R.drawable.bg_but);
			}
			return false;
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			goBack();
			return true;

		}
		return false;
	}

	public void goBack() {
//		Intent intent = new Intent();
//		intent.setClass(ImageActivity.this, ElementListActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		Bundle bundle = new Bundle();
//		bundle.putInt("KEY_FLAT_ID", flatId);
//		bundle.putInt("KEY_SITE_ID", siteId);
//		bundle.putInt("KEY_FLAT_TYPE_ID", flatTypeId);
//		bundle.putInt("KEY_AREA_ID", areaId);
//		bundle.putInt("KEY_ROUND_NO", roundNo);
//		bundle.putString("KEY_TITLE", title);
//		bundle.putInt("KEY_ELEMENT_TYPE_ID", elementTypeId);
//		bundle.putStringArray("KEY_MENU", menu);
//		bundle.putInt("KEY_ELEMENT_ID", elementId);
//		intent.putExtras(bundle);
//		startActivity(intent);
		ImageActivity.this.finish();
	}

	// 双击响应事件
	private void doubleClick(int index) {
		Editable remarks = editRemarks.getText();
		remarks.append(standardRemarks[index]);
		editRemarks.setText(remarks);
	}
	

	public void onLowMemory()
	{
		System.gc();
		Runtime.getRuntime().gc();
	}
}
