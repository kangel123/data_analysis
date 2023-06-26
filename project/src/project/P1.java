package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class P1 {
	public static void main(String args[]) {
		// 각 카테고리 이름에 따른 csv파일 이름의 일부분
		String[] readFileNamePart = { "Beauty", "Parenting", "Action", "Personalization", "Word", "Books & Reference",
				"Entertainment", "Music & Audio", "Board", "House & Home"};
		
		// 쓸 파일 이름
		String writeFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore_ver2.csv";

		// 모든 파일 읽어서 저장		
		ArrayList<String[]> arr = new ArrayList<>();		
		for (String part : readFileNamePart) {
			String readFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore-" + part + ".csv";
			arr = readFile(arr, readFileName);
		}
		
		System.out.println("총 레코드 개수 : "+arr.size());
		// 파일 쓰기
		if(writeFile(arr, writeFileName)) {
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
	}

	// 파일 읽어오기
	public static ArrayList<String[]> readFile(ArrayList<String[]> arr, String fileName) {

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

	// 파일 쓰기
	public static boolean writeFile(ArrayList<String[]> arr, String fileName) {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));
			for (String[] data : arr) {
				csvWriter.writeNext(data);
			}
			csvWriter.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
