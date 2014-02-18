package com.example.socketthread;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;

import com.example.dts.MainActivity;
import com.example.objects.StaticVar;

public class CheckConnect extends SuperSocketThread {
	
	MainActivity ctx;
	
	public CheckConnect(MainActivity ctx){
		this.ctx=ctx;
		initHandle();
	}
	
	AlertDialog dia;
	Handler mThirdHandler;
	public void initHandle() {
		// TODO Auto-generated method stub
		this.mThirdHandler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                
                ctx.setIsConnected(msg.what==1);
            };
        };
	}
	
	public void run(){
		while(true){
			client = new Socket();
			try{
				Log.i("cck", "1");
				client.connect(new InetSocketAddress(StaticVar.ServerName,StaticVar.ServerPort), 10000);
				Log.i("cck", "2");
				String text=ping();
				Log.i("cck", "3");
				closeSocket();
				Log.i("cck", "4");
				String result=text.split("\\|")[1];
				Log.i("cck", "5");
				mThirdHandler.sendEmptyMessage(Integer.valueOf(result));
				//mThirdHandler.sendEmptyMessage(1);
				Log.i("cck", "6");
				this.sleep(30000);
			}
			catch(Exception e){
				mThirdHandler.sendEmptyMessage(0);
			}
		}
	}
	
	public String ping(){
		String strMsg = "PING|";
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
