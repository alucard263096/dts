package com.example.objects;

import java.sql.Date;
import java.util.List;

public class windowDefectObj {
	
	private int window_defect_id;
	private int site_id;
	private int flat_id;
	private int area_id;
	private int window_id;
	private int window_type_id;
	private int window_defect_type_id;
	
	private int round_no;
	private int x_coor;
	private int y_coor;
	
	private String desc;
	private Date lockKey;
	private int status;
	private int createdUserId;
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
	
	private boolean isDeleted=false;
    
    

    public int getWindowDefectId(){return window_defect_id;}
    public void setWindowDefectId(int _window_defect_id){window_defect_id=_window_defect_id;}

    public int getSiteId(){return site_id;}
    public void setSiteId(int _site_id){site_id=_site_id;}

    public int getFlatId(){return flat_id;}
    public void setFlatId(int _flat_id){flat_id=_flat_id;}

    public int getAreaId(){return area_id;}
    public void setAreaId(int _area_id){area_id=_area_id;}

    public int getWindowId(){return window_id;}
    public void setWindowId(int _window_id){window_id=_window_id;}

    public int getWindowTypeId(){return window_type_id;}
    public void setWindowTypeId(int _window_type_id){window_type_id=_window_type_id;}

    public int getWindowDefectTypeId(){return window_defect_type_id;}
    public void setWindowDefectTypeId(int _window_defect_type_id){window_defect_type_id=_window_defect_type_id;}
    
    public int getRoundNo(){return round_no;}
    public void setRoundNo(int _round_no){round_no=_round_no;}
    
    public int getXcoor(){return x_coor;}
    public void setXcoor(int _x_coor){x_coor=_x_coor;}

    public int getYcoor(){return y_coor;}
    public void setYcoor(int _y_coor){y_coor=_y_coor;}
    
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
	    
	   
//	public boolean IsDeleted(){
//		return isDeleted;
//	}
//	
//	public void delete(){
//		isDeleted=true;
//	}

	private boolean isInsert=true;
	private boolean isUpdate=false;
	public void SetIsUpdate(){
		isInsert=false;
		isUpdate=true;
	}
	
	public boolean IsUpdate(){
		return isUpdate;
	}
	
	public boolean IsInsert(){
		return isInsert;
	}

	private boolean isOriginal=false;
	public void SetIsOriginal(){
		isOriginal=true;
	}
	
	private boolean IsOriginal(){
		return isOriginal;
	}
	private boolean isSelected=false;
	public void SetSelected(boolean value){
		isSelected=value;
	}
	
	public boolean GetSelected(){
		return isSelected;
	}
	
	
}
