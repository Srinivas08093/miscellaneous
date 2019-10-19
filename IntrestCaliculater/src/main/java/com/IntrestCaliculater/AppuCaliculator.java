package com.IntrestCaliculater;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;;

public class AppuCaliculator {

	private static final String FILE_NAME = "C:\\Users\\dell\\Desktop\\APPU\\Appu.xlsx";

	public static void main(String[] args) {
		List<String> excelHeadings=Arrays.asList("Person Name","Principle Amount","Intrest Rate","Date Of Money Taken","Caliculated Intrest","Total Amount");
	
		String fileAppend=new SimpleDateFormat("MM-dd-yyy").format(new Date())+Math.random();
		String fileFolderName="C:\\Users\\dell\\Desktop\\APPU\\";
		String fileExtenction=".xlsx";
		String fileName="TotalDue";
		writeDataToExcel(addIntrestAndTotalAmount(getAppuList(FILE_NAME)),excelHeadings,String.format("%s%s%s%s", fileFolderName,fileName,fileAppend,fileExtenction));
	}

	public static List<Appu> addIntrestAndTotalAmount(List<Appu> appus) {

		return appus.stream().map(a -> {
			a.setCaliculatedIntrest(simpleIntrest(a));
			a.setTotalAmount(a.getPrincipleAmount() + a.getCaliculatedIntrest());
			return a;
		}).collect(toList());

	}

	public static List<Appu> getAppuList(String filePath) {
		List<Appu> appus = new ArrayList<>();

		try (FileInputStream excelFile = new FileInputStream(new File(filePath));
				Workbook workbook = new XSSFWorkbook(excelFile)) {

			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			boolean skipFirstRow = true;
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				Iterator<Cell> cellIterator = currentRow.iterator();
				int i = 0;
				Appu appu = new Appu();
				dd: while (cellIterator.hasNext()) {

					if (skipFirstRow) {
						skipFirstRow = false;
						break dd;
					}

					Cell currentCell = cellIterator.next();
					if (i == 0) {
						appu.setPersonName(currentCell.getStringCellValue());
					} else if (i == 1) {
						appu.setPrincipleAmount(currentCell.getNumericCellValue());
					} else if (i == 2) {
						appu.setIntrestRate(currentCell.getNumericCellValue());
					} else if (i == 3) {
						DataFormatter dataFormatter = new DataFormatter();
						appu.setDateOfMoneyTaken(new SimpleDateFormat("MM/dd/yy")
								.parse(dataFormatter.formatCellValue(currentCell).trim()));
					}
					i++;

				}
				appus.add(appu);
			}
			appus.remove(0);
			return appus;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static double simpleIntrest(Appu appu) {
		return (appu.getPrincipleAmount() * appu.getIntrestRate() * noOfMonths(appu.getDateOfMoneyTaken())) / 100;
	}

	private static int noOfMonths(Date date) {
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(date);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(new Date());

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		return diffMonth;
	}
	
	private static void writeDataToExcel(List<Appu> appus,List<String> headings,String destinationFIleName) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Appu");
	        
	        int rowNum = 0;
	        System.out.println("Creating excel");
            
	        Row headingRow = sheet.createRow(rowNum++);
	        int colNum1= 0;
	        for(String heading:headings) {
	            headingRow.createCell(colNum1++).setCellValue(heading);
            }
	        
	       for(Appu appu:appus) {
	    	   Row remainingRows = sheet.createRow(rowNum++);
	    	   int colNum = 0;
	    	   remainingRows.createCell(colNum++).setCellValue(anyThingToString(appu.getPersonName()));
	    	   remainingRows.createCell(colNum++).setCellValue(anyThingToString(appu.getPrincipleAmount()));
	    	   remainingRows.createCell(colNum++).setCellValue(anyThingToString(appu.getIntrestRate()));
	    	   remainingRows.createCell(colNum++).setCellValue(appu.getDateOfMoneTakenInString());
	    	   remainingRows.createCell(colNum++).setCellValue(anyThingToString(appu.getCaliculatedIntrest()));
	    	   remainingRows.createCell(colNum++).setCellValue(anyThingToString(appu.getTotalAmount()));
	    	   
	       }
	       Row lastRow=    sheet.createRow(rowNum++);
	       int column = 0;
	 
	       lastRow.createCell(column++).setCellValue(anyThingToString("--"));
	       lastRow.createCell(column++).setCellValue(anyThingToString("--"));
	       lastRow.createCell(column++).setCellValue(anyThingToString("--"));
	       lastRow.createCell(column++).setCellValue(anyThingToString("--"));

	       lastRow.createCell(column++).setCellValue(anyThingToString(appus.stream().map(a->a.getCaliculatedIntrest()).reduce(0.0,Double::sum)));
	       lastRow.createCell(column++).setCellValue(anyThingToString(appus.stream().map(a->a.getTotalAmount()).reduce(0.0,Double::sum)));
	 
	 
	 
	       autoSizeColumns(workbook);

	        try {
	        	
	            FileOutputStream outputStream = new FileOutputStream(destinationFIleName);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 

	        System.out.println("Done");
	    
	}
	private static String anyThingToString(Object data) {
		return data.toString();
	}
	public static void  autoSizeColumns(Workbook workbook) {
	    int numberOfSheets = workbook.getNumberOfSheets();
	    for (int i = 0; i < numberOfSheets; i++) {
	        Sheet sheet = workbook.getSheetAt(i);
	        if (sheet.getPhysicalNumberOfRows() > 0) {
	            Row row = sheet.getRow(sheet.getFirstRowNum());
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                int columnIndex = cell.getColumnIndex();
	                sheet.autoSizeColumn(columnIndex);
	            }
	        }
	    }
	}
}