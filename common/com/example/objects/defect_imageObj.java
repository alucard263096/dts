package com.example.objects;

import java.sql.Blob;
import java.sql.Date;

import android.R.bool;
import android.R.integer;

public class defect_imageObj  {

	private int defectImageId;
	private int DefectId;
	private int ImageType;
	private int UserId;
	private byte[] Pic;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
	private int actionType;
    
    
    public int getDefectImageId(){return defectImageId;}
    public void setDefectImageId(int IntDefectImageId){defectImageId=IntDefectImageId;}
    
    public int getDefectId(){return DefectId;}
    public void setDefectId(int longDefectId){DefectId=longDefectId;}
    
    public int getImageType(){return ImageType;}
    public void setImageType(int longImageType){ImageType=longImageType;}
    
    public int getUserId(){return UserId;}
    public void setUserId(int longUserId){UserId=longUserId;}
    
    public byte[] getPic(){return Pic;}
    public void setPic(byte[] longPic){Pic=longPic;}
    
    public Date getLockKey(){return lockKey;}
    public void setLockKey(Date dateLockKey){lockKey=dateLockKey;} 
    
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
