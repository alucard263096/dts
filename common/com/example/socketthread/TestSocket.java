package com.example.socketthread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.objects.StaticVar;

public class TestSocket extends Thread {

	private Context ctx;


	public TestSocket(Context ctx){
		this.ctx=ctx;
	}
	
	
	public void run(){
		try{
        	Socket client=new Socket(StaticVar.ServerName,StaticVar.ServerPort);
        	BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        	PrintWriter out = new PrintWriter(client.getOutputStream());
        	out.println("aa");
        	out.flush();
        	String str=in.readLine();
        	Log.i("aa", str);
    	}catch (UnknownHostException e){
    		e.printStackTrace();
    	}catch (IOException e){
    		e.printStackTrace();
    	}
	}
}
