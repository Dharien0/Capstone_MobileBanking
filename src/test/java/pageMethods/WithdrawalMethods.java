package pageMethods;

import common.BaseMethods;
import data.mobilebanking;
import io.appium.java_client.android.AndroidDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pageLocators.BankTransferPage;
import pageLocators.CheckTransferPage;
import pageLocators.WithdrawalPage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class WithdrawalMethods extends BaseMethods {

    public WithdrawalMethods(AndroidDriver driver) throws FileNotFoundException {
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
        clickElement(WithdrawalPage.LoginButton);
    }

    public void ValidateWithdrawalElements(){
        assertElementDisplayed(WithdrawalPage.clickChecking);
        clickElement(WithdrawalPage.clickChecking);
        assertElementDisplayed(WithdrawalPage.Balance);
        assertElementDisplayed(WithdrawalPage.WithdrawalButton);
        assertElementDisplayed(WithdrawalPage.WithdrawalField);
    }

    public void ValidateWithdrawalFunctional() throws IOException {

                double getActualValue, getExpectedValue;
                getActualValue = getBalanceValue(CheckTransferPage.Balance,"initial amount");
                try {
                    System.out.println("Withdrawal Amount: " + data.getWithdrawalAmount());
                    sendTextToElement(WithdrawalPage.WithdrawalField, data.getWithdrawalAmount());
                    clickElement(WithdrawalPage.WithdrawalButton);
                    getExpectedValue = getBalanceValue(CheckTransferPage.Balance, "After withdrawal Amount");
                    validateDeductValue(getActualValue, getExpectedValue, data.getWithdrawalAmount());
                }
                catch(Exception e){

                }
            }

        }



