package common;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Utils {
	private static XSSFWorkbook excelWorkBook;
	private static XSSFSheet excelWorkSheet;
	private static XSSFCell excelCell;
	private static String excelFilePath;
	
	
	public static void setExcelFile(String path, String sheet) throws Exception {
		excelFile(path, sheet);
		excelFilePath = path;
	}
	
	//Method for setting up Excel File
	public static void excelFile(String excelPath, String sheetName) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(excelPath);
			excelWorkBook = new XSSFWorkbook(ExcelFile);
			excelWorkSheet = excelWorkBook.getSheet(sheetName);
			excelFilePath = excelPath;
		} catch (Exception e) {
			throw (e);
		}
	}
	
	public static String getCellData(int row, int col) throws Exception{
		String cellData = "";
		
		try {
			excelCell = excelWorkSheet.getRow(row).getCell(col);
			excelCell.setCellType(CellType.STRING);
			cellData = excelCell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void setCellData(int row, int col, String value) throws Exception {
		excelCell = excelWorkSheet.getRow(row).getCell(col);
		excelCell.setCellType(CellType.STRING);
		excelCell.setCellValue(value);
		
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		excelWorkBook.write(fos);
	}
	
}
