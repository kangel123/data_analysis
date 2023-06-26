package project;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXlsx {
	public static void main(String args[]) {
		try {
			String file = "C:\\Users\\kjw\\Desktop\\abc.xlsx";
			FileInputStream fis = new FileInputStream(file);
			try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
				XSSFSheet sheet = workbook.getSheet("abc");

				for(int row=1;row<sheet.getPhysicalNumberOfRows();row++) {
					XSSFRow rows = sheet.getRow(row);
					if(rows!=null) {
						String value = "";
						int cells = rows.getPhysicalNumberOfCells();
						System.out.println(cells);
						System.out.println(rows);
						for(int column=0;column<=cells;column++) {
							XSSFCell cell = rows.getCell(column);
							if(cell!=null){
								switch(cell.getCellType()) {
								case NUMERIC:
									value = cell.getNumericCellValue()+"";
									break;
								case STRING:
									value = cell.getStringCellValue()+"";
									break;
								case BLANK:
									value = cell.getBooleanCellValue()+"";
									break;
								case ERROR:
									value = cell.getErrorCellValue()+"";
									break;
								default:
									break;
								}
							}
							System.out.println(value+"");
						}
					}
					System.out.println();
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
