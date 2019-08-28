package features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import pageobject.ActiveProjectCustomerPage;
import pageobject.CreateNewCustomerPage;
import pageobject.CreateNewProjectPage;
import pageobject.EditCustomerInfoPage;
import pageobject.EnterTimeTrackPage;
import pageobject.LoginPage;
import pageobject.OpenTaskPage;

public class TaskFeature 
{
	WebDriver driver;
	LoginPage lp;
	EnterTimeTrackPage ettp;
	OpenTaskPage otp;
	ActiveProjectCustomerPage apcp;
	CreateNewCustomerPage cncp;
	EditCustomerInfoPage ecip;
	CreateNewProjectPage cnpp;
	
	public TaskFeature(WebDriver driver)
	{
		this.driver = driver;
		lp = new LoginPage(driver);
		ettp = new EnterTimeTrackPage(driver);
		otp = new OpenTaskPage(driver);
		apcp = new ActiveProjectCustomerPage(driver);
		cncp = new CreateNewCustomerPage(driver);
		ecip = new EditCustomerInfoPage(driver);
		cnpp = new CreateNewProjectPage(driver);
	}
	
	public void createCustomer(String username, String password, String custname)
	{
		lp.login(username, password);
		ettp.getTaskPageLink().click();
		otp.getProjectCustomerLink().click();
		apcp.getCreateNewCustomerBtn().click();
		cncp.createCust(custname);
		
		String actualMsg = apcp.getSuccessMsg().getText();
		String expectedMsg = "Customer \""+custname+"\" has been successfully created.";
		Assert.assertEquals(actualMsg, expectedMsg);
		Reporter.log(custname+" is verified", true);
	}
	
	public void deleteCustomer(String username, String password, String custname)
	{
		lp.login(username, password);
		ettp.getTaskPageLink().click();
		otp.getProjectCustomerLink().click();
		
		Select sel = new Select(apcp.getCustomerDropdown());
		sel.selectByVisibleText(custname);
		apcp.getShowBtn().click();
		apcp.getCustomerNameLink().click();
		ecip.performDelete();
		
		for (int i = 0; i < sel.getOptions().size(); i++) 
		{
			String flag = sel.getOptions().get(i).getText();
			Assert.assertNotEquals(custname, flag);
		}
		Reporter.log(custname+" has been deleted successfully!!", true);
	}
	
	public void createProject(String username, String password, String custname, String projname)
	{
		lp.login(username, password);
		ettp.getTaskPageLink().click();
		otp.getProjectCustomerLink().click();
		apcp.getCreateNewProjectBtn().click();
		
		Select sel = new Select(cnpp.getCustDropdown());
		sel.selectByVisibleText(custname);
		cnpp.getProjectTextbx().sendKeys(projname);
		cnpp.getCreateProjectBtn().click();
		String actualMsg = apcp.getSuccessMsg().getText();
		String expectedMsg = "Project \""+projname+"\" has been successfully created.";
		Assert.assertEquals(actualMsg, expectedMsg);
		Reporter.log(projname+" has beeb created!!", true);	
	}
	
	
}
