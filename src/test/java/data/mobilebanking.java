package data;

import common.Utils;



    public class mobilebanking extends Utils {

        static String excelFile = System.getProperty("user.dir").replace("\\", "/") + "/src/test/resources/Testdata/TestData.xlsx";
        static String workSheet = "mobileApp";
        static String countScriptToString;

        public String getValueTransferChoice() throws Exception{
            setExcelFile(excelFile, workSheet);
            return getCellData(2, 2);
        }

        public String getTransferAmount() throws Exception{
            setExcelFile(excelFile, workSheet);
            return getCellData(2, 3);
        }

        public String getUsername() throws Exception{
            setExcelFile(excelFile, workSheet);
            return getCellData(1, 6);
        }
        public String getPassword() throws Exception{
            setExcelFile(excelFile, workSheet);
            return getCellData(2, 6);
        }

        public String getDepositAmount() throws Exception{
            setExcelFile(excelFile, workSheet);
            return getCellData(6, 2);
        }

        public String getWithdrawalAmount() throws Exception{
            setExcelFile(excelFile, workSheet);
            return getCellData(6, 6);
        }

}
