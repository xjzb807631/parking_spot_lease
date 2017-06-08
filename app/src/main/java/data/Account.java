package data;

import java.io.Serializable;

public class Account implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public String cellphone;
public String userID;
public String password;
public void print()
{
	System.out.println(cellphone);
	System.out.println(userID);
	System.out.println(password);
}
public Account (String cellphone, String userID, String password)
{
	this.cellphone=cellphone;
	this.userID=userID;
	this.password=password;
}
public Account (){}
public Account (Account other){
	this.cellphone=other.cellphone;
	this.userID=other.userID;
}

}

