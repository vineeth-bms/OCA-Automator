package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OCA_SDriver {


    static WebDriver driver = new ChromeDriver();

    static JavascriptExecutor js = (JavascriptExecutor) driver;




    public static  void openDriver(){

        System.out.println("inside ocadriver constructor");
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");


        //options.setPageLoadStrategy(PageLoadStrategy.EAGER);


        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            // 1 | open | https://ngc-pro-oca-internal.its.hpecorp.net/ocacluster2/OCAInternalLogin |
            driver.get("https://ngc-pro-oca-internal.its.hpecorp.net/oca/OCAInternalLogin");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2 | setWindowSize | 1296x696 |
            driver.manage().window().maximize();

        }catch (Exception e) {
            System.out.println("Page Loading error");

        }

        }

public static  void closeDriver()
{
    System.out.println("closing browser...");
    driver.quit();
}

    public static Map<String,String> testConfig(String filePath, String sheetName) {

        Map<String,String> res = new HashMap<>();
        String idno = "";
        List<String> adhoc_partnumbers = new ArrayList<>();
        List<String> adhoc_quantity = new ArrayList<>();


        System.out.println("Starting test .....");
        String tester = "Retry";
        String auto_desc = "";
        String UCID = "";
        List<String> partnumbers = ExcelUtils.getColumnValues(filePath, sheetName, "Part Number");
        List<String> quantities = ExcelUtils.getColumnValues(filePath, sheetName, "QTY");
        System.out.println("partnumbers are ");
        for(int j=0;j< partnumbers.size();j++)
            System.out.println(partnumbers.get(j)+" "+quantities.get(j));

        int i = 0;



        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 3 | click | id=img_normal |
           try{ driver.findElement(By.id("img_normal")).click();}
           catch (NoSuchElementException e)
           {
               driver.navigate().refresh();
               driver.findElement(By.id("img_normal")).click();

           }

            // 4 | click | css=#labelforcto > .radio_default |
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }

            try{wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@id='labelforcto']//span[@class='radio_default']")));}
                catch (TimeoutException t){
                driver.navigate().refresh();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try{ driver.findElement(By.id("img_normal")).click();}
                    catch (NoSuchElementException e)
                    {
                        driver.navigate().refresh();
                        driver.findElement(By.id("img_normal")).click();

                    }


                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    {
                        WebElement element = driver.findElement(By.tagName("body"));
                        Actions builder = new Actions(driver);
                        builder.moveToElement(element, 0, 0).perform();
                    }
                }
           try{ driver.findElement(By.xpath("//label[@id='labelforcto']//span[@class='radio_default']")).click();}
           catch (Exception ex){

               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               driver.findElement(By.xpath("//label[@id='labelforcto']//span[@class='radio_default']")).click();

           }
            //driver.findElement(By.xpath("//font[normalize-space()='CTO']")).click();

            // 5 | click | id=s_product |
            driver.findElement(By.id("s_product")).click();
            // 6 | type | id=ncp_search | P39478-B21
            driver.findElement(By.id("ncp_search")).sendKeys(partnumbers.get(i));
            // 7 | sendKeys | id=ncp_search | ${KEY_ENTER}
            driver.findElement(By.id("ncp_search")).sendKeys(Keys.ENTER);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            auto_desc = "//span[@id='auto_desc_" + partnumbers.get(i) + "']";

            // 8 | click | id=auto_desc_P39478-B21 |
            try {driver.findElement(By.xpath(auto_desc)).click();}
            catch (Exception e)
            {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                driver.findElement(By.xpath(auto_desc)).click();

            }
            // 9 | click | id=create_config_button |

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //driver.findElement(By.xpath("//div[@id='integrate_in_rack_div']/div/span")).click();
            //driver.findElement(By.xpath("//div[@id='integrate_in_rack_div']/div/div/span")).click();
            js.executeScript("window.scrollTo(0,200)");
            driver.findElement(By.id("create_config_button")).click();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 10 | click | css=label:nth-child(3) |
            driver.findElement(By.xpath("//label[@for='menu_cust_cmn_toggle']")).click();
            // 11 | click | id=_confirm_yes |
            driver.findElement(By.id("_confirm_yes")).click();
            // 12 | click | id=expand_all |
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.id("expand_all")).click();
            // 13 | click | id=menu_search |
            String PnXpath;
            String QtyXpath;
            for (i = 1; i < partnumbers.size(); i++) {
                //dqty = "1";
                // 19 | click | css=.menu_search |
                driver.findElement(By.cssSelector(".menu_search")).click();
                driver.findElement(By.xpath("//input[@id='menu_search']")).sendKeys(Keys.CONTROL + "a"); // Select all text
                driver.findElement(By.xpath("//input[@id='menu_search']")).sendKeys(Keys.DELETE); // Delete selected text
                driver.findElement(By.xpath("//input[@id='menu_search']")).click();
                // 20 | type | id=menu_search | P37611-B21

                driver.findElement(By.id("menu_search")).sendKeys(partnumbers.get(i));
                // 21 | sendKeys | id=menu_search | ${KEY_ENTER}
                driver.findElement(By.id("menu_search")).sendKeys(Keys.ENTER);
                try{ Thread.sleep(1000);}catch (InterruptedException e){}
                PnXpath = "//span[normalize-space()=\'" + partnumbers.get(i) + "\']";

                try{
                    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

                    driver.findElement(By.xpath(PnXpath)).click();
                    try{    if (driver.findElement(By.id("0")).isDisplayed()) {
                        driver.findElement(By.id(quantities.get(i))).click();
                        //dqty = quantities.get(i);

                    }

                    } catch (Exception e) {

                        System.out.println("quantity");


                    }
                } catch (Exception e) {

                    System.out.println("Hello  partnumber "+partnumbers.get(i) + " doesnt exist");
                    adhoc_partnumbers.add(partnumbers.get(i));
                    adhoc_quantity.add(quantities.get(i));



                }




                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


////tr[@id='item_tr_P37611-B21_ProcessorSection_ProcessorChoice']//div[@class='item_qty_div'][normalize-space()='1']

                js.executeScript("window.scrollTo(0,0)");
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 24 | click | id=menu_search |
                try{
                driver.findElement(By.cssSelector(".menu_search")).click();}
                catch(Exception e)
                {
                    System.out.println("menu delay");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    driver.findElement(By.cssSelector(".menu_search")).click();
                }
                driver.findElement(By.xpath("//input[@id='menu_search']")).sendKeys(Keys.CONTROL + "a"); // Select all text
                driver.findElement(By.xpath("//input[@id='menu_search']")).sendKeys(Keys.DELETE); // Delete selected text
                driver.findElement(By.xpath("//input[@id='menu_search']")).click();
            }
            //Ad-Hoc Section
            for(int j = 0;j<adhoc_partnumbers.size();j++)
            {

                driver.findElement(By.cssSelector(".menu_search")).click();
                driver.findElement(By.xpath("//input[@id='menu_search']")).sendKeys(Keys.CONTROL + "a"); // Select all text
                driver.findElement(By.xpath("//input[@id='menu_search']")).sendKeys(Keys.DELETE); // Delete selected text
                driver.findElement(By.xpath("//input[@id='menu_search']")).click();
                // 20 | type | id=menu_search | P37611-B21

                driver.findElement(By.id("menu_search")).sendKeys(adhoc_partnumbers.get(j));
                // 21 | sendKeys | id=menu_search | ${KEY_ENTER}
                driver.findElement(By.id("menu_search")).sendKeys(Keys.ENTER);
                try{ Thread.sleep(1000);}catch (InterruptedException e){}

                try{ Thread.sleep(1000);}catch (InterruptedException ed){}

                driver.findElement(By.xpath("//button[@id='button_adhoc_section']")).click();
                try{ Thread.sleep(1000);}catch (InterruptedException ed){}
                driver.findElement(By.xpath("//button[@id='button_adhoc_section']")).click();

                try{ Thread.sleep(1000);}catch (InterruptedException ed){}
                driver.findElement(By.xpath("//span[normalize-space()='<new ad-hoc item>']")).click();

                driver.findElement(By.xpath("//input[@id='ad_hoc_input']")).sendKeys(adhoc_partnumbers.get(j));
                driver.findElement(By.xpath("//input[@id='ad_hoc_input']")).sendKeys(Keys.ENTER);
                try{ Thread.sleep(4000);}catch (InterruptedException ed){}


                try{
                    String xpathstr = "//td[contains(@id,'value_selected_instance_"+  idno +"')]";
                    WebElement ele = driver.findElement(By.xpath(xpathstr));

                    System.out.println("id is " + ele.getAttribute("id"));
                    idno = String.valueOf(1 + Integer.parseInt(ele.getAttribute("id").replace("value_selected_instance_","")));

                    ele.click();
                }
                catch (Exception ec) {


                    ec.printStackTrace();

                }

                //input[@id='popup_textbox']
                driver.findElement(By.xpath("//input[@id='popup_textbox']")).sendKeys(adhoc_quantity.get(j));
                driver.findElement(By.xpath("//input[@id='popup_textbox']")).sendKeys(Keys.ENTER);
                try{ Thread.sleep(4000);}catch (InterruptedException ed){}

            }

            // 50 | click | id=ui-id-14 |
            driver.findElement(By.id("ui-id-14")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 51 | click | id=save_href |
            try {
            driver.findElement(By.id("save_href")).click();

          // 52 | click | id=oca_save_all |
               driver.findElement(By.id("oca_save_all")).click();
               // 53 | click | id=clickCheck |

            driver.findElement(By.id("clickCheck")).click();
            // 54 | click | id=bom_clic |
            driver.findElement(By.id("bom_clic")).click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 55 | click | id=_clic_ok |
            driver.findElement(By.id("_clic_ok")).click();
           }catch (Exception e) {
               tester = "Unbuildable";
           }
            try {
                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                if (driver.findElement(By.xpath("//td[@id='Unbuildable']")).isDisplayed()) {
                    tester = "Unbuildable";
                }

            }catch (NoSuchElementException e){
                tester = "Buildable";
            }
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                // 56 | click | id=save_href |
                driver.findElement(By.id("save_href")).click();
                // 57 | click | id=oca_save_all |
                driver.findElement(By.id("oca_save_all")).click();
                // 58 | runScript | window.scrollTo(0,0) |
                js.executeScript("window.scrollTo(0,0)");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                try{
                    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                    if( driver.findElement(By.xpath("//i[@id='ack_checkbox']")).isDisplayed()) {

                        driver.findElement(By.xpath("//i[@id='ack_checkbox']")).click();
                        driver.findElement(By.id("save_btn")).click();
                        System.out.println("error check box");
                    }

                }catch (Exception e){
                    driver.findElement(By.id("save_btn")).click();
                    System.out.println("no check box");

                }
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
            }

            WebElement element = driver.findElement(By.xpath("//span[@class='config-ucid']"));
            if (element.isDisplayed()) {

            }
            // Get the text value of the web element
            UCID = element.getText();
            if(UCID.equals(""))
            {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }

                WebElement ucid_element = driver.findElement(By.xpath("//span[@class='config-ucid']"));
                if (element.isDisplayed()) {

                }
                // Get the text value of the web element
                UCID = ucid_element.getText();

            }
            System.out.println("UCID value: " + UCID);

            // Report Generating part
