package com.example.socketthread;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.example.objects.StaticVar;

public class SuperSocketThread extends Thread {
	public Socket client;
	public Handler mThirdHandler;
	public void initHandle() {
		
	}
	
	public String sendDone(){
		String strMsg = "DONE|";
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
	public List<byte[]> sendReady(int intReceiveBufSize){
		String strMsg = "READY|";
		return getArrayListByteArrayFromSocket(strMsg, intReceiveBufSize);
	}
	
	public byte[] getByteArrayFromSocket(List<byte[]> bufferin, int intReceiveBufSize){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] resultBuff = null;
		byte buffer[] ;
		OutputStream out;
		try
		{
			// Send request message to agent
			out = client.getOutputStream();
			for(int i = 0 ; i < bufferin.size(); i++)
				out.write(bufferin.get(i));
	    	out.flush();

	    	if(intReceiveBufSize < 0)
	    	{
	    		buffer = new byte[1024];
	    		int s = client.getInputStream().read(buffer, 0, buffer.length);
	    		baos.write(buffer, 0, s);
		    	baos.flush();
	    	}
	    	else
	    	{
		    	int intSizeCnt = 0;
		    	buffer = new byte[1024];
		    	while(intSizeCnt < intReceiveBufSize)
		    	{
		    		int s=client.getInputStream().read(buffer, 0, buffer.length);
		    	
		    		baos.write(buffer, 0, s);
		    		intSizeCnt += s;
		    	}
		    	baos.flush();
	    	}
			resultBuff = baos.toByteArray();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			baos = null;
			out = null;
			buffer = null;
		}
		return resultBuff;
	}
	public byte[] getByteArrayFromSocket(String strMsg, int intReceiveBufSize)
	{
		List<byte[]> alByte = new ArrayList<byte[]>();
		int intMaxSize = StaticVar.ByteArraySize;
		int intBufSize = 1024;
		ByteArrayOutputStream baos;
		byte[] resultBuff = null;

		if(intReceiveBufSize > intMaxSize)
			baos = new ByteArrayOutputStream(intMaxSize);
		else
			baos = new ByteArrayOutputStream();
		
		try
		{
			// Send request message to agent
			PrintWriter out = new PrintWriter(client.getOutputStream());
	    	out.println(strMsg);
	    	out.flush();

	    	if(intReceiveBufSize < 0)
	    	{
	    		byte buffer[] = new byte[1024];
	    		int s=client.getInputStream().read(buffer, 0, buffer.length);
	    		Log.i("buffer.length", String.valueOf(s));
	    		if(s>0){

		    		baos.write(buffer, 0, s);
			    	baos.flush();
	    		}
	    		resultBuff = baos.toByteArray();
	    	}
	    	else
	    	{
		    	int intSizeCnt = 0;
		    	byte buffer[] = new byte[intBufSize];
		    	boolean NeedFlush = false;
		    	while(intSizeCnt < intReceiveBufSize)
		    	{
		    		int s=client.getInputStream().read(buffer, 0, buffer.length);
		    	
		    		baos.write(buffer, 0, s);
		    		intSizeCnt += s;
		    		NeedFlush = true;
		    		
		    		if(baos.size() + intBufSize > intMaxSize)
		    		{
		    			alByte.add(baos.toByteArray());
		    			NeedFlush = false;
		    			baos.reset();
		    		}
		    	}
		    	if(NeedFlush)
		    	{
		    		if(intSizeCnt + intBufSize > intMaxSize)
		    		{
		    			alByte.add(baos.toByteArray());
		    		}
		    		else
		    		{
			    		baos.flush();
			    		resultBuff = baos.toByteArray();
		    		}
		    	}
	    	}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			baos = null;
		}
		return resultBuff;
	}
	public List<byte[]> getArrayListByteArrayFromSocket(String strMsg, int intReceiveBufSize)
	{
		List<byte[]> alByte = new ArrayList<byte[]>();
		int intMaxSize = 10240000;
		int intBufSize = 1024;
		ByteArrayOutputStream baos;
		byte[] resultBuff = null;

		if(intReceiveBufSize > intMaxSize)
			baos = new ByteArrayOutputStream(intMaxSize);
		else
			baos = new ByteArrayOutputStream();
		
		try
		{
			// Send request message to agent
			PrintWriter out = new PrintWriter(client.getOutputStream());
	    	out.println(strMsg);
	    	out.flush();

	    	if(intReceiveBufSize < 0)
	    	{
	    		byte buffer[] = new byte[1024];
	    		int s=client.getInputStream().read(buffer, 0, buffer.length);
	    		Log.i("buffer.length", String.valueOf(s));
	    		if(s>0){

		    		baos.write(buffer, 0, s);
			    	baos.flush();
	    		}
	    		resultBuff = baos.toByteArray();
	    		alByte.add(resultBuff);
	    	}
	    	else
	    	{
		    	int intSizeCnt = 0;
		    	byte buffer[] = new byte[intBufSize];
		    	boolean NeedFlush = false;
		    	while(intSizeCnt < intReceiveBufSize)
		    	{
		    		int s=client.getInputStream().read(buffer, 0, buffer.length);
		    	
		    		baos.write(buffer, 0, s);
		    		intSizeCnt += s;
		    		NeedFlush = true;
		    		
		    		if(baos.size() + intBufSize > intMaxSize)
		    		{
		    			alByte.add(baos.toByteArray());
		    			NeedFlush = false;
		    			baos.reset();
		    		}
		    	}
		    	if(NeedFlush)
		    	{
		    		if(intSizeCnt + intBufSize > intMaxSize)
		    		{
		    			alByte.add(baos.toByteArray());
		    		}
		    		else
		    		{
			    		baos.flush();
			    		resultBuff = baos.toByteArray();
			    		alByte.add(resultBuff);
		    		}
		    	}
	    	}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			baos = null;
		}
		return alByte;
	}

	public void closeSocket()
	{
		try
		{
			String strMsg = "BYE|";
			// Send request message to agent
			PrintWriter out = new PrintWriter(client.getOutputStream());
	    	out.println(strMsg);
	    	out.flush();
	    	
	    	client.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
