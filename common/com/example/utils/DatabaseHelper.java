package com.example.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.example.objects.StaticVar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	 private static final int DB_VERSION = 1;
	    private  String DB_PATH        = "/data/data/com.example.dts/databases/";
	/*
	    //�����������ݿ��ļ������SD���Ļ�
	    private static String DB_PATH        = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
	                                        + "/arthurcn/drivertest/packfiles/";
	*/
	    private  String DB_NAME;
	    private final Context myContext;
	    
	    public DatabaseHelper(Context context,String path, String name, CursorFactory factory, int version) {
	        //����ͨ��super���ø��൱�еĹ��캯��
	        super(context, name, null, version);
	        this.myContext = context;
	        this.DB_NAME=name;
	        this.DB_PATH=path;
	    }
	    

	    public DatabaseHelper(Context context,String path, String name, int version){
	        this(context,path,name,null,version);
	    }
	    
	    public DatabaseHelper(Context context, String name, int version){
	        this(context,"/data/data/com.example.dts/databases/",name,null,version);
	    }
	 
	    public DatabaseHelper(Context context, String name){
	        this(context,name,DB_VERSION);
	    }
	   
	  
	     /**
	      * ������ݿ��ļ��ϴ�ʹ��FileSplit�ָ�ΪС��1M��С�ļ�
	      * �����зָ�Ϊ hello.db.101    hello.db.102    hello.db.103
	      */
	    //��һ���ļ�����׺
	    private static final int ASSETS_SUFFIX_BEGIN    = 101;
	    //���һ���ļ�����׺
	    private static final int ASSETS_SUFFIX_END        = 103;
	   
	    /**
	     * ��SQLiteOpenHelper�����൱�У������иù��캯��
	     * @param context    �����Ķ���
	     * @param name        ���ݿ�����
	     * @param factory    һ�㶼��null
	     * @param version    ��ǰ���ݿ�İ汾��ֵ���������������ǵ�����״̬
	     */
	   
	    
	    public long getDatabaseLength(){
	    	 try {
	    		 Log.i(DB_PATH, DB_PATH + DB_NAME);
	    		 File dbf = new File(DB_PATH + DB_NAME);
            if(dbf.exists()){
            	return dbf.length();
            }
	            } catch (Exception e) {
	               return 0;
	            }
	    	 return 0;
	    }
	    
	    public void deleteDataBase(){
	    	 try {
	    		 File dbf = new File(DB_PATH + DB_NAME);
            if(dbf.exists()){
                dbf.delete();
            	Log.i("d", "delete db");
            }
	            } catch (Exception e) {
	                throw new Error("ɾ��ʧ��");
	            }
	    }

	    public void createDataBase(byte[] buffer) throws IOException{
	    	
            //�������ݿ�
            try {
                File dir = new File(DB_PATH);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + DB_NAME);
                if(dbf.exists()){
                    dbf.delete();
                }
                copyDataBase(buffer);
            } catch (IOException e) {
            	e.printStackTrace();
                throw new Error("���ݿⴴ��ʧ��");
            }
	        
	    }

	    public void createDataBase(List<byte[]> albuffer) throws IOException{
	    	
            //�������ݿ�
            try {
                File dir = new File(DB_PATH);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + DB_NAME);
                if(dbf.exists()){
                    dbf.delete();
                }
                copyDataBase(albuffer);
            } catch (IOException e) {
            	e.printStackTrace();
                throw new Error("���ݿⴴ��ʧ��");
            }
	        
	     }
	    
	    public void createDataBase() throws IOException{
	    	
	        boolean dbExist = checkDataBase();
	        if(dbExist){
	        	Log.i("dbexists", DB_NAME);
	        }else{
	            try {
	                File dir = new File(DB_PATH);
	                if(!dir.exists()){
	                    dir.mkdirs();
	                }
	                File dbf = new File(DB_PATH + DB_NAME);
	                if(dbf.exists()){
	                    dbf.delete();
	                }
	                //SQLiteDatabase.openOrCreateDatabase(dbf, null);
	                
	                copyDataBase();
	            } catch (IOException e) {
	            	e.printStackTrace();
	                throw new Error("���ݿⴴ��ʧ��");
	            }
	        }
	     }
	   
	     //������ݿ��Ƿ���Ч
	     public boolean checkDataBase(){
	         SQLiteDatabase checkDB = null;
	         String myPath = DB_PATH + DB_NAME;
	         try{           
	             checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	         }catch(SQLiteException e){
	             //database does't exist yet.
	        }
	        if(checkDB != null){
	            checkDB.close();
	        }
	         return checkDB != null ? true : false;
	    }
	 
	    /**
	     * Copies your database from your local assets-folder to the just created empty database in the
	     * system folder, from where it can be accessed and handled.
	      * This is done by transfering bytestream.
	     * */
	     private void copyDataBase() throws IOException{
	    	 InputStream myInput;
	    	 //Open your local db as the input stream
	         myInput = myContext.getAssets().open("user_0.db");
	    		 
	         // Path to the just created empty db
	         String outFileName = DB_PATH + DB_NAME;
	         //Open the empty db as the output stream
	         OutputStream myOutput = new FileOutputStream(outFileName);
	         //transfer bytes from the inputfile to the outputfile
	         byte[] buffer = new byte[1024];
	        int length;
	         while ((length = myInput.read(buffer))>0){
	             myOutput.write(buffer, 0, length);
	         }
	         //Close the streams
	         myOutput.flush();
	         myOutput.close();
	         myInput.close();
	     }
	     
	     private void copyDataBase(byte[] buffer) throws IOException{
	         //Open your local db as the input stream
	         //InputStream myInput = myContext.getAssets().open(DB_NAME);
	         // Path to the just created empty db
	         String outFileName = DB_PATH + DB_NAME;
	         //Open the empty db as the output stream
	         OutputStream myOutput = new FileOutputStream(outFileName);
	         //transfer bytes from the inputfile to the outputfile
	         int length;
	         length = buffer.length;
	         //while ((length = buffer.length)>0){
	             myOutput.write(buffer, 0, length);
	         //}
	         //Close the streams
	         myOutput.flush();
	         myOutput.close();
	         //myInput.close();
	     }

	     private void copyDataBase(List<byte[]> alBuf) throws IOException{
	         // Path to the just created empty db
	         String outFileName = DB_PATH + DB_NAME;
	         // Open the empty db as the output stream
	         OutputStream myOutput = new FileOutputStream(outFileName);
	         for(int i= 0 ; i < alBuf.size(); i++)
	         {
		         int length;
		         length = alBuf.get(i).length;
		         myOutput.write(alBuf.get(i), 0, length);
	         }
	         //Close the streams
	         myOutput.flush();
	         myOutput.close();
	     }
	    
	    //����assets�µĴ����ݿ��ļ�ʱ�����
	     private void copyBigDataBase() throws IOException{
	         InputStream myInput;
	         String outFileName = DB_PATH + DB_NAME;
	         OutputStream myOutput = new FileOutputStream(outFileName);
	         for (int i = ASSETS_SUFFIX_BEGIN; i < ASSETS_SUFFIX_END+1; i++) {
	             myInput = myContext.getAssets().open(DB_NAME + "." + i);
	             byte[] buffer = new byte[1024];
	             int length;
	             while ((length = myInput.read(buffer))>0){
	                myOutput.write(buffer, 0, length);
	             }
	             myOutput.flush();
	             myInput.close();
	         }
	         myOutput.close();
	     }
	    
//	     @Override
//	     public synchronized void close() {
//	        if(myDataBase != null){
//	             myDataBase.close();
//	         }
//	         super.close();
//	     }
	    
	     /**
	      * �ú������ڵ�һ�δ�����ʱ��ִ�У�
	      * ʵ�����ǵ�һ�εõ�SQLiteDatabase�����ʱ��Ż�����������
	      */
	    @Override
	     public void onCreate(SQLiteDatabase db) {
	     }
	    
	     /**
	      * ���ݿ��ṹ�б仯ʱ����
	      */
	     @Override
	     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	     }


}
