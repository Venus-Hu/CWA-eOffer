package gov.gsa.sst.offerslist;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

/**
 * Created by skumar on 4/27/2017.
 */
@Component
public class OffersList extends Steps{

    public OffersListPage offersListPage;

    @Given("^I am on the Offers List Page$")
    public void i_am_on_the_Offers_List_Page() throws Throwable {
        if(offersListPage == null)
        {
            offersListPage = new OffersListPage(executionContext);
        }
        offersListPage.get();
    }

    @When("^I \"([^\"]*)\" an offer$")
    public void i_an_offer(String arg1) throws Throwable {
        if(arg1.equalsIgnoreCase("create")) {
            offersListPage.createOffer(executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString());
        }else if(arg1.equalsIgnoreCase("edit"))
        {
        	offersListPage.editOffer(executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString());
        }else if(arg1.equalsIgnoreCase("delete"))
        {

        }
    }


}
