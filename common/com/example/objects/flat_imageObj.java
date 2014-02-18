package com.example.objects;

import java.sql.Date;

public class flat_imageObj {
	private int FlatImageId;
	private int FlatId;
	private byte[] Pic;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    
    
    public int getFlatImageId(){return FlatImageId;}
    public void setFlatImageId(int IntDefectImageId){FlatImageId=IntDefectImageId;}
    
    public int getFlatId(){return FlatId;}
    public void setFlatId(int longDefectId){FlatId=longDefectId;}
    
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

