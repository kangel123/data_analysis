package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class P2 {
	public static void main(String args[]) {
		// �� ī�װ� �̸��� ���� xlsx���� �̸��� �Ϻκ�
		String[] readFileNamePart = { "Beauty", "Parenting"};
		
		// �� ���� �̸�
		String writeFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\xlsx\\Google-Playstore_ver2.xlsx";

		// ��� ���� �о ����		
		ArrayList<String[]> arr = new ArrayList<>();		
		for (String part : readFileNamePart) {
			String readFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\xlsx\\Google-Playstore-" + part + ".xlsx";
			arr = readFile(arr, readFileName);
		}
		
		System.out.println("�� ���ڵ� ���� : "+arr.size());
		// ���� ����
		if(writeFile(arr, writeFileName)) {
			System.out.println("����");
		} else {
			System.out.println("����");
		}
	}

	// ���� �о����
	public static ArrayList<String[]> readFile(ArrayList<String[]> arr, String fileName) {

		try {			
			FileInputStream fis = new FileInputStream(fileName);
			try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
				XSSFSheet sheet = workbook.getSheet("Sheet1");
				for(int row=1;row<sheet.getPhysicalNumberOfRows();row++) {
					XSSFRow rows = sheet.getRow(row);
					if(rows!=null) {
						String value = "";
						int cells = rows.getPhysicalNumberOfCells();
						String[] line = new String[cells+1];
						
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
							line[column]=value;							
						}
						arr.add(line);
					}
				}
				return arr;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arr;
	}

	// ���� ����
    public static boolean writeFile(ArrayList<String[]> arr, String fileName) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet1");
            int rowNum = 0;
            for (String[] data : arr) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String cellData : data) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(cellData);
                }
            }
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
  
	}
}
