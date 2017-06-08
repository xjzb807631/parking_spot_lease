package client;
import data.*;

import java.net.*;
import java.io.*;
public class ClientAccountHandler {
	User user;
	/*
	 * 向服务断请求登录*/
public NetworkResult login(Account account){
	Socket socket=null;
	NetworkResult networkResult;
	//先发送login请求，然后立即发送数据
	try {
		socket=new Socket(InetAddress.getLocalHost(),server.AccountHandler.port);
		ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
		os.writeObject(AccountRequest.Login);
		os.writeObject(account);
		ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		if ((QueryResult)is.readObject()!=QueryResult.Success)
		{
			networkResult=NetworkResult.DataError;
		}
		else
		{
			user=(User)is.readObject();
			networkResult= NetworkResult.Success;
		}
		is.close();
		os.close();
		socket.close();
	} catch (Exception e) {
		networkResult=NetworkResult.ConnectionError;
	}
	return networkResult;
}
/*注册新账号
 * Precondition:user对象所有字段信息齐全正确*/
public NetworkResult register(User user,StringBuffer result)
{
	Socket socket=null;
	NetworkResult networkResult=NetworkResult.Success;
	//先发送login请求，然后立即发送数据
	try {
		socket=new Socket(InetAddress.getLocalHost(),server.AccountHandler.port);
		ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
		os.writeObject(AccountRequest.Register);
		os.writeObject(user);
		ObjectInputStream is=new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		result.append((String)is.readObject());
		System.out.println(result);
		is.close();
		os.close();
		socket.close();
	}
	catch (Exception e) {
		networkResult=NetworkResult.ConnectionError;
	}
	return networkResult;
}
public static void main(String argv[])
{
	User user_test=new User(new Account("1801607086",null,"bjgbjgbjg"));
	user_test.identity_number="1111";
	user_test.nickname="nick";
	user_test.realname="wxt";
	ClientAccountHandler testHandler=new ClientAccountHandler();
	StringBuffer resultString=new StringBuffer();
	NetworkResult res=testHandler.register(user_test,resultString);
	System.out.println(res);
	System.out.println(resultString);
}
}
