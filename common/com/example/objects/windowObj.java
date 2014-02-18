package com.example.objects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class windowObj {
	private int windowId;
	private int siteId;
	private int userId;
	private String windowName;
	private byte[] Pic;
	private String desc;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    private List<windowDefectObj> lstWindowDefectObj=new ArrayList<windowDefectObj>();
    
    public int getWindowId(){return windowId;}
    public void setWindowId(int IntWindowId){windowId=IntWindowId;}
    
    public int getSiteId(){return siteId;}
    public void setSiteId(int longSiteId){siteId=longSiteId;}
    
    public int getUserId(){return userId;}
    public void setUserId(int longUserId){userId=longUserId;}
    
    
    public String getWindowName(){return windowName;}
    public void setWindowName(String longWindowName){windowName=longWindowName;}
    
    public Date getLockKey(){return lockKey;}
    public void setLockKey(Date dateLockKey){lockKey=dateLockKey;}
 
    public byte[] getPic(){return Pic;}
    public void setPic(byte[] longPic){Pic=longPic;}
    
    public String getDesc(){return desc;}
    public void setDesc(String longDesc){desc=longDesc;}
    
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
    
    public List<windowDefectObj> getDefectPointList(){
    
    	return lstWindowDefectObj;
    }
    
    public void setDefectPointList(List<windowDefectObj> lst){
    	lstWindowDefectObj=lst;
    }
    
    public void addDefectPoint(windowDefectObj obj){
    	lstWindowDefectObj.add(obj);
    }
	public int getDefectPointListMaxRoundNo() {
		// TODO Auto-generated method stub
		int max=0;
		for(windowDefectObj obj:lstWindowDefectObj){
			if(obj.getRoundNo()>max){
				max=obj.getRoundNo();
			}
		}
		return max;
	}
	public void removeDefect(windowDefectObj obj) {
		// TODO Auto-generated method stub
		lstWindowDefectObj.remove(obj);
	}
    
    
}
