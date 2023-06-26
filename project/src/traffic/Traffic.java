package traffic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;


public class Traffic {
	public static void main(String args[]) {
		String readFileName = "C:\\Users\\kjw\\Desktop\\����\\04��_�����_���뷮_�����ڷ�(2023).csv";
		
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(readFileName),"CP949"));
			
			String[] fieldName; 
			if((fieldName=csvReader.readNext())==null) {
				return;
			}

			String[] nextLine;	
			Map<String,Integer> mapMaxHour = new HashMap<>();	// �ִ밪
			Map<String,Integer> mapMinHour = new HashMap<>();	// �ּҰ�
			Map<String,Integer> mapSumHour = new HashMap<>();	// �հ�(x)
			Map<String,Integer> mapPowSumHour = new HashMap<>();	// E(x^2)
			
			// ���� �о �ʿ��� �� ���ϱ�
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
			
			// ī�װ� �� ���� ���
			System.out.println("�� ����:"+count);
			for(String hour : mapSumHour.keySet()) {				
				Double averageHour = (double) (mapSumHour.get(hour)/count);	// E(x)
				Double averagePowHour = (double) (mapPowSumHour.get(hour)/count);	// E(x^2)
				
				System.out.println("-----------------------------------------------");
				System.out.println(hour+"�� �ִ밪:"+mapMaxHour.get(hour));
				System.out.println(hour+"�� �ּҰ�:"+mapMinHour.get(hour));
				System.out.println(hour+"�� ���:"+averageHour);	// E(x)
				System.out.println(hour+"�� �л�:"+(averagePowHour-Math.pow(averageHour,2)));	// E(x)^2-E(x^2)
				System.out.println(hour+"�� ǥ������:"+Math.sqrt(averagePowHour-(Math.pow(averageHour,2))));	// (E(x)^2-E(x^2))^1/2
			}
			
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
