package generic;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.google.common.io.Files;

public class MyTestngListener implements ITestListener 
{
	public static int passCount, failCount, skipCount, executionCount = 0;
	public static int startMinute, endMinute = 0; 

	@Override
	public void onTestStart(ITestResult result) 
	{
		executionCount++;
		Reporter.log(result.getName()+" script has started "+new Date(), true);
	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{
		passCount++;
		Reporter.log(result.getName()+" script has passed!!",true);
	}

	@Override
	public void onTestFailure(ITestResult result) 
	{
		failCount++;
		Reporter.log(result.getName()+" script has failed", true);
		
		TakesScreenshot ts = (TakesScreenshot)Baselib.driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./screenshot/"+result.getName()+".png");
		
		try 
		{
			Files.copy(srcFile, destFile);
			Reporter.log("Screenshot has been taken",true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) 
	{
		skipCount++;
		Reporter.log(result.getName()+" script has skipped", true);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{}

	@Override
	public void onStart(ITestContext context) 
	{
		Reporter.log("Script execution starts "+new Date(), true);
		startMinute = LocalTime.now().getMinute();
	}

	@Override
	public void onFinish(ITestContext context) 
	{
		Reporter.log("Script execution ends "+new Date(), true);
		endMinute = LocalTime.now().getMinute();
		
		int totalDuration = endMinute-startMinute;
		Reporter.log("Total time taken by the scripts: "+totalDuration+" mins",true);
		Reporter.log("Total no. of scripts executed: "+executionCount,true);
		Reporter.log("Total no. of scripts passed: "+passCount,true);
		Reporter.log("Total no. of scripts failed: "+failCount,true);
		Reporter.log("Total no. of scripts skipped: "+skipCount,true);
	}

}
