package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.DataPrice;
import com.example.Farming_App.services.DailyPriceUpdatesService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DailyPriceUpdatesServiceImpl implements DailyPriceUpdatesService {

    public List<DataPrice> getDailyPriceUpdates() throws InterruptedException, MalformedURLException {

        List<DataPrice> dataPrices=new ArrayList<>();

//        System.setProperty("webdriver.edge.driver", "C:\\edgedriver_win64\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Khởi tạo WebDriver
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options);

        // Mở trang web
        driver.get("https://giaca.nsvl.com.vn/TCTongHop.aspx");

        // Đợi trang tải xong
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);



        WebElement table = driver.findElement(By.id("DQBoxGNS01_MyGrid"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement view=driver.findElement(By.id("DQBoxGNS01_LTieuDe"));
        js.executeScript("arguments[0].scrollIntoView();",view);

        TimeUnit.SECONDS.sleep(3);
        // Duyệt qua các hàng trong bảng
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 0) {
                String itemName = cells.get(0).getText().trim();
                String unit = cells.get(1).getText().trim();
                String price = cells.get(2).getText().trim();

                dataPrices.add(new DataPrice(itemName,unit,price));

            }
        }
        driver.quit();
        return dataPrices;
    }
}
