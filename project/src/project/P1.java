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
		// �� ī�װ� �̸��� ���� csv���� �̸��� �Ϻκ�
		String[] readFileNamePart = { "Beauty", "Parenting", "Action", "Personalization", "Word", "Books & Reference",
				"Entertainment", "Music & Audio", "Board", "House & Home"};
		
		// �� ���� �̸�
		String writeFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore_ver2.csv";

		// ��� ���� �о ����		
		ArrayList<String[]> arr = new ArrayList<>();		
		for (String part : readFileNamePart) {
			String readFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore-" + part + ".csv";
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

	// ���� ����
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
