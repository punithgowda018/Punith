package automationTest01;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class UiTesting {
	public static void main(String[] args) throws IOException {
		WebDriver driver = null;

		switch ("chrome") {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("Invalid browser data");
			break;
		}

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		
		Set<String> urls = new HashSet<>();
		urls.add("https://www.getcalley.com/blog/");
		urls.add("https://www.getcalley.com/");
		urls.add("https://www.getcalley.com/free-trial-calley-teams-plan-v2/");
		urls.add("https://www.getcalley.com/free-trial-calley-pro-v2/");
		urls.add("https://www.getcalley.com/why-automatic-call-dialer-software/");

		Set<String> resolutions = new HashSet<>();
		resolutions.add("1920×1080");
		resolutions.add("1366×768");
		resolutions.add("1536×864");
		int i = 1;
		
		for (String url : urls) {
			for (String res : resolutions) {
				String[] str = res.split("×");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				Dimension d = new Dimension(x, y);
				driver.manage().window().setSize(d);

				driver.get(url);
				File file = new File( "./Screenshot/"+cap.getBrowserName()+"_url"+i+"_"+res + "_" + getCurrentTime() + ".png");
				Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
						.takeScreenshot(driver);
				ImageIO.write(screenshot.getImage(), "PNG", file);
				
//				Thread.sleep(2000);
			}
			i++;
		}
		

		driver.quit();

	}
	
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_sss");
		return sdf.format(date);
	}
}