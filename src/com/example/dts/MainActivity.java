package com.example.dts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




import com.example.common.global_info;
import com.example.dao.tb_BlockDAO;
import com.example.dao.tb_FloorDAO;
import com.example.dao.tb_flatDAO;
import com.example.dao.tb_siteDAO;
import com.example.dao.tb_window_defectDAO;
import com.example.objects.StaticVar;
import com.example.objects.blockObj;
import com.example.objects.flatObj;
import com.example.objects.floorObj;
import com.example.objects.siteObj;
import com.example.socketthread.CheckConnect;
import com.example.socketthread.DataToDevice;
import com.example.socketthread.DataToServer;
import com.example.socketthread.TestSocket;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.UserDB;

import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	UITableView siteTableView;
	UITableView blockTableView;
	UITableView floorTableView;
	UITableView flatTableView;
	CheckConnect tsCheckConnect;
	TextView tvIsConnected;
	ImageView ivIsConnected;
	public Button btnDataToDevice;
	public Button btnDataToServer;
	public Button btnLogout;
	private global_info app;
	private List<siteObj> siteList;
	private List<blockObj> blockList;
	private List<floorObj> floorList;
	private List<flatObj> flatList;
	private static final int CMNET=3;
	private static final int CMWAP=2;
	private static final int WIFI=1;
	protected static final int MENU_Data_To_Device=Menu.FIRST;
	protected static final int MENU_Data_To_Server=Menu.FIRST+1;
	protected static final int MENU_Connection=Menu.FIRST+2;
	protected static final int MENU_Help=Menu.FIRST+3;
	protected static final int MENU_Quit=Menu.FIRST+4;
	private GridView menuGrid;
	private String[] menu_name_array = { "       Inspect       ", " View All Image ", "Attach Floor Plan", " View Floor Plan ", "       Confirm       ","        Close        "
			};
	int[] menu_image_array = { R.drawable.reader_plus_widget_pressed,
			R.drawable.filemanager_classification_picture_icon, R.drawable.filemanager_classification_picture_icon,
			R.drawable.filemanager_classification_picture_icon, R.drawable.confirm, R.drawable.banner_focused_close_btn };
	private String selectSiteName;
	private String selectBlockName;
	private String selectFloorName;
	private String selectFlatName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findview();     
                    
        eventBinding();
        initHandle();
        
        //SetEnableFor4Views(false);
        mThirdHandler.sendEmptyMessage(1);
        UserDB db=DBFactory.GetUserDB(MainActivity.this);
        if(db.CheckDbExists()==false){
        	db.DeleteDB();
        	mThirdHandler.sendEmptyMessage(0);
        	//db.CreateDBOnAsset();
        	//initSiteList(); 
        }else{
        	Log.i("dbsize", String.valueOf(db.getDatabaseLength()));
        	initSiteList(); 
        }
	}
	Handler mThirdHandler ;
	private void initHandle() {
		// TODO Auto-generated method stub
		this.mThirdHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if(msg.what==0){

               	 	DataToDevice ts=new DataToDevice(MainActivity.this,Integer.valueOf(StaticVar.USERID));
               	 	ts.start();
                }
                else if(msg.what==1){

               	 	tsCheckConnect=new CheckConnect(MainActivity.this);
               	 	tsCheckConnect.start();
                }
                else if(msg.what==2){
               	 	DataToServer ts=new DataToServer(MainActivity.this,Integer.valueOf(StaticVar.USERID));
               	 	ts.start();
                }
            };
        };
	}

	private void findview(){
		siteTableView = (UITableView) findViewById(R.id.siteTableView); 
		blockTableView = (UITableView) findViewById(R.id.blockTableView);  
		floorTableView = (UITableView) findViewById(R.id.floorTableView0);  
		flatTableView = (UITableView) findViewById(R.id.flatTableView);  
		btnDataToDevice=(Button)findViewById(R.id.btnDataToDevice);
		btnDataToServer=(Button)findViewById(R.id.btnDataToServer);
		btnLogout=(Button)findViewById(R.id.btnLogOut);
		
		tvIsConnected=(TextView)findViewById(R.id.tvIsConnected);
		ivIsConnected=(ImageView)findViewById(R.id.ivIsConnected);
	}
	public void SetEnableFor4Views(boolean enabled){

	}
	
	public void eventBinding(){
		btnDataToDevice.setOnClickListener(dataToDevice);
		btnDataToServer.setOnClickListener(dataToServer);
		btnLogout.setOnClickListener(logout);
	}
	Handler mApplicationHandler;
	private Button.OnClickListener dataToDevice = new Button.OnClickListener() {
		public void onClick(View v) {
			
			new  AlertDialog.Builder(MainActivity.this)   
			.setTitle("Save" )  
			.setMessage("Are you sure to replace you data from remote server?" )  
			.setPositiveButton("Yes" ,   
					new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog,
		                        int whichButton) {
		                	
		                	if(isConnect){
		                		mThirdHandler.sendEmptyMessage(0);
		                	}else{

								Toast.makeText(getApplicationContext(), "Cannot connect server.",Toast.LENGTH_SHORT).show();
		                	}
		                	
		                	
		        			
		                }
		            } )  
			.setNegativeButton("No" , null)  
			.show();  
			
			
			
		}
	};
	private Button.OnClickListener dataToServer = new Button.OnClickListener() {
		public void onClick(View v) {
			
			new  AlertDialog.Builder(MainActivity.this)   
			.setTitle("Save" )  
			.setMessage("Are you sure to upload you data to remote server?" )  
			.setPositiveButton("Yes" ,   
					new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog,
		                        int whichButton) {
		                	
		                	if(isConnect){
		                		mThirdHandler.sendEmptyMessage(2);
		                	}else{

								Toast.makeText(getApplicationContext(), "Cannot connect server.",Toast.LENGTH_SHORT).show();
		                	}
		                	
		                	
		        			
		                }
		            } )  
			.setNegativeButton("No" , null)  
			.show();  
			
			
			
		}
	};

	private Button.OnClickListener logout = new Button.OnClickListener() {
		public void onClick(View v) {
			
			LogoutMain();
			
			
			
		}
	};
	 public void initSiteList() {
		 
		 siteTableView.RemoveAllItem();
		 blockTableView.RemoveAllItem();
		 floorTableView.RemoveAllItem();
		 flatTableView.RemoveAllItem();
		 
		 SiteListClickListener listener = new SiteListClickListener();
	    	siteTableView.setClickListener(listener);
	    	DBUtil util=DBFactory.GetUserDB(MainActivity.this);
			util.open();
			tb_siteDAO dao=new tb_siteDAO();
			siteList=dao.getSiteList(util);
			util.close();

			for(siteObj obj:siteList){
				siteTableView.addBasicItem(obj.getSiteName());
			}
			
	    	siteTableView.commit();
	    }
	 
	 private void initBlockList(Integer siteId) {
		 BlockListClickListener listener = new BlockListClickListener();
		 blockTableView.setClickListener(listener);
	    	DBUtil util=DBFactory.GetUserDB(MainActivity.this);
			util.open();
			tb_BlockDAO dao=new tb_BlockDAO();
			blockList=dao.getBlockList(util, siteId);
			util.close();

			for(blockObj obj:blockList){
				blockTableView.addBasicItem(obj.getBlockName());
			}
			
			blockTableView.commit();
	    }
	 
	 private void initFloorList(Integer siteId,Integer blockId) {
		 FloorListClickListener listener = new FloorListClickListener();
		 floorTableView.setClickListener(listener);
	    	DBUtil util=DBFactory.GetUserDB(MainActivity.this);
			util.open();
			tb_FloorDAO dao=new tb_FloorDAO();
			floorList=dao.getFloorList(util, siteId, blockId);
			util.close();

			for(floorObj obj:floorList){
				floorTableView.addBasicItem(obj.getFloorName());
			}
			
			floorTableView.commit();
	    }
	 
	 private void initFlatList(Integer siteId,Integer blockId,Integer floorId) {
		 FlatListClickListener listener = new FlatListClickListener();
		 flatTableView.setClickListener(listener);
	    	DBUtil util=DBFactory.GetUserDB(MainActivity.this);
			util.open();
			tb_flatDAO dao=new tb_flatDAO();
			flatList=dao.getFlatList(util, siteId, blockId, floorId);
			util.close();

			for(flatObj obj:flatList){
				flatTableView.addBasicItem(obj.getFlatName());
			}
			
			flatTableView.commit();
	    }
	 
	 private class SiteListClickListener implements ClickListener {

			@Override
			public void onClick(int index) {
				siteTableView.setSelectView(index);
				 floorTableView.clear();
				 flatTableView.clear();
				 blockTableView.clear();
				 selectSiteName=siteList.get(index).getSiteName();
				initBlockList( siteList.get(index).getSiteId());
			}
	    	
	    }
	 
	 private class BlockListClickListener implements ClickListener {

			@Override
			public void onClick(int index) {
				blockTableView.setSelectView(index);
				floorTableView.clear();
				 flatTableView.clear();
				 selectBlockName=", "+blockList.get(index).getBlockName();
				initFloorList( blockList.get(index).getSiteId(),blockList.get(index).getBlockId());
			}
	    	
	    }
	 
	 private class FloorListClickListener implements ClickListener {

			@Override
			public void onClick(int index) {
				floorTableView.setSelectView(index);
				flatTableView.clear();
				selectFloorName=", "+floorList.get(index).getFloorName();
				initFlatList( floorList.get(index).getSiteId(),floorList.get(index).getBlockId(),floorList.get(index).getFloorId());
			}
	    	
	    }
	 
	 private class FlatListClickListener implements ClickListener {

			@Override
			public void onClick(int index) {
				selectFlatName=", "+flatList.get(index).getFlatName();
				openDialog( flatList.get(index).getFlatId(),flatList.get(index).getSiteId(),flatList.get(index).getFlatTypeId());
			}
	    	
	    }

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			LogoutMain();
			return true;

		}
		return false;
	}

	public void LogoutMain() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle(R.string.msg_title)
				.setMessage("Are you sure to Logout?")
				.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						tsCheckConnect.interrupt();
						finish();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();

	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		super.onPrepareOptionsMenu(menu);
		int netType=getAPNType(this);
		menu.clear();
		if(netType==-1){
			menu.add(0,MENU_Data_To_Device,0,getString(R.string.data_to_device_label)).setIcon(R.drawable.download).setEnabled(false);
			menu.add(0,MENU_Data_To_Server,0,getString(R.string.data_to_server_label)).setIcon(R.drawable.upload).setEnabled(false);
			menu.add(0,MENU_Connection,0,getString(R.string.connection_label)).setIcon(R.drawable.error_but);
		}
		else{
			menu.add(0,MENU_Data_To_Device,0,getString(R.string.data_to_device_label)).setIcon(R.drawable.download);
			menu.add(0,MENU_Data_To_Server,0,getString(R.string.data_to_server_label)).setIcon(R.drawable.upload);
			menu.add(0,MENU_Connection,0,getString(R.string.connection_label)).setIcon(R.drawable.yes);
		}
		menu.add(0,MENU_Help,0,getString(R.string.help_label)).setIcon(R.drawable.help);
		menu.add(0,MENU_Quit,0,getString(R.string.quit_label)).setIcon(R.drawable.logout);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		     case MENU_Data_To_Device:
			     // openOptionsDialog();
			      break;
		     case MENU_Data_To_Server:
			     // openOptionsDialog();
			      break;
		     case MENU_Quit:
		    	 LogoutMain();
		    	 break;
		}
		return true;
	}

	private void openDialog(final int FlatId,final int SiteId,final int FlatTypeId) {
		View menuView = View.inflate(this, R.layout.gridview_menu, null);
		// ´´½¨AlertDialog
		final AlertDialog menuDialog = new AlertDialog.Builder(this).create();
		menuDialog.setView(menuView,0,0,0,0);
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 5) {
					menuDialog.cancel();
				}
				else if(arg2==0){
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, AreaListActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					Bundle bundle=new Bundle();
					bundle.putInt("KEY_FLAT_ID",FlatId);
					bundle.putInt("KEY_SITE_ID",SiteId);
					bundle.putInt("KEY_FLAT_TYPE_ID",FlatTypeId);
					bundle.putInt("KEY_ROUND_NO",1);
					bundle.putString("KEY_TITLE", selectSiteName+selectBlockName+selectFloorName+selectFlatName+", #Round "+1);
					intent.putExtras(bundle);
					startActivity(intent);
					menuDialog.dismiss();
					//tsCheckConnect.interrupt();
					//MainActivity.this.finish();
					
				}
			}
		});
		Window window=menuDialog.getWindow();
		WindowManager.LayoutParams lp=window.getAttributes();
		lp.alpha=0.7f;
		//lp.dimAmount =1f;
		window.setAttributes(lp);
		menuDialog.show();
	}
	
	private ListAdapter getMenuAdapter(String[] menuNameArray,
			int[] menuImageArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", menuImageArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;

	}
	
	private  int getAPNType(Context context){ 
        int netType = -1;  
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); 
        if(networkInfo==null){ 
            return netType; 
        } 
        int nType = networkInfo.getType(); 
        if(nType==ConnectivityManager.TYPE_MOBILE){ 
            //Log.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is "+networkInfo.getExtraInfo()); 
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){ 
                netType = CMNET; 
            } 
            else{ 
                netType = CMWAP; 
            } 
        } 
        else if(nType==ConnectivityManager.TYPE_WIFI){ 
            netType = WIFI; 
        } 
        return netType; 

    }
	
	public boolean isConnect=false;

	public void setIsConnected(boolean isConnected) {
		// TODO Auto-generated method stub
		if(isConnected){
			isConnect=isConnected;
			tvIsConnected.setText("connected");
			ivIsConnected.setImageResource(R.drawable.connection_tick);
		}else{
			isConnect=isConnected;
			tvIsConnected.setText("disconnect");
			ivIsConnected.setImageResource(R.drawable.connection_cross);
		}
		
	}

}
