package pageMethods;

import common.BaseMethods;
import data.mobilebanking;
import io.appium.java_client.android.AndroidDriver;
import pageLocators.BankTransferPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class BankTransferMethods extends BaseMethods {

    mobilebanking data = new mobilebanking();
    public BankTransferMethods(AndroidDriver driver) {
        super(driver);
    }
    public void LoggingIn() {
        try {
            sendTextToElement(BankTransferPage.UserName,data.getUsername());
            sendTextToElement(BankTransferPage.Password,data.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clickElement(BankTransferPage.LoginButton);
    }

    public void ValidateBankTransfer(){
        clickElement(BankTransferPage.TransferButton);
        assertElementDisplayed(BankTransferPage.TransferAmount);
        assertElementDisplayed(BankTransferPage.TransferOptions);
    }

    public void ValidateFunctionalBankTransfer() throws Exception {
        double getSavingsBalance,getCheckBalance,getCheckBalanceExpected,getSavingsBalanceExpected;
        Properties prop = new Properties();
        prop.load(Files.newInputStream(Paths.get("./config.properties")));
        sendTextToElement(BankTransferPage.TransferAmount,data.getTransferAmount());
        getCheckBalance = getBalanceValue(BankTransferPage.CheckBalance,"Initial Check Balance");
        getSavingsBalance = getBalanceValue(BankTransferPage.SavingsBalance,"Initial savings Balance");

        if(data.getValueTransferChoice().equals("SavingsToCheckingValue")) {
            clickElement(BankTransferPage.TransferOptions);
            validateIfCorrectText(BankTransferPage.CheckingToSavingsValue, "From Checking to Savings");
            validateIfCorrectText(BankTransferPage.SavingsToCheckingValue, "From Savings to Checking");
            clickElement(BankTransferPage.SavingsToCheckingValue);
            clickElement(BankTransferPage.Transfer);
            getCheckBalanceExpected = getBalanceValue(BankTransferPage.CheckBalance, "After Transfer Check Balance");
            getSavingsBalanceExpected = getBalanceValue(BankTransferPage.SavingsBalance, "After Transfer savings Balance");
            validateTransferSavingsToChecking(getCheckBalance,getSavingsBalance,getCheckBalanceExpected,getSavingsBalanceExpected,data.getTransferAmount());
        }
        else if(data.getValueTransferChoice().equals("CheckingToSavingsValue")) {
            clickElement(BankTransferPage.TransferOptions);
            validateIfCorrectText(BankTransferPage.CheckingToSavingsValue, "From Checking to Savings");
            validateIfCorrectText(BankTransferPage.SavingsToCheckingValue, "From Savings to Checking");
            clickElement(BankTransferPage.CheckingToSavingsValue);
            clickElement(BankTransferPage.Transfer);
            getCheckBalanceExpected = getBalanceValue(BankTransferPage.CheckBalance, "After Transfer Check Balance");
            getSavingsBalanceExpected = getBalanceValue(BankTransferPage.SavingsBalance, "After Transfer savings Balance");
            validateTransferCheckingToSavings(getCheckBalance,getSavingsBalance,getCheckBalanceExpected,getSavingsBalanceExpected,data.getTransferAmount());
        }

    }

}
