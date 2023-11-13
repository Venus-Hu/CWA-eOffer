package gov.gsa.sst.deletions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class DeleteMod extends Steps{
	
	private static Logger log = LoggerFactory.getLogger(DeleteMod.class);
	private DeleteProductModPage deleteProductModPage;
	private DeleteSinModPage deleteSinModPage;
	private DeleteLaborCategoryPage deleteLaborPage;
	
	public void initProduct()
	{
		if(deleteProductModPage == null)
			deleteProductModPage = new DeleteProductModPage(executionContext);
		deleteProductModPage.get();
	}
	
	public void initSin()
	{
		if(deleteSinModPage == null)
			deleteSinModPage = new DeleteSinModPage(executionContext);
		deleteSinModPage.get();
	}
	
	public void initLaborCategory(){
		if(deleteLaborPage == null)
			deleteLaborPage = new DeleteLaborCategoryPage(executionContext);
		deleteLaborPage.load();
	}
	
	@Given("^I am on the Deletions eMod \"([^\"]*)\" page$")
	public void i_am_on_the_deletions_eMod_page(String mod) throws Throwable {
		switch (mod) {
		case "Delete Product(s)":
			initProduct();	
			break;
		case "Delete SIN":
			initSin();	
			break;
		case "Delete Labor Category and/or Service Offerings":
			initLaborCategory();
			break;
		default:
			break;
		}
		
	}
	
	@When("^I complete Delete Product details successfully$")
	public void i_complete_delete_product_details_successfully(){
		deleteProductModPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
		log.info("Delete Product update complete");
	}
	
	@When("^I complete Delete SIN details successfully$")
	public void i_complete_delete_sin_details_successfully(){
		deleteSinModPage.selectSinsToDelete(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices").getAsJsonArray("sinDetails"));
		log.info("Delete SIN complete");
	}
	
	@When("I complete Delete Labor Category and/or Service Offerings details successfully")
	public void  I_complete_Delete_Labor_Category_and_Service_Offerings_details_successfully(){
		deleteLaborPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
		log.info("Delete SIN complete");
	}
}
