package data;

import java.io.Serializable;

public class Area implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public String name;
public int id;
public String city;
public String address;
public String property_contact;
public String district;
public Area(int id,String name,String city,String district,String address,String property_contact)
{
	this.id=id;
	this.name=name;
	this.city=city;
	this.property_contact=property_contact;
	this.address=address;
	this.district=district;
}
public Area(String name,String city,String district,String address,String property_contact)
{
	this.name=name;
	this.city=city;
	this.property_contact=property_contact;
	this.address=address;
	this.district=district;
}
public Area()
{}
}
