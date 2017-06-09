package server;
import java.net.*;
import java.io.*;
import java.sql.*;

import data.*;
public class SpotHandler {
	public static int port=7778;
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
        			// TODO Auto-generated catch block
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
	            	SpotRequest request=null;
	            	try {
	        			request=(SpotRequest)in.readObject();
	        		} catch (ClassNotFoundException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	if (request!=null&&request.equals(SpotRequest.InsertArea))
	            	{
	            		InsertArea(socket);
	            	}
	            	if (request!=null&&request.equals(SpotRequest.SearchAreaByDistrict))
	            	{
	            		searchAreaByDistrict(socket);
	            	}
	            	if (request!=null&&request.equals(SpotRequest.InsertSpot))
	            	{
	            		InsertSpot(socket);
	            	}
	            	if (request!=null&&request.equals(SpotRequest.GetSpotByUser))
	            	{
	            		getSpotByUser(socket);
	            	}
	            	if (request!=null&&request.equals(SpotRequest.SearchCitiesByProvince))
	            	{
	            		searchCitiesByProvince(socket);
	            	}
	            	if (request!=null&&request.equals(SpotRequest.SearchDistrictsByCity))
	            	{
	            		searchDistrictsByCity(socket);
	            	}
	            	if (request!=null&&request.equals(SpotRequest.SearchSpotByArea))
	            	{
	            		searchSpotByArea(socket);
	            	}
            	} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        }).start();
	}
	
