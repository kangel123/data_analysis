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


			Map<String,Integer> mapCount = new HashMap<>();	// �� ����
			Map<String,Double> mapMax = new HashMap<>();	// �ִ밪
			Map<String,Double> mapMin = new HashMap<>();	// �ּҰ�
			Map<String,Double> mapInstalls = new HashMap<>();	// x:��ġ��
			Map<String,Double> mapPowInstalls = new HashMap<>();	// E(x^2)
			
			// ���� �о �ʿ��� �� ���ϱ�
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
			
			// ī�װ� �� ���� ���
			System.out.println("�� �� ����:"+count);
			System.out.println("�� ī�װ� ����:"+mapInstalls.size());
			for(String appCategory : mapInstalls.keySet()) {				
				Double averageInstalls = mapInstalls.get(appCategory)/mapCount.get(appCategory);	// E(x)
				Double averagePowInstalls = mapPowInstalls.get(appCategory)/mapCount.get(appCategory);	// E(x^2)
								
				System.out.println("-----------------------------------------------");
				System.out.println(appCategory+"�� �ִ밪:"+mapMax.get(appCategory));
				System.out.println(appCategory+"�� �ּҰ�:"+Math.round(mapMin.get(appCategory)));
				System.out.println(appCategory+"�� ���:"+averageInstalls);	// E(x)
				System.out.println(appCategory+"�� �л�:"+(averagePowInstalls-Math.pow(averageInstalls,2)));	// E(x)^2-E(x^2)
				System.out.println(appCategory+"�� ǥ������:"+Math.sqrt((averagePowInstalls-Math.pow(averageInstalls,2))));	// (E(x)^2-E(x^2))^1/2
			}			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
