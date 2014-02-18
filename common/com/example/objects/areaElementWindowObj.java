package com.example.objects;

import java.sql.Date;


public class areaElementWindowObj {
	private int areaId;
	private int siteId;
	private int userId;
	private int flatTypeId;
	private int elementTypeId;
	private String Name;
	private int id;
	private boolean isWindow;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    
    
    public int getAreaId(){return areaId;}
    public void setAreaId(int IntAreaId){areaId=IntAreaId;}
    
    public int getSiteId(){return siteId;}
    public void setSiteId(int longSiteId){siteId=longSiteId;}
    
    public int getElementTypeId(){return elementTypeId;}
    public void setElementTypeId(int longElementTypeId){elementTypeId=longElementTypeId;}
    
    public int getFlatTypeId(){return flatTypeId;}
    public void setFlatTypeId(int longFlatTypeId){flatTypeId=longFlatTypeId;}
    
    public int getUserId(){return userId;}
    public void setUserId(int longUserId){userId=longUserId;}
    
    public int getId(){return id;}
    public void setId(int longId){id=longId;}
    
    public String getName(){return Name;}
    public void setName(String longAreaName){Name=longAreaName;}
    
    public boolean getIsWindow(){return isWindow;}
    public void setIsWindow(boolean longIsWindow){isWindow=longIsWindow;}
    
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
