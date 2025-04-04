package com.justdial.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utils {

	private Workbook workbook = null;
	private Sheet sheet;

	// Read Data Using For Loop
	public void readDataFromExcel(String SheetName) throws IOException {

		String excelFilePath = System.getProperty("user.dir") + "\\DataFiles\\DistrictNameForGoogleSearch.xlsx";
		File excelFile = new File(excelFilePath);
		System.out.println(excelFile);
		if (!excelFile.exists()) {
			System.err.println("File does not exist at the specified path: " + excelFilePath);
			return; // Exit the program if the file does not exist
		}
		try {

			FileInputStream fis = new FileInputStream(excelFile);
			String extention = excelFilePath.substring(excelFilePath.lastIndexOf(".") + 1);
System.out.println(extention);
			// for access excelworkbook
			if (extention.equals("xlsx")) {
System.out.println("76");
				workbook = new XSSFWorkbook(fis);
			} else if (extention.equals("xlx")) {

				workbook = new HSSFWorkbook(fis);
			}

			// Get no.of sheets present in workbook
			int sheetsCount = workbook.getNumberOfSheets();
			System.out.println("Count of Sheets : " + sheetsCount);

			for (int i = 0; i <= sheetsCount; i++) {

				if (workbook.getSheetName(i).equalsIgnoreCase(SheetName)) {
					sheet = workbook.getSheetAt(i);
					break;
				}
			}

			// Get Count of Total rows & Colums in sheet
			int totalRows = sheet.getPhysicalNumberOfRows();
			System.out.println(" totalRows : " + totalRows);
			
			int totalCols = sheet.getRow(0).getLastCellNum();
			System.out.println("totalCols : " + totalCols);
			
			for (int r = 0; r <= totalRows; r++) {
				Row row = sheet.getRow(r);

				DecimalFormat df = new DecimalFormat("#"); // Decimal format for integers
				for (int c = 0; c < totalCols; c++) {
					Cell cell = row.getCell(c);

					CellType cellType = cell.getCellType();
					switch (cellType) {
					case STRING:
						System.out.print(cell.getStringCellValue());
						break;
					case NUMERIC:
						if (cell.getNumericCellValue() == (int) cell.getNumericCellValue()) {
							System.out.print(df.format(cell.getNumericCellValue()));
						} else {
							System.out.print(cell.getNumericCellValue());
						}
						break;
					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue());
						break;
					case BLANK:
						System.out.print(" ");
						break;
					default:
						System.out.print("Unknown Cell Type");

					}
					System.out.print(" | ");
				}
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
		workbook.close();
	}

	
	
	// Read Data by iterator
	public void readDataFromExcelByIterator(String SheetName) throws IOException {
		String excelFilePath = System.getProperty("user.dir") + "\\DataFiles\\DistrictNameForGoogleSearch.xlsx";

		File excelFile = new File(excelFilePath);

		if (!excelFile.exists()) {
			System.err.println("File does not exist at the specified path: " + excelFilePath);
			return; // Exit the program if the file does not exist
		}
		FileInputStream fis = new FileInputStream(excelFile);
		String extention = excelFilePath.substring(excelFilePath.lastIndexOf(".") + 1);

		// for access excelworkbook
		if (extention.equals("xlsx")) {

			workbook = new XSSFWorkbook(fis);
		} else if (extention.equals("xlx")) {

			workbook = new HSSFWorkbook(fis);
		}

		// Get no.of sheets present in workbook
		int sheetsCount = workbook.getNumberOfSheets();
		System.out.println("Count of Sheets : " + sheetsCount);

		for (int i = 0; i <= sheetsCount; i++) {

			if (workbook.getSheetName(i).equalsIgnoreCase(SheetName)) {
				sheet = workbook.getSheetAt(i);
				break;
			}
		}

		// Get Sheets Rows
		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			// For Iterate to Each row
			Row row = rows.next();

			Iterator<Cell> cells = row.iterator();
			while (cells.hasNext()) {
				DecimalFormat df = new DecimalFormat("#"); // Decimal format for integers
				Cell cell = cells.next();
				CellType cellType = cell.getCellType();
				switch (cellType) {
				case STRING:
					System.out.print(cell.getStringCellValue());
					break;
				case NUMERIC:
					if (cell.getNumericCellValue() == (int) cell.getNumericCellValue()) {
						System.out.print(df.format(cell.getNumericCellValue()));
					} else {
						System.out.print(cell.getNumericCellValue());
					}
					break;
				case BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					break;
				case BLANK:
					System.out.print(" ");
					break;
				default:
					System.out.print("Unknown Cell Type");

				}
				System.out.print(" | ");
			}
			System.out.println();
		}
	}
	
	
	public void readDataByColumnName()
	{
		
	}
	
	
	public void wrightDataInExcel() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("GoogleSearchData");

		Object[][] data = { { "Name", "Location", "Experience" }, 
				{ "Pratiksha", "Dharashiv", "2" },
				{ "Ganesha", "Shiv", "100" }, 
				{ "Harish", "", "" } };

		// Total no. of Rows
		int rows = data.length;
		// Total No. of Columns
		int columns = data[0].length;
      
         XSSFCellStyle styleTopRow=workbook.createCellStyle();
         XSSFFont font = workbook.createFont();
         font.setBold(true);
         font.setColor(IndexedColors.WHITE.getIndex());
         styleTopRow.setFont(font);
         styleTopRow.setFillForegroundColor(IndexedColors.BLUE.getIndex());
         styleTopRow.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         styleTopRow.setBorderLeft(BorderStyle.THIN);
         styleTopRow.setBorderRight(BorderStyle.THIN);
         styleTopRow.setBorderTop(BorderStyle.THIN);
         styleTopRow.setBorderBottom(BorderStyle.THIN);
         
         XSSFCellStyle styleRow=workbook.createCellStyle();
         styleRow.setBorderLeft(BorderStyle.THIN);
         styleRow.setBorderRight(BorderStyle.THIN);
         styleRow.setBorderTop(BorderStyle.THIN);
         styleRow.setBorderBottom(BorderStyle.THIN);
         
		for (int r = 0; r < rows; r++) {
			XSSFRow row = sheet.createRow(r);

			for (int c = 0; c < columns; c++) {
				XSSFCell cell = row.createCell(c);
				Object cellValue = data[r][c];

				if (cellValue instanceof String) {
					cell.setCellValue((String) cellValue);

				} else if (cellValue instanceof Integer) {
					cell.setCellValue((Integer) cellValue);

				} else if (cellValue instanceof Boolean) {

					cell.setCellValue((Boolean) cellValue);
				} else if (cellValue == null) {
					cell.setCellValue("");
				}
				if(r==0)
				{
				cell.setCellStyle(styleTopRow);
				}else
				{
					cell.setCellStyle(styleRow);	
				}
			}
			

		}

		File file = new File(System.getProperty("user.dir") + "\\DataFiles\\JustDialDataFromGoogleSearch.xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		workbook.close();
	}
}
