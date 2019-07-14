package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Common {

	
	// MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/Yada";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "wzy2006";
	static final String PASS = "wzy2006";
	
	static Connection conn = null;
	public static Connection getNewConnection() throws ClassNotFoundException {
		if (null != conn) {
			return conn;
		}
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return null;
	}
	

}