//
//            driver.findElement(By.id("generate_doc_href")).click();
//                // 61 | click | id=customerfacingExportMenu |
//            driver.findElement(By.id("customerfacingExportMenu")).click();
//
//
//
//            // 62 | click | css=.label-checkbox-radio:nth-child(4) > .radio_default |
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//////span[@class='radio_default']
//
//            driver.findElement(By.xpath("//div[@id='fileTypeDiv']/div/label[2]/span")).click();
//
//           try {
//               driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//               if (driver.findElement(By.xpath("//label[@for='summary_checkbox_id']//span[@class='checkbox_active']")).isDisplayed()) {
//                   System.out.println("already selected options");
//                   driver.findElement(By.id("yes-id")).click();
//               }
//           }catch (Exception e){
//               System.out.println("not selected options");
//            // 63 | click | css=#summary_checkbox_div .checkbox_default |
//            driver.findElement(By.xpath("//label[@for='summary_checkbox_id']//span[@class='checkbox_default']")).click();
//            // 64 | click | css=#grpProdType_div .checkbox_default |
//            driver.findElement(By.xpath("//label[@for='grpProdType_checkbox_id']//span[@class='checkbox_default']")).click();
//            // 65 | click | css=#hideFactIntergratedOpn_div .checkbox_default |
//            driver.findElement(By.xpath("//label[@for='hideFactIntergratedOpn_checkbox_id']//span[@class='checkbox_default']")).click();
//            // 66 | click | id=yes-id |
//            driver.findElement(By.id("yes-id")).click();}
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            driver.navigate().refresh();


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 67 | click | id=ui-id-30 |
           // driver.findElement(By.id("ui-id-30")).click();

            System.out.println( sheetName + " " +tester);

        } catch (Exception e) {

            tester = "Error thrown";
            e.printStackTrace();
        } finally {

            System.out.println( "finally " + sheetName + " " +tester);
            //driver.quit();
        }

        res.put(tester,UCID);
        return res;
    }
}
//
//td[@id='value_selected_instance_12']
//td[@id='value_selected_instance_13']