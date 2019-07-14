package function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import domain.WorkDetail;

public class SumIsMaintain {

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
			String sql = "select 完成时间,工作人员,商户号 from 员工工作明细 order by 完成时间 desc";
			ResultSet rs = getResultWithSql(sql);
			while (rs.next()) {
				// 通过字段检索
				String complteTime = rs.getString("完成时间");
				String employee = rs.getString("工作人员");
				String bid = rs.getString("商户号");
				bids.add(bid);
				WorkDetail workDetail = new WorkDetail(employee, bid, complteTime);
//				workHasMap.put(bid, workDetail);
				workDetails.add(workDetail);
//				System.out.print("工作人员: " + employee + " 商户号：" + bid + " 完成时间:" + complteTime + " \n");
			}
			// 完成后关闭
			rs.close();
		} catch (Exception se) {
			se.printStackTrace();
		}

		Object[] rets = { workDetails, bids };
		return rets;
	}

	public static WorkDetail getLastWorkDetail(String bid, ArrayList<WorkDetail> details) {
		for (WorkDetail wd : details) {
			if (bid.equals(wd.bid)) {
				return wd;
			}
		}
		return null;
	}

	public static WorkDetail getOldestWorkDetail(String bid, ArrayList<WorkDetail> details) {
		for (int x = details.size() - 1; x >= 0; x--) {
			if (bid.equals(details.get(x).bid)) {
				return details.get(x);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			long start = System.currentTimeMillis();
			Object[] rets = getAllWorkDetail();
			ArrayList<WorkDetail> allworks = (ArrayList<WorkDetail>) rets[0];
			Set<String> bids = (Set<String>) rets[1];

//			for (WorkDetail workDetail : allworks) {
//				System.out.println(workDetail.completeTime);
//			}

			String sql = "select 商户号 from 2019年度工作任务";
			ResultSet rs = getResultWithSql(sql);
			Connection conn = Common.getNewConnection();
			Statement statement = conn.createStatement();
//            // 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				String bid = rs.getString("商户号");
				if (bids.contains(bid)) {
					WorkDetail origin = getOldestWorkDetail(bid, allworks);
					WorkDetail lastet = getLastWorkDetail(bid, allworks);
//					System.out.print(" 商户号：" + bid + " 最新维护时间 " + lastet.completeTime + " 原始维护人：" + origin.employee + "\n");

					String resultSql = "update 2019年度工作任务 set 2019年是否维护='是', 负责员工='" + origin.employee + "',最近维护日期='"
							+ lastet.completeTime + "' where 商户号='" + bid + "'";
//					conn.createStatement().execute(resultSql);
					statement.executeUpdate(resultSql);
//					conn.commit();
				}
			}
			// 完成后关闭
			rs.close();
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start));

		} catch (Exception se) {
			se.printStackTrace();
		}
		System.out.println("Goodbye!");
	}
}