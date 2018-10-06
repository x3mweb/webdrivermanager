package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class РageObject {
    protected WebDriver driver;

    public РageObject(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}