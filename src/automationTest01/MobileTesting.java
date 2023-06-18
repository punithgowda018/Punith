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
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class MobileTesting {

	public static void main(String[] args) throws IOException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		
Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		
		Set<String> urls = new HashSet<>();
		urls.add("https://www.getcalley.com/blog/");
		urls.add("https://www.getcalley.com/");
		urls.add("https://www.getcalley.com/free-trial-calley-teams-plan-v2/");
		urls.add("https://www.getcalley.com/free-trial-calley-pro-v2/");
		urls.add("https://www.getcalley.com/why-automatic-call-dialer-software/");

		Set<String> resolutions = new HashSet<>();
		resolutions.add("360×640");
		resolutions.add("414×896");
		resolutions.add("375×667");
		int i = 1;
		
		for (String url : urls) {
			for (String res : resolutions) {
				String[] str = res.split("×");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				Dimension d = new Dimension(x, y);
				driver.manage().window().setSize(d);

				driver.get(url);
				File file = new File( "./MobileScreenshot/"+cap.getBrowserName()+"_url"+i+"_"+res + "_" + getCurrentTime() + ".png");
				Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
						.takeScreenshot(driver);
				ImageIO.write(screenshot.getImage(), "PNG", file);
				
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
