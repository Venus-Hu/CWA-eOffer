package gov.gsa.sst.util.ws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.gson.JsonObject;

import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.XmlUtil;

/**
 * Client with pre-defined SOAP messages that can be re-used.
 *
 */
public class SOAPClient {
	private static Logger log = LoggerFactory.getLogger(SOAPClient.class);

	public static void createGSAMod(JsonObject jsonData) throws MalformedURLException, FileNotFoundException, IOException {
		String filePath = XmlUtil.generateModRequestFile(jsonData);
		generateMod(filePath);
	}
	
	public static void generateMod(String modRequestFile)
			throws MalformedURLException, FileNotFoundException, IOException {
		String modRequestXml = DataUtil.readFile(modRequestFile);
		if(modRequestXml != null && !modRequestXml.isEmpty()){
			StringBuilder tmp = new StringBuilder();
			tmp.append(
					"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://common.emodsmaster.eoffer.gsa.gov\">");
			tmp.append("<soapenv:Header/>");
			tmp.append("<soapenv:Body>");
			tmp.append("<com:createGSAMod soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
			tmp.append(
					"<modRequestXML xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
							+ "<![CDATA[" + modRequestXml.trim() + "]]></modRequestXML>" + "</com:createGSAMod>");
			tmp.append("</soapenv:Body>");
			tmp.append("</soapenv:Envelope>");
	
			String webUrl = LoadProperties.getProperty("web.url").replace("https", "http");
			String[] url = webUrl.split(":");
			int port = new Integer(url[2]);
			webUrl = url[0] + ":" + url[1] + ":" + (port - 1);
			log.info("Updated soap url: " + webUrl);
			SOAPClient.sendMessage(tmp.toString(), webUrl, "//EModsReader/services/EModService");
		}else 
			log.error("The input string for mod creation is empty!");
	}

	/**
	 * Deletes an existing offer using the endpoint defined in the
	 * Application.properties using keys: 'webservice.host' and
	 * 'offer.services.endpoint'
	 * 
	 * An example endpoint is:
	 * http://cpt3.karsun-csb.com:49006/offer.services/WorkingOfferUpdate
	 * 
	 * @param solicitationNumber
	 *            e.g. 2FAA-BU-050001-B
	 * @param refreshNumber
	 *            e.g. 22
	 * @param duns
	 *            the DUNS ID e.g. 123456789
	 * @throws MalformedURLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void deleteOffer(String solicitationNumber, String refreshNumber, String duns)
			throws MalformedURLException, FileNotFoundException, IOException {
		StringBuilder tmp = new StringBuilder();
		tmp.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://fas.gsa.gov/services/Offer/v1.0\">");
		tmp.append("<soapenv:Header/>");
		tmp.append("<soapenv:Body>");
		tmp.append("<v1:offerKey>");
		tmp.append("<solicitationKey>" + "<solicitationNumber>" + solicitationNumber.trim() + "</solicitationNumber>"
				+ "<solicitationRefreshNumber>" + refreshNumber.trim() + "</solicitationRefreshNumber>"
				+ "</solicitationKey>" + "<vendorKey>" + "<duns>" + duns.trim() + "</duns>" + "</vendorKey>"
				+ "</v1:offerKey>");
		tmp.append("</soapenv:Body>");
		tmp.append("</soapenv:Envelope>");
		SOAPClient.sendMessage(tmp.toString(), LoadProperties.getProperty("webservice.host"),
				LoadProperties.getProperty("offer.services.endpoint"));
	}

	private static void sendMessage(String message, String host, String endpoint)
			throws MalformedURLException, FileNotFoundException, IOException {
		log.info("Sending SOAP message to " + host + endpoint);
		log.info("SOAP Message: " + message);
		String USER_AGENT = "Mozilla/5.0";
		String result = "";

		String URLString = host + endpoint;
		URL url = new URL(URLString);

		HttpURLConnection con;
		if (URLString.contains("https")) {
			log.info("Using HTTPS client");
			con = (HttpsURLConnection) url.openConnection();
		} else {
			log.info("Using HTTP client");
			con = (HttpURLConnection) url.openConnection();
		}
		con.setRequestProperty("SOAPAction", "");
		con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(message);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		log.info("Response code: " + con.getResponseCode() + "\n response: " + con.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			result = response.toString();
		}
		log.info("SOAP server response: " + result);
		if (result.trim().isEmpty()) {
			throw new RuntimeException("SOAP server response was empty! Check server log for errors!");
		}else if (result.contentEquals("ERROR"))
			throw new RuntimeException("SOAP server response contains errors! Check server log for errors!");
		con.disconnect();
	}

	public static void main(String[] args) {

		try {
			SOAPClient.generateMod("src/test/resources/solXmlData/GSAMod_Request.xml");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}