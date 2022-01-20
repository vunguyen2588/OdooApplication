package aspiraapp;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class OdooApplication {
    public WebDriver driver;
    public String chromeLocal = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
    public String timeStamp;

    @BeforeClass
    public void setup() {
        timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary(chromeLocal);
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\driver\\chromedriver.exe");
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testOdooApplication() throws InterruptedException {
        System.out.println(timeStamp);
        driver.get("https://aspireapp.odoo.com/");
        // Login
        driver.findElement(By.cssSelector("#login")).sendKeys("user@aspireapp.com");
        driver.findElement(By.cssSelector("#password")).sendKeys("@sp1r3app");
        driver.findElement(By.cssSelector("button.btn-primary")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='oe_topbar_name']")).getText(), "user");

        driver.findElement(By.xpath("//div[text()='Inventory']/parent::a")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Products')]")).click();
        driver.findElement(By.xpath("//a[contains(@class, 'o_menu_entry_lvl_2')]/span[contains(text(), 'Products')]")).click();
        driver.findElement(By.cssSelector("button.o-kanban-button-new")).click();
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys(timeStamp);
        driver.findElement(By.xpath("//button[@name='action_update_quantity_on_hand']")).click();
        driver.findElement((By.cssSelector("button.o_list_button_add"))).click();
        driver.findElement(By.xpath("//div[@name='location_id']//input[contains(@class, 'ui-autocomplete-input')]")).click();
        driver.findElement(By.xpath("//a[text()='WH/Stock']")).click();
        driver.findElement(By.xpath("//input[@name='inventory_quantity']")).click();
        driver.findElement(By.xpath("//input[@name='inventory_quantity']")).sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.xpath("//input[@name='inventory_quantity']")).sendKeys("17");
        driver.findElement(By.cssSelector("button.o_list_button_save")).click();
        driver.findElement(By.xpath("//a[@title='Applications']")).click();
        driver.findElement(By.xpath("//div[text()='Manufacturing']/parent::a")).click();
        driver.findElement(By.xpath("//button[@class='btn btn-primary o_list_button_add']")).click();
        driver.findElement(By.xpath("//div[@name='product_id']//input")).sendKeys(timeStamp);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@name='product_id']//input")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@class='tab-pane active']//a[text()='Add a line']")).click();
        driver.findElement(By.xpath("//div[@class='tab-pane active']//div[@name='product_id']//input")).sendKeys(timeStamp);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@name='product_id']//input")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[contains(@class, 'o_technical_modal')]//span[text()='Create']/parent::button")).click();

        Actions action = new Actions(driver);
        WebElement link = driver.findElement(By.xpath("//input[@name='product_uom_qty']"));
        action.doubleClick(link).perform();
        driver.findElement(By.xpath("//input[@name='product_uom_qty']")).sendKeys("2");
        driver.findElement(By.cssSelector("button.o_form_button_save")).click();
        driver.findElement(By.xpath("//button[@name='action_confirm' and @class='btn btn-primary']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@name='action_confirm' and @class='btn btn-primary']")).click();
        driver.findElement(By.xpath("//button[@name='button_mark_done' and @class='btn btn-primary']")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'o_technical_modal')]//span[text()='Apply']/parent::button")).click();
    }

    @AfterClass
    public void tearDown() {
		driver.quit();
    }
}