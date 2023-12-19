package pageMethods;


import common.BaseMethods;
import data.mobilebanking;
import io.appium.java_client.android.AndroidDriver;
import pageLocators.BankTransferPage;
import pageLocators.LoginPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class LoginMethods extends BaseMethods {

    mobilebanking data = new mobilebanking();

   public LoginMethods(AndroidDriver driver) {
       super(driver);
   }

   public void validateLoginPage(){
       assertElementDisplayed(LoginPage.LoginButton);
       assertElementDisplayed(LoginPage.UserName);
       assertElementDisplayed(LoginPage.Password);
   }

   public void validateFunctional() throws IOException {
       try {
           sendTextToElement(BankTransferPage.UserName,data.getUsername());
           sendTextToElement(BankTransferPage.Password,data.getPassword());
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       clickElement(LoginPage.LoginButton);
       assertElementDisplayed(LoginPage.CheckingButton);
       assertElementDisplayed(LoginPage.TransferButton);
       assertElementDisplayed(LoginPage.SavingsButton);

       //transfer
   }
}
