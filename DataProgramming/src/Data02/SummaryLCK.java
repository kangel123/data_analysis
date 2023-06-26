package Data02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;

public class SummaryLCK {
	public static void main(String args[]) {
		String readFileName = "C:\\Users\\kjw\\Desktop\\LCK_Game_Information.csv";

		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName), "CP949"));

			String[] nextLine;
			if ((nextLine = csvReader.readNext()) == null) {
				return;
			}

			Map<String, Double> mapMax = new HashMap<>();
			Map<String, Double> mapMin = new HashMap<>();
			Map<String, Integer> mapsumK = new HashMap<>(); // k
			Map<String, Integer> mapsumD = new HashMap<>(); // d
			Map<String, Integer> mapsumA = new HashMap<>(); // a

			Map<String, Double> mapPowKDA = new HashMap<>(); // E(x^2)
			Map<String, Integer> mapCount = new HashMap<>(); //

			int count = 0;
			while ((nextLine = csvReader.readNext()) != null) {
				String champion = "챔피언 : " + nextLine[2];
				int k = Integer.parseInt(nextLine[6]);
				int d = Integer.parseInt(nextLine[7]);
				int a = Integer.parseInt(nextLine[8]);

				Double kda = Double.parseDouble(nextLine[9]);
				Double PowKDA = Math.pow(kda, 2);

				mapsumK.put(champion, mapsumK.getOrDefault(champion, 0) + k);
				mapsumD.put(champion, mapsumD.getOrDefault(champion, 0) + d);
				mapsumA.put(champion, mapsumA.getOrDefault(champion, 0) + a);
				mapCount.put(champion, mapCount.getOrDefault(champion, 0) + 1);
				mapPowKDA.put(champion, mapPowKDA.getOrDefault(champion, (double) 0) + PowKDA);

				if (mapMax.getOrDefault(champion, Double.MIN_VALUE) < kda) {
					mapMax.put(champion, kda);
				}

				if (mapMin.getOrDefault(champion, Double.MAX_VALUE) > kda) {
					mapMin.put(champion, kda);
				}
				count++;
			}
			csvReader.close();

			for (String champion : mapCount.keySet()) {
				Double rate = 1.0;
				int addD = 0;
				if (mapsumD.get(champion) == 0) {
					addD=1;
					rate = 1.2;
				}

				int sumK = mapsumK.get(champion);
				int sumD = mapsumD.get(champion);
				int sumA = mapsumA.get(champion);
				
				Double averageK = mapsumK.get(champion) / (double) mapCount.get(champion);
				Double averageD = mapsumD.get(champion) / (double) mapCount.get(champion);
				Double averageA = mapsumA.get(champion) / (double) mapCount.get(champion);
				Double averageKDA = rate*((double) (mapsumK.get(champion) + mapsumA.get(champion)) / (mapsumD.get(champion)+addD));
				Double dispersion = (mapPowKDA.get(champion) / mapCount.get(champion)) - Math.pow(averageKDA, 2);
				Double standardDeviation = Math.sqrt(dispersion);

				System.out.println("-----------------------------------------------");
				System.out.println(champion);
				System.out.println("플레이 횟수:" + mapCount.get(champion));
				System.out.println("최대 kda:" + mapMax.get(champion));
				System.out.println("최소 kda:" + mapMin.get(champion));
				System.out.println("평균 킬:" + averageK); // E(x)
				System.out.println("평균 데스:" + averageD); // E(x)
				System.out.println("평균 어시:" + averageA); // E(x)
				System.out.println("산술 평균 kda:" + averageKDA); // E(x)
				System.out.println("분산 kda:" + dispersion);
				System.out.println("표준편차 kda:" + standardDeviation);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
