package Data02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
	public static WebDriver driver;
	public static String base_url = "http://192.168.23.51:8083/Board/boardList.jsp";
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\DataProgram\\chromedriver_win32\\chromedriver.exe";

	public static void main(String[] args) {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*"); // 해당 부분 추가
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		try {
			driver.get(base_url);
			Thread.sleep(1000);
			String newButton = "/html/body/div[1]/button";
			driver.findElement(By.xpath(newButton)).click(); // 버튼 클릭하기
			String title="/html/body/form/table[1]/tbody/tr[2]/td[2]/input";
			driver.findElement(By.xpath(title)).click(); // 버튼 클릭하기
			Thread.sleep(500);
			driver.findElement(By.xpath(title)).sendKeys("강원기141●▅▇█▇▆▅▄▇"); // 버튼 클릭하기
			String write = "/html/body/form/table[2]/tbody/tr/td/button[2]";
			driver.findElement(By.xpath(write)).click(); // 버튼 클릭하기
			Thread.sleep(500);
			String main = "/html/body/table[2]/tbody/tr/td/button[1]";
			driver.findElement(By.xpath(main)).click(); // 버튼 클릭하기
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			driver.get(base_url);
//			Thread.sleep(1000);
//			for(int i=2;i<12;i++) {
//				String titleText="/html/body/table/tbody/tr["+i+"]/td[2]/a";
//				String temp = driver.findElement(By.xpath(titleText)).getText();
//				System.out.println(temp);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
