package server;
import java.net.*;
import java.io.*;
import java.sql.*;

import data.*;
public class MainHandler {
	public static int port=7779;
	ServerSocket s_socket=null;
	ObjectInputStream in;
	ObjectOutputStream out;
	static Connection conn;
	int new_area_id;
	public void start()
	{
		new Thread(new Runnable() {  
            public void run() {
            	try {
            		conn=SQLConnection.getConnection();
        			s_socket=new ServerSocket(port);
        			while (true)
        			{
        				Socket socket=s_socket.accept();
        				socket.setSoTimeout(1000);
        				invoke(socket);
        			}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
            }).start();
	}
	private void invoke(final Socket socket) throws IOException {  
        new Thread(new Runnable() {  
            public void run() {
            	try {
					in=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
					out=new ObjectOutputStream(socket.getOutputStream());
	            	MainRequest request=null;
	            	try {
	        			request=(MainRequest)in.readObject();
	        		} catch (ClassNotFoundException e) {
	        			e.printStackTrace();
	        		}
	            	if (request!=null&&request.equals(MainRequest.CreateOffer))
	            	{
	            		createOffer(socket);
	            	}
	            	if (request!=null&&request.equals(MainRequest.RemoveOrderById))
	            	{
	            		removeOrderById(socket);
	            	}
	            	if (request!=null&&request.equals(MainRequest.createProposal))
	            	{
	            		createProposal(socket);
	            	}
					if (request!=null&&request.equals(MainRequest.SearchAvailableSpotByArea))
					{
						searchAvailableSpotByArea(socket);
					}
            	} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        }).start();
	}
	
	
	private void createOffer(Socket socket)
	{
		Order Offer=null;
		try {
			Offer=(Order)in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Statement stat=null;
			stat=conn.createStatement();
			String amountString;
			if (!isIdle(Offer.spot,Offer.datetimeBegin,Offer.datetimeEnd))
			{
				out.writeObject("Time Conflict");
				stat.close();
				return;
			}
			if (Offer.amount!=0)
				amountString="\'"+Offer.amount+"\'";
			else
				amountString="NULL";
			
			String sql=
					"insert parking_lease.order(datetime_begin,datetime_end,spot_id,amount,state,lessee) values("+
						"\'"+Offer.datetimeBegin.toString()+"\'"+","+
						"\'"+Offer.datetimeEnd.toString()+"\'"+","+
						Offer.spot+","+
						amountString+
							",\'Offer\',null);";
			if (!stat.execute(sql))
			{
				out.writeObject("Success");
				stat.close();
	    		return;
			}
			else
			{
				out.writeObject("Data Error");
				stat.close();
	    		return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}
	
	private void createProposal(Socket socket)
	{
		Order proposal=null;
		try {
			proposal=(Order)in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Statement stat=null;
			stat=conn.createStatement();
			if (!timeInRange(proposal))
			{
				out.writeObject("Time out of range");
				stat.close();
				return;
			}

			
			String sql=
					"insert parking_lease.order(datetime_begin,datetime_end,spot_id,amount,state,lessee,refOrder) values("+
						"\'"+proposal.datetimeBegin.toString()+"\'"+","+
						"\'"+proposal.datetimeEnd.toString()+"\'"+","+
						proposal.spot+","+
						proposal.amount+","+
						"\'Proposal\',"+proposal.lessee+","+
						proposal.refOrder+
							");";
			if (!stat.execute(sql))
			{
				out.writeObject("Success");
				stat.close();
	    		return;
			}
			else
			{
				out.writeObject("Data Error");
				stat.close();
	    		return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}
	
	private void searchAvailableSpotByArea(Socket socket)
	{
		try{
			int areaid=in.readInt();
			Timestamp from=(Timestamp)in.readObject();
			Timestamp to=(Timestamp)in.readObject();
			ResultSet resultSet=null;
			Statement stat=null;
			try {
				stat=conn.createStatement();
				String sql=
						"select * from parking_lease.spot natural join parking_lease.order where area_id="+areaid+" and " +
								" parking_lease.order.state=\'Offer\'and " +
								"datetime_begin <= "+from.toString()+"and " +
								"datetime_end >= "+to.toString()+";";
				resultSet=stat.executeQuery(sql);
				System.out.println(sql);
				int num=0;
				
					while (resultSet.next())
					{
						Spot spotRes=new Spot(resultSet.getInt("user_id"),
								areaid,
								resultSet.getString("spot_local_ref"),
								resultSet.getString("description"),
								resultSet.getString("category"),
								resultSet.getString("size"));
						Order offerRes=new Order(resultSet.getTimestamp("datetime_begin"),
								resultSet.getTimestamp("datetime_end"),
								resultSet.getInt("spot_id"),
								resultSet.getFloat("amount"));
						out.writeObject(QueryResult.Success);
						out.writeObject(spotRes);
						num++;
					}
					if (num==0)
					{
						System.out.print("notFound");
						out.writeObject(QueryResult.NotFound);
					}
					else
					{
						out.writeObject(QueryResult.End);
					}
			
			resultSet.close();
			stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				out.writeObject(QueryResult.Error);
				System.out.println(e.getMessage());
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void removeOrderById(Socket socket)
	{
		int id=0;
		try {
			id=in.readInt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Statement stat=null;
			stat=conn.createStatement();
			String sql="delete from parking_lease.order where order_id="+id+";";
			int count=stat.executeUpdate(sql);
			if (count==0)
			{
				out.writeObject("NotFound");
				stat.close();
			}
			else
			{
				out.writeObject("Success");
				stat.close();
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}
	
	public static boolean timeInRange(Order order)
	{
		if (order.refOrder==0)
			return false;
		try {
			Statement stat=null;
			stat=conn.createStatement();
			String sql="select datetime_end,datetime_begin from parking_lease.order where order_id="+
					order.refOrder+";"
					;
			ResultSet resultSet=stat.executeQuery(sql);
			resultSet.next();
			System.out.println(resultSet.getTimestamp("datetime_end"));
			System.out.println(!order.datetimeBegin.before(resultSet.getTimestamp("datetime_begin")));
			if (resultSet.getTimestamp("datetime_end")!=null&&resultSet.getTimestamp("datetime_begin")!=null&&!order.datetimeBegin.before(resultSet.getTimestamp("datetime_begin"))&&!order.datetimeEnd.after(resultSet.getTimestamp("datetime_end")))
			{
				System.out.println("x");
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print(e.getMessage());
			return false;
		}
	}
	
	public static boolean isIdle(int spot,Timestamp datetimeBegin,Timestamp datetimeEnd)
	{
		try {
			Statement stat=null;
			stat=conn.createStatement();
			String sql="select datetime_end,max(datetime_begin) as begin from parking_lease.order where spot_id="+
					spot+" and (state=\'Offer\' or state=\'doing\') and datetime_begin<\'"+datetimeEnd.toString()+"\';"
					;
			ResultSet resultSet=stat.executeQuery(sql);
			resultSet.next();
			if (resultSet.getTimestamp("begin")==null)
			{
				stat.close();
				resultSet.close();
				return true;
			}
			else
			{
				Timestamp orderEnd=new Timestamp(0);
				orderEnd=resultSet.getTimestamp("datetime_end");
				if (orderEnd.getTime()>datetimeBegin.getTime())
				{
					stat.close();
					resultSet.close();
					return false;
				}
				else
				{
					stat.close();
					resultSet.close();
					return true;
				}
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		return false;
		}
	public static void main(String argv[])
	{
		MainHandler handler=new MainHandler();
		handler.start();
	}
}
	
	