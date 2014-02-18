package com.example.dts;

import java.util.ArrayList;
import java.util.List;

import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.example.common.CommonEnum;
import com.example.dao.tb_Area_ElementDAO;
import com.example.dao.tb_Area_WindowDAO;
import com.example.dao.tb_flatDAO;
import com.example.dao.tb_windowDAO;
import com.example.dao.tb_window_defectDAO;
import com.example.dao.tb_window_defect_typeDAO;
import com.example.dao.tb_window_imageDAO;
import com.example.objects.areaElementWindowObj;
import com.example.objects.windowDefectObj;
import com.example.objects.windowDefectTypeObj;
import com.example.objects.windowObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.FormatUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class WindowListActivity extends Activity {
	UITableView tableView;
	private TextView view_title;
	private Button button_other_type;
	private RelativeLayout windowBGGlazing;
	private RelativeLayout windowBGFrame;
	private List<windowObj> WindowList;
	private windowObj currentWindowObj; 
	private List<windowDefectTypeObj> WindowDefectTypeList;
	private Button btnSelectAll;
	private Button btnDelete;
	private Button btnRectify;
	private Button btnEditPhoto;
	private Button btnSave;
	private Button btnHelp;
	private int siteId;
	private int flatId;
	private int flatTypeId;
	private int areaId;
	private int roundNo;
	private String title;
	private String menu[];
	private TabHost tabHost;
	private List<windowDefectTypeObj> lstDefectType;
	
	private TextView TVDT_B;
	private TextView TVDT_D;
	private TextView TVDT_G;
	private TextView TVDT_M;
	private TextView TVDT_P;
	private TextView TVDT_O;
	private TextView TVDT_S;
	private TextView TVDT_A;
	
	private String defectTypeCode="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_list);
		
		
		getBundleData();
		findViews();
		init();
		setListensers();
		createDefectType();
		createTableList();
	}

	private void createDefectType() {
		// TODO Auto-generated method stub
		
	}

	private void getBundleData() {
		Bundle bundle = this.getIntent().getExtras();
		
		
//		
		siteId = bundle.getInt("KEY_SITE_ID");
		flatId = bundle.getInt("KEY_FLAT_ID");
		flatTypeId = bundle.getInt("KEY_FLAT_TYPE_ID");
		areaId = bundle.getInt("KEY_AREA_ID");
		menu = bundle.getStringArray("KEY_MENU");
		roundNo=bundle.getInt("KEY_ROUND_NO");
		title=bundle.getString("KEY_TITLE");
		
//	
//		siteId = 1;
//		flatId = 1;
//		flatTypeId = 1;
//		areaId = 1;
//		menu = new String[] {"aaa" };
//		roundNo=1;
//		title="半山道一號, 雲衫軒, 1樓, 1單元 , #Round 1";
		
	}

	private void findViews() {
		view_title = (TextView) findViewById(R.id.txtTitle);
		button_other_type=(Button) findViewById(R.id.btnParent);
		tableView = (UITableView) findViewById(R.id.tableView);
		windowBGGlazing=(RelativeLayout) findViewById(R.id.window_bg_glazing);
		windowBGFrame=(RelativeLayout) findViewById(R.id.window_bg_frame);
		btnSelectAll=(Button) findViewById(R.id.btnSelectAll);
		btnDelete=(Button) findViewById(R.id.btnDelete);
		btnRectify=(Button) findViewById(R.id.btnRectify);
		btnEditPhoto=(Button) findViewById(R.id.btnEditPhoto);
		btnSave=(Button) findViewById(R.id.btnSave);
		btnHelp=(Button) findViewById(R.id.btnHelp);
		tabHost = (TabHost) findViewById(R.id.tabhost); 
		
		

		TVDT_B = (TextView) findViewById(R.id.TVDT_B);
		TVDT_D = (TextView) findViewById(R.id.TVDT_D);
		TVDT_G = (TextView) findViewById(R.id.TVDT_G);
		TVDT_M = (TextView) findViewById(R.id.TVDT_M);
		TVDT_P = (TextView) findViewById(R.id.TVDT_P);
		TVDT_O = (TextView) findViewById(R.id.TVDT_O);
		TVDT_S = (TextView) findViewById(R.id.TVDT_S);
		TVDT_A = (TextView) findViewById(R.id.TVDT_A);
		
		
	}

	private void init() {
		 view_title.setText(title);
		 //android:drawableLeft="@drawable/error_icon"
		 setGlazingAndFrameWindow();
		 setDefectType();
	}
	
	StringBuilder sbDefectTypeDescription=new StringBuilder();
	
	private void setDefectType() {
		// TODO Auto-generated method stub
		DBUtil util=DBFactory.GetUserDB(WindowListActivity.this);
		util.open();
		tb_window_defect_typeDAO dao = new tb_window_defect_typeDAO();
		lstDefectType= dao.getWindowDefectTypeList(util);
		
		for (windowDefectTypeObj obj : lstDefectType) {
			Log.i("cc", obj.getCode());
			TextView tv=getDefectTypeTVByCode(obj.getCode());
			if(tv!=null){
				tv.setText(obj.getWindowDefectTypeName());
				sbDefectTypeDescription.append(obj.getWindowDefectTypeName()+": "+obj.getDescription()+"\r\n\r\n");
			}
		}
		util.close();
	}

	private String getDefectTypeIdCodeByTypeId(int defectTypeId){
		String code="";
		if(defectTypeId==1){
			code="B";
		}else if(defectTypeId==2){
			code="D";
		}else if(defectTypeId==3){
			code="G";
		}else if(defectTypeId==4){
			code="M";
		}else if(defectTypeId==5){
			code="P";
		}else if(defectTypeId==6){
			code="O";
		}else if(defectTypeId==7){
			code="S";
		}else if(defectTypeId==8){
			code="A";
		}
		return code;
	}
	private int getDefectTypeIdByCode(String code){
		int defectTypeId=0;
		if(code.equals("B")){
			defectTypeId=1;
		}else if(code.equals("D")){
			defectTypeId=2;
		}else if(code.equals("G")){
			defectTypeId=3;
		}else  if(code.equals("M")){
			defectTypeId=4;
		}else  if(code.equals("P")){
			defectTypeId=5;
		}else  if(code.equals("O")){
			defectTypeId=6;
		}else  if(code.equals("S")){
			defectTypeId=7;
		}else  if(code.equals("A")){
			defectTypeId=8;
		}
		return defectTypeId;
	}
	
	private String getDefectTypeCodeById(int id){
		String code="";
		if(TVDT_B.getId()==id){
			code= "B";
		}else if(TVDT_D.getId()==id){
			code= "D";
		}else if(TVDT_G.getId()==id){
			code= "G";
		}else  if(TVDT_M.getId()==id){
			code= "M";
		}else  if(TVDT_P.getId()==id){
			code= "P";
		}else  if(TVDT_O.getId()==id){
			code= "O";
		}else  if(TVDT_S.getId()==id){
			code= "S";
		}else  if(TVDT_A.getId()==id){
			code= "A";
		}
		
		return code;
	}
	private TextView getDefectTypeTVByCode(String code){
		TextView tv=null;
		if(code.equals("B")){
			tv=(TextView)findViewById(R.id.TVDT_B);
		}else if(code.equals("D")){
			tv=(TextView)findViewById(R.id.TVDT_D);
			
		}else if(code.equals("G")){

			tv=(TextView)findViewById(R.id.TVDT_G);
		}else if(code.equals("M")){

			tv=(TextView)findViewById(R.id.TVDT_M);
		}else if(code.equals("P")){

			tv=(TextView)findViewById(R.id.TVDT_P);
		}else if(code.equals("O")){

			tv=(TextView)findViewById(R.id.TVDT_O);
		}else if(code.equals("S")){

			tv=(TextView)findViewById(R.id.TVDT_S);
		}else if(code.equals("A")){

			tv=(TextView)findViewById(R.id.TVDT_A);
		}
		
		return tv;
	}

	private ImageView getDefectTypeIMByCode(String code){
		ImageView tv=null;
		if(code.equals("B")){
			tv=(ImageView)findViewById(R.id.IVDT_B);
		}else if(code.equals("D")){
			tv=(ImageView)findViewById(R.id.IVDT_D);
			
		}else if(code.equals("G")){

			tv=(ImageView)findViewById(R.id.IVDT_G);
		}else if(code.equals("M")){

			tv=(ImageView)findViewById(R.id.IVDT_M);
		}else if(code.equals("P")){

			tv=(ImageView)findViewById(R.id.IVDT_P);
		}else if(code.equals("O")){

			tv=(ImageView)findViewById(R.id.IVDT_O);
		}else if(code.equals("S")){

			tv=(ImageView)findViewById(R.id.IVDT_S);
		}else if(code.equals("A")){

			tv=(ImageView)findViewById(R.id.IVDT_A);
		}
		return tv;
	}
	private void setGlazingAndFrameWindow(){
		
        tabHost.setup();  
        TabWidget tabWidget = tabHost.getTabWidget();  
          
        tabHost.addTab(tabHost.newTabSpec("tab1")  
                .setIndicator("Glazing")  
                .setContent(R.id.window_bg_glazing));  
          
        tabHost.addTab(tabHost.newTabSpec("tab3")  
                .setIndicator("Frame")  
                .setContent(R.id.window_bg_frame));  
          
        for (int i =0; i < tabWidget.getChildCount(); i++) {  
            tabWidget.getChildAt(i).getLayoutParams().height = 40;  
            tabWidget.getChildAt(i).getLayoutParams().width = 165;
        }
	}
	public void setButtonEnabled(Button button, boolean isEnable) {
		button.setEnabled(isEnable);
		if (isEnable) {
			button.setAlpha(1);
		} else {
			button.setAlpha(0.5f);
		}
	}
	
	private void createTableList() {
		
		setButtonEnabled(btnSelectAll, false);
		setButtonEnabled(btnDelete, false);
		setButtonEnabled(btnRectify, false);
		setButtonEnabled(btnEditPhoto, false);
		setButtonEnabled(btnSave, false);
		
		tableView.clear();
		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
		DBUtil util=DBFactory.GetUserDB(WindowListActivity.this);
		util.open();
		
		Object[] bindArgs = {};
		tb_window_imageDAO daoc = new tb_window_imageDAO();
		daoc.createTable(util);
		//util.execSQL("update tb_window_defect set window_defect_type_id=1,status=0 where window_defect_id=34", bindArgs);
		//util.execSQL("update tb_window_defect set window_defect_type_id=2,status=1 where window_defect_id=35", bindArgs);
		//util.execSQL("update tb_window_defect set window_defect_type_id=3,status=0 where window_defect_id=33", bindArgs);
//		
//		util.execSQL("delete from tb_window_defect_type", bindArgs);
//		
//		
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(1,'B','Broken','Broken / Demage',0,1,'2013-09-23 16:28:47.767',1,'2013-09-23 16:28:47.767',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(2,'D','Dent-Mark','Dented Mark',0,1,'2013-09-23 16:28:47.783',1,'2013-09-23 16:28:47.783',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(3,'G','Gap','Gap',0,1,'2013-09-23 16:28:47.783',1,'2013-09-23 16:28:47.783',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(4,'M','Malfunct','Malfunction',0,1,'2013-09-23 16:28:47.797',1,'2013-09-23 16:28:47.797',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(5,'P','Miss-Parts','Missing Parts',0,1,'2013-09-23 16:28:47.797',1,'2013-09-23 16:28:47.797',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(6,'O','Others','Others',0,1,'2013-09-23 16:28:47.813',1,'2013-09-23 16:28:47.813',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(7,'S','Scratch','Scratch',0,1,'2013-09-23 16:28:47.83',1,'2013-09-23 16:28:47.83',0);", bindArgs);
//		util.execSQL("INSERT INTO tb_window_defect_type (window_defect_type_id,code,window_defect_type_name,desc,status,created_user_id,created_date,updated_user_id,updated_date,user_id) VALUES(8,'A','Sealant','Sealant / Gacket',0,1,'2013-09-23 16:28:47.83',1,'2013-09-23 16:28:47.83',0);", bindArgs);
		
		tb_windowDAO dao = new tb_windowDAO();
		WindowList = dao.getWindowList(util,areaId, siteId, flatTypeId);
		util.close();
		for (windowObj area : WindowList) {
			tableView.addBasicItem(area.getWindowName());
		}

		tableView.commit();
		ClickWindow(0);
	}

	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			ClickWindow(index);
		}

		
	}
	
	public void ClickWindow(int index){
		tableView.setSelectView(index);
		
		setButtonEnabled(btnSelectAll, true);
		setButtonEnabled(btnDelete, true);
		setButtonEnabled(btnRectify, true);
		setButtonEnabled(btnEditPhoto, true);
		setButtonEnabled(btnSave, true);
		
		FormatUtil formatUtil=new FormatUtil();
		currentWindowObj=WindowList.get(index);
		

		
		tabHost.setCurrentTab(0);
		DBUtil util=DBFactory.GetUserDB(WindowListActivity.this);
		util.open();
		tb_windowDAO dao = new tb_windowDAO();
		dao.RefreshDefectList(util, currentWindowObj);
		util.close();
		
		windowBGGlazing.setBackground(formatUtil.byteToDrawable(currentWindowObj.getPic()));
		windowBGGlazing.removeAllViews();
		
		windowBGFrame.setBackground(formatUtil.byteToDrawable(currentWindowObj.getPic()));
		windowBGGlazing.removeAllViews();
		
		List<windowDefectObj> lstPoint= currentWindowObj.getDefectPointList();
		setDefectTypeAllNoneSelect();
		showDefectPoint(currentWindowObj);
		updateDefectTypeStatus(lstPoint);
	}
	
	
	private void showDefectPoint(windowObj obj) {
		// TODO Auto-generated method stub
		List<windowDefectObj> lstDefect=obj.getDefectPointList();
		FormatUtil formatUtil=new FormatUtil();
		for (windowDefectObj defect : lstDefect) {

			Log.i("cal", String.valueOf(defect.getStatus()));
			ImageView img=new ImageView(this);
			img.setId(defect.getWindowDefectId());
			//img.setImageDrawable(formatUtil.byteToDrawable(defectType.getPic()));
			
			if(defect.getStatus()==0){
				img.setImageResource(R.drawable.error_icon);
			}else{
				img.setImageResource(R.drawable.rectify_icon);
			}
			img.setX(defect.getXcoor());
			img.setY(defect.getYcoor());
			img.setTag(defect);
			if(defect.getWindowTypeId()==1){
				windowBGGlazing.addView(img);
			}
			else{
				windowBGFrame.addView(img);
			}
		}
	}
	

	public void updateDefectTypeStatus(List<windowDefectObj> lstDefect) {
		// TODO Auto-generated method stub
		
		int windowType=tabHost.getCurrentTab()+1;
		
		setDefectTypeEmpty();
		
		for (windowDefectObj defect : lstDefect) {
			if(defect.getWindowTypeId()==windowType&&defect.getStatus()==1){
				windowDefectTypeObj typeObj=getWindowDefectTypeById(defect.getWindowDefectTypeId());
				ImageView iv=getDefectTypeIMByCode(typeObj.getCode());
				iv.setImageResource(R.drawable.tick);
			}
		}
		for (windowDefectObj defect : lstDefect) {
			if(defect.getWindowTypeId()==windowType&&defect.getStatus()==0){
				windowDefectTypeObj typeObj=getWindowDefectTypeById(defect.getWindowDefectTypeId());
				ImageView iv=getDefectTypeIMByCode(typeObj.getCode());
				iv.setImageResource(R.drawable.no_tick);
			}
		}
	}

	private void setDefectTypeEmpty() {
		// TODO Auto-generated method stub
		ImageView iv=null;
		
		
		
		iv=(ImageView)findViewById(R.id.IVDT_B);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_D);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_G);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_M);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_P);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_O);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_S);
		iv.setImageResource(R.drawable.empty);
		iv=(ImageView)findViewById(R.id.IVDT_A);
		iv.setImageResource(R.drawable.empty);
	}

	private windowDefectTypeObj getWindowDefectTypeById(int windowDefectTypeId) {
		// TODO Auto-generated method stub
		
		for(windowDefectTypeObj type:lstDefectType){
			
			if(type.getWindowDefectTypeId()==windowDefectTypeId){
				return type;
			}
		}
		return null;
	}

	private void setListensers() {

		//windowBG.setOnTouchListener(setDefectPoint);

		btnSave.setOnTouchListener(TouchButton);
		btnEditPhoto.setOnTouchListener(TouchButton);

		btnSelectAll.setOnClickListener(selectAll);

		btnEditPhoto.setOnClickListener(callEditPhoto);
		
		btnSave.setOnClickListener(saveWindow);
		btnHelp.setOnClickListener(helpTips);
		btnHelp.setOnTouchListener(TouchButton);
		
		btnDelete.setOnClickListener(deleteSelectPoint);
		btnRectify.setOnClickListener(rectifySelectPoint);
		
		
		tabHost.setOnTabChangedListener(tabOnChange);  
		

		windowBGGlazing.setOnTouchListener(BGOnTouch);
		windowBGFrame.setOnTouchListener(BGOnTouch);

		TVDT_B.setOnClickListener(setDefectType);
		TVDT_D.setOnClickListener(setDefectType);
		TVDT_G.setOnClickListener(setDefectType);
		TVDT_M.setOnClickListener(setDefectType);
		TVDT_P.setOnClickListener(setDefectType);
		TVDT_O.setOnClickListener(setDefectType);
		TVDT_S.setOnClickListener(setDefectType);
		TVDT_A.setOnClickListener(setDefectType);
	}
	int downx=0;
	int downy=0;
	ImageView downCV=null;
	
	private OnTouchListener BGOnTouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				
				downx=0;
				downy=0;
				downCV=null;
				
				ImageView cv=GetDefectPoint((RelativeLayout)v,event.getX(),event.getY());
				if(cv==null){
					AddDefectPoint((RelativeLayout)v,event.getX(),event.getY());
					return false;
				}
				else{
					downx=(int)cv.getX();
					downy=(int)cv.getY();
					downCV=cv;
					windowDefectObj obj =(windowDefectObj)cv.getTag();
					if(obj.getStatus()==1){
						return false;
					}
					return true;

				}
			}else if(event.getAction()==MotionEvent.ACTION_MOVE){
				
				downCV.setX(event.getX()-20);
				downCV.setY(event.getY()-20);
				
				return true;
			}else if(event.getAction()==MotionEvent.ACTION_UP){

				Log.i("d1", String.valueOf(downx));
				Log.i("d2", String.valueOf(event.getX()));
				Log.i("d3", String.valueOf(downy));
				Log.i("d4", String.valueOf(event.getY()));
				Log.i("d", String.valueOf(((downx-event.getX())*(downx-event.getX())+(downy-event.getY())*(downy-event.getY()))));
				
				
				if(((downx-event.getX())*(downx-event.getX())+(downy-event.getY())*(downy-event.getY()))<4000){
					windowDefectObj obj =(windowDefectObj)downCV.getTag();
					
					if(obj.GetSelected()==false){
						obj.SetSelected(true);
						downCV.setBackgroundColor(android.graphics.Color.BLUE);
					}else{
						obj.SetSelected(false);
						downCV.setBackgroundColor(android.graphics.Color.TRANSPARENT);
					}
				}else{
					ImageView cv=GetDefectPoint((RelativeLayout)v,event.getX(),event.getY());
					if(cv==null){
						windowDefectObj obj =(windowDefectObj)downCV.getTag();
						obj.setXcoor((int)event.getX()-20);
						obj.setYcoor((int)event.getY()-20);
					}
					else{
						downCV.setX(downx);
						downCV.setY(downy);
						Toast.makeText(getApplicationContext(), "Cannot set defect at the same location.",Toast.LENGTH_SHORT).show();
					}
				}
			}
			return false;
		}
	};

	protected ImageView GetDefectPoint(RelativeLayout v, float x, float y) {
		// TODO Auto-generated method stub
		
		int count=v.getChildCount();
		for(int i=0;i<count;i++){
			ImageView cv=(ImageView)v.getChildAt(i);
			
			if(downCV!=null){
				if(cv==downCV){
					continue;
				}
			}
			
			if(cv.getX()+20<=x+25&&cv.getX()+20>=x-25
					&&cv.getY()+20<=y+25&&cv.getY()+20>=y-25){
					Log.i("cvx", String.valueOf(cv.getX()));
					Log.i("x", String.valueOf(x));
					Log.i("cvy", String.valueOf(cv.getY()));
					Log.i("y", String.valueOf(y));
				return cv;
			}
		}
		return null;
	}
	
	private void SetAllPointNonSelected(){
		int count=windowBGGlazing.getChildCount();
		for(int i=0;i<count;i++){
			ImageView cv=(ImageView)windowBGGlazing.getChildAt(i);
			windowDefectObj obj =(windowDefectObj)cv.getTag();
			obj.SetSelected(false);
			cv.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		}
		
		 count=windowBGFrame.getChildCount();
			for(int i=0;i<count;i++){
				ImageView cv=(ImageView)windowBGFrame.getChildAt(i);
				windowDefectObj obj =(windowDefectObj)cv.getTag();
				obj.SetSelected(false);
				cv.setBackgroundColor(android.graphics.Color.TRANSPARENT);
			}
		
	}
	
	long lastClickDefectTypeLable=0;
	private OnClickListener setDefectType = new OnClickListener() {
		public void onClick(View v) {
			setDefectTypeAllNoneSelect();
			SetAllPointNonSelected();
			String code=getDefectTypeCodeById(v.getId());
			defectTypeCode=code;
			v.setBackgroundColor(android.graphics.Color.BLUE);
			if(System.currentTimeMillis()-lastClickDefectTypeLable<1000){
				SelectDefectPointByType(tabHost.getCurrentTab()==0?windowBGGlazing:windowBGFrame);
			}
			lastClickDefectTypeLable=System.currentTimeMillis();
		}
	};
	private OnTabChangeListener tabOnChange = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String arg0) {
			// TODO Auto-generated method stub
			
			
			setDefectTypeAllNoneSelect();
			SetAllPointNonSelected();
			List<windowDefectObj> lstPoint= currentWindowObj.getDefectPointList();
			updateDefectTypeStatus(lstPoint);
			
			
		}
	};
	
	

	protected void SelectDefectPointByType(RelativeLayout relativeLayout) {
		// TODO Auto-generated method stub
		int count=relativeLayout.getChildCount();
		for(int i=0;i<count;i++){
			ImageView cv=(ImageView)relativeLayout.getChildAt(i);
			windowDefectObj obj =(windowDefectObj)cv.getTag();
			if(defectTypeCode.equals(getDefectTypeIdCodeByTypeId(obj.getWindowDefectTypeId()))){
				obj.SetSelected(true);
				cv.setBackgroundColor(android.graphics.Color.BLUE);
			}
		}
	}

	protected void setDefectTypeAllNoneSelect() {
		// TODO Auto-generated method stub
		defectTypeCode="";
		TVDT_B.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_D.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_G.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_M.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_P.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_O.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_S.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		TVDT_A.setBackgroundColor(android.graphics.Color.TRANSPARENT);
	}

	

	private void AddDefectPoint(RelativeLayout windowBG,float x,float y) {
		// TODO Auto-generated method stub
		int windowDefectId=0;
		if(defectTypeCode.equals("")){
			return;
		}
		FormatUtil formatUtil=new FormatUtil();
		//windowDefectTypeObj defectType=getWindowDefectType(defectTypeId);
		ImageView img=new ImageView(this);
		//img.setImageDrawable(formatUtil.byteToDrawable(defectType.getPic()));
		img.setImageResource(R.drawable.error_icon);
		img.setX(x-20);
		img.setY(y-20);
		windowBG.addView(img);
		
		windowDefectObj obj = new windowDefectObj();
		obj.setWindowDefectId(0);
		obj.setSiteId(siteId);
		obj.setFlatId(flatId);
		obj.setAreaId(areaId);
		obj.setWindowId(currentWindowObj.getWindowId());
		obj.setWindowTypeId(tabHost.getCurrentTab()+1);
		obj.setWindowDefectTypeId(getDefectTypeIdByCode(defectTypeCode));
		
		obj.setRoundNo(currentWindowObj.getDefectPointListMaxRoundNo()+1);
		obj.setXcoor((int)x-20);
		obj.setYcoor((int)y-20);
		
		obj.setDesc("");
		obj.setCreatedUserId(0);
		obj.setUpdatedUserId(0);
		

		img.setTag(obj);
		
		
		currentWindowObj.addDefectPoint(obj);
		
		updateDefectTypeStatus(currentWindowObj.getDefectPointList());
		
		Log.i("counter",String.valueOf( currentWindowObj.getDefectPointList().size()));
	}
	
	private OnTouchListener TouchDark = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				button_other_type.setAlpha(0.8f);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				button_other_type.setAlpha(1f);
			}
			return false;
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
	private Button.OnClickListener callEditPhoto = new Button.OnClickListener() {
		public void onClick(View v) {
			
			Intent intent = new Intent();
			intent.setClass(WindowListActivity.this,
					WindowImageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Bundle bundle = new Bundle();
			bundle.putInt("KEY_FLAT_ID",flatId);
			bundle.putInt("KEY_SITE_ID",siteId);
			bundle.putInt("KEY_FLAT_TYPE_ID",flatTypeId);
			bundle.putInt("KEY_AREA_ID",areaId);
			bundle.putInt("KEY_ROUND_NO", roundNo);
			bundle.putString("KEY_TITLE", title);
			bundle.putStringArray("KEY_MENU", menu);;

			bundle.putInt("KEY_DEFECT_ID", currentWindowObj.getWindowId());
			bundle.putString("KEY_DEFECT_NAME", currentWindowObj.getWindowName());
			
			intent.putExtras(bundle);
			startActivityForResult(intent,1);
			//WindowListActivity.this.finish();
		}
	};
	private Button.OnClickListener selectAll = new Button.OnClickListener() {
		public void onClick(View v) {
			RelativeLayout relativeLayout=tabHost.getCurrentTab()==0?windowBGGlazing:windowBGFrame;
			int count=relativeLayout.getChildCount();
			for(int i=0;i<count;i++){
				ImageView cv=(ImageView)relativeLayout.getChildAt(i);
				windowDefectObj obj =(windowDefectObj)cv.getTag();
				obj.SetSelected(true);
				cv.setBackgroundColor(android.graphics.Color.BLUE);
			}
		}
	};
	private Button.OnClickListener helpTips = new Button.OnClickListener() {
		public void onClick(View v) {
			
			new  AlertDialog.Builder(WindowListActivity.this)    
			                .setTitle("Help" )  
			                .setMessage(sbDefectTypeDescription.toString() )  
			                .setPositiveButton("OK" ,  null ) 
			                .show(); 
		}
	};
	private Button.OnClickListener saveWindow = new Button.OnClickListener() {
		public void onClick(View v) {
			
			new  AlertDialog.Builder(WindowListActivity.this)   
			.setTitle("Save" )  
			.setMessage("Defect point cannot delete after saved.\r\nAre you sure to Save?" )  
			.setPositiveButton("Yes" ,   
					new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog,
		                        int whichButton) {

		        			DBUtil util=DBFactory.GetUserDB(WindowListActivity.this);
		        			util.open();
		        			tb_window_defectDAO dao = new tb_window_defectDAO();
		        			dao.saveWindowDefect(util,dao,currentWindowObj.getDefectPointList(),tabHost.getCurrentTab()+1,false);
		        			util.close();
		        			
		        			Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();
		                }
		            } )  
			.setNegativeButton("No" , null)  
			.show();  
			
			
			
		}
	};

	private Button.OnClickListener rectifySelectPoint = new Button.OnClickListener() {
		public void onClick(View v) {
			
			new  AlertDialog.Builder(WindowListActivity.this)   
			.setTitle("Rectify" )  
			.setMessage("Defect point cannot delete after rectified\r\nAre you sure to Rectify?" )  
			.setPositiveButton("Yes" ,   
					new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog,
		                        int whichButton) {
		                	RectifyAllSelectPoint(tabHost.getCurrentTab()==0?windowBGGlazing:windowBGFrame);
		        			updateDefectTypeStatus(currentWindowObj.getDefectPointList());
		        			
		                	DBUtil util=DBFactory.GetUserDB(WindowListActivity.this);
		        			util.open();
		        			tb_window_defectDAO dao = new tb_window_defectDAO();
		        			dao.saveWindowDefect(util,dao,currentWindowObj.getDefectPointList(),tabHost.getCurrentTab()+1,true);
		        			util.close();
		        			
		        			Toast.makeText(getApplicationContext(), "Rectified",Toast.LENGTH_SHORT).show();
		                }

						
		            } )  
			.setNegativeButton("No" , null)  
			.show();  
			
			
			
		}
	};
	private Button.OnClickListener deleteSelectPoint = new Button.OnClickListener() {
		public void onClick(View v) {
			
			new  AlertDialog.Builder(WindowListActivity.this)   
			.setTitle("Delete" )  
			.setMessage("Are you sure to Delete?" )  
			.setPositiveButton("Yes" ,   
					new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog,
		                        int whichButton) {

		        			DeleteAllSelectPoint(tabHost.getCurrentTab()==0?windowBGGlazing:windowBGFrame);
		        			updateDefectTypeStatus(currentWindowObj.getDefectPointList());
		        			Toast.makeText(getApplicationContext(), "Deleted",Toast.LENGTH_SHORT).show();
		                }

						
		            } )  
			.setNegativeButton("No" , null)  
			.show();  
			
			
			
		}
	};
	private void DeleteAllSelectPoint(
			RelativeLayout relativeLayout) {
		// TODO Auto-generated method stub
		List<ImageView> lstCVID=new ArrayList<ImageView>();
		
		int count=relativeLayout.getChildCount();
		for(int i=0;i<count;i++){
			ImageView cv=(ImageView)relativeLayout.getChildAt(i);
			windowDefectObj obj =(windowDefectObj)cv.getTag();
			if(obj.GetSelected()&&obj.IsInsert()){
				currentWindowObj.removeDefect(obj);
				lstCVID.add(cv);
			}
		}
		for(ImageView iv:lstCVID){
			relativeLayout.removeView(iv);
		}
	}
	private void RectifyAllSelectPoint(
			RelativeLayout relativeLayout) {
		// TODO Auto-generated method stub
		
		int count=relativeLayout.getChildCount();
		for(int i=0;i<count;i++){
			ImageView cv=(ImageView)relativeLayout.getChildAt(i);
			windowDefectObj obj =(windowDefectObj)cv.getTag();
			if(obj.GetSelected()&&obj.getStatus()==0){
				obj.setStatus(1);
				cv.setImageResource(R.drawable.rectify_icon);
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			goBack();
			return true;

		}
		return false;
	}

	public void goBack(){
//		Intent intent = new Intent();
//		intent.setClass(WindowListActivity.this,
//				AreaElementListActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		Bundle bundle = new Bundle();
//		bundle.putInt("KEY_FLAT_ID",flatId);
//		bundle.putInt("KEY_SITE_ID",siteId);
//		bundle.putInt("KEY_FLAT_TYPE_ID",flatTypeId);
//		bundle.putInt("KEY_AREA_ID",areaId);
//		bundle.putInt("KEY_ROUND_NO", roundNo);
//		bundle.putString("KEY_TITLE", title);
//		String tempMenu[] = {menu[0]};
//		bundle.putStringArray("KEY_MENU", tempMenu);
//		intent.putExtras(bundle);
//		startActivity(intent);
		WindowListActivity.this.finish();
	}
}

