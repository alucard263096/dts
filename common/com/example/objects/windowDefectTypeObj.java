package com.example.objects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.example.utils.DBUtil;

public class windowDefectTypeObj {
	
	private int window_defect_type_id;
	private String code;
	private String window_defect_type_name;
	private String description;
	private byte[] Pic;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    
    
    public int getWindowDefectTypeId(){return window_defect_type_id;}
    public void setWindowDefectTypeId(int IntWindowDefectTypeId){window_defect_type_id=IntWindowDefectTypeId;}
    

    public String getCode(){return code;}
    public void setCode(String strCode){code=strCode;}
    
    
    
    public String getWindowDefectTypeName(){return window_defect_type_name;}
    public void setWindowDefectTypeName(String strWindowDefectTypeName){window_defect_type_name=strWindowDefectTypeName;}
    
    public String getDescription(){return description;}
    public void setDescription(String strDescription){description=strDescription;}
    
    public Date getLockKey(){return lockKey;}
    public void setLockKey(Date dateLockKey){lockKey=dateLockKey;}
 
    public byte[] getPic(){return Pic;}
    public void setPic(byte[] longPic){Pic=longPic;}
    
    
    public int getStatus(){return status;}
    public void setStatus(int longStatus){status=longStatus;}
    
    public int getCreatedUserId(){return createdUserId;}
    public void setCreatedUserId(int longCreatedUserId){createdUserId=longCreatedUserId;}
    
    public Date getCreatedDate(){return createdDate;}
    public void setCreatedDate(Date dateCreatedDate){createdDate=dateCreatedDate;}
    
    public int getUpdatedUserId(){return updatedUserId;}
    public void setUpdatedUserId(int longUpdatedUserId){updatedUserId=longUpdatedUserId;}
    
    public Date getUpdatedDate(){return updatedDate;}
    public void setUpdatedDate(Date dateUpdatedDate){updatedDate=dateUpdatedDate;}
}
