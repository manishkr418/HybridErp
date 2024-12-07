package driverFactory;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class AppTest extends FunctionLibrary {

	String inputpath = "./FileInput/OpenCart_DataEngine.xlsx";
	String outputpath = "./FileOutPut/OpenCart_Results.xlsx";
	String TCSheet = "MasterTestCases";
	ExtentReports reports;
	ExtentTest logger;

	@Test
	public void startTest() throws Throwable {
		String Module_Status = "";
		String Module_New = "";

		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		int rc = xl.rowCount(TCSheet);
		// iterate all rows in master test case
		for (int i = 1; i <= rc; i++) {

			if (xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y")) {

				String TCModule = xl.getCellData(TCSheet, i, 1);

				for (int j = 1; j <= xl.rowCount(TCModule); j++) {

					String Description = xl.getCellData(TCModule, j, 0);
					String ObjectType = xl.getCellData(TCModule, j, 1);
					String LType = xl.getCellData(TCModule, j, 2);
					String LValue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);

					try {
						if (ObjectType.equalsIgnoreCase("startBrowser")) {
	
							driver = FunctionLibrary.startBrowser();
						}
						if (ObjectType.equalsIgnoreCase("openUrl")) {
							FunctionLibrary.openUrl();

						}
						if (ObjectType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(LType, LValue, TestData);
						}
						if (ObjectType.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(LType, LValue);
						}
						if (ObjectType.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(LType, LValue, TestData);
						}
						if (ObjectType.equalsIgnoreCase("validateTitle")) { 
							
							FunctionLibrary.validateTitle(TestData);
						}
						if (ObjectType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();

						}

						xl.setCellData(TCModule, j, 5, "pass", outputpath);
						Module_Status = "true";
					} catch (Exception e) {

						xl.setCellData(TCModule, j, 5, "fail", outputpath);
						Module_New = "false";

					}
					if (Module_Status.equalsIgnoreCase("true")) {
						xl.setCellData(TCSheet, i, 3, "pass", outputpath);
					}
					if (Module_New.equalsIgnoreCase("false")) {
						xl.setCellData(TCSheet, i, 3, "fail", outputpath);
					}

				}

			} else {
				// write as blocked into TCSheet which testcase Flag to N
				xl.setCellData(TCSheet, i, 3, "blocked", outputpath);

			}
		}

	}

}
