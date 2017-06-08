package data;

import java.io.Serializable;

public class Spot implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public int user;
public int area;
public String description;
public String category;
public String size;
public String spot_local_ref;
public Spot (int user,int area,String spot_local_ref,String description,String category,String size)
{
	this.user=user;
	this.area=area;
	this.description=description;
	this.category=category;
	this.size=size;
	this.spot_local_ref=spot_local_ref;
}
}
