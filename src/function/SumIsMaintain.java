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
			Object[] rets = Common.getAllWorkDetail();
			ArrayList<WorkDetail> allworks = (ArrayList<WorkDetail>) rets[0];
			Set<String> bids = (Set<String>) rets[1];

//			for (WorkDetail workDetail : allworks) {
//				System.out.println(workDetail.completeTime);
//			}

			String sql = "select 商户号 from 2019年度工作任务";
			ResultSet rs = Common.getResultWithSql(sql);
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