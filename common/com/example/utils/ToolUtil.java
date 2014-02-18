package com.example.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.example.objects.StaticVar;

import android.util.Log;

public class ToolUtil {
	public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
 
            int i;
 
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
 
            re_md5 = buf.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Log.i("md5", re_md5);
        return re_md5;
    }
	
	 // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    public static List<byte[]> getByteFromFile(File file){
    	List<byte[]> albyte = new ArrayList<byte[]>();
    	byte[] bytes = null;
    	int intMaxSize = StaticVar.ByteArraySize;
        if(file!=null)
        {
            InputStream is;
			try {
				is = new FileInputStream(file);
	            int length = (int)file.length();
	            
	            if(length>Integer.MAX_VALUE)   
	            {
	                System.out.println("this file is max ");
	                return null;
	            }
	            
	            if(length > intMaxSize)
	            {
	            	bytes = new byte[intMaxSize];
	            	
	            	int offset = 0;
		            int numRead = 0;
		            while(offset < length && (numRead = is.read(bytes, 0, intMaxSize)) >= 0)
		            {
		                offset+=numRead;
		          
		                albyte.add(bytes);
		                
		                bytes = new byte[intMaxSize];
		            }
		            //如果得到的字节长度和file实际的长度不一致就可能出错了
		            if(offset < length)
		            {
		                System.out.println("file length is error");
		                return null;
		            }
	            }
	            else
	            {
		            bytes = new byte[length];
		            int offset = 0;
		            int numRead = 0;
		            while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
		            {
		                offset+=numRead;
		            }
		            //如果得到的字节长度和file实际的长度不一致就可能出错了
		            if(offset<bytes.length)
		            {
		                System.out.println("file length is error");
		                return null;
		            }
		            albyte.add(bytes);
	            }
				is.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                return null;
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                return null;
			}
        }
        return albyte;
    }
}
