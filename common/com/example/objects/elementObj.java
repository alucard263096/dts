package com.example.objects;

import java.sql.Date;

public class elementObj {
	private int elementId;
	private int siteId;
	private int userId;
	private int elementTypeId;
	private String elementName;
	private int tradeId;
	private String desc;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    
    
    public int getElementId(){return elementId;}
    public void setElementId(int IntElementId){elementId=IntElementId;}
    
    public int getSiteId(){return siteId;}
    public void setSiteId(int longSiteId){siteId=longSiteId;}
    
    public int getElementTypeId(){return elementTypeId;}
    public void setElementTypeId(int longElementTypeId){elementTypeId=longElementTypeId;}
    
    public int getUserId(){return userId;}
    public void setUserId(int longUserId){userId=longUserId;}
    
    public int getTradeId(){return tradeId;}
    public void setTradeId(int longTradeId){tradeId=longTradeId;}
    
    public String getDesc(){return desc;}
    public void setDesc(String longDesc){desc=longDesc;}
    
    public String getElementName(){return elementName;}
    public void setElementName(String longElementName){elementName=longElementName;}
    
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
