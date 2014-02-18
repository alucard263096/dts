package com.example.objects;

import java.sql.Date;

public class defectListObj {
	private int defectId;
	private int defectType;
	private int siteId;
	private int userId;
	private int flatId;
	private int areaId;
	private int elementTypeId;
	private int elementId;
	private int defectTypeId;
	private int roundNo;
	private String name;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    private int org_status;
    private String confirm_completion_date;
    
    public int getDefectId(){return defectId;}
    public void setDefectId(int IntId){defectId=IntId;}
    
    public int getDefectType(){return defectType;}
    public void setDefectType(int IntDefectType){defectType=IntDefectType;}
    
    public int getSitetId(){return siteId;}
    public void setSitetId(int IntSitetId){siteId=IntSitetId;}
    
    public int getUserId(){return userId;}
    public void setUserId(int longUserId){userId=longUserId;}
    
    public int getFlatId(){return flatId;}
    public void setFlatId(int IntFlatId){flatId=IntFlatId;}
    
    public int getAreaId(){return areaId;}
    public void setAreaId(int IntAreaId){areaId=IntAreaId;}
    
    public int getElementTypeId(){return elementTypeId;}
    public void setElementTypeId(int IntElementTypeId){elementTypeId=IntElementTypeId;}
    
    public int getElementId(){return elementId;}
    public void setElementId(int IntElementId){elementId=IntElementId;}
    
    public int getDefectTypeId(){return defectTypeId;}
    public void setDefectTypeId(int IntDefectTypeId){defectTypeId=IntDefectTypeId;}
    
    public int getRoundNo(){return roundNo;}
    public void setRoundNo(int IntRoundNo){roundNo=IntRoundNo;}
      
    public String getName(){return name;}
    public void setName(String stringDesc){name=stringDesc;}
    
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
    
    public String getConfirmCompletionDate(){return confirm_completion_date;}
    public void setConfirmCompletionDate(String date){confirm_completion_date=date;}
    
    public int getOrgStatus(){return org_status;}
    public void setOrgStatus(int _org_status){this.org_status=_org_status;}
    
}
