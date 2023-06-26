package Data03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math4.legacy.stat.correlation.PearsonsCorrelation;

import com.opencsv.CSVReader;

public class Lottery {
	public static void main(String args[]) {
		String readFileName = "C:\\Users\\kjw\\Desktop\\�ζ�����.csv";

//		frequencyNum(readFileName);	// ��� ��ȣ�� �󵵼�
//		frequencyList(readFileName);	// ȸ���� ��÷��ȣ�� ������ �󵵼�, ��ġ�� ��찡 ������
		getCorrelationCoefficient(readFileName);	// �� ȸ���� ���,�ּ�,�ִ��� ������		
	}

	private static void getCorrelationCoefficient(String readFileName) {
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));

			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				return;
			}
			
			double[] averageArr = new double[1073];
			double[] maxArr = new double[1073];
			double[] minArr = new double[1073];
			
			int count=0;
			while ((nextLine = csvReader.readNext()) != null) {
				int x1=Integer.parseInt(nextLine[0]);
				int x2=Integer.parseInt(nextLine[1]);
				int x3=Integer.parseInt(nextLine[2]);
				int x4=Integer.parseInt(nextLine[3]);
				int x5=Integer.parseInt(nextLine[4]);
				int x6=Integer.parseInt(nextLine[5]);
				int x7=Integer.parseInt(nextLine[6]);
				
				double average = (double)(x1+x2+x3+x4+x5+x6+x7)/7;
				int[] arr = {x1, x2, x3, x4, x5, x6, x7 };
				Arrays.sort(arr);

				averageArr[count]= average;
				maxArr[count]= arr[0];
				minArr[count]= arr[6];
				
				count++;
			}
			csvReader.close();

			double correlation1 = new PearsonsCorrelation().correlation(averageArr, maxArr);
			double correlation2 = new PearsonsCorrelation().correlation(averageArr, minArr);
			double correlation3 = new PearsonsCorrelation().correlation(maxArr, minArr);
			
			System.out.println("ȸ�� : "+count);
			System.out.println("��հ� �ִ밪�� ��� ���:" + correlation1);
			System.out.println("��հ� �ּҰ��� ��� ���:" + correlation2);		
			System.out.println("�ִ밪�� �ּҰ��� ��� ���:" + correlation3);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private static void frequencyList(String readFileName) {
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));

			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				return;
			}

			nextLine = csvReader.readNext();
			nextLine = csvReader.readNext();

			Map<int[], Integer> frequencyMap = new HashMap<>();
			int count = 0;
			while ((nextLine = csvReader.readNext()) != null) {
				int x1 = Integer.parseInt(nextLine[0]);
				int x2 = Integer.parseInt(nextLine[1]);
				int x3 = Integer.parseInt(nextLine[2]);
				int x4 = Integer.parseInt(nextLine[3]);
				int x5 = Integer.parseInt(nextLine[4]);
				int x6 = Integer.parseInt(nextLine[5]);
				int x7 = Integer.parseInt(nextLine[6]);

				int[] arr = { x1, x2, x3, x4, x5, x6, x7 };
				Arrays.sort(arr);

				frequencyMap.put(arr, frequencyMap.getOrDefault(arr, 0) + 1);

				count++;
			}
			csvReader.close();

			System.out.println("ȸ�� : " + count);
			int maxValue = Integer.MIN_VALUE;
			for (int[] key : frequencyMap.keySet()) {
				for (int i : key)
					System.out.print(i + "\t");
				System.out.println(":\t" + frequencyMap.get(key));
				if (maxValue < frequencyMap.get(key)) {
					maxValue = frequencyMap.get(key);
				}
			}

			System.out.println(maxValue);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void frequencyNum(String readFileName) {
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));

			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				return;
			}

			Map<String, Integer> frequencyMap = new HashMap<>();
			int count = 0;
			while ((nextLine = csvReader.readNext()) != null) {
				for(int i=0;i<7;i++)
					frequencyMap.put(nextLine[i], frequencyMap.getOrDefault(nextLine[i], 0) + 1);
				count++;
			}
			csvReader.close();

			System.out.println("ȸ�� : " + count);
			int maxValue = Integer.MIN_VALUE;
			String maxKey = "";
			for (String key : frequencyMap.keySet()) {
				System.out.println(key + ":" + frequencyMap.get(key));
				if (maxValue < frequencyMap.get(key)) {
					maxKey = key;
					maxValue = frequencyMap.get(key);
				}
			}

			System.out.println(maxKey + "���� �ִ����� " + maxValue + "�� ������.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
