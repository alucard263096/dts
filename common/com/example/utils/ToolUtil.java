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
	
	 // �����ļ�
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // �½��ļ����������������л���
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // �½��ļ���������������л���
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // ��������
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // ˢ�´˻���������
            outBuff.flush();
        } finally {
            // �ر���
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
		            //����õ����ֽڳ��Ⱥ�fileʵ�ʵĳ��Ȳ�һ�¾Ϳ��ܳ�����
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
		            //����õ����ֽڳ��Ⱥ�fileʵ�ʵĳ��Ȳ�һ�¾Ϳ��ܳ�����
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
