package client;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import data.*;

public class ClientSpotHandler {
	public NetworkResult insertArea(Area area,StringBuffer result)
	{
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.InsertArea);
			os.writeObject(area);
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			result.append((String)is.readObject());
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	public NetworkResult insertSpot(Spot spot,StringBuffer result)
	{
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.InsertSpot);
			os.writeObject(spot);
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			result.append((String)is.readObject());
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	public NetworkResult searchAreaByDistrict(String city,String district,int start,List<Area> areas,StringBuffer result)
	{
		areas.clear();
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.SearchAreaByDistrict);
			os.writeObject(city);
			os.writeObject(district);
			os.writeInt(start);
			os.flush();
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			QueryResult queryResult=(QueryResult)is.readObject();
			if (queryResult.equals(QueryResult.NotFound))
			{
				networkResult=NetworkResult.NotFound;
				is.close();
				os.close();
				socket.close();
				return networkResult;
			}
			while (queryResult.equals(QueryResult.Success))
			{
				areas.add((Area)is.readObject());
				queryResult=(QueryResult)is.readObject();
				if (queryResult.equals(QueryResult.Error))
				{
					networkResult=NetworkResult.DataError;
					is.close();
					os.close();
					socket.close();
					return networkResult;
				}
				if (queryResult.equals(QueryResult.End))
				{
					break;
				}
			}
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	public NetworkResult searchCitiesByProvince(String province,List<String> cities,StringBuffer result)
	{
		cities.clear();
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.SearchCitiesByProvince);
			os.writeObject(province);
			os.flush();
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			QueryResult queryResult=(QueryResult)is.readObject();
			if (queryResult.equals(QueryResult.NotFound))
			{
				networkResult=NetworkResult.NotFound;
				is.close();
				os.close();
				socket.close();
				return networkResult;
			}
			while (queryResult.equals(QueryResult.Success))
			{
				cities.add((String)is.readObject());
				queryResult=(QueryResult)is.readObject();
				if (queryResult.equals(QueryResult.Error))
				{
					networkResult=NetworkResult.DataError;
					is.close();
					os.close();
					socket.close();
					return networkResult;
				}
				if (queryResult.equals(QueryResult.End))
				{
					break;
				}
			}
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	
	public NetworkResult searchDistrictsByCity(String city,List<String> districts,StringBuffer result)
	{
		districts.clear();
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.SearchDistrictsByCity);
			os.writeObject(city);
			os.flush();
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			QueryResult queryResult=(QueryResult)is.readObject();
			if (queryResult.equals(QueryResult.NotFound))
			{
				networkResult=NetworkResult.NotFound;
				is.close();
				os.close();
				socket.close();
				return networkResult;
			}
			while (queryResult.equals(QueryResult.Success))
			{
				districts.add((String)is.readObject());
				queryResult=(QueryResult)is.readObject();
				if (queryResult.equals(QueryResult.Error))
				{
					networkResult=NetworkResult.DataError;
					is.close();
					os.close();
					socket.close();
					return networkResult;
				}
				if (queryResult.equals(QueryResult.End))
				{
					break;
				}
			}
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	
	public NetworkResult searchSpotByAreaid(int areaid,List<Spot> spots,StringBuffer result)
	{
		spots.clear();
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.SearchSpotByArea);
			os.writeInt(areaid);
			os.flush();
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			QueryResult queryResult=(QueryResult)is.readObject();
			if (queryResult.equals(QueryResult.NotFound))
			{
				networkResult=NetworkResult.NotFound;
				is.close();
				os.close();
				socket.close();
				result.append("Not Found");
				return networkResult;
			}
			while (queryResult.equals(QueryResult.Success))
			{
				spots.add((Spot)is.readObject());
				queryResult=(QueryResult)is.readObject();
				if (queryResult.equals(QueryResult.Error))
				{
					networkResult=NetworkResult.DataError;
					is.close();
					os.close();
					socket.close();
					return networkResult;
				}
				if (queryResult.equals(QueryResult.End))
				{
					break;
				}
			}
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	public NetworkResult GetSpotByUser(int userid,List<Spot> spots,StringBuffer result)
	{
		spots.clear();
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		//先发送login请求，然后立即发送数据
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(SpotRequest.GetSpotByUser);
			os.writeInt(userid);
			os.flush();
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			QueryResult queryResult=(QueryResult)is.readObject();
			if (queryResult.equals(QueryResult.NotFound))
			{
				networkResult=NetworkResult.NotFound;
				is.close();
				os.close();
				socket.close();
				result.append("Not Found");
				return networkResult;
			}
			while (queryResult.equals(QueryResult.Success))
			{
				spots.add((Spot)is.readObject());
				queryResult=(QueryResult)is.readObject();
				if (queryResult.equals(QueryResult.Error))
				{
					networkResult=NetworkResult.DataError;
					is.close();
					os.close();
					socket.close();
					return networkResult;
				}
				if (queryResult.equals(QueryResult.End))
				{
					break;
				}
			}
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
		}
		return networkResult;
	}
	public static void insertSpotTest()
	{
		Spot spot=new Spot(10003,1,"101","no description","outside","large");
		ClientSpotHandler clientSpotHandler=new ClientSpotHandler();
		StringBuffer result=new StringBuffer();
		NetworkResult networkResult=clientSpotHandler.insertSpot(spot,result);
		System.out.println(networkResult);
		System.out.println(result);
	}
	public static void getSpotsByUserTest()
	{
		ClientSpotHandler clientSpotHandler=new ClientSpotHandler();
		StringBuffer result=new StringBuffer();
		List<Spot> spots=new ArrayList<Spot>();
		NetworkResult networkResult=clientSpotHandler.GetSpotByUser(10001, spots, result);
		System.out.println(result);
		System.out.println(spots.get(0).size);
	}
	
	public static void searchSpotsByAreaidTest()
	{
		ClientSpotHandler clientSpotHandler=new ClientSpotHandler();
		StringBuffer result=new StringBuffer();
		List<Spot> spots=new ArrayList<Spot>();
		NetworkResult networkResult=clientSpotHandler.searchSpotByAreaid(1, spots, result);
		System.out.println(result);
		System.out.println(spots.get(0).spot_local_ref);
	}
	
	static void searchCitiesByProvinceTest()
	{
		ClientSpotHandler clientSpotHandler=new ClientSpotHandler();
		StringBuffer result=new StringBuffer();
		List<String> cities=new ArrayList<String>();
		NetworkResult networkResult=clientSpotHandler.searchCitiesByProvince("ss", cities, result);
		System.out.println(result);
		System.out.println(cities.get(0));
	}
	
	static void searchDistrictsByCityTest()
	{
		ClientSpotHandler clientSpotHandler=new ClientSpotHandler();
		StringBuffer result=new StringBuffer();
		List<String> districts=new ArrayList<String>();
		NetworkResult networkResult=clientSpotHandler.searchDistrictsByCity("Shanghai", districts, result);
		System.out.println(result);
		System.out.println(districts.get(1));
	}
	static void insertShanghaiArea()
	{
		String city="上海市";
	}
	public static void main(String argv[])
	{
		searchSpotsByAreaidTest();
	}
}
