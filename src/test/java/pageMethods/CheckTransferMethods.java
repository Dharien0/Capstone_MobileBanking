package pageMethods;

import common.BaseMethods;
import data.mobilebanking;
import io.appium.java_client.android.AndroidDriver;
import pageLocators.BankTransferPage;
import pageLocators.CheckTransferPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class CheckTransferMethods extends BaseMethods {

    public CheckTransferMethods(AndroidDriver driver) {
        super(driver);
    }
    mobilebanking data = new mobilebanking();
    public void LoggingIn(){
        try {
            sendTextToElement(BankTransferPage.UserName,data.getUsername());
            sendTextToElement(BankTransferPage.Password,data.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        clickElement(CheckTransferPage.LoginButton);
    }

    public void ValidateCheckTransferElements(){
        assertElementDisplayed(CheckTransferPage.clickChecking);
        clickElement(CheckTransferPage.clickChecking);
        assertElementDisplayed(CheckTransferPage.Balance);
        assertElementDisplayed(CheckTransferPage.DepositAmount);
        assertElementDisplayed(CheckTransferPage.DepositButton);
    }

    public void NavigateToCheckBalancePageAndDepositAmount() throws IOException {
        double getActualValue,getExpectedValue;
        getActualValue = getBalanceValue(CheckTransferPage.Balance,"Initial Amount");
        System.out.println(getActualValue);
        try {
            System.out.println("Deposit Amount"+data.getDepositAmount());
            sendTextToElement(CheckTransferPage.DepositAmount, data.getDepositAmount());
            clickElement(CheckTransferPage.DepositButton);
            getExpectedValue = getBalanceValue(CheckTransferPage.Balance, "After Deposit");
            validateAddedValue(getActualValue, getExpectedValue, data.getDepositAmount());
        }
        catch(Exception e){

        }
    }

}
