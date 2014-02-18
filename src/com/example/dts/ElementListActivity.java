package com.example.dts;

import java.util.List;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.example.dao.tb_DefectDAO;
import com.example.dao.tb_ElementDAO;
import com.example.dao.tb_flatDAO;
import com.example.objects.defectListObj;
import com.example.objects.defectObj;
import com.example.objects.elementObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ElementListActivity extends Activity {
	UITableView tableView;
	UITableView defectTableView;
	private TextView view_title;
	private TextView view_menu;
	private Button button_back;
	private Button button_back_left;
	private List<elementObj> elementList;
	private List<defectListObj> defectList;
	private int siteId;
	private int flatId;
	private int flatTypeId;
	private int areaId;
	private int elementTypeId;
	private int roundNo;
	private int elementId;
	private String title;
	private String menu[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_defect_list);

		getBundleData();
		findViews();
		setTitle();
		setMenu();
		setListensers();
		createTableList();
		init();
	}

	private void init(){
		if(elementId>0){
			createDefectTableList(elementId);
		}
		
	}
	private void getBundleData() {
		Bundle bundle = this.getIntent().getExtras();
		siteId = bundle.getInt("KEY_SITE_ID");
		flatId = bundle.getInt("KEY_FLAT_ID");
		flatTypeId = bundle.getInt("KEY_FLAT_TYPE_ID");
		areaId = bundle.getInt("KEY_AREA_ID");
		elementTypeId = bundle.getInt("KEY_ELEMENT_TYPE_ID");
		menu = bundle.getStringArray("KEY_MENU");
		roundNo=bundle.getInt("KEY_ROUND_NO");
		title=bundle.getString("KEY_TITLE");
		elementId=bundle.getInt("KEY_ELEMENT_ID");
	}

	private void findViews() {
		view_title = (TextView) findViewById(R.id.txtTitle);
		view_menu = (TextView) findViewById(R.id.txtMenu);
		button_back=(Button) findViewById(R.id.btnParent);
		button_back_left=(Button) findViewById(R.id.btnParentLeft);
		tableView = (UITableView) findViewById(R.id.tableView);
		defectTableView=(UITableView) findViewById(R.id.defectTableView);

	}

	private void setTitle() {
		 view_title.setText(title);
	}

	private void setMenu() {
		String menuString = " ";
		for (int i = 0; i < menu.length; i++) {
			if (i == 0) {
				menuString += menu[i];
			} else {
				menuString += " > " + menu[i];
			}
		}
		view_menu.setText(menuString);
		button_back.setText(menu[0] + " ");
	}

	private void createTableList() {
		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
		
		DBUtil util=DBFactory.GetUserDB(ElementListActivity.this);
		util.open();
		tb_ElementDAO dao = new tb_ElementDAO();
		elementList = dao.getElementList(util,areaId, siteId, flatTypeId,elementTypeId);
		util.close();
		
		int position=-1;
		int i=0;
		for (elementObj area : elementList) {
			tableView.addBasicItem(area.getElementName());
			if(area.getElementId()==elementId){
				position=i;
			}
			i++;
		}
        	tableView.commitComment(position);
		

	}
	
	private void createDefectTableList(int elementId) {
		defectTableView.clear();
		DefectCustomClickListener listener = new DefectCustomClickListener();
		defectTableView.setClickListener(listener);
		
		DBUtil util=DBFactory.GetUserDB(ElementListActivity.this);
		util.open();
		tb_DefectDAO dao = new tb_DefectDAO();
		defectList = dao.getDefectList(util,areaId, siteId, flatId, elementId, roundNo);
		util.close();
		
		for (defectListObj defectObj : defectList) {
			LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.special_table_view, null);
			((TextView) view.findViewById(R.id.title)).setText(defectObj.getName());
			TextView view_status=(TextView) view.findViewById(R.id.txtStatus);
			if(defectObj.getDefectId()==0){
			    ((ImageView)view.findViewById(R.id.imgEidt)).setImageResource(R.drawable.not_edited);
			    view_status.setText("");
			}
			else{
				
				((ImageView)view.findViewById(R.id.imgEidt)).setImageResource(R.drawable.edited);
				view_status.setText("Has Defect");
				view_status.setTextColor(Color.parseColor("#ff0000"));
				if(defectObj.getConfirmCompletionDate()!=null){
					view_status.setText("Completed");
					view_status.setTextColor(Color.parseColor("#008000"));
				}
				if(defectObj.getStatus()==1){
					view_status.setText("Rectified");
					view_status.setTextColor(Color.parseColor("#ffa500"));
				}
			}
			
			ViewItem viewItem = new ViewItem(view);
			defectTableView.addViewItem(viewItem);
		}

		defectTableView.commit();

	}

	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			tableView.setSelectView(index);
			createDefectTableList(elementList.get(index).getElementId());
		}
	}
	
	private class DefectCustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			
             final int tableViewIndex=index;
			// create QuickAction. Use QuickAction.VERTICAL or
			// QuickAction.HORIZONTAL param to define layout
			// orientation
			 QuickAction quickAction = new QuickAction(
					ElementListActivity.this, QuickAction.HORIZONTAL);

			// add action items into QuickAction
			 if(defectList.get(index).getDefectId()==0){
				 ActionItem editItem = new ActionItem(1, "New");
				 quickAction.addActionItem(editItem);
			 }else {
				 ActionItem editItem = new ActionItem(2, "Edit");
				 quickAction.addActionItem(editItem);
			}
			 
			 ActionItem CompletedItem = new ActionItem(3, "Completed",Color.parseColor("#008000"));
			 quickAction.addActionItem(CompletedItem);
			 ActionItem RectifiedItem = new ActionItem(4, "Rectified",Color.parseColor("#ffa500"));
			 quickAction.addActionItem(RectifiedItem);
			 ActionItem ClearItem = new ActionItem(5, "Clear");
			 quickAction.addActionItem(ClearItem);
			
			
			

			// Set listener for action item clicked
			quickAction
					.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
						@Override
						public void onItemClick(QuickAction source,
								int pos, int actionId) {
							QuickAction(actionId,tableViewIndex);
						}
					});

			quickAction.showSpecial(((ViewGroup)((ViewGroup)defectTableView.getChildAt(0)).getChildAt(0)).getChildAt(index).findViewById(R.id.imgEidt));
		}
	}

	private void QuickAction(int actionId,int tableViewIndex){
		if(actionId==1||actionId==2){
			Intent intent = new Intent();
			intent.setClass(ElementListActivity.this,
					ImageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Bundle bundle = new Bundle();
			bundle.putInt("KEY_FLAT_ID",flatId);
			bundle.putInt("KEY_SITE_ID",siteId);
			bundle.putInt("KEY_FLAT_TYPE_ID",flatTypeId);
			bundle.putInt("KEY_AREA_ID",areaId);
			bundle.putInt("KEY_ROUND_NO", roundNo);
			bundle.putString("KEY_TITLE", title);
			bundle.putInt("KEY_DEFECT_ID", defectList.get(tableViewIndex).getDefectId());
			bundle.putInt("KEY_ELEMENT_TYPE_ID", elementTypeId);
			bundle.putStringArray("KEY_MENU", menu);
			bundle.putInt("KEY_ELEMENT_ID", defectList.get(tableViewIndex).getElementId());
			bundle.putString("KEY_DEFECT_NAME", " - "+defectList.get(tableViewIndex).getName());
			bundle.putInt("KEY_DEFECT_TYPE_ID", defectList.get(tableViewIndex).getDefectTypeId());
			intent.putExtras(bundle);
			startActivity(intent);
			//ElementListActivity.this.finish();
		}
		else{
			if(defectList.get(tableViewIndex).getDefectId()==0){
				Toast.makeText(getApplicationContext(), "There are not a defect",Toast.LENGTH_SHORT).show();
				return;
			}
			int orgStatus=defectList.get(tableViewIndex).getOrgStatus();
			int defectId=defectList.get(tableViewIndex).getDefectId();
			DBUtil util=DBFactory.GetUserDB(ElementListActivity.this);
			util.open();
			
			ViewItem viewItem=defectTableView.getViewItem(tableViewIndex);
			
			RelativeLayout view = (RelativeLayout) viewItem.getView();
//			((TextView) view.findViewById(R.id.title)).setText(defectObj.getName());
//			TextView view_status=(TextView) view.findViewById(R.id.txtStatus);
//			if(defectObj.getDefectId()==0){
//			    ((ImageView)view.findViewById(R.id.imgEidt)).setImageResource(R.drawable.not_edited);
//			    view_status.setText("");
//			}
//			else{
//				((ImageView)view.findViewById(R.id.imgEidt)).setImageResource(R.drawable.edited);
//				view_status.setText("Has Defect");
//				view_status.setTextColor(Color.parseColor("#ff0000"));
//			}
			
			
			
			tb_DefectDAO dao=new tb_DefectDAO(); 
			if(actionId==3){
				
					dao.completeDefect(util,defectId);
					TextView view_status=(TextView) view.findViewById(R.id.txtStatus);
					view_status.setText("Completed");
					view_status.setTextColor(Color.parseColor("#008000"));
			}else if(actionId==4){
				
					dao.rectifyDefect(util,defectId);
					TextView view_status=(TextView) view.findViewById(R.id.txtStatus);
					view_status.setText("Rectified");
					view_status.setTextColor(Color.parseColor("#ffa500"));
					
			}else if(actionId==5){
				if(orgStatus!=0){
					Toast.makeText(getApplicationContext(), "You cannot clear this record",Toast.LENGTH_SHORT).show();
				}else{
					dao.deleteDefect(util,defectId);
					 ((ImageView)view.findViewById(R.id.imgEidt)).setImageResource(R.drawable.not_edited);
					TextView view_status=(TextView) view.findViewById(R.id.txtStatus);
					view_status.setText("");
				}
			}
			util.close();
			
		}
	}
	private void setListensers() {
		button_back.setOnTouchListener(TouchDark);
		button_back_left.setOnTouchListener(TouchDark);
		button_back.setOnClickListener(btnBack);
		button_back_left.setOnClickListener(btnBack);
	}

	private OnTouchListener TouchDark = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				button_back.setAlpha(0.8f);
				button_back_left.setAlpha(0.8f);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				button_back.setAlpha(1f);
				button_back_left.setAlpha(1f);
			}
			return false;
		}
	};

	private Button.OnClickListener btnBack = new Button.OnClickListener() {
		public void onClick(View v) {
			goBack();

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

	
	public void goBack(){
//		Intent intent = new Intent();
//		intent.setClass(ElementListActivity.this,
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
		ElementListActivity.this.finish();
	}
}


