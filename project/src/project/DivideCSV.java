package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DivideCSV {
    public static void main(String args[]) {
        String readFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore.csv";
        String[] writeFileNamePart = { "Beauty", "Parenting", "Action", "Personalization", "Word", "Books & Reference",
                "Entertainment", "Music & Audio", "Board", "House & Home", "Education", "Events", "Lifestyle",
                "Shopping", "Racing", "News & Magazines", "Maps & Navigation", "Dating", "Communication", "Sports",
                "Business", "Art & Design", "Productivity", "Social", "Adventure", "Finance", "Tools",
                "Health & Fitness", "Travel & Local", "Educational", "Casual", "Trivia", "Food & Drink", "Arcade",
                "Card", "Photography", "Weather", "Puzzle", "Simulation", "Casino", "Music", "Libraries & Demo",
                "Strategy", "Medical", "Role Playing", "Comics", "Auto & Vehicles", "Video Players & Editors" };

        // 파일 읽기
        ArrayList<String[]> arr = readFile(readFileName);

        // 파일 나누어 쓰기
        for (String part : writeFileNamePart) {
        	divideCSV(arr, part);
        }
    }

    // 파일 읽어오기
    public static ArrayList<String[]> readFile(String fileName) {
        ArrayList<String[]> arr = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(fileName), "CP949"));

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                arr.add(nextLine);
            }

            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static void divideCSV(ArrayList<String[]> arr, String part) {
        try {
            String writeFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore-" + part + ".csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(writeFileName));
            int count = 0;
            for (String[] data : arr) {
                if (data[2].equals(part)) {
                    csvWriter.writeNext(data);
                    count++;
                }
            }
            csvWriter.close();
            System.out.println(part + " 카테고리 파일 생성(레코드:" + count + ")");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void divideXLSX(ArrayList<String[]> arr, String part) {
        try {
            String writeFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\xlsx\\Google-Playstore-" + part + ".xlsx";
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Sheet1");

            int rowNum = 0;
            for (String[] data : arr) {
                if (data[2].equals(part)) {
                    Row row = sheet.createRow(rowNum++);
                    int colNum = 0;
                    for (String cellData : data) {
                        Cell cell = row.createCell(colNum++);
                        cell.setCellValue(cellData);
                    }
                }
            }

            FileOutputStream fileOut = new FileOutputStream(writeFileName);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println(part + " 카테고리 파일 생성(레코드:" + rowNum + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
