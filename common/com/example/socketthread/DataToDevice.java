package com.example.socketthread;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.dao.tb_UserDAO;
import com.example.dao.tb_window_defectDAO;
import com.example.dts.MainActivity;
import com.example.dts.WindowListActivity;
import com.example.objects.StaticVar;
import com.example.utils.BaseDB;
import com.example.utils.DBFactory;
import com.example.utils.DBUtil;
import com.example.utils.UserDB;

public class DataToDevice  extends SuperSocketThread {
	MainActivity ctx;
	
	int user_id=0;
	AlertDialog dia;
	
	public DataToDevice(MainActivity ctx,int user_id){
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
                	ctx.SetEnableFor4Views(true);
                	break;
                case 1:
                	//ctx.SetEnableFor4Views(false);
                	
                	break;
                case 2:
                	//Toast.makeText(ctx, "Start Download.....",Toast.LENGTH_SHORT).show();
                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Downloading" )  .setCancelable(false)
        			.show();  
                	break;
                case 3:
                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Failed, cannot connect server" )  .setNegativeButton("OK" , null)  
        			.show();  
                	break;
                case 31:
                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Failed, unknown" )   .setNegativeButton("OK" , null)  
        			.show();  
                	break;
                case 4:
                	if(dia!=null){
                		dia.dismiss();
                	}
                	dia= new  AlertDialog.Builder(ctx)   
        			.setMessage("Updated" )   .setNegativeButton("OK" , null)  
        			.show();
                	break;
                case 5:
                	if(dia!=null){
                		dia.dismiss();
                	}
                	ctx.initSiteList();
                	break;
                }
            };
        };
	}

	public void run(){
		
		mThirdHandler.sendEmptyMessage(1);
		mThirdHandler.sendEmptyMessage(2);
		try{
			// Create Socket with timeout 10s
			client = new Socket();
			client.connect(new InetSocketAddress(StaticVar.ServerName,StaticVar.ServerPort), 10000);
			// Try to receive the Data DB size
			int intSize = sendDownload();
			if(intSize <= 0){
				mThirdHandler.sendEmptyMessage(31);
			}else{
					// Receive DB from Agent
					List<byte[]> receivedByte = sendReady(intSize);
					if(receivedByte == null){
						mThirdHandler.sendEmptyMessage(31);
					}else{
						UserDB db=new UserDB(ctx,String.valueOf(user_id));
						db.CreateDB(receivedByte);
						BaseDB basedb=DBFactory.GetBaseDB(ctx);
						basedb.open();
						tb_UserDAO dao=new tb_UserDAO();
						dao.updateLastSyncDate(basedb, user_id);
						basedb.close();
						closeSocket();
						mThirdHandler.sendEmptyMessage(4);
						mThirdHandler.sendEmptyMessage(5);
					}
			}
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mThirdHandler.sendEmptyMessage(31);
		}
		catch (IOException e){
			mThirdHandler.sendEmptyMessage(3);
    		e.printStackTrace();
    	}catch(Exception e){
			mThirdHandler.sendEmptyMessage(31);
		}
		mThirdHandler.sendEmptyMessage(0);
	}
	
	public int sendDownload(){
		String strMsg = "DOWNLOAD|"+String.valueOf(user_id);
		byte[] resultBuff = getByteArrayFromSocket(strMsg, -1);
		
		try
		{
			return Integer.parseInt((new String(resultBuff, "UTF-8")).split("\\|")[1]);
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	
	
}
