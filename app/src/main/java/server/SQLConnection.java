package server;
import data.*;
import java.sql.*;
import java.io.*;
public class SQLConnection {
	private static Connection connection=null;
	private static final String DRIVER = "com.mysql.jdbc.Driver";  
	public static String MYSQLPORT="3306";
    /** 
     * �����ַ��� 
     */  
    public static String URLSTR = "jdbc:mysql://localhost:";  
  
    /** 
     * �û��� 
     */  
    private static final String USERNAME = "root";  
  
    /** 
     * ���� 
     */  
    private static final String USERPASSWORD = "123456";  
    static {  
        try {  
            // �������ݿ���������  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
            System.out.println("������������");  
            System.out.println(e.getMessage());  
        }  
    }
	public static Connection getConnection() {  
        try {  
            // ��ȡ����  
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
