package pageLocators;

public class BankTransferPage {
    public static final String[] UserName = {"id", "Username", "com.androiddevelopment.mobile_banking:id/usernameEditText"};

    public static  final String[] Password = {"id", "Password", "com.androiddevelopment.mobile_banking:id/passwordEditText"};

    public static  final String[] LoginButton = {"id", "Loginbutton", "com.androiddevelopment.mobile_banking:id/loginButton"};

    public static  final String[] TransferButton = {"id", "TransferButton", "com.androiddevelopment.mobile_banking:id/transferButton"};

    public  static  final String[] TransferAmount = {"id", "Transfer", "com.androiddevelopment.mobile_banking:id/TransferEditText"};

    public static  final  String[] TransferOptions = {"id", "TransferOptionsDropDown", "com.androiddevelopment.mobile_banking:id/TransferSpinner"};

    public static  final  String[] CheckingToSavingsValue = {"xpath", "Check validation for savings Value","//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"From Checking to Savings\"]"};

    public static  final  String[] SavingsToCheckingValue = {"xpath","Check validation for Checking value", "//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"From Savings to Checking\"]"};

    public static  final  String[] Transfer = {"id","Transfer", "com.androiddevelopment.mobile_banking:id/TransferButton"};


    public static  final  String[] CheckBalance = {"id","Transfer", "com.androiddevelopment.mobile_banking:id/cBalanceTextView"};

    public static  final  String[] SavingsBalance = {"id","Transfer", "com.androiddevelopment.mobile_banking:id/sBalanceTextView"};
}
