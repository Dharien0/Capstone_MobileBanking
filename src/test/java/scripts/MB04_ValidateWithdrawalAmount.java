package scripts;

import common.BaseClass;
import org.testng.annotations.Test;
import pageMethods.WithdrawalMethods;
import utils.ExtentReportsUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MB04_ValidateWithdrawalAmount extends BaseClass {

    @Test
    public void MB_04() throws IOException {
        ExtentReportsUtil.logstep("MB_04 - Validate Withdrawal Transfer Page and check Bank Transfer Options");
        WithdrawalMethods wdm = new WithdrawalMethods(driver);
        ExtentReportsUtil.info("Validate Withdrawal functional and validate Bank Transfer Options");
        wdm.LoggingIn();
        wdm.ValidateWithdrawalElements();
        wdm.ValidateWithdrawalFunctional();
    }
}
