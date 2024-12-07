package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {

	XSSFWorkbook wb;

//	constructor for reading path

	public ExcelFileUtil(String Excelpath) throws Throwable {
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = new XSSFWorkbook(fi);

	}
	// method for counting no of row

	public int rowCount(String sheetname) {
		return wb.getSheet(sheetname).getLastRowNum();

	}

//	read data
	public String getCellData(String sheetName, int row, int column) {
		String data = "";

		if (wb.getSheet(sheetName).getRow(row).getCell(column).getCellType() == CellType.NUMERIC) {
			int Celldata = (int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(Celldata);

		} else {

			data = wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();

		}
		return data;

	}

	// writing date
	public void setCellData(String sheetName, int row, int column, String status, String writeExcel) throws Throwable {

		// get sheet from workbook
		XSSFSheet ws = wb.getSheet(sheetName);
		// get row from sheet
		XSSFRow rowNum = ws.getRow(row);
		// create cell
		XSSFCell cell = rowNum.createCell(column);
		// write status
		cell.setCellValue(status);

		if (status.equalsIgnoreCase("PASS")) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);

		} else if (status.equalsIgnoreCase("FAIL")) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);

		} else if (status.equalsIgnoreCase("Blocked")) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);

		}

		FileOutputStream fo = new FileOutputStream(writeExcel);
		wb.write(fo);

		
	}

}
