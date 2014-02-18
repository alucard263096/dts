package com.example.objects;

import java.sql.Date;

public class siteObj {
	private int siteId;
	private int userId;
	private String siteName;
	private String desc;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
      
    public int getSiteId(){return siteId;}
    public void setSiteId(int longSiteId){siteId=longSiteId;}
    
    public int getUserId(){return userId;}
    public void setUserId(int longUserId){userId=longUserId;}
    
    public String getSiteName(){return siteName;}
    public void setSiteName(String longAreaName){siteName=longAreaName;}
    
    public String getDesc(){return desc;}
    public void setDesc(String longDesc){desc=longDesc;}
    
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
