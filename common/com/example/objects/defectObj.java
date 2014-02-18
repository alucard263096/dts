package com.example.objects;

import java.sql.Date;


public class defectObj {
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
	private String detailDesc;
	private Date scheduledCompletionDate;
	private Date actualCompletionDate;
	private int defectFormRef;
	private Date formReceivedDate;
	private String desc;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
    
    
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
    
    public String getDetailDesc(){return detailDesc;}
    public void setDetailDesc(String stringDetailDesc){detailDesc=stringDetailDesc;}
    
    public Date getActualCompletionDate(){return actualCompletionDate;}
    public void setActualCompletionDate(Date dateActualCompletionDate){actualCompletionDate=dateActualCompletionDate;}
    
    public Date getScheduledCompletionDate(){return scheduledCompletionDate;}
    public void setScheduledCompletionDate(Date dateScheduledCompletionDate){scheduledCompletionDate=dateScheduledCompletionDate;}
    
    public int getDefectFormRef(){return defectFormRef;}
    public void setDefectFormRef(int longDefectFormRef){defectFormRef=longDefectFormRef;}
    
    public Date getFormReceivedDate(){return formReceivedDate;}
    public void setFormReceivedDate(Date dateFormReceivedDate){formReceivedDate=dateFormReceivedDate;}
    
    public String getDesc(){return desc;}
    public void setDesc(String stringDesc){desc=stringDesc;}
    
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

