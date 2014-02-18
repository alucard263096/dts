package com.example.dts;

import java.util.List;

import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.example.common.CommonEnum;
import com.example.dao.tb_Area_ElementDAO;
import com.example.dao.tb_Area_WindowDAO;
import com.example.dao.tb_flatDAO;
import com.example.objects.areaElementWindowObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AreaElementListActivity extends Activity {
	UITableView tableView;
	private TextView view_title;
	private TextView view_menu;
	private Button button_back;
	private Button button_back_left;
	private List<areaElementWindowObj> areaElementWindowList;
	private int siteId;
	private int flatId;
	private int flatTypeId;
	private int areaId;
	private int roundNo;
	private String title;
	private String menu[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_child_list);

		getBundleData();
		findViews();
		setTitle();
		setMenu();
		setListensers();
		createTableList();
	}

	private void getBundleData() {
		Bundle bundle = this.getIntent().getExtras();
		siteId = bundle.getInt("KEY_SITE_ID");
		flatId = bundle.getInt("KEY_FLAT_ID");
		flatTypeId = bundle.getInt("KEY_FLAT_TYPE_ID");
		areaId = bundle.getInt("KEY_AREA_ID");
		menu = bundle.getStringArray("KEY_MENU");
		roundNo=bundle.getInt("KEY_ROUND_NO");
		title=bundle.getString("KEY_TITLE");
	}

	private void findViews() {
		view_title = (TextView) findViewById(R.id.txtTitle);
		view_menu = (TextView) findViewById(R.id.txtMenu);
		button_back=(Button) findViewById(R.id.btnParent);
		button_back_left=(Button) findViewById(R.id.btnParentLeft);
		tableView = (UITableView) findViewById(R.id.tableView);

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
		button_back.setText("Top ");
	}

	private void createTableList() {
		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
		
		DBUtil util=DBFactory.GetUserDB(AreaElementListActivity.this);
		util.open();
		tb_Area_ElementDAO aedao = new tb_Area_ElementDAO();
		tb_Area_WindowDAO awdao = new tb_Area_WindowDAO();
		areaElementWindowList = aedao.getAreaElementList(util,areaId, siteId,
				flatTypeId);
		List<areaElementWindowObj> tempList = awdao.getAreaWindowList(util,areaId,
				siteId, flatTypeId);
		util.close();
		
		areaElementWindowList.addAll(tempList);
		for (areaElementWindowObj area : areaElementWindowList) {
			tableView.addBasicItem(area.getName());
		}

		tableView.commit();

	}

	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt("KEY_FLAT_ID", flatId);
			bundle.putInt("KEY_SITE_ID", siteId);
			bundle.putInt("KEY_FLAT_TYPE_ID", flatTypeId);
			bundle.putInt("KEY_ROUND_NO", roundNo);
			bundle.putInt("KEY_AREA_ID", areaElementWindowList.get(index)
					.getAreaId());
			bundle.putString("KEY_TITLE", title);
			String tempMenu[] = { menu[0],
					areaElementWindowList.get(index).getName() };
			bundle.putStringArray("KEY_MENU", tempMenu);
			if (areaElementWindowList.get(index).getIsWindow()) {	
				intent.setClass(AreaElementListActivity.this,
						WindowListActivity.class);

			} else {
				bundle.putInt("KEY_ELEMENT_TYPE_ID", areaElementWindowList.get(index)
						.getElementTypeId());
				intent.setClass(AreaElementListActivity.this,
						ElementListActivity.class);
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtras(bundle);
			startActivity(intent);
			//AreaElementListActivity.this.finish();
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
//		intent.setClass(AreaElementListActivity.this,
//				AreaListActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		Bundle bundle = new Bundle();
//		bundle.putInt("KEY_FLAT_ID", flatId);
//		bundle.putInt("KEY_SITE_ID", siteId);
//		bundle.putInt("KEY_FLAT_TYPE_ID", flatTypeId);
//		bundle.putInt("KEY_ROUND_NO", roundNo);
//		bundle.putString("KEY_TITLE", title);
//		intent.putExtras(bundle);
//		startActivity(intent);
		AreaElementListActivity.this.finish();
	}
}
