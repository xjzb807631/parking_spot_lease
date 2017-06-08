package server;
import data.*;
import java.sql.*;
import java.io.*;
public class SQLConnection {
	private static Connection connection=null;
	private static final String DRIVER = "com.mysql.jdbc.Driver";  
	public static String MYSQLPORT="3306";
    /** 
     * 连接字符串 
     */  
    public static String URLSTR = "jdbc:mysql://localhost:";  
  
    /** 
     * 用户名 
     */  
    private static final String USERNAME = "root";  
  
    /** 
     * 密码 
     */  
    private static final String USERPASSWORD = "123456";  
    static {  
        try {  
            // 加载数据库驱动程序  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
            System.out.println("加载驱动错误");  
            System.out.println(e.getMessage());  
        }  
    }
	public static Connection getConnection() {  
        try {  
            // 获取连接  
            connection = DriverManager.getConnection(URLSTR+MYSQLPORT+"/", USERNAME,  
                    USERPASSWORD);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return connection;  
    }
//	public static void execute(String sql,ObjectInputStream in,ObjectOutputStream out)
//	{
//		
//	}
	
}
