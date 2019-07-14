package function;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import domain.WorkDetail;

public class WorkSum {

	public static void main(String[] args) {

		try {

			long start = System.currentTimeMillis();
			String tempMidTabName = "员工工作量_记录_勿删";
			// 第一步，地区所有的工作量按照工作类型插入到中间表
			makeTempTableFromDetail(tempMidTabName);
			System.out.println("中间表处理完成");

			// 第二部，按照姓名分组，以开始时间和结束时间为条件分组查询生成新的查询统计表
			// 如果要统计全部时间，startTime，endTime 设置为空字符串
			String startTime = "";
			String endTime = "";

			sumByDate(tempMidTabName, startTime, endTime);

			System.out.println("总耗时：" + (System.currentTimeMillis() - start));

		} catch (Exception se) {
			se.printStackTrace();
		}
		System.out.println("Goodbye!");
	}

	/**
	 * 从工作明细中读取数据生成中间表
	 * 
	 * @param tempMidTabName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void makeTempTableFromDetail(String tempMidTabName) throws ClassNotFoundException, SQLException {
		Object[] rets = Common.getAllWorkDetail();
		ArrayList<WorkDetail> allworks = (ArrayList<WorkDetail>) rets[0];
		Connection con = Common.getNewConnection();
		Statement smt = con.createStatement();
		System.out.println("生成中间表");
		smt.execute("truncate table " + tempMidTabName + ";");
		for (WorkDetail workDetail : allworks) {

			String[] types = workDetail.workType.split("、");
			String sql = "insert into " + tempMidTabName + "(姓名,日期,";
			String value = "";

			try {
				if (null == types || workDetail.workType.trim().length() == 0) {
					continue;
				}
				for (int i = 0, isize = types.length; i < isize; i++) {
					String type = types[i];
					if (i == isize - 1) {
						value += "1";
						sql += type;
					} else {
						value += "1,";
						sql += type + ",";
					}
				}

				sql += ") values('" + workDetail.employee + "','" + workDetail.completeTime + "'," + value + ")";
				smt.execute(sql);

			} catch (Exception e) {
				System.out.println(workDetail.workType);
				System.out.println(sql);
			}

		}
	}

	/**
	 * 按日期统计出每个人的工作结果
	 * 
	 * @param tempMidTabName
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void sumByDate(String tempMidTabName, String startTime, String endTime)
			throws SQLException, ClassNotFoundException {
		Set<String> excludeSet = new HashSet<>();
		excludeSet.add("姓名");
		excludeSet.add("日期");

		ArrayList<String> tabCols = Common.getTabCul(tempMidTabName, excludeSet);

		String tempTabName = "_员工工作量统计";
		if (null != startTime && null != endTime) {
			tempTabName = startTime.replace("-", "_") + "_to_" + endTime.replace("-", "_") + "_" + tempTabName;
		}

		Common.getNewConnection().createStatement().execute("drop table if exists " + tempTabName);

		String sumSql = "create table " + tempTabName + " as select 姓名,";

		for (int i = 0, isize = tabCols.size(); i < isize; i++) {
			if (i == (isize - 1)) {
				sumSql += "count(" + tabCols.get(i) + ") as " + tabCols.get(i);
			} else {
				sumSql += "count(" + tabCols.get(i) + ") as " + tabCols.get(i) + ",";
			}
		}
		sumSql += " from 员工工作量_记录_勿删 ";

		if (startTime.length() > 0 && endTime.length() > 0) {
			sumSql += " where 日期 >= '" + startTime + "' and 日期 <= '" + endTime + "' ";
		}
		sumSql += "group by 姓名";

		System.out.println(sumSql);
		Common.getNewConnection().createStatement().execute(sumSql);
	}

}
