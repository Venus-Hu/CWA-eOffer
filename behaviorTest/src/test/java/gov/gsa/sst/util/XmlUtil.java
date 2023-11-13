package gov.gsa.sst.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonObject;

public class XmlUtil {
	private static Logger log = LoggerFactory.getLogger(XmlUtil.class);
	private static String filePath = "src/test/resources/solXmlData/UpdatedMod_Request.xml";

	public static String generateModRequestFile(JsonObject scenarioObj) {
		String modType = scenarioObj.get("modTypes").getAsJsonArray().get(0).getAsString();
		String duns = scenarioObj.get("UEI").getAsString();
		String formalContract = scenarioObj.get("contractNumber").getAsString();
		String contract = scenarioObj.get("basicContractNumber").getAsString();
		String schedule = scenarioObj.get("scheduleNumber").getAsString();
		try {
			File fXmlFile = new File("src/test/resources/solXmlData/GSAMod_Request.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			Node modRequest = doc.getFirstChild();
			log.info("Root element :" + modRequest.getNodeName());

			// log.info("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("ModRequest");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;
				element.setAttribute("ModType", modType);
				element.setAttribute("DunsNumber", duns);
				log.info("ModType : " + element.getAttribute("ModType"));
				log.info("Duns : " + element.getAttribute("DunsNumber"));
			}

			nList = doc.getElementsByTagName("ContractInformation");
			nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;
				Node node = element.getElementsByTagName("FormalContract").item(0);
				log.info("FormalContract: " + node.getTextContent());
				node.setTextContent(formalContract);

				node = element.getElementsByTagName("ContractNumber").item(0);
				node.setTextContent(contract);
				
				node = element.getElementsByTagName("ScheduleNumber").item(0);
				node.setTextContent(schedule);
			}

			nList = doc.getElementsByTagName("ModDescriptionInformation");
			nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;
				Node node = element.getElementsByTagName("ModShortDescription").item(0);
				node.setTextContent(modType);
				log.info("ModShortDescription: " + node.getTextContent());
				
				node = element.getElementsByTagName("ModDetailDescription").item(0);
				node.setTextContent(modType);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
}
