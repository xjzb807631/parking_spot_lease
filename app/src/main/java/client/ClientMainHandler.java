package client;
import android.util.Pair;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import server.SpotHandler;
import data.*;
public class ClientMainHandler {
	public NetworkResult createOffer(Order offer,StringBuffer result)
	{
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.MainHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(MainRequest.CreateOffer);
			os.writeObject(offer);
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
	
	public NetworkResult removeOrderById(int id,StringBuffer result){
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.MainHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(MainRequest.RemoveOrderById);
			os.writeInt(id);
			os.flush();
			ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			result.append((String)is.readObject());
			is.close();
			os.close();
			socket.close();
		}
		catch (Exception e) {
			networkResult=NetworkResult.ConnectionError;
			System.out.println(e.getMessage());
		}
		return networkResult;
	}
	
	public NetworkResult createProposal(Order proposal,StringBuffer result)
	{
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.MainHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(MainRequest.createProposal);
			os.writeObject(proposal);
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
	
	public NetworkResult searchAvailableSpot(int areaid, Timestamp from, Timestamp to, List<Pair<Spot,Order>> res, StringBuffer result)
	{
		res.clear();
		Socket socket=null;
		NetworkResult networkResult=NetworkResult.Success;
		try {
			socket=new Socket(InetAddress.getLocalHost(),server.SpotHandler.port);
			ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(MainRequest.SearchAvailableSpotByArea);
			os.writeInt(areaid);
			os.writeObject(from);
			os.writeObject(to);
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
				Spot spot=(Spot)is.readObject();
				Order order=(Order)is.readObject();
				res.add(new Pair<Spot,Order>(spot,order));
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
	
	private static void createOfferTest(){
		@SuppressWarnings("deprecation")
		Order offer1=new Order(new Timestamp(171,6,24,3,5,5,24),new Timestamp(171,6,24,3,5,8,28),1,0);
		ClientMainHandler clientMainHandler=new ClientMainHandler();
		StringBuffer result=new StringBuffer();
		NetworkResult networkResult=clientMainHandler.createOffer(offer1,result);
		System.out.println(networkResult);
		System.out.println(result);
	}
	private static void removeOrderByIdTest(){
		@SuppressWarnings("deprecation")
		ClientMainHandler clientMainHandler=new ClientMainHandler();
		StringBuffer result=new StringBuffer();
		NetworkResult networkResult=clientMainHandler.removeOrderById(11,result);
		System.out.println(networkResult);
		System.out.println(result);
	}
	private static void createProposalTest(){
		@SuppressWarnings("deprecation")
		Order proposal1=new Order(10003,new Timestamp(171,6,24,3,5,5,24),new Timestamp(171,6,24,3,5,9,0),1,0,2);
		ClientMainHandler clientMainHandler=new ClientMainHandler();
		StringBuffer result=new StringBuffer();
		NetworkResult networkResult=clientMainHandler.createProposal(proposal1, result);
		System.out.println(networkResult);
		System.out.println(result);
	}
	private static void searchDistrictsByCityTest()
	{
		ClientMainHandler clientMainHandler=new ClientMainHandler();
		StringBuffer result=new StringBuffer();
		List<Pair<Spot,Order>> res=new ArrayList<Pair<Spot,Order>>();
		NetworkResult networkResult=clientMainHandler.searchAvailableSpot(10003,new Timestamp(171,6,24,3,5,5,24),new Timestamp(171,6,24,3,5,9,0),res,result);
		System.out.println(result);
		System.out.println(res.get(0));
	}
	public static void main(String argv[])
	{
		createProposalTest();
	}
}
