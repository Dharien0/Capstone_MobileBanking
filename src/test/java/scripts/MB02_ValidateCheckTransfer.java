package scripts;

import common.BaseClass;
import org.testng.annotations.Test;
import pageMethods.CheckTransferMethods;
import utils.ExtentReportsUtil;

import java.io.IOException;

public class MB02_ValidateCheckTransfer extends BaseClass {
    @Test
    public void MB_02() throws IOException {
        ExtentReportsUtil.logstep("MB_02 - Validate Check Transfer Page");
        CheckTransferMethods chm = new CheckTransferMethods(driver);
        ExtentReportsUtil.info("Validate if deposit amount is updated after deposit");
        chm.LoggingIn();
        chm.ValidateCheckTransferElements();
        chm.NavigateToCheckBalancePageAndDepositAmount();

    }
}
