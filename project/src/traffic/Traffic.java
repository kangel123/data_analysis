package traffic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;


public class Traffic {
	public static void main(String args[]) {
		String readFileName = "C:\\Users\\kjw\\Desktop\\교통\\04월_서울시_교통량_조사자료(2023).csv";
		
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName),"CP949"));
			
			String[] fieldName; 
			if((fieldName=csvReader.readNext())==null) {
				return;
			}

			String[] nextLine;	
			Map<String,Integer> mapMaxHour = new HashMap<>();	// 최대값
			Map<String,Integer> mapMinHour = new HashMap<>();	// 최소값
			Map<String,Integer> mapSumHour = new HashMap<>();	// 합계(x)
			Map<String,Integer> mapPowSumHour = new HashMap<>();	// E(x^2)
			
			// 파일 읽어서 필요한 값 구하기
			int count=0;
			for(int i=0;i<60;i++) {
				nextLine=csvReader.readNext();
				for(int j=6;j<30;j++) {
					int num=Integer.parseInt(nextLine[j]);
					Integer powernum= (int)Math.pow(num,2);
				
					mapSumHour.put(fieldName[j],mapSumHour.getOrDefault(fieldName[j], 0)+num);						
					mapPowSumHour.put(fieldName[j],mapPowSumHour.getOrDefault(fieldName[j], 0)+powernum);		
				
					if(mapMaxHour.getOrDefault(fieldName[j], Integer.MIN_VALUE) < num) {
						mapMaxHour.put(fieldName[j], num);
					}
					
					if(mapMinHour.getOrDefault(fieldName[j], Integer.MAX_VALUE) > num) {
						mapMinHour.put(fieldName[j], num);
					}	
				}
				count++;
			}
			csvReader.close();
			
			// 카테고리 별 정보 출력
			System.out.println("총 개수:"+count);
			for(String hour : mapSumHour.keySet()) {				
				Double averageHour = (double) (mapSumHour.get(hour)/count);	// E(x)
				Double averagePowHour = (double) (mapPowSumHour.get(hour)/count);	// E(x^2)
				
				System.out.println("-----------------------------------------------");
				System.out.println(hour+"의 최대값:"+mapMaxHour.get(hour));
				System.out.println(hour+"의 최소값:"+mapMinHour.get(hour));
				System.out.println(hour+"의 평균:"+averageHour);	// E(x)
				System.out.println(hour+"의 분산:"+(averagePowHour-Math.pow(averageHour,2)));	// E(x)^2-E(x^2)
				System.out.println(hour+"의 표준편차:"+Math.sqrt(averagePowHour-(Math.pow(averageHour,2))));	// (E(x)^2-E(x^2))^1/2
			}
			
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
