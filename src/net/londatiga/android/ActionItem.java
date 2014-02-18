package net.londatiga.android;

import android.R.integer;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;

/**
 * Action item, displayed as menu with icon and text.
 * 
 * @author Lorensius. W. L. T <lorenz@londatiga.net>
 * 
 * Contributors:
 * - Kevin Peck <kevinwpeck@gmail.com>
 *
 */
public class ActionItem {
	private Drawable icon;
	private Bitmap thumb;
	private String title;
	private int colorId=-1;
	private int actionId = -1;
    private boolean selected;
    private boolean sticky;
	
    /**
     * Constructor
     * 
     * @param actionId  Action id for case statements
     * @param title     Title
     * @param icon      Icon to use
     */
    public ActionItem(int actionId, String title, Drawable icon,int colorId) {
        this.title = title;
        this.icon = icon;
        this.actionId = actionId;
        this.colorId=colorId;
    }
    
    /**
     * Constructor
     */
    public ActionItem() {
        this(-1, null, null,-1);
    }
    
    /**
     * Constructor
     * 
     * @param actionId  Action id of the item
     * @param title     Text to show for the item
     */
    public ActionItem(int actionId, String title) {
        this(actionId, title, null,-1);
    }
    
    /**
     * Constructor
     * 
     * @param actionId  Action id of the item
     * @param title     Text to show for the item
     */
    public ActionItem(int actionId, String title,int colorId) {
        this(actionId, title, null,colorId);
    }
    
    /**
     * Constructor
     * 
     * @param icon {@link Drawable} action icon
     */
    public ActionItem(Drawable icon) {
        this(-1, null, icon,-1);
    }
    
    /**
     * Constructor
     * 
     * @param actionId  Action ID of item
     * @param icon      {@link Drawable} action icon
     */
    public ActionItem(int actionId, Drawable icon) {
        this(actionId, null, icon,-1);
    }
	
	/**
	 * Set action title
	 * 
	 * @param title action title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get action title
	 * 
	 * @return action title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set action title
	 * 
	 * @param title action title
	 */
	public void setColor(int colorId) {
		this.colorId = colorId;
	}
	
	/**
	 * Get action title
	 * 
	 * @return action title
	 */
	public int getColor() {
		return this.colorId;
	}
	
	/**
	 * Set action icon
	 * 
	 * @param icon {@link Drawable} action icon
	 */
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	/**
	 * Get action icon
	 * @return  {@link Drawable} action icon
	 */
	public Drawable getIcon() {
		return this.icon;
	}
	
	 /**
     * Set action id
     * 
     * @param actionId  Action id for this action
     */
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
    
    /**
     * @return  Our action id
     */
    public int getActionId() {
        return actionId;
    }
    
    /**
     * Set sticky status of button
     * 
     * @param sticky  true for sticky, pop up sends event but does not disappear
     */
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }
    
    /**
     * @return  true if button is sticky, menu stays visible after press
     */
    public boolean isSticky() {
        return sticky;
    }
    
	/**
	 * Set selected flag;
	 * 
	 * @param selected Flag to indicate the item is selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Check if item is selected
	 * 
	 * @return true or false
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Set thumb
	 * 
	 * @param thumb Thumb image
	 */
	public void setThumb(Bitmap thumb) {
		this.thumb = thumb;
	}
	
	/**
	 * Get thumb image
	 * 
	 * @return Thumb image
	 */
	public Bitmap getThumb() {
		return this.thumb;
	}
}