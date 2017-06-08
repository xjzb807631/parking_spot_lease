package data;

import java.io.Serializable;

public class Transfer implements Serializable {
	public QueryResult queryResult;
	public Transfer(QueryResult queryResult)
	{
		this.queryResult=queryResult;
	}
	public Transfer()
	{
	}
}
