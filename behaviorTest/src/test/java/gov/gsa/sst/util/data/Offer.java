package gov.gsa.sst.util.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.availableOfferings.AvailableOfferingsPage;
import gov.gsa.sst.capturepricing.dataentry.DataEntryOptionPage;
import gov.gsa.sst.capturepricing.downloaduploadtemplate.DownloadUploadTemplatePage;
import gov.gsa.sst.capturepricing.edi.EdiOptionPage;
import gov.gsa.sst.contractorwarranty.ContractorWarrantyPage;
import gov.gsa.sst.delivery.DeliveryPage;
import gov.gsa.sst.discounts.DiscountsPage;
import gov.gsa.sst.exception.ExceptionPage;
import gov.gsa.sst.finalpricing.FinalPricingDocumentPage;
import gov.gsa.sst.negotiator.NegotiatorPage;
import gov.gsa.sst.productanalysisreport.ProductAnalysisReportPage;
import gov.gsa.sst.solicitationclause.BasicInfoPage;
import gov.gsa.sst.solicitationclause.CommercialSalesPracticePage;
import gov.gsa.sst.solicitationclause.OrderingInfoPage;
import gov.gsa.sst.solicitationclause.PointOfContactPage;
import gov.gsa.sst.solicitationprovision.CorporateExperiencePage;
import gov.gsa.sst.solicitationprovision.PastPerformancePage;
import gov.gsa.sst.solicitationprovision.QualityControlPage;
import gov.gsa.sst.solicitationprovision.ScaMatrixPage;
import gov.gsa.sst.solicitationprovision.Sch70RelevantExperiencePage;
import gov.gsa.sst.solicitationprovision.Section889PartAPage;
import gov.gsa.sst.solicitationprovision.Section889PartBPage;
import gov.gsa.sst.solicitationprovision.SolicitationProvisionPage;
import gov.gsa.sst.standardresponse.StandardResponsePage;
import gov.gsa.sst.subcontract.SubContractingPlanPage;
import gov.gsa.sst.upload.UploadDocumentsPage;
import gov.gsa.sst.upload.photos.UploadPhotosPage;
import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import gov.gsa.sst.wizardmanagement.specialcharges.SpecialChargesPage;
import gov.gsa.sst.wizardmanagement.specialfeatures.SpecialFeaturesPage;
import gov.gsa.sst.wizardmanagement.zonalpricing.ZonalPricingPage;

public class Offer {

	public static class Builder {
		private ExecutionContext executionContext;
		private List<Page> pageObjects = new ArrayList<>();
		private List<Page> orderSensitivePageObjects = new ArrayList<>();
		private static Logger log = LoggerFactory.getLogger(Builder.class);

		public Builder(ExecutionContext executionContext) {
			this.executionContext = executionContext;
		}

		public Builder negotiators() {
			pageObjects.add(new NegotiatorPage(executionContext));
			return this;
		}

		public Builder goodsAndServices() {
			pageObjects.add(new AvailableOfferingsPage(executionContext));
			return this;
		}

		public Builder wizardManagement() {
			if (doesDataObjectExists("wizardManagement")) {
				pageObjects.add(new WizardManagementPage(executionContext));
			}
			return this;
		}

		public Builder discounts() {
			pageObjects.add(new DiscountsPage(executionContext));
			return this;
		}

		public Builder delivery() {
			pageObjects.add(new DeliveryPage(executionContext));
			return this;
		}

		public Builder downloadUploadPricingTemplate() {
			if (doesDataObjectExists("downloadUploadTemplate"))
				pageObjects.add(new DownloadUploadTemplatePage(executionContext));
			return this;
		}

		public Builder ediOption() {
			if (doesDataObjectExists("ediOption"))
				pageObjects.add(new EdiOptionPage(executionContext));
			return this;
		}

		public Builder dataEntryOption() {
			if (doesDataObjectExists("capturePricingDataEntry"))
				pageObjects.add(new DataEntryOptionPage(executionContext));
			return this;
		}

		public Builder productAnalysisReport() {
			if (doesDataObjectExists("productAnalysisReport")) {
				pageObjects.add(new ProductAnalysisReportPage(executionContext));
			}
			return this;
		}

		public Builder finalPricingDocument() {
			if (doesDataObjectExists("finalPricingDocument")) {
				pageObjects.add(new FinalPricingDocumentPage(executionContext));
			}
			return this;
		}

