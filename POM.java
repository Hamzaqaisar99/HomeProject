package homeproject;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.imageio.ImageIO;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class POM {

WebDriver driver;
Logger log = LogManager.getLogger("POMProject");

/*
 * 1-Navigate to https://www.galaxy.pk/
 * 
 * 2-Hover the mouse pointer above the "Products" and click on the "laptop"
 * option from the list.
 * 
 * 3-Extract each tablet's Name, Price, and description from the website and
 * then dump this information into the excel sheet.
 * 
 * 4-Also, save each tablet display picture in a folder with the same name
 * mentioned on the website.
 */

	//waqar.rauf@curemd.com
	//constructor


	public POM(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//ul[@class='dropdown-menu yamm']/child::li[@id='menu-item-4761']") WebElement LaptopButton;
	@FindBy(xpath="//select[@name='ppp']") WebElement ShowBt;
	
	@FindBy(xpath="//div[@class='product-loop-body product-item__body']/span/following-sibling::a/h2") List<WebElement> Laptops;
	@FindBy(xpath="//span[@class='price']") List<WebElement> Prices;
	@FindBy(xpath="//div[@class='product-short-description']") List<WebElement> Description;
	
	
	public void hoverAndCLick() throws InterruptedException
	{

		String ExpectedTitle = "Galaxy.pk – Computer ka Sub kuch Milega";
		String ActualTitle = driver.getTitle();
		Assert.assertEquals(ActualTitle, ExpectedTitle);
		
		Actions act = new Actions(driver);
		act.moveToElement(LaptopButton).perform();;
		Thread.sleep(3000);
		LaptopButton.click();
		ShowBt.click();
		Select all = new Select(ShowBt);
		all.selectByVisibleText("Show All");
		Thread.sleep(8000);
		
		log.info("Moved to next tab");
		
		String ExpectedTitle2 = "Laptops – Galaxy.pk";
		String ActualTitle2 = driver.getTitle();
		
		Assert.assertEquals(ActualTitle2, ExpectedTitle2);
 }
	
	public void scrapingPage() throws InterruptedException
	{
		List <WebElement> Products;
		ArrayList<String> Texts1 = new ArrayList<String>();
		System.out.println("========================================================== ");
		System.out.println("Extracting Laptop's Names: ");
		Products = Laptops;
		 
			for(WebElement Name : Products)
			{
				Texts1.add(Name.getText());
		    }
			
			for(String NameText: Texts1)
			{
				System.out.println(NameText);
			}
			log.info("Names Extracted");
			
			
		//----------------------------------------------------------//	
			
			List <WebElement> PriceofLaptop;
			ArrayList<String> Texts2 = new ArrayList<String>();
			
			System.out.println("========================================================== ");
			System.out.println("Extracting Tablet's Prices: ");
			 PriceofLaptop = Prices;
			 
			 
				for(WebElement GetPrice : PriceofLaptop)
				{
					Texts2.add(GetPrice.getText());
			    }
				
				for(String PriceText: Texts2)
				{
					System.out.println(PriceText);
					
				}
				
				log.info("Prices Extracted");
				

      //-------------------------------------------------------------//
		   List <WebElement> DescriptionOfLaptop;
			ArrayList<String> Texts3 = new ArrayList<String>();
			System.out.println("========================================================== ");
			System.out.println("Extracting Tablet's Description: ");
			DescriptionOfLaptop = Description;
				
				for(WebElement Des : DescriptionOfLaptop)
				{
					Texts3.add(Des.getText());
				}
					
				for(String DesText: Texts3)
				{
				    System.out.println(DesText);
				    System.out.println(" ");
				    
				}				
		
				log.info("Descriptions Extracted");
				
	//-----------------------------------------------------//
				
				   //Blank workbook
			    XSSFWorkbook workbook = new XSSFWorkbook();

			    //Create a blank sheet
			    XSSFSheet sheet = workbook.createSheet("Employee Data");

			    //This data needs to be written (Object[])
			    Map<String, Object[]> data = new TreeMap<String, Object[]>();
			    //data.put("1", new Object[]{"Name", "Price", "Description" });
			    for(int i=1; i<=158; i++)
			    {   
			    String Number=Integer.toString(i);
			    String Names = Texts1.get(i);
			    String Prices = Texts2.get(i);
			    String Descriptions = Texts3.get(i);
				data.put(Number, new Object[] {Names,Prices,Descriptions});
			    }
	
			    //Iterate over data and write to sheet
			    Set<String> keyset = data.keySet();

			    int rownum = 0;
			    for (String key : keyset) 
			    {
			        //create a row of excelsheet
			        Row row = sheet.createRow(rownum++);

			        //get object array of particular key
			        Object[] objArr = data.get(key);

			        int cellnum = 0;

			        for (Object obj : objArr) 
			        {
			            Cell cell = row.createCell(cellnum++);
			            if (obj instanceof String) 
			            {
			                cell.setCellValue((String) obj);
			            }
			            else if (obj instanceof Integer) 
			            {
			                cell.setCellValue((Integer) obj);
			            }
			        }
			    }
			    try 
			    {
			        //Write the workbook in file system
			        FileOutputStream out = new FileOutputStream(new File("C:/Saved Files/Book1.xlsx"));
			        workbook.write(out);
			        out.close();
			        System.out.println("Book1.xlsx written successfully on disk.");
			    } 
			    catch (Exception e)
			    {
			        e.printStackTrace();
			    }
			    }
			    
			    
        public void imageDisplay() throws InterruptedException, IOException, AWTException
				{
        	
//        	 Thread.sleep(7000);
//					List<WebElement> list = driver.findElements(By.tagName("img"));
//					int count = 1;
//					for(WebElement element : list)
//					{
//						String src = element.getAttribute("src");
//						System.out.println(src);
//						URL imageURL = new URL(src);
//						BufferedImage save = ImageIO.read(imageURL);
//						ImageIO.write(save, "jpd", new File(count+".jpg"));
//					}
					
					   
			    List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			   System.out.println("No. of Images= "+ imagesList.size());
			   
			   for(int i=0 ; i<imagesList.size();i++)
			   {
				   if(!(imagesList.get(i).getAttribute("src").equals("")&& !(imagesList.get(i).getAttribute("src")==null)))
				   {
					   System.out.println(imagesList.get(i).getAttribute("src"));
				   }
			   }
				
	 
	    
   }
}