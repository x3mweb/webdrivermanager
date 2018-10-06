package pages;

import org.openqa.selenium.support.PageFactory;
import utilities.Browser;


public class Pages
{


    public static HomePage homePage()
    {
        return PageFactory.initElements(Browser.Driver(), HomePage.class);
    }
}
