$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("availableOfferings.feature");
formatter.feature({
  "line": 1,
  "name": "Add Goods/Services to the Offer",
  "description": "\nAs a vendor\nI want to add SINs to the offer\nSo that I can associate Goods/Services to the offer",
  "id": "add-goods/services-to-the-offer",
  "keyword": "Feature"
});
formatter.before({
  "duration": 21286949584,
  "status": "passed"
});
formatter.write("508 violations created in 508-violations_for_UEI_1.txt");
formatter.write("508 test was performed and No Violations found with AXE");
formatter.before({
  "duration": 55046323500,
  "status": "passed"
});
formatter.scenario({
  "line": 17,
  "name": "Available Offerings: Edit SIN successful on Available Offerings",
  "description": "",
  "id": "add-goods/services-to-the-offer;available-offerings:-edit-sin-successful-on-available-offerings",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 16,
      "name": "@all_env1"
    }
  ]
});
formatter.step({
  "line": 18,
  "name": "I am on the Available Offerings page",
  "keyword": "Given "
});
formatter.step({
  "line": 19,
  "name": "I \"add\" SIN details on the Available Offerings page",
  "keyword": "And "
});
formatter.step({
  "line": 20,
  "name": "I \"edit\" SIN details on the Available Offerings page",
  "keyword": "When "
});
formatter.step({
  "line": 21,
  "name": "the SIN details will be displayed on Available Offerings page",
  "keyword": "Then "
});
formatter.match({
  "location": "AvailableOfferings.i_am_on_the_Available_Offerings_page()"
});
formatter.result({
  "duration": 24484221625,
  "error_message": "org.openqa.selenium.TimeoutException: Expected condition failed: waiting for presence of element located by: By.id: pathwayToSuccessCertAction_pathwayAcknowledged (tried for 10 second(s) with 500 MILLISECONDS interval)\n\tat org.openqa.selenium.support.ui.WebDriverWait.timeoutException(WebDriverWait.java:80)\n\tat org.openqa.selenium.support.ui.FluentWait.until(FluentWait.java:232)\n\tat gov.gsa.sst.commonpage.SeleniumHelper.findElem_tempFix(SeleniumHelper.java:137)\n\tat gov.gsa.sst.commonpage.SeleniumHelper.clickCheckBoxById_tempFix(SeleniumHelper.java:253)\n\tat gov.gsa.sst.offerslist.OffersListPage.submitForm(OffersListPage.java:134)\n\tat gov.gsa.sst.offerslist.OffersListPage.editOffer(OffersListPage.java:221)\n\tat gov.gsa.sst.corporateinfo.CorporateInformationPage.load(CorporateInformationPage.java:67)\n\tat com.karsun.kic.tan.duke.Page.get(Page.java:16)\n\tat gov.gsa.sst.availableOfferings.AvailableOfferingsPage.load(AvailableOfferingsPage.java:66)\n\tat com.karsun.kic.tan.duke.Page.get(Page.java:16)\n\tat gov.gsa.sst.availableOfferings.AvailableOfferings.init(AvailableOfferings.java:24)\n\tat gov.gsa.sst.availableOfferings.AvailableOfferings.i_am_on_the_Available_Offerings_page(AvailableOfferings.java:29)\n\tat ✽.Given I am on the Available Offerings page(availableOfferings.feature:18)\nCaused by: org.openqa.selenium.NoSuchElementException: Cannot locate an element using By.id: pathwayToSuccessCertAction_pathwayAcknowledged\nFor documentation on this error, please visit: http://seleniumhq.org/exceptions/no_such_element.html\nBuild info: version: \u00273.4.0\u0027, revision: \u0027unknown\u0027, time: \u0027unknown\u0027\nSystem info: host: \u0027Xiaowens-Laptop.local\u0027, ip: \u0027fdf7:621:4f57:4dfa:8a0:dd64:31fb:92a6%en0\u0027, os.name: \u0027Mac OS X\u0027, os.arch: \u0027x86_64\u0027, os.version: \u002710.16\u0027, java.version: \u00271.8.0_202\u0027\nDriver info: driver.version: unknown\n\tat org.openqa.selenium.support.ui.ExpectedConditions.lambda$findElement$0(ExpectedConditions.java:883)\n\tat java.util.Optional.orElseThrow(Optional.java:290)\n\tat org.openqa.selenium.support.ui.ExpectedConditions.findElement(ExpectedConditions.java:882)\n\tat org.openqa.selenium.support.ui.ExpectedConditions.access$000(ExpectedConditions.java:44)\n\tat org.openqa.selenium.support.ui.ExpectedConditions$6.apply(ExpectedConditions.java:183)\n\tat org.openqa.selenium.support.ui.ExpectedConditions$6.apply(ExpectedConditions.java:180)\n\tat org.openqa.selenium.support.ui.FluentWait.until(FluentWait.java:209)\n\tat gov.gsa.sst.commonpage.SeleniumHelper.findElem_tempFix(SeleniumHelper.java:137)\n\tat gov.gsa.sst.commonpage.SeleniumHelper.clickCheckBoxById_tempFix(SeleniumHelper.java:253)\n\tat gov.gsa.sst.offerslist.OffersListPage.submitForm(OffersListPage.java:134)\n\tat gov.gsa.sst.offerslist.OffersListPage.editOffer(OffersListPage.java:221)\n\tat gov.gsa.sst.corporateinfo.CorporateInformationPage.load(CorporateInformationPage.java:67)\n\tat com.karsun.kic.tan.duke.Page.get(Page.java:16)\n\tat gov.gsa.sst.availableOfferings.AvailableOfferingsPage.load(AvailableOfferingsPage.java:66)\n\tat com.karsun.kic.tan.duke.Page.get(Page.java:16)\n\tat gov.gsa.sst.availableOfferings.AvailableOfferings.init(AvailableOfferings.java:24)\n\tat gov.gsa.sst.availableOfferings.AvailableOfferings.i_am_on_the_Available_Offerings_page(AvailableOfferings.java:29)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat cucumber.runtime.Utils$1.call(Utils.java:37)\n\tat cucumber.runtime.Timeout.timeout(Timeout.java:13)\n\tat cucumber.runtime.Utils.invoke(Utils.java:31)\n\tat cucumber.runtime.java.JavaStepDefinition.execute(JavaStepDefinition.java:38)\n\tat cucumber.runtime.StepDefinitionMatch.runStep(StepDefinitionMatch.java:37)\n\tat cucumber.runtime.Runtime.runStep(Runtime.java:299)\n\tat cucumber.runtime.model.StepContainer.runStep(StepContainer.java:44)\n\tat cucumber.runtime.model.StepContainer.runSteps(StepContainer.java:39)\n\tat cucumber.runtime.model.CucumberScenario.run(CucumberScenario.java:44)\n\tat cucumber.runtime.junit.ExecutionUnitRunner.run(ExecutionUnitRunner.java:91)\n\tat com.karsun.kic.tan.duke.runner.DataInjectedFeatureRunner.runChild(DataInjectedFeatureRunner.java:77)\n\tat com.karsun.kic.tan.duke.runner.DataInjectedFeatureRunner.runChild(DataInjectedFeatureRunner.java:26)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat com.karsun.kic.tan.duke.runner.DataInjectedFeatureRunner.run(DataInjectedFeatureRunner.java:84)\n\tat com.karsun.kic.tan.duke.cukes.MergedDataInjectedCucumber.runChild(MergedDataInjectedCucumber.java:140)\n\tat com.karsun.kic.tan.duke.cukes.MergedDataInjectedCucumber.runChild(MergedDataInjectedCucumber.java:34)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat com.karsun.kic.tan.duke.cukes.MergedDataInjectedCucumber.run(MergedDataInjectedCucumber.java:145)\n\tat org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:93)\n\tat org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:40)\n\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:529)\n\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:756)\n\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:452)\n\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:210)\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "add",
      "offset": 3
    }
  ],
  "location": "AvailableOfferings.i_SIN_details_on_the_Available_Offerings_page(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "edit",
      "offset": 3
    }
  ],
  "location": "AvailableOfferings.i_SIN_details_on_the_Available_Offerings_page(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "AvailableOfferings.the_SIN_details_will_be_displayed_on_Available_Offerings_page()"
});
formatter.result({
  "status": "skipped"
});
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 852723792,
  "status": "passed"
});
});