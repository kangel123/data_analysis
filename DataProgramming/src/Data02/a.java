package Data02;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.opencsv.CSVWriter;

public class a {
	public static WebDriver driver;
	public static String base_url = "https://lol.inven.co.kr/dataninfo/match/playerList.php";
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\DataProgram\\chromedriver_win32\\chromedriver.exe";
	public static String fileName = "C:\\Users\\kjw\\Desktop\\LCK_Game_Information.csv";

	public static void main(String[] args) {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*"); // 해당 부분 추가
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		List<Player> list = new ArrayList<>();

		try {
			driver.get(base_url);
			Thread.sleep(1000);
			
			for (int i = 1; i <= 1000; i++) {
				String titlePath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr[1]/td[2]";
				String title = driver.findElement(By.xpath(titlePath)).getText();
				if(!title.equals("2023 LCK 서머")) {
					break;
				}
				
				Player player = new Player();

				String gameDatePath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[1]";
				String gameDate = driver.findElement(By.xpath(gameDatePath)).getText();
				Thread.sleep(500);
			
				player.setGameDate(gameDate);
				String namePath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[3]/a";
				String name = driver.findElement(By.xpath(namePath)).getText();
				Thread.sleep(500);
				player.setName(name);

				String championNamePath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[4]/img";
				String championName = driver.findElement(By.xpath(championNamePath)).getAttribute("outerHTML");
				Thread.sleep(500);
				championName = GetChampionNameFromXPath(championName);
				player.setChampionName(championName);

				String spell1Path = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[5]/img[1]";
				String spell1 = driver.findElement(By.xpath(spell1Path)).getAttribute("outerHTML");
				Thread.sleep(500);
				spell1 = GetChampionNameFromXPath(spell1);
				player.setSpell1(spell1);

				String spell2Path = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[5]/img[2]";
				String spell2 = driver.findElement(By.xpath(spell2Path)).getAttribute("outerHTML");
				Thread.sleep(500);
				spell2 = GetChampionNameFromXPath(spell2);
				player.setSpell2(spell2);

				String resultPath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[6]";
				String result = driver.findElement(By.xpath(resultPath)).getText();
				Thread.sleep(500);
				player.setResult(result);

				String killPath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[7]";
				String kill = driver.findElement(By.xpath(killPath)).getText();
				Thread.sleep(500);
				player.setKill(kill);

				String deathPath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[8]";
				String death = driver.findElement(By.xpath(deathPath)).getText();
				Thread.sleep(500);
				player.setDeath(death);

				String assistPath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[9]";
				String assist = driver.findElement(By.xpath(assistPath)).getText();
				Thread.sleep(500);
				player.setAssist(assist);
				
				String kdaPath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[10]";
				String kda = driver.findElement(By.xpath(kdaPath)).getText();
				Thread.sleep(500);
				player.setKda(kda);

				String ratePath = "/html/body/div[3]/div[1]/section/article/section[2]/div[2]/div/div/div[4]/table/tbody/tr["
						+ i + "]/td[11]";
				String rate = driver.findElement(By.xpath(ratePath)).getText();
				Thread.sleep(500);
				player.setRate(rate);

				list.add(player);
				System.out.println(i + "번째 추출 완료");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}

		MakeCssFile(list);
	}

	public static String GetChampionNameFromXPath(String name) {

		int startIndex = name.indexOf("'") + 1;
		int endIndex = name.lastIndexOf("'");
		String imgName = name.substring(startIndex, endIndex);

		return imgName;
	}

	public static boolean MakeCssFile(List<Player> list) {

		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));

			String[] filed = { "일자", "소환사명", "챔피언", "스펠", "승패(문자)","승패(숫자)" , "K", "D", "A", "KDA", "킬관여율" };
			csvWriter.writeNext(filed);

			for (Player player : list) {				
				String iResult = "0";
				if(player.getResult().equals("승")) {
					iResult = "1";
				}
				String[] rowData = { player.getGameDate(), player.getName(), player.getChampionName(),
						player.getSpell1() + ", " + player.getSpell2(), player.getResult(), iResult, player.getKill(),
						player.getDeath(), player.getAssist(), player.getKda(), player.getRate() };
				csvWriter.writeNext(rowData);
			}
			csvWriter.close();
			System.out.println("파일 생성 완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
