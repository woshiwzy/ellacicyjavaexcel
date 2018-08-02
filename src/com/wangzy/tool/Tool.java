package com.wangzy.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Tool {

	public static void copyFileUsingFileStreams(File source, File dest) throws IOException {

		System.out.println("cp:" + source.getAbsolutePath() + " --->> " + dest.getAbsolutePath());
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

	// 写入，往指定sheet表的单元格
	public static void insertExcel3(Set<String> sets, String absFilePath) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook(); // 读取的文件
		
		XSSFSheet sheet = null;
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		sheet = workbook.createSheet("导入失败的数据汇总_"+sdf.format(new Date()));

		for (String data : sets) {
			XSSFRow row = sheet.getRow(0); // 获取指定的行对象，无数据则为空，需要创建
			if (row == null) {
				row = sheet.createRow(0); // 该行无数据，创建行对象
			}
			Cell cell = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
			cell.setCellValue(data.replace(".mp3", "")); // 设置内容
		}
		FileOutputStream fo = new FileOutputStream(absFilePath); // 输出到文件
		workbook.write(fo);
	}

}
