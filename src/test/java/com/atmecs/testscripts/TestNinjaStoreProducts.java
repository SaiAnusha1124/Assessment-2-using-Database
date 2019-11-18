package com.atmecs.testscripts;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.atmecs.actions.ClickOnElementAction;
import com.atmecs.actions.SendKeysAction;
import com.atmecs.constants.ConstantsFilePaths;
import com.atmecs.extentreports.ExtentReport;
import com.atmecs.helpers.LocatorType;
import com.atmecs.testbase.TestBase;
import com.atmecs.utils.ReadExcelFile;
import com.atmecs.utils.ReadLocatorsFile;
import com.atmecs.utils.SqlConnection;
import com.atmecs.validation.VerifyProducts;

public class TestNinjaStoreProducts extends TestBase {
	SqlConnection database;
	String firstproduct, firstquantity, secondproduct, secondquantity, nagative,ninjastore;
	/*
	 * In this class,validating home page,selecting one product in products list
	 */
	Properties properties, properties1;
	ClickOnElementAction click = new ClickOnElementAction();
	SendKeysAction sendkeys = new SendKeysAction();
	ReadExcelFile readexcel = new ReadExcelFile();
	String browserUrl;

	@BeforeClass
	public void launchingUrl() throws IOException {
		properties = ReadLocatorsFile.loadProperty(ConstantsFilePaths.CONFIG_FILE);
		browserUrl = properties.getProperty("url");
		driver.get(browserUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void selectingFirstProduct() {
		database = new SqlConnection();
		// locators are reading through LOCATOR_FILE
		properties = ReadLocatorsFile.loadProperty(ConstantsFilePaths.LOCATOR_FILE);
		// expected data are reading through TESTDATA_FILE
		properties1 = ReadLocatorsFile.loadProperty(ConstantsFilePaths.TESTDATA_FILE);

		VerifyProducts.verifyingHomePage();
		ExtentReport.reportLog("verifyingHomePage", "failed");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"),database.getData("firstproduct", "ninjastore"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected Iphone Product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-iphone"));
		VerifyProducts.verifyingFirstProduct();
		ExtentReport.reportLog("verifyingFirstProduct", "failed");
		driver.findElement(By.xpath(properties.getProperty("loc-sendkeys-quantity1"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkeys-quantity1"),database.getData("firstquantity", "ninjastore"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-addcart1"));
		log.info("Added product to cart");
		log.info("Successfully selected and validate for first product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		driver.findElement(By.xpath(properties.getProperty("loc-click-bttn-search"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"),database.getData("secondproduct", "ninjastore"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected MacBook Air Product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-macbookair"));
		VerifyProducts.verifyingSecondProduct();
		ExtentReport.reportLog("verifyingSecondProduct", "failed");
		driver.findElement(By.xpath(properties.getProperty("loc-sendkeys-quantity2"))).clear();
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-sendkeys-quantity2"),database.getData("secondquantity", "ninjastore"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-addcart2"));
		log.info("Added product to cart");
		log.info("Successfully selected and validate for second product");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-gotocart2"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-viewcart"));
		VerifyProducts.verifyingCartList();
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-removeproduct"));
		VerifyProducts.afterUpdateGrandTotal();
		ExtentReport.reportLog("afterUpdateGrandTotal", "failed");
		log.info("Sucessfulyy selected and validated both iphone and macbook air products");
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"));
		sendkeys.sendKeys(driver, LocatorType.XPATH, properties.getProperty("loc-click-bttn-search"),database.getData("nagative", "ninjastore"));
		click.clickElement(driver, LocatorType.XPATH, properties.getProperty("loc-click-search"));
		log.info("Selected Chairs as Negative Product");
		VerifyProducts.verifyingNegativeCase();
		ExtentReport.reportLog("verifyingNegativeCase", "failed");
	}
}