		public Builder standardResponses() {
			if (doesDataObjectExists("standardResponse")) {
				pageObjects.add(new StandardResponsePage(executionContext));
			}
			return this;
		}

		public Builder solClauseBasicInformation() {
			if (doesDataObjectExists("basicInfo")) {
				pageObjects.add(new BasicInfoPage(executionContext));
			}
			return this;
		}

		public Builder exception() {
			if (doesDataObjectExists("exception")) {
				pageObjects.add(new ExceptionPage(executionContext));
			}
			return this;
		}

		public Builder subContractingPlan() {
			if (doesDataObjectExists("subkPlan")) {
				pageObjects.add(new SubContractingPlanPage(executionContext));
			}
			return this;
		}

		public Builder solClausePointOfContact() {
			if (doesDataObjectExists("pointOfContact")) {
				pageObjects.add(new PointOfContactPage(executionContext));
			}
			return this;
		}

		public Builder solClauseOrderingInformation() {
			if (doesDataObjectExists("orderingInformation")) {
				pageObjects.add(new OrderingInfoPage(executionContext));
			}
			return this;
		}

		public Builder solClauseCSP() {
			if (doesDataObjectExists("commercialSalesPractice")) {
				pageObjects.add(new CommercialSalesPracticePage(executionContext));
			}
			return this;
		}

		public Builder solProvisionCorporateExperience() {
			if (doesDataObjectExists("addCorporateExperience")) {
				pageObjects.add(new CorporateExperiencePage(executionContext));
			}
			return this;
		}

		public Builder solProvisionPastPerformance() {
			if (doesDataObjectExists("pastPerformance")) {
				pageObjects.add(new PastPerformancePage(executionContext));
			}
			return this;
		}

		public Builder solProvisionQualityControl() {
			if (doesDataObjectExists("qualityControl")) {
				pageObjects.add(new QualityControlPage(executionContext));
			}
			return this;
		}

		public Builder solProvisionRelevantExperience() {
			if (doesDataObjectExists("relevantExperience")) {
				pageObjects.add(new Sch70RelevantExperiencePage(executionContext));
			}
			return this;
		}

		public Builder solProvisionSectionPartA() {
			if (doesDataObjectExists("section889PartA")) {
				pageObjects.add(new Section889PartAPage(executionContext));
			}
			return this;
		}

		public Builder solProvisionSectionPartB() {
			if (doesDataObjectExists("section889PartB")) {
				pageObjects.add(new Section889PartBPage(executionContext));
			}
			return this;
		}

		public Builder solProvisionSCAMatrix() {
			if (doesDataObjectExists("scaLaborCategoryMatrix")) {
				pageObjects.add(new ScaMatrixPage(executionContext));
			}
			return this;
		}

		public Builder solProvisionTradeAct() {
			if (doesDataObjectExists("tradeAgreementsCompliance")) {
				pageObjects.add(new SolicitationProvisionPage(executionContext));
			}
			return this;
		}

		public Builder uploadDocuments() {
			if (doesDataObjectExists("uploadDocuments")) {
				pageObjects.add(new UploadDocumentsPage(executionContext));
			}
			return this;
		}

		public Builder uploadPhotos() {
			if (doesDataObjectExists("uploadPhotos")) {
				pageObjects.add(new UploadPhotosPage(executionContext));
			}
			return this;
		}

		public Offer build() throws Exception {
			return new Offer(this);
		}

		/**
		 * Complete page objects for selected Wizard management options
		 *
		 * @return
		 */
		public Builder completeWizardSubPages() {
			if (executionContext.getCurrentScenarioObj().has("wizardManagement")) {
				JsonObject wizObj = executionContext.getCurrentScenarioObj().getAsJsonObject("wizardManagement");
				executionContext.getCurrentScenarioObj().entrySet().forEach((option) -> {
					switch (option.getKey()) {
					case "discounts":
						if (wizObj.get("selectDollarDiscount").getAsString().equalsIgnoreCase("Yes")
								&& wizObj.get("discountLevel").getAsString().equalsIgnoreCase("Line Item")) {
							// Do nothing since Discount page will not be displayed for Line Item level
						} else
							pageObjects.add(new DiscountsPage(executionContext));
						break;
					case "delivery":
						if (wizObj != null) {
							if (wizObj.get("selectDelivery").getAsString().equalsIgnoreCase("Yes")
									&& wizObj.get("deliveryLevel").getAsString().equalsIgnoreCase("Line Item")) {
								// Do nothing since Delivery page will not be displayed for Line Item level
							} else
								pageObjects.add(new DeliveryPage(executionContext));
						} else
							pageObjects.add(new DeliveryPage(executionContext));
						break;
					case "contractorWarranty":
						pageObjects.add(new ContractorWarrantyPage(executionContext));
						break;
					case "zonalPricing":
						pageObjects.add(new ZonalPricingPage(executionContext));
						break;
					case "specialCharges":
						pageObjects.add(new SpecialChargesPage(executionContext));
						break;
					case "specialFeatures":
						pageObjects.add(new SpecialFeaturesPage(executionContext));
						break;
					}
				});
			} else {
				if (executionContext.getCurrentScenarioObj().has("discounts"))
					pageObjects.add(new DiscountsPage(executionContext));
				if (executionContext.getCurrentScenarioObj().has("delivery"))
					pageObjects.add(new DeliveryPage(executionContext));
				if (executionContext.getCurrentScenarioObj().has("contractorWarranty"))
					pageObjects.add(new ContractorWarrantyPage(executionContext));
				if (executionContext.getCurrentScenarioObj().has("zonalPricing"))
					pageObjects.add(new ZonalPricingPage(executionContext));
				if (executionContext.getCurrentScenarioObj().has("specialCharges"))
					pageObjects.add(new SpecialChargesPage(executionContext));
				if (executionContext.getCurrentScenarioObj().has("specialFeatures"))
					pageObjects.add(new SpecialFeaturesPage(executionContext));
			}
			return this;
		}

		/**
		 * Ensure all Wizard management options are set to No, so that every scenario
		 * starts with a clean slate and all Wizard subpages are cleaned up by default
		 */
		public void clearWizardOptions() {
			WizardManagementPage wizPage = new WizardManagementPage(executionContext);
			wizPage.get();
			wizPage.resetWizMgmtOptions();
		}

		/**
		 * This method needs to be tweaked to create an Offer based on data provided in
		 * a scenario
		 *
		 * @return
		 */
		public Builder createOfferPages() {
			executionContext.getCurrentScenarioObj().entrySet().forEach((option) -> {
				switch (option.getKey()) {
				case "discounts":
					pageObjects.add(new DiscountsPage(executionContext));
					break;
				case "delivery":
					pageObjects.add(new DeliveryPage(executionContext));
					break;
				case "contractorWarranty":
					pageObjects.add(new ContractorWarrantyPage(executionContext));
					break;
				case "zonalPricing":
					pageObjects.add(new ZonalPricingPage(executionContext));
					break;
				case "specialCharges":
					pageObjects.add(new SpecialChargesPage(executionContext));
					break;
				case "specialFeatures":
					pageObjects.add(new SpecialFeaturesPage(executionContext));
					break;
				}
			});
			return this;
		}

		/**
		 * Check if data exists for a page in the scenario object or not
		 *
		 * @param objectName
		 * @return
		 */
		private boolean doesDataObjectExists(String objectName) {
			if (!executionContext.getCurrentScenarioObj().has(objectName)) {
				log.info("Data does not exist for " + objectName);
				return false;
			}
			return true;
		}

	}

	private Offer(Builder builder) throws Exception {
		for (Page page : builder.pageObjects) {

			if (page instanceof NegotiatorPage) {
				page.get();
				NegotiatorPage np = (NegotiatorPage) page;
				np.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "negotiator")
						.getAsJsonArray());

			} else if (page instanceof AvailableOfferingsPage) {
				page.get();
				AvailableOfferingsPage availOfferings = (AvailableOfferingsPage) page;
				availOfferings.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "goodsAndServices")
								.getAsJsonObject());
				availOfferings.selectPreponderance(builder.executionContext.getCurrentScenarioObj()
						.getAsJsonObject("goodsAndServices").getAsJsonObject("preponderance"));

			} else if (page instanceof WizardManagementPage) {
				page.get();
				WizardManagementPage wmp = (WizardManagementPage) page;
				wmp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "wizardManagement")
						.getAsJsonObject());

			} else if (page instanceof SpecialChargesPage) {
				page.get();
				SpecialChargesPage scp = (SpecialChargesPage) page;
				scp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "specialCharges")
						.getAsJsonArray());

			} else if (page instanceof SpecialFeaturesPage) {
				page.get();
				SpecialFeaturesPage sfp = (SpecialFeaturesPage) page;
				sfp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "specialFeatures")
						.getAsJsonArray());

			} else if (page instanceof ZonalPricingPage) {
				page.get();
				ZonalPricingPage zpp = (ZonalPricingPage) page;
				zpp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "zonalPricing")
						.getAsJsonArray());

			} else if (page instanceof DownloadUploadTemplatePage) {
				page.get();
				DownloadUploadTemplatePage dutp = (DownloadUploadTemplatePage) page;
				dutp.populateForm();

			} else if (page instanceof EdiOptionPage) {
				page.get();
				EdiOptionPage edip = (EdiOptionPage) page;
				edip.populateForm();

			} else if (page instanceof DataEntryOptionPage) {
				page.get();
				DataEntryOptionPage deo = (DataEntryOptionPage) page;
				if (builder.executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry")
						.has("worksheetData"))
					deo.populateForm(builder.executionContext.getCurrentScenarioObj()
							.getAsJsonObject("capturePricingDataEntry").getAsJsonObject("worksheetData"));
				else
					deo.populatePricingModForm(builder.executionContext.getCurrentScenarioObj()
							.getAsJsonObject("capturePricingDataEntry").getAsJsonArray("pricingData"));

			} else if (page instanceof UploadPhotosPage) {
				builder.orderSensitivePageObjects.add(page);

			} else if (page instanceof DiscountsPage) {
				page.get();
				DiscountsPage dp = (DiscountsPage) page;
				dp.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "discounts").getAsJsonArray());

			} else if (page instanceof DeliveryPage) {
				page.get();
				DeliveryPage dp = (DeliveryPage) page;
				dp.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "delivery").getAsJsonArray());

			} else if (page instanceof ContractorWarrantyPage) {
				page.get();
				ContractorWarrantyPage cwp = (ContractorWarrantyPage) page;
				cwp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "contractorWarranty")
						.getAsJsonArray());

			} else if (page instanceof ProductAnalysisReportPage) {
				builder.orderSensitivePageObjects.add(page);

			} else if (page instanceof FinalPricingDocumentPage) {
				builder.orderSensitivePageObjects.add(page);

			} else if (page instanceof StandardResponsePage) {
				page.get();
				StandardResponsePage srp = (StandardResponsePage) page;
				srp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "standardResponse")
						.getAsJsonObject());

			} else if (page instanceof BasicInfoPage) {
				page.get();
				BasicInfoPage bip = (BasicInfoPage) page;
				bip.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "basicInfo")
						.getAsJsonObject());

			} else if (page instanceof PointOfContactPage) {
				page.get();
				PointOfContactPage pcp = (PointOfContactPage) page;
				pcp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "pointOfContact")
						.getAsJsonArray());

			} else if (page instanceof OrderingInfoPage) {
				page.get();
				OrderingInfoPage oip = (OrderingInfoPage) page;
				oip.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "orderingInformation")
						.getAsJsonObject());

			} else if (page instanceof CommercialSalesPracticePage) {
				page.get();
				CommercialSalesPracticePage csp = (CommercialSalesPracticePage) page;
				csp.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "commercialSalesPractice")
								.getAsJsonObject());

			} else if (page instanceof CorporateExperiencePage) {
				page.get();
				CorporateExperiencePage cep = (CorporateExperiencePage) page;
				cep.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "addCorporateExperience")
								.getAsJsonObject());

			} else if (page instanceof PastPerformancePage) {
				page.get();
				PastPerformancePage ppp = (PastPerformancePage) page;
				ppp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "pastPerformance")
						.getAsJsonObject());

			} else if (page instanceof QualityControlPage) {
				page.get();
				QualityControlPage qcp = (QualityControlPage) page;
				qcp.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "qualityControl")
						.getAsJsonObject());

			} else if (page instanceof Sch70RelevantExperiencePage) {
				page.get();
				Sch70RelevantExperiencePage rep = (Sch70RelevantExperiencePage) page;
				rep.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "relevantExperience")
						.getAsJsonArray());

			} else if (page instanceof Section889PartAPage) {
				page.get();
				Section889PartAPage spa = (Section889PartAPage) page;
				spa.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "section889PartA")
						.getAsJsonObject());

			} else if (page instanceof Section889PartBPage) {
				page.get();
				Section889PartBPage spb = (Section889PartBPage) page;
				spb.populateForm(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "section889PartB")
						.getAsJsonObject());

			} else if (page instanceof ScaMatrixPage) {
				page.get();
				ScaMatrixPage smp = (ScaMatrixPage) page;
				smp.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "scaLaborCategoryMatrix")
								.getAsJsonArray());

			} else if (page instanceof SolicitationProvisionPage) {
				page.get();
				SolicitationProvisionPage taa = (SolicitationProvisionPage) page;
				taa.completeTAA(
						builder.executionContext.getCurrentScenarioObj().getAsJsonObject("tradeAgreementsCompliance"));
			} else if (page instanceof ExceptionPage) {
				page.get();
				ExceptionPage exception = (ExceptionPage) page;
				exception.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "exception").getAsJsonArray());
			} else if (page instanceof SubContractingPlanPage) {
				page.get();
				SubContractingPlanPage subContractingPlan = (SubContractingPlanPage) page;
				JsonObject subkObj = getJsonElement(builder.executionContext.getCurrentScenarioObj(), "subkPlan")
						.getAsJsonObject();
				String isNew = subkObj.get("isNew").getAsString().equalsIgnoreCase("yes") ? "New" : "Existing";
				String planType = subkObj.get("planType").getAsString();
				subContractingPlan.selectPlan(isNew, planType);
				subContractingPlan.populateForm(
						getJsonElement(builder.executionContext.getCurrentScenarioObj(), "subkPlan").getAsJsonObject());
			} else if (page instanceof UploadDocumentsPage) {

				page.get();
				UploadDocumentsPage udp = (UploadDocumentsPage) page;
				udp.documentAction(getJsonElement(builder.executionContext.getCurrentScenarioObj(), "uploadDocuments")
						.getAsJsonArray());

			} else {
				throw new RuntimeException("Unknown page: " + page.getClass().getName());
			}
		}

		/*
		 * Arrange instantiated page objects in order. This code is needed for FPT
		 * offers. First find what types there are in the list
		 */
		List<Page> orderedPageObjects = new ArrayList<>();
		for (Page page : builder.orderSensitivePageObjects) {
			int count = orderedPageObjects.size();
			if (page instanceof UploadPhotosPage) {
				orderedPageObjects.add(0, page);
			} else if (page instanceof ProductAnalysisReportPage) {
				if (count == 0)
					orderedPageObjects.add(0, page);
				else
					orderedPageObjects.add(1, page);
			} else if (page instanceof FinalPricingDocumentPage) {
				if (count == 0)
					orderedPageObjects.add(0, page);
				else if (count == 1)
					orderedPageObjects.add(1, page);
				else
					orderedPageObjects.add(2, page);
			}
		}
		/*
		 * Execute each page object in the order set above
		 */
		for (Page page : orderedPageObjects) {
			if (page instanceof UploadPhotosPage) {
				if (builder.executionContext.getCurrentScenarioObj().has("uploadPhotos")) {
					page.get();
					((UploadPhotosPage) page).populateForm(
							builder.executionContext.getCurrentScenarioObj().get("uploadPhotos").getAsJsonArray());
				}
			} else if (page instanceof ProductAnalysisReportPage) {
				page.get();
				ProductAnalysisReportPage parPage = (ProductAnalysisReportPage) page;
				parPage.generatePAR();
				parPage.waitForPARCompletion();
				parPage.validateProductData();
			} else if (page instanceof FinalPricingDocumentPage) {
				page.get();
				FinalPricingDocumentPage fpPage = (FinalPricingDocumentPage) page;
				fpPage.generatePricingDocument();
				fpPage.waitForCompletion();
				fpPage.validatePricingDocument();
			}
		}

	}

	private JsonElement getJsonElement(JsonObject dataObject, String objectKey) {
		if (!dataObject.has(objectKey)) {
			throw new RuntimeException(
					"Test data object key '" + objectKey + "' does not exist in scenario data. Check your data");
		}
		return dataObject.get(objectKey);
	}
}
