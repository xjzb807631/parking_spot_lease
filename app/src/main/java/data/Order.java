package data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

public class Order implements Serializable {
	public Timestamp datetimeBegin;
	public Timestamp datetimeEnd;
	public int spot;
	public float amount;
	public int lessee;
	public Order()
	{}
	public int refOrder;
	public Order (int lessee,Timestamp datetimeBegin,Timestamp datetimeEnd,int spot,float amount,int refOrder){
		this.lessee=lessee;
		this.datetimeBegin=datetimeBegin;
		this.datetimeEnd=datetimeEnd;
		this.spot=spot;
		this.amount=amount;
		this.refOrder=refOrder;
	}
	public Order (Timestamp datetimeBegin,Timestamp datetimeEnd,int spot,float amount){
		this.datetimeBegin=datetimeBegin;
		this.datetimeEnd=datetimeEnd;
		this.spot=spot;
		this.amount=amount;
	}
}
