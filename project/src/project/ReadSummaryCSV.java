package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;


public class ReadSummaryCSV {
	public static void main(String args[]) {
		String readFileName = "C:\\Users\\kjw\\Desktop\\Google-Playstore\\csv\\Google-Playstore.csv";
		
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName),"CP949"));
			
			String[] nextLine;		
			if((nextLine=csvReader.readNext())==null) {
				return;
			}


			Map<String,Integer> mapCount = new HashMap<>();	// 총 개수
			Map<String,Double> mapMax = new HashMap<>();	// 최대값
			Map<String,Double> mapMin = new HashMap<>();	// 최소값
			Map<String,Double> mapInstalls = new HashMap<>();	// x:설치수
			Map<String,Double> mapPowInstalls = new HashMap<>();	// E(x^2)
			
			// 파일 읽어서 필요한 값 구하기
			int count=0;
			while((nextLine=csvReader.readNext())!=null) {
				String appCategory=nextLine[2];
				Double installs=Double.parseDouble(nextLine[7]);
				Double powerInstalls=Math.pow(installs,2);
				

				mapInstalls.put(appCategory,mapInstalls.getOrDefault(appCategory, (double) 0)+installs);	
				mapPowInstalls.put(appCategory,mapPowInstalls.getOrDefault(appCategory, (double) 0)+powerInstalls);		
				mapCount.put(appCategory,mapCount.getOrDefault(appCategory, 0)+1);		
				
				if(mapMax.getOrDefault(appCategory, Double.MIN_VALUE) < installs) {
					mapMax.put(appCategory, installs);
				}
				
				if(mapMin.getOrDefault(appCategory, Double.MAX_VALUE) > installs) {
					mapMin.put(appCategory, installs);
				}	
				
				count++;
			}
			csvReader.close();
			
			// 카테고리 별 정보 출력
			System.out.println("총 앱 개수:"+count);
			System.out.println("총 카테고리 개수:"+mapInstalls.size());
			for(String appCategory : mapInstalls.keySet()) {				
				Double averageInstalls = mapInstalls.get(appCategory)/mapCount.get(appCategory);	// E(x)
				Double averagePowInstalls = mapPowInstalls.get(appCategory)/mapCount.get(appCategory);	// E(x^2)
								
				System.out.println("-----------------------------------------------");
				System.out.println(appCategory+"의 최대값:"+mapMax.get(appCategory));
				System.out.println(appCategory+"의 최소값:"+Math.round(mapMin.get(appCategory)));
				System.out.println(appCategory+"의 평균:"+averageInstalls);	// E(x)
				System.out.println(appCategory+"의 분산:"+(averagePowInstalls-Math.pow(averageInstalls,2)));	// E(x)^2-E(x^2)
				System.out.println(appCategory+"의 표준편차:"+Math.sqrt((averagePowInstalls-Math.pow(averageInstalls,2))));	// (E(x)^2-E(x^2))^1/2
			}			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