private void InsertArea(Socket socket)
{
	Area area=null;
	try {
		area=(Area)in.readObject();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		//???????????????????
		Statement stat=null;
		stat=conn.createStatement();
		//??????
		String propertyString;
		if (area.property_contact!=null)
			propertyString="\'"+area.property_contact+"\'";
		else
			propertyString="NULL";
		String sql=
				"insert parking_lease.area(area_name,city,districtaddress,property_contact) values("+
					"\'"+area.name+"\'"+","+
					"\'"+area.city+"\'"+","+
					"\'"+area.district+"\'"+","+
					"\'"+area.address+"\'"+","+
					propertyString+
						");";
		System.out.println(sql);
		System.out.flush();
		if (!stat.execute(sql))
		{
			out.writeObject("Success");
			stat.close();
    		return;
		}
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.print(e.getMessage());
	}
}
private void InsertSpot(Socket socket)
{
	Spot spot=null;
	try {
		spot=(Spot)in.readObject();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		Statement stat=null;
		stat=conn.createStatement();
		String sql="select * from parking_lease.spot where spot_local_ref="+
		"\'"+spot.spot_local_ref+"\'"+" and area_id="+
		"\'"+spot.area+"\';";
		ResultSet resultSet=stat.executeQuery(sql);
		if (resultSet.next())
		{
			out.writeObject("Existing Spot");
			stat.close();
			resultSet.close();
    		return;
		}
		resultSet.close();
		String description;
		if (spot.description!=null)
			description="\'"+spot.description+"\'";
		else
			description="NULL";
		sql=
				"insert parking_lease.spot(spot_local_ref,description,category,size,area_id,user_id) values("+
					"\'"+spot.spot_local_ref+"\'"+","+
					description+","+
					"\'"+spot.category+"\'"+","+
					"\'"+spot.size+"\'"+","+
					"\'"+spot.area+"\'"+","+
					"\'"+spot.user+"\'"+
						");";
		System.out.println(sql);
		System.out.flush();
		if (!stat.execute(sql))
		{
			out.writeObject("Success");
			stat.close();
    		return;
		}
		else
		{
			out.writeObject("Error");
			stat.close();
    		return;
		}
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.print(e.getMessage());
	}
}
/*	??¦Ç???10?????
	?????	district??????
			start????????
	?????	?????§Þ????????notfound??
			????§µ????????success?????area??
			???end?????
*/
public void searchAreaByDistrict(Socket socket)
{
	try{
		String city=(String)in.readObject();
		String district=(String)in.readObject();
		int start=in.readInt();
		ResultSet resultSet=null;
		Statement stat=null;
		try {
			stat=conn.createStatement();
			String sql=
					"select * from parking_lease.area where district=\'"+district+"\' limit "+start+",10;";
			resultSet=stat.executeQuery(sql);
			System.out.println(sql);
			int num=0;							//??????????????
			
				while (resultSet.next())
				{
					Area res=new Area(resultSet.getInt("area_id"),
							resultSet.getString("area_name"),
							resultSet.getString("city"),
							resultSet.getString("district"),
							resultSet.getString("address"),
							resultSet.getString("property_contact"));
					out.writeObject(QueryResult.Success);
					out.writeObject(res);
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
			out.writeObject(QueryResult.Error);
			System.out.println(e.getMessage());
		}
	} catch (Exception e)
	{
		System.out.println(e.getMessage());
	}
}
public void searchCitiesByProvince(Socket socket)
{
	try{
		String province=(String)in.readObject();
		ResultSet resultSet=null;
		Statement stat=null;
		try {
			stat=conn.createStatement();
			String sql=
					"select * from parking_lease.city where province=\'"+province+";";
			resultSet=stat.executeQuery(sql);
			System.out.println(sql);
			int num=0;							//??????????????
			
				while (resultSet.next())
				{
					String res=resultSet.getString("city");
					out.writeObject(QueryResult.Success);
					out.writeObject(res);
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

public void searchDistrictsByCity(Socket socket)
{
	try{
		String city=(String)in.readObject();
		ResultSet resultSet=null;
		Statement stat=null;
		try {
			stat=conn.createStatement();
			String sql=
					"select * from parking_lease.district where city=\'"+city+";";
			resultSet=stat.executeQuery(sql);
			System.out.println(sql);
			int num=0;							//??????????????
			
				while (resultSet.next())
				{
					String res=resultSet.getString("district");
					out.writeObject(QueryResult.Success);
					out.writeObject(res);
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


private void getSpotByUser(Socket socket)
{
	try{
		int userid=in.readInt();
		ResultSet resultSet=null;
		Statement stat=null;
		try {
			stat=conn.createStatement();
			String sql=
					"select * from parking_lease.spot where user_id="+userid+";";
			resultSet=stat.executeQuery(sql);
			System.out.println(sql);
			int num=0;							//??????????????
			
				while (resultSet.next())
				{
					Spot res=new Spot(userid,
							resultSet.getInt("area_id"),
							resultSet.getString("spot_local_ref"),
							resultSet.getString("description"),
							resultSet.getString("category"),
							resultSet.getString("size"));
					out.writeObject(QueryResult.Success);
					out.writeObject(res);
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


private void searchSpotByArea(Socket socket)
{
	try{
		int areaid=in.readInt();
		ResultSet resultSet=null;
		Statement stat=null;
		try {
			stat=conn.createStatement();
			String sql=
					"select * from parking_lease.spot where area_id="+areaid+";";
			resultSet=stat.executeQuery(sql);
			System.out.println(sql);
			int num=0;							//??????????????
			
				while (resultSet.next())
				{
					Spot res=new Spot(resultSet.getInt("user_id"),
							areaid,
							resultSet.getString("spot_local_ref"),
							resultSet.getString("description"),
							resultSet.getString("category"),
							resultSet.getString("size"));
					out.writeObject(QueryResult.Success);
					out.writeObject(res);
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
/*
private void InsertSpot(Socket socket)
{
	Spot spot=null;
	//???account
	try {
		spot=(Spot)in.readObject();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//???????????????
	try {
    	if (getUser(user.cellphone)!=null)
    	{
    		out.writeObject("Existing cellphone");
    		return;
    	}
    	ResultSet resultSet=null;
		Statement stat=null;
		stat=conn.createStatement();
		//??????????????
		String sql=
				"select * from parking_lease.user where identity_number=\'"+user.identity_number+"\';";
		resultSet=stat.executeQuery(sql);
		if (resultSet.next())
		{
			out.writeObject("Existing identity_number");
			resultSet.close();
			stat.close();
    		return;
		}
		//?????????????????
		sql=
				"insert parking_lease.user(nickname,identity_number,cellphone,password,realname) values("+
					"\'"+user.nickname+"\'"+","+
					"\'"+user.identity_number+"\'"+","+
					"\'"+user.cellphone+"\'"+","+
					"\'"+user.password+"\'"+","+
					"\'"+user.realname+"\'"+
						");";
		System.out.println(sql);
		System.out.flush();
		if (!stat.execute(sql))
		{
			out.writeObject("Success");
			resultSet.close();
			stat.close();
    		return;
		}
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.print(e.getMessage());
	}
}
*/
public static void main(String argv[])
{
	SpotHandler handler=new SpotHandler();
	handler.start();
}
}
