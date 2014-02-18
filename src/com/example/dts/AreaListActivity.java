package com.example.dts;


import java.util.List;

import com.example.common.CommonEnum;
import com.example.dao.tb_AreaDAO;
import com.example.dao.tb_flatDAO;
import com.example.objects.areaObj;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;

import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class AreaListActivity extends Activity {

	UITableView tableView;
	private TextView view_title;
	private List<areaObj> areaList;
	private int siteId;
	private int flatId;
	private int flatTypeId;
	private int roundNo;
	private String title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_list);

		Bundle bundle=this.getIntent().getExtras();
		siteId=bundle.getInt("KEY_SITE_ID");
		flatId=bundle.getInt("KEY_FLAT_ID");
		flatTypeId=bundle.getInt("KEY_FLAT_TYPE_ID");
		roundNo=bundle.getInt("KEY_ROUND_NO");
		title=bundle.getString("KEY_TITLE");
		
		
		findViews();
		setTitle();
		createTableList();
	}
	
	
	private void findViews() {
		view_title = (TextView) findViewById(R.id.txtTitle);
		tableView = (UITableView) findViewById(R.id.tableView);

	}
	
	private void setTitle(){
	    view_title.setText(title);
	}

	private void createTableList() {
		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
		
		DBUtil util=DBFactory.GetUserDB(AreaListActivity.this);
		util.open();
		tb_AreaDAO adao=new tb_AreaDAO();
		areaList=adao.getAreaList(util,siteId,flatTypeId);
		util.close();

		for(areaObj area:areaList){
			tableView.addBasicItem(area.getAreaName());
		}
		
		tableView.commit();

	}

	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			Intent intent=new Intent();
			intent.setClass(AreaListActivity.this, AreaElementListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Bundle bundle=new Bundle();
			bundle.putInt("KEY_FLAT_ID",flatId);
			bundle.putInt("KEY_SITE_ID",siteId);
			bundle.putInt("KEY_FLAT_TYPE_ID",flatTypeId);
			bundle.putInt("KEY_AREA_ID",areaList.get(index).getAreaId());
			bundle.putInt("KEY_ROUND_NO", roundNo);
			bundle.putString("KEY_TITLE", title);
			String menu[] = {areaList.get(index).getAreaName()};
			bundle.putStringArray("KEY_MENU", menu);
			intent.putExtras(bundle);
			startActivity(intent);
			//AreaListActivity.this.finish();
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
//		intent.setClass(AreaListActivity.this,
//				MainActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		startActivity(intent);
		AreaListActivity.this.finish();
	}
	
	public void QuitApp() {
		new AlertDialog.Builder(AreaListActivity.this)
				.setTitle(R.string.msg_title)
				.setMessage("Are you sure to Quit?")
				.setPositiveButton("Sure",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								finish();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();

	}
}
