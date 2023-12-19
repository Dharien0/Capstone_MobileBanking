package scripts;

import common.BaseClass;
import org.testng.annotations.Test;
import pageMethods.BankTransferMethods;
import utils.ExtentReportsUtil;

import java.io.IOException;

public class MB03_ValidateBankTransfer extends BaseClass {

    @Test
    public void MB_03() throws Exception {
        ExtentReportsUtil.logstep("MB_03 - Validate Bank Transfer Page and check Bank Transfer Options");
        BankTransferMethods btm = new BankTransferMethods(driver);
        ExtentReportsUtil.info("Validate Elements are Present");
        btm.LoggingIn();
        btm.ValidateBankTransfer();
        btm.ValidateFunctionalBankTransfer();
    }

}
