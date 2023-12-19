package scripts;

import common.BaseClass;
import org.testng.annotations.Test;
import pageMethods.LoginMethods;
import utils.ExtentReportsUtil;

import java.io.IOException;

public class MB01_ValidateLoginPage extends BaseClass {
    @Test
    public void MB_01() {
        ExtentReportsUtil.logstep("MB_01 - Validate Login Page");
        LoginMethods lm = new LoginMethods(driver);
        ExtentReportsUtil.info("Validate Elements are Present and functional");
        lm.validateLoginPage();
        try {
            lm.validateFunctional();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
