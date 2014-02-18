package com.example.common;

import android.app.Application;

public class global_info extends Application{ 
	
	private int userId;
    private String loginName;
    private String userName ; 
    private String companyName;
    private int role;
    private String contactNo;
    private String email;
    private int status;
	
    public String getUserContactNo(){   
        return contactNo;   
    }      
    public void setUserContactNo(String s){   
        this.contactNo = s;   
    }
    
    public String getUserEmail(){   
        return email;   
    }      
    public void setUserEmail(String s){   
        this.email = s;   
    }
    
    public String getUserName(){   
        return userName;   
    }      
    public void setUserName(String s){   
        this.userName = s;   
    }
    
    public String getCompanyName(){   
        return companyName;   
    }      
    public void setCompanyName(String s){   
        this.companyName = s;   
    }
    
    public String getLoginName(){   
        return loginName;   
    }      
    public void setLoginName(String s){   
        this.loginName = s;   
    }
    
    public int getUserId(){   
        return userId;   
    }      
    public void setUserId(int s){   
        this.userId = s;   
    }
    
    public int getUserStatus(){   
        return status;   
    }      
    public void setUserStatus(int s){   
        this.status = s;   
    }
    
    public int getUserRole(){   
        return role;   
    }      
    public void setUserRole(int s){   
        this.role = s;   
    }
    
    @Override   
    public void onCreate() {   
        // TODO Auto-generated method stub   
        super.onCreate();        
    } 
}
