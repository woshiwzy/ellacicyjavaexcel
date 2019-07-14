package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import domain.Employee;
import domain.WorkDetail;

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
	
	
	/**
	 * 获取结果集
	 * 
	 * @param sql
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static ResultSet getResultWithSql(String sql) throws ClassNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = Common.getNewConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return null;
	}

	
	public static Object[] getAllWorkDetail() {
		ArrayList<WorkDetail> workDetails = new ArrayList<>();
		Set<String> bids = new HashSet<>();
//		HashMap<String, WorkDetail> workHasMap = new HashMap();
		try {
			String sql = "select 完成时间,工作人员,商户号,任务类型 from 员工工作明细 order by 完成时间 desc ";
			ResultSet rs = getResultWithSql(sql);
			while (rs.next()) {
				// 通过字段检索
				String complteTime = rs.getString("完成时间");
				String employee = rs.getString("工作人员");
				String bid = rs.getString("商户号");
				String workType=rs.getString("任务类型")
						.replace("增装", "新装")
						.replace("其他、", "")
						.replace("程序升级、", "升级");
				
				bids.add(bid);
				WorkDetail workDetail = new WorkDetail(employee, bid, complteTime,workType);
				workDetails.add(workDetail);
				
			}
			// 完成后关闭
			rs.close();
		} catch (Exception se) {
			se.printStackTrace();
		}

		Object[] rets = { workDetails, bids };
		return rets;
	}
	
	
	/**
	 * 获取所有员工
	 * @return
	 */
	public static ArrayList<Employee> getAllEmployee() {
		ArrayList<Employee> workDetails = new ArrayList<>();
		try {
			String sql = "select * from 员工工作量";
			ResultSet rs = getResultWithSql(sql);
			while (rs.next()) {
				workDetails.add(new Employee(rs.getString("姓名"), ""));
			}
			// 完成后关闭
			rs.close();
		} catch (Exception se) {
			se.printStackTrace();
		}
		return workDetails;
	}
	
	/**
	 * 获取表字段
	 * @return
	 */
	public static ArrayList<String> getTabCul(String tableName,Set<String> excludeSet) {
		ArrayList<String> workDetails = new ArrayList<>();
		try {
			String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = '"+tableName+"'";
			System.out.println(sql);
			ResultSet rs = getResultWithSql(sql);
			while (rs.next()) {
				String cname=rs.getString("COLUMN_NAME");
				if(!excludeSet.contains(cname)) {
					workDetails.add(cname);
				}
			}
			// 完成后关闭
			rs.close();
		} catch (Exception se) {
			se.printStackTrace();
		}
		return workDetails;
	}
	
//	select COLUMN_NAME from information_schema.COLUMNS where table_name = '员工工作量_记录' ;  	

}
