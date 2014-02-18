package com.example.socketthread;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;

import com.example.dts.LoginActivity;
import com.example.objects.StaticVar;
import com.example.utils.BaseDB;

public class LoginGet extends SuperSocketThread{
	LoginActivity ctx;
	
	String userName;
	String psw;
	
	public LoginGet(LoginActivity ctx,String userName,String psw){
		this.ctx=ctx;
		this.userName=userName;
		this.psw=psw;
		initHandle();
	}
	
	AlertDialog dia;
	
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
	                	dia= new  AlertDialog.Builder(ctx).setMessage("Logining, please waiting" ).setCancelable(false).show();  
	                	break;
	                case 1:
	                	if(dia!=null){
	            			dia.dismiss();
	                	}
	                	ctx.Login();
                }
            };
        };
	}
	
	public void run(){
		client = new Socket();
		mThirdHandler.sendEmptyMessage(0);
		try{
			Log.i("act", "1");
			// Create Socket with timeout 10s
			client.connect(new InetSocketAddress(StaticVar.ServerName,StaticVar.ServerPort), 10000);
			Log.i("act", "2");
			// Try to receive the User DB size
			int intReceiveBufferSize = sendLoginRequest();
			if (intReceiveBufferSize > 0)
			{
				Log.i("act", "3");
				// Receive UserDB from Agent
				byte[] midbytes = sendDownload(intReceiveBufferSize);
				Log.i("act", "4");
				closeSocket();
				if(midbytes.length > 0)
				{
					Log.i("act", "5");
					BaseDB db=new BaseDB(ctx);
					Log.i("act", "6");
					db.CreateDB(midbytes);
					Log.i("act", "7");
				}
			}
		}catch (UnsupportedEncodingException e) {
			if(!client.isClosed())
				closeSocket();
			e.printStackTrace();
		}
		catch (IOException e){
			if(!client.isClosed())
				closeSocket();
    		e.printStackTrace();
    	}
		catch (Exception e){
			if(!client.isClosed())
				closeSocket();
    		e.printStackTrace();
    	}
		Log.i("act", "0");
		mThirdHandler.sendEmptyMessage(1);
	}
	
	public int sendLoginRequest()
	{
		String strMsg = "LOGIN|"+userName+"|"+psw;
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
	
	public byte[] sendDownload(int intReceiveBufSize)
	{
		String strMsg = "READY|";
		byte[] resultBuff = getByteArrayFromSocket(strMsg, intReceiveBufSize);
		return resultBuff;
	}
	
}
