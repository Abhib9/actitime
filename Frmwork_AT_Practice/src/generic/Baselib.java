package generic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class Baselib 
{
	public static WebDriver driver;
	
	@BeforeMethod
	@Parameters({"browser","url"})
	public void preCondition(String browsername, String baseurl)
	{
		driver = BrowserFactory.launch(browsername);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.get(baseurl);
	}
	
	@AfterMethod
	public void postCondition()
	{
		driver.close();
		if(driver!=null)
		{
			driver.quit();
		}
	}
}
