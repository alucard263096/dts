package com.example.socketthread;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;

import com.example.dao.tb_UserDAO;
import com.example.dts.MainActivity;
import com.example.objects.StaticVar;
import com.example.utils.BackUpDB;
import com.example.utils.BaseDB;
import com.example.utils.DBFactory;
import com.example.utils.ToolUtil;

public class DataToServer  extends SuperSocketThread {
	MainActivity ctx;
	
	int user_id=0;
	AlertDialog dia;
	
	public DataToServer(MainActivity ctx,int user_id){
		this.ctx=ctx;
		this.user_id=user_id;
		initHandle();
	}
	
	public void initHandle() {
		// TODO Auto-generated method stub
		this.mThirdHandler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch(msg.what){
                case 0:

                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Finished" )   .setNegativeButton("OK" , null)  
        			.show();
                	break;
                case 1:
                	//ctx.SetEnableFor4Views(false);

                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Current data is null" )   .setNegativeButton("OK" , null)  
        			.show();
                	break;
                	
                case 2:
                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Uploading" )  .setCancelable(false)
        			.show();  
                	break;
                case 3:

                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("unknow error" )   .setNegativeButton("OK" , null)  
        			.show();
                	break;
                }
            };
        };
	}

	public void run(){

		try {
			
			mThirdHandler.sendEmptyMessage(2);

			// Get last sync. date
			BaseDB basedb=DBFactory.GetBaseDB(ctx);
			basedb.open();
			tb_UserDAO dao = new tb_UserDAO();
			String LastSyncDate = dao.getUserLastSyncDate(basedb, user_id);
			basedb.close();
			
			// Backup user data DB
			BackUpDB bakDB = new BackUpDB(ctx,"user_"+String.valueOf(user_id)+".db");
			bakDB.doBackUp();
			
			// Delete useless data
			FilterData(bakDB, LastSyncDate);
			
			// Read backup DB into bytes array
			File bakdbfile = bakDB.getFile();
			List<byte[]> bf = ToolUtil.getByteFromFile(bakdbfile);
			
			if(bf != null){
				// Create socket
				client = new Socket();
				client.connect(new InetSocketAddress(StaticVar.ServerName,StaticVar.ServerPort), 10000);
				
				Log.i("upload file size", String.valueOf(bakdbfile.length()));
				
				// Send bytes array length to agent
				String uploadResuld = SendUploadUser(bakdbfile.length());
				
				Log.i("uploadResuld", uploadResuld);
				
				// Get return result
				String result=uploadResuld.split("\\|")[1];
				
				if(result.equals("1")){
					Log.i("SendDB", "start");
					
					// Send bytes array
					result=SendDB(bf).split("\\|")[0];
					
					Log.i("uploadResuld", "end");
					
					// Show message depend on message
					if(result.equals("END"))
						mThirdHandler.sendEmptyMessage(0);
					else
						mThirdHandler.sendEmptyMessage(3);
				}else{
					mThirdHandler.sendEmptyMessage(3);
				}
			}else{
				mThirdHandler.sendEmptyMessage(1);
			}
		} catch (Exception e) {
			mThirdHandler.sendEmptyMessage(1);
			e.printStackTrace();
		}finally{
			closeSocket();
		}
	}
	
	private void FilterData(BackUpDB bakDB, String LastSyncDate) {
		bakDB.open();
		
		/* Delete Table that no need to sync.
		 * Table List:
		 * tb_site, tb_block, tb_window_type, tb_window_defect_type, tb_area_window,
		 * tb_defect_type, tb_element_defect_type, tb_inspection, tb_floor, tb_defect_remark,
		 * tb_floor_plan, tb_flat, tb_flat_type, tb_area, tb_element_type, tb_element, tb_area_element, tb_window
		 */
		String[] DelTableList = new String[]{"tb_site", "tb_block", "tb_window_type", "tb_window_defect_type", "tb_area_window",
												"tb_defect_type", "tb_element_defect_type", "tb_inspection", "tb_floor", "tb_defect_remark",
		 										"tb_floor_plan", "tb_flat", "tb_flat_type", "tb_area", "tb_element_type", "tb_element", "tb_area_element", "tb_window"};
		
		for (int i = 0; i < DelTableList.length; i++)
		{
			bakDB.deleteTable(DelTableList[i]);
		}
		
		/* Delete the record in following that no need to sync.
		 * Table list:
		 * tb_defect, tb_window_defect, tb_flat_inspection, tb_defect_image, tb_window_image
		 */
		DelTableList = new String[]{"tb_window_defect", "tb_flat_inspection", "tb_defect", "tb_defect_image", "tb_window_image"};
		String where = "updated_date < ?";
		String[] whereArgs = new String[]{LastSyncDate};
		for(int i = 0; i < DelTableList.length ; i++)
		{
			bakDB.deleteTable(DelTableList[i], where, whereArgs);
		}
		
		bakDB.execSQL("VACUUM", new Object[]{});
		bakDB.close();
	}

	private String SendDB(List<byte[]> bf) {
		byte[] resultBuff = getByteArrayFromSocket(bf, -1);
		
		try
		{
			String strResult = new String(resultBuff, "UTF-8");
			return strResult;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	private String SendUploadUser(long size) {
		String strMsg = "UPLOAD|"+String.valueOf(user_id)+"|"+String.valueOf(size);
		byte[] resultBuff = getByteArrayFromSocket(strMsg, -1);
		
		try
		{
			String strResult = new String(resultBuff, "UTF-8");
			return strResult;
		}
		catch(Exception e)
		{
			return "";
		}
	}
}
