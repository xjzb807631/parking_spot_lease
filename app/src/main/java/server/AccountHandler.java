package server;
import java.net.*;
import java.io.*;
import java.sql.*;

import data.*;
public class AccountHandler {
	public static int port=7777;
	ServerSocket s_socket=null;
	ObjectInputStream in;
	ObjectOutputStream out;
	static Connection conn;
	public void start()
	{
		//开新线程用来监听用户发来的账号有关信息
		new Thread(new Runnable() {  
            public void run() {
            	try {
            		conn=SQLConnection.getConnection();
        			s_socket=new ServerSocket(port);
        			while (true)
        			{
        				Socket socket=s_socket.accept();
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
	            	AccountRequest request=null;
	            	try {
	        			request=(AccountRequest)in.readObject();
	        		} catch (ClassNotFoundException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	if (request!=null&&request.equals(AccountRequest.Login))
	            	{
	            		login(socket);
	            	}
	            	if (request!=null&&request.equals(AccountRequest.Register))
	            	{
	            		register(socket);
	            	}
            	} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        }).start();
	}
	//通过sql，以account查询user
	
	
	private void login(Socket socket)
	{
		Account account_rec=null;
    	//得到account
    	try {
			account_rec=(Account)in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//查询数据库
    	System.out.print(account_rec.cellphone);
    	User user=getUser(account_rec.cellphone);
    	try {
	    	if (user!=null)
	    	{
	    		if (!user.password.equals(account_rec.password))
	    		{
	    			out.writeObject(QueryResult.IncorrectPassword);
	    		}
	    		else
	    		{
		    		out.writeObject(QueryResult.Success);
		    		out.flush();
		    		out.writeObject(user);
		    		out.flush();
	    		}
	    	}
	    	else
	    	{
	    		out.writeObject(QueryResult.NotFound);
	    		out.flush();
	    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void register(Socket socket)
	{
		User user=null;
    	//得到account
    	try {
			user=(User)in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//查手机号是否已存在
    	try {
	    	if (getUser(user.cellphone)!=null)
	    	{
	    		out.writeObject("Existing cellphone");
	    		return;
	    	}
	    	ResultSet resultSet=null;
			Statement stat=null;
			stat=conn.createStatement();
			//查身份证号是否重复
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
			//用户信息合法，插入记录
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
	private static User getUser(String cellphone)
	{
		ResultSet resultSet=null;
		Statement stat=null;
		User res=new User();
		try {
			stat=conn.createStatement();
			String sql=
					"select * from parking_lease.user where cellphone=\'"+cellphone+"\';";
			resultSet=stat.executeQuery(sql);
			if (!resultSet.next())
			{
				System.out.print("notFound");
				return null;
			}
			else
			{
				System.out.print("Found");
				res.cellphone=cellphone;
				res.password=resultSet.getString("password");
				res.identity_number=resultSet.getString("identity_number");
				res.nickname=resultSet.getString("nickname");
				res.realname=resultSet.getString("realname");
				res.wechat=resultSet.getString("wechat");
			}
			resultSet.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			System.out.println(e.getMessage());
		}
		return res;
	}
	public static void main(String argv[])
	{
		AccountHandler handler=new AccountHandler();
		handler.start();
	}
}
