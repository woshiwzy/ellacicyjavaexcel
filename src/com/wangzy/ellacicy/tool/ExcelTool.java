package com.wangzy.ellacicy.tool;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.wangzy.ellacicy.domain.Device;
import com.wangzy.ellacicy.domain.DeviceRecord;
import com.wangzy.ellacicy.domain.WorkHistory;

public class ExcelTool {

	/**
	 * 读取银行提供的所有机具
	 * 
	 * @param srcExcelFile
	 * @return
	 */
	public static ArrayList<Device> readAllDevices(String srcExcelFile) {
		ArrayList<Device> devices = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(srcExcelFile);
			Workbook workBook = WorkbookFactory.create(fis);

			Sheet sheet = workBook.getSheetAt(0);

			int numberOfSheets = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < numberOfSheets; r++) { // 总行

				try {
					Row row = sheet.getRow(r);

					long ternimal = (long) (row.getCell(0).getNumericCellValue());
					long busnessNo = (long) row.getCell(1).getNumericCellValue();
					String partBank = row.getCell(2).getStringCellValue().trim();
					String busnessName = row.getCell(3).getStringCellValue().trim();

					Device device = new Device();

					device.setTerminalNo(ternimal);
					device.setBusnessNo(busnessNo);
					device.setPartBank(partBank);
					device.setBusnessName(busnessName);
					devices.add(device);

				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("错误行:" + r);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices;
	}

	/**
	 * 读取所有的维护记录
	 * 
	 * @param dir
	 * @return
	 */
	public static ArrayList<WorkHistory> readAllWorkHistory(String dir) {
		ArrayList<WorkHistory> workhistories = new ArrayList<>();
		File workDirs = new File(dir);
		if (workDirs.exists() && workDirs.isDirectory()) {

			File[] files = workDirs.listFiles();
			for (File file : files) {
				if (file.getName().startsWith(".~")) {
					continue;
				}

				if (file.getAbsolutePath().endsWith(".xls") || file.getAbsolutePath().endsWith(".xlsx")) {
					try {
						FileInputStream fis = new FileInputStream(file);
						Workbook workBook = WorkbookFactory.create(fis);
						Sheet sheet = workBook.getSheetAt(0);

						int numberOfSheets = sheet.getPhysicalNumberOfRows();

						for (int r = 1; r < numberOfSheets; r++) { // 总行

							try {
								Row row = sheet.getRow(r);

								String name = row.getCell(0).getStringCellValue();
								long terminalNo = (long) row.getCell(1).getNumericCellValue();
								Date date = row.getCell(2).getDateCellValue();
								String worktype = row.getCell(3).getStringCellValue();

								WorkHistory workHistory = new WorkHistory();
								workHistory.setWorkName(name);
								workHistory.setTerminalNo(terminalNo);
								workHistory.setDate(date);
								workHistory.setFileName(file.getAbsolutePath());
								workHistory.setWorkType(worktype);

								workhistories.add(workHistory);

							} catch (Exception e) {
//								e.printStackTrace();
//								System.err.println("错误详情行:" + r);
							}

						}
					} catch (Exception e) {
//						e.printStackTrace();
//						System.err.println("错误文件:" + file.getAbsolutePath());
					}

				}
			}
		}

		return workhistories;
	}

	/**
	 * 读取所有的机具登记表
	 * 
	 * @param srcExcelFile
	 * @return
	 */
	public static ArrayList<DeviceRecord> readAllDeviceRecode(String srcExcelFile) {
		ArrayList<DeviceRecord> deviceRecords = new ArrayList<>();

		try {
			FileInputStream fis = new FileInputStream(srcExcelFile);
			Workbook workBook = WorkbookFactory.create(fis);

			Sheet sheet = workBook.getSheetAt(0);

			int numberOfSheets = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < numberOfSheets; r++) { // 总行

				try {
					Row row = sheet.getRow(r);

					DeviceRecord deviceRecord = new DeviceRecord();

					String manager = row.getCell(0).getStringCellValue().trim();
					String desc = row.getCell(1).getStringCellValue().trim();
					String busnessName = row.getCell(2).getStringCellValue().trim();
					String address = row.getCell(3).getStringCellValue().trim();
					
					
					String contactPeople ="";
					
					try {
						contactPeople=row.getCell(4).getStringCellValue().trim();
					} catch (Exception e) {
						contactPeople=String.valueOf(row.getCell(4).getNumericCellValue());
					}
					

					String contactPhone = "";

					try {
						contactPhone = row.getCell(5).getStringCellValue().trim();
					} catch (Exception e) {
						contactPhone = String.valueOf(row.getCell(5).getNumericCellValue());
					}

					String busnessNo = "";

					try {
						busnessNo = row.getCell(6).getStringCellValue();
					} catch (Exception e) {
						busnessNo = String.valueOf((long)row.getCell(6).getNumericCellValue());
					}

					long terminalNo = (long) row.getCell(7).getNumericCellValue();
					String brand = row.getCell(8).getStringCellValue().trim();
					String model = row.getCell(9).getStringCellValue().trim();
					String host = row.getCell(10).getStringCellValue().trim();

					String keyBoard = "";

					try {
						keyBoard = String.valueOf(row.getCell(11).getNumericCellValue());
					}catch (Exception e) {
						keyBoard = String.valueOf(row.getCell(11).getStringCellValue());
					}
					

					String hostAddrss = "";

					try {
						hostAddrss = row.getCell(12).getStringCellValue().trim();
					} catch (Exception e) {
						e.printStackTrace();
						hostAddrss = String.valueOf(row.getCell(12).getNumericCellValue());
					}

					Date date = row.getCell(13).getDateCellValue();
					String connectType = row.getCell(14).getStringCellValue();
					String partBank = row.getCell(15).getStringCellValue();
					String note = row.getCell(16).getStringCellValue();

					deviceRecord.setManager(manager);
					deviceRecord.setDesc(desc);
					deviceRecord.setBusnessName(busnessName);
					deviceRecord.setAddress(address);
					deviceRecord.setContact(contactPeople);
					deviceRecord.setContactPhone(contactPhone);
					deviceRecord.setBusnessNo(busnessNo.trim());
					deviceRecord.setDeviceNo(terminalNo);
					deviceRecord.setBrand(brand);
					deviceRecord.setModel(model);
					deviceRecord.setHost(host);
					deviceRecord.setKeyborad(keyBoard);
					deviceRecord.setHost(host);
					deviceRecord.setHostAddrss(hostAddrss);
					deviceRecord.setSwitchDate(date);
					deviceRecord.setConnectType(connectType);
					deviceRecord.setPartBank(partBank);
					deviceRecord.setNote(note);

					deviceRecords.add(deviceRecord);

				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("错误行:" + r);
				}

			}
		} catch (Exception e) {
//			e.printStackTrace();
		}

		return deviceRecords;
	}

}
