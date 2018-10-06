package pages;

import org.openqa.selenium.support.PageFactory;
import utilities.Browser;


public class Pages
{
//nie uzywam juz tej klasy

    public static HomePage homePage()
    {
        return PageFactory.initElements(Browser.Driver(), HomePage.class);
    }
}
