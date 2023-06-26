package Data03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;

import org.apache.commons.math4.legacy.stat.correlation.PearsonsCorrelation;

import com.opencsv.CSVReader;

public class ApartmentSale {
	public static void main(String args[]) {
		String readFileName = "C:\\Users\\kjw\\Desktop\\아파트_매매_실거래가격지수_20230626100434.csv";

		readCSV(readFileName, 201001, 202304);
		readCSV(readFileName, 201301, 202112);
		readCSV(readFileName, 202201, 202304);
	}

	private static void readCSV(String readFileName, int startDate, int EndDate) {
		System.out.println(startDate / 100 + "년 " + startDate % 100 + "월부터 " + EndDate / 100 + "년 " + EndDate % 100
				+ "월까지의 상관계수\n");
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));

			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				return;
			}

			String[] fileName = nextLine;

			List<double[]> list = new ArrayList<>();

			double[] x1 = new double[160];
			double[] x2 = new double[160];
			double[] x3 = new double[160];
			double[] x4 = new double[160];
			double[] x5 = new double[160];
			double[] x6 = new double[160];
			double[] x7 = new double[160];
			double[] x8 = new double[160];
			double[] x9 = new double[160];
			double[] x10 = new double[160];

			int count = 0;
			while ((nextLine = csvReader.readNext()) != null) {
				int date = Integer.parseInt(nextLine[0].substring(0, 6));
				if (startDate <= date && date <= EndDate) {
					x1[count] = Double.parseDouble(nextLine[1]);
					x2[count] = Double.parseDouble(nextLine[2]);
					x3[count] = Double.parseDouble(nextLine[3]);
					x4[count] = Double.parseDouble(nextLine[4]);
					x5[count] = Double.parseDouble(nextLine[5]);
					x6[count] = Double.parseDouble(nextLine[6]);
					x7[count] = Double.parseDouble(nextLine[7]);
					x8[count] = Double.parseDouble(nextLine[8]);
					x9[count] = Double.parseDouble(nextLine[9]);
					x10[count] = Double.parseDouble(nextLine[10]);
					
				}
				count++;
			}
			csvReader.close();

			list.add(x1);
			list.add(x2);
			list.add(x3);
			list.add(x4);
			list.add(x5);
			list.add(x6);
			list.add(x7);
			list.add(x8);
			list.add(x9);
			list.add(x10);

			String maxI = "";
			String maxJ = "";
			String minI = "";
			String minJ = "";
			double maxCorrelation = Double.MIN_VALUE;
			double minCorrelation = Double.MAX_VALUE;
			for (int i = 0; i < list.size(); i++) {
				for (int j = i + 1; j < list.size(); j++) {
					double correlation = new PearsonsCorrelation().correlation(list.get(i), list.get(j));
					System.out.println(fileName[i + 1] + ", " + fileName[j + 1] + "의 상관 계수:" + correlation);
					if (maxCorrelation < correlation) {
						maxI = fileName[i + 1];
						maxJ = fileName[j + 1];
						maxCorrelation = correlation;
					}
					if (minCorrelation > correlation) {
						minI = fileName[i + 1];
						minJ = fileName[j + 1];
						minCorrelation = correlation;
					}
				}
			}
			System.out.println("\n최대 상관계수는 " + maxI + "와 " + maxJ + "일 때, " + maxCorrelation + "이다");
			System.out.println("최소 상관계수는 " + minI + "와 " + minJ + "일 때, " + minCorrelation + "이다");
			System.out.println("---------------------------------------------------------");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
