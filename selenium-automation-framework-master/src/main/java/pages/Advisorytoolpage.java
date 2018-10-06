package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.PropertyLoader;


    public class Advisorytoolpage extends РageObject {



        @FindBy(xpath = "mat-card-header[_ngcontent-c5]")
        private WebElement customerHeader;

        @FindBy(xpath = "(//span[@_ngcontent-c5])[1]")
        private WebElement customerName;

        @FindBy(xpath = "(//span[@_ngcontent-c5])[2]")
        private WebElement customerOrganisationNumber;

        @FindBy(xpath = "(//span[@_ngcontent-c5])[3]")
        private WebElement customerCompanyType;

        @FindBy(xpath = "(//span[@_ngcontent-c5])[4]")
        private WebElement campaignID;

        @FindBy(xpath = "mat-card-header[_ngcontent-c6]")
        private WebElement applicationHeader;

        @FindBy(xpath = "(//p[@_ngcontent-c6])[2]")
        private WebElement productType;

        @FindBy(xpath = "(//p[@_ngcontent-c6])[4]")
        private WebElement amount;

        @FindBy(xpath = "(//p[@_ngcontent-c6])[3]")
        private WebElement purpose;

        @FindBy(xpath = "mat-card-header[_ngcontent-c7]")
        private WebElement applicationAndCompanyInformationHeader;

        @FindBy(xpath = "(//span[@_ngcontent-c7])[1]")
        private WebElement companyName;

        @FindBy(xpath = "(//span[@_ngcontent-c7])[2]")
        private WebElement companyOrganisationNumber;

        @FindBy(xpath = "(//span[@_ngcontent-c7])[3]")
        private WebElement companyType;

        @FindBy(xpath = "(//span[@_ngcontent-c7])[4]")
        private WebElement companyNACECode;

        @FindBy(xpath = "'mat-card-header[_ngcontent-c8]")
        private WebElement contactHeader;


        @FindBy(xpath = "(//div[@_ngcontent-c8])[7]")
        private WebElement contactName ;

        @FindBy(xpath = "(//div[@_ngcontent-c8])[11]")
        private WebElement contactPhone;

        @FindBy(xpath = "(//div[@_ngcontent-c8])[15]")
        private WebElement contactEmail;

        @FindBy(xpath = "mat-card-header[_ngcontent-c9]")
        private WebElement KYCHeader ;

        @FindBy(xpath = "(//span[@_ngcontent-c9])[2]")
        private WebElement KYCInfo;

        @FindBy(xpath = "(//span[@_ngcontent-c9])[3])")
        private WebElement KYCRisk;

        @FindBy(xpath = "(//span[@_ngcontent-c9])[4]")
        private WebElement KYCLastUpdate;

        @FindBy(xpath = "mat-card-header[_ngcontent-c10]")
        private WebElement requestInformationHeader;

        @FindBy(xpath = "(//span[@_ngcontent-c10])[1]")
        private WebElement account;

        @FindBy(xpath = "(//span[@_ngcontent-c10])[2]")
        private WebElement appliedAmount ;

        @FindBy(xpath = "(//span[@_ngcontent-c10])[3]")
        private WebElement appliedCreditLimit;

        @FindBy(xpath = "(//span[@_ngcontent-c10])[4]")
        private WebElement basicInterestRate ;

        @FindBy(xpath = "contractualInterestRate")
        private WebElement contractualInterestRate;

        @FindBy(xpath = "(//span[@_ngcontent-c10])[6]")
        private WebElement fee;

        @FindBy(xpath = "'mat-card-header[_ngcontent-c11]")
        private WebElement ownersInformationAndGuaranteeHeader ;

        @FindBy(xpath = "(//span[@_ngcontent-c11])[1]")
        private WebElement ownersInformationAndGuaranteeInfo ;

        @FindBy(xpath = "(//span[@_ngcontent-c11])[2]")
        private WebElement ownersInformationAndGuaranteeName;

        @FindBy(xpath = "(//span[@_ngcontent-c11])[3]")
        private WebElement ownersInformationAndGuaranteePersonalNumber ;

        @FindBy(xpath = "(//span[@_ngcontent-c11])[4]")
        private WebElement ownersInformationAndGuaranteeOwnerGuarantee ;

        @FindBy(xpath = "(//span[@_ngcontent-c11])[5]")
        private WebElement ownersInformationAndGuaranteeMonthlyIncome ;

        @FindBy(xpath = "mat-card-header[_ngcontent-c12]")
        private WebElement LoanStatusHeader ;

        @FindBy(xpath = "(//span[@_ngcontent-c12])[2]")
        private WebElement LoanStatusKYC ;

        @FindBy(xpath = "(//span[@_ngcontent-c12])[4]")
        private WebElement LoanStatusCreditRecommendation ;






        private static String cName = PropertyLoader.loadProperty("customer.Name");

        private static String cOrganisationNumber = PropertyLoader.loadProperty("customerOrganisationNumber");

        private static String cCompanyType = PropertyLoader.loadProperty("customerCompanyType");


        private static String campID = PropertyLoader.loadProperty("campaignID");


        private static String prodType = PropertyLoader.loadProperty("productType");

        private static String amount_ = PropertyLoader.loadProperty("amount");

        private static String purpose_ = PropertyLoader.loadProperty("purpose");

        private static String companyName_ = PropertyLoader.loadProperty("companyName");


        private static String companyOrganisationNumber_ = PropertyLoader.loadProperty("companyOrganisationNumber");


        private static String appliedAmount_ = PropertyLoader.loadProperty("appliedAmount");

        private static String appliedCreditLimit_ = PropertyLoader.loadProperty("appliedCreditLimit");


        private static String companyType_ = PropertyLoader.loadProperty("companyType");

        private static String companyNACECode_ = PropertyLoader.loadProperty("companyNACECode");

        private static String contactName_ = PropertyLoader.loadProperty("contactName");

        private static String contactPhone_ = PropertyLoader.loadProperty("contactPhone");

        private static String contactEmail_ = PropertyLoader.loadProperty("contactEmail");

        private static String KYCInfo_ = PropertyLoader.loadProperty("KYCInfo");

        private static String KYCRisk_ = PropertyLoader.loadProperty("KYCRisk");

        private static String KYCLastUpdate_ = PropertyLoader.loadProperty("KYCLastUpdate");

        private static String account_ = PropertyLoader.loadProperty("account");

        private static String basicInterestRate_ = PropertyLoader.loadProperty("basicInterestRate");

        private static String contractualInterestRate_ = PropertyLoader.loadProperty("contractualInterestRate");

        private static String fee_ = PropertyLoader.loadProperty("fee");

        private static String ownersInformationAndGuaranteeHeader_ = PropertyLoader.loadProperty("ownersInformationAndGuaranteeHeader");

        private static String ownersInformationAndGuaranteeInfo_ = PropertyLoader.loadProperty("ownersInformationAndGuaranteeInfo");

        private static String ownersInformationAndGuaranteeName_ = PropertyLoader.loadProperty("ownersInformationAndGuaranteeName");

        private static String ownersInformationAndGuaranteePersonalNumber_ = PropertyLoader.loadProperty("ownersInformationAndGuaranteePersonalNumber");

        private static String ownersInformationAndGuaranteeOwnerGuarantee_ = PropertyLoader.loadProperty("ownersInformationAndGuaranteeOwnerGuarantee");

        private static String ownersInformationAndGuaranteeMonthlyIncome_ = PropertyLoader.loadProperty("ownersInformationAndGuaranteeMonthlyIncome");

        private static String LoanStatusKYC_ = PropertyLoader.loadProperty("LoanStatusKYC");

        private static String LoanStatusCreditRecommendation_ = PropertyLoader.loadProperty("LoanStatusCreditRecommendation");

        public Advisorytoolpage(WebDriver driver) {
            super(driver);
        }

      /*  public String confirmationHeader(){
            return header.getText();
        }*/

    }



/*

        public static void Initialize()
        {
            webDriver = WebDriverFactory.getInstance(BrowserName);
            webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            goTo("");
        }

        public static String getTitle()
        {
            return webDriver.getTitle();
        }

        public static WebDriver Driver()
        {
            return webDriver;
        }

          public static void close()
        {
            webDriver.close();
        }


    }

}


*/
       /* public static void goTo(String url)
        {
            webDriver.get(baseUrl + url);
        }
*/

