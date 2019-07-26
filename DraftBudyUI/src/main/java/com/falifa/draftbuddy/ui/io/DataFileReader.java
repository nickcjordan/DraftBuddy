package com.falifa.draftbuddy.ui.io;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.CHROMEDRIVER_PATH;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.jaunt.Document;
import com.jaunt.Element;
import com.jaunt.UserAgent;
import com.jauntium.Browser;

public class DataFileReader {

	private static final Logger log = LoggerFactory.getLogger(DataFileReader.class);

//	public List<List<String>> getSplitLinesFromFile(String fileName, boolean skipHeader, String regex) throws FileNotFoundException {
//		List<List<String>> splitLinesList = new ArrayList<>();
//		Scanner scanner = new Scanner(new File(fileName));
//		if (skipHeader && scanner.hasNextLine()) {
//			scanner.nextLine(); // move past header
//		}
//		while (scanner.hasNextLine()) {
//			String line = scanner.nextLine();
//			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))) {
//				List<String> split = splitAndCleanupLine(line, regex);
//				splitLinesList.add(split);
//			}
//		}
//		scanner.close();
//		return splitLinesList;
//	}
//
//	private List<String> splitAndCleanupLine(String line, String regex) {
//		List<String> splitLine = new ArrayList<String>();
//		for (String text : line.split(regex)) {
//			String edited = (text == null || StringUtils.isEmpty(text.trim()) || text.trim().equals("\"\"")) ? "-" : text.trim();
//			splitLine.add(edited.replaceAll("\"", ""));
//		}
//		return splitLine;
//	}
//
//	public List<String> getLinesFromFile(String fileName) throws FileNotFoundException {
//		List<String> names = new ArrayList<String>();
//		Scanner scanner = new Scanner(new File(fileName));
//		if (scanner.hasNextLine()) {
//			scanner.nextLine(); // move past header
//		}
//		while (scanner.hasNextLine()) {
//			String line = scanner.nextLine();
//			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))) {
//				names.add(line);
//			}
//		}
//		scanner.close();
//		return names;
//	}

//	public String getDataFilePathFromFantasyProsWebsite(String url) {
//		try {
//
//			System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH); // setup (edit the path)
//			ChromeOptions options = new ChromeOptions(); // create chrome options object
////			options.addArguments("--headless");
//			ChromeDriver driver = new ChromeDriver(options);
//			Browser browser = new Browser(driver); // create new browser window
//			browser.visit("https://www.fantasypros.com/nfl/rankings/ppr-cheatsheets.php"); // visit a url
////			System.out.println(browser.doc.outerHTML()); // print the HTML
////			browser.quit();
//
//			
//			WebElement ele = driver.findElement(By.xpath("/html/body/div[3]/div[5]/div/div[1]/div/div[1]/div/div[2]/div/ul/li/ul/li[2]/a"));
//			JavascriptExecutor executor = (JavascriptExecutor)driver;
//			executor.executeScript("arguments[0].click();", ele);
//			
//			
//			
//			com.jauntium.Element dropdown = browser.doc.findFirst("<ul class=\"fp-dropdown-menu higher\">");
//			
//			com.jauntium.Element button = dropdown.getChildElements().get(1);
//			
//			boolean displayed = button.isDisplayed();
//			boolean enabled = button.isEnabled();
//			boolean selected = button.isSelected();
//			
//			com.jauntium.Element link = button.getChildElements().get(0);
//			
//			boolean linkdisplayed = link.isDisplayed();
//			boolean linkenabled = link.isEnabled();
//			boolean linkselected = link.isSelected();
//			
//			
//			WebElement element1 = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(link));
//			link.click();
//			
//			
//			 UserAgent userAgent = new UserAgent(); // create new userAgent (headless browser)
//			 userAgent.visit(url); // send request
//			 String source = userAgent.getSource();
//			 String xml = userAgent.doc.innerXML();
//			 String innerHtml = userAgent.doc.innerHTML();
//			 String outerHtml = userAgent.doc.outerHTML();
//			 
//			 
//			
//			
//			 userAgent.sendGET("https://www.fantasypros.com/nfl/rankings/ppr-cheatsheets.php");
//			 //send HTTP POST Request with queryString
////			 String secondInnerHtml = userAgent.doc.innerHTML();
////			 Element linky = userAgent.doc.findFirst("<a href>");
//			
////			 Element dropdown = userAgent.doc.findFirst("<ul class=\"fp-dropdown-menu higher\">");
//			// userAgent.openContent(html);
//			// Element body = userAgent.doc.findFirst("<body>");
//			// Element link = body.findFirst("<a href class download=\"FantasyPros_2019_Draft_Overall_Rankings.csv\">");
//			// Element link = body.findFirst("<a download>");
//			
//			// Elements res = body.findEvery("<a href>");
//			
//			
//			
//			 List<Element> dropdownOptions = userAgent.doc.findFirst("fp-dropdown-menu higher").getChildElements();
//			 Element linkDropdown = dropdownOptions.get(1);
//			 Element linker = linkDropdown.getChildElements().get(0);
//			
//			 Document doc = userAgent.openContent(linker.innerHTML());
//			 Document submitted = doc.submit();
//			 String submittedHtml = submitted.innerHTML();
//			
//			 String path = linker.getAtString("href");
//			// String path = "https://www.fantasypros.com/nfl/rankings/ppr-cheatsheets.php";
//			 return path;
//		} catch (Exception e) { // if an HTTP/connection error occurs, handle JauntException.
//			System.err.println(e);
//		}
//		return null;
//	}

}
