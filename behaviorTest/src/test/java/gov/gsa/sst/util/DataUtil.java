package gov.gsa.sst.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import comment.ExecutionContext;

import gov.gsa.sst.common.GetUEIandContract;
import gov.gsa.sst.dunspage.DunsPage;
import gov.gsa.sst.util.exceptions.TestExecutionException;

public class DataUtil {
	private static Logger log = LoggerFactory.getLogger(DataUtil.class);
	private static final String PAGE_OBJECT_ELEMENTS_FILE = "src/test/resources/data/page_object_elements.json";

	/**
	 * Parses and collect the "filename" property of the JSON objects in a
	 * JSON object array with format as below:
	 *
	 * 	[{
	 *		"type": "Order Form for Past Performance Eval",
	 *		"action": "upload",
	 *		"filename": "exampleDir/exampleFile.xls"
	 *	}, ...]
	 *
	 * @param jsonArray
	 * @return a filename List
	 */
	public static List<String> getFilenames(JsonArray jsonArray) {
		List <String> filenames = new ArrayList<>();

		Iterator<JsonElement> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JsonObject documentObject = iterator.next().getAsJsonObject();
			if (!documentObject.has("filename")) {
				log.error("Expected property 'filename' does not exist in object " + documentObject.toString());
				continue;
			}

			String filename = documentObject.get("filename").getAsString();
			if (filename.contains("/")) {
				filenames.add(getFilename(filename));
			} else {
				filenames.add(filename);
			}
		}

		return filenames;
	}

	/**
	 * Parse a file-path string such as "a/b/c/file" and returns the
	 * filename (e.g. 'file') which is the last element
	 *
	 * @param filename
	 * @return
	 */
	public static String getFilename(String filename) {
		String[] filenameParts = filename.split("/");
		return filenameParts[filenameParts.length - 1];
	}


	/**
	 * Get the DUNS value for the currently running scenario
	 *
	 * DUNS is defined in the test scenario data but can be overridden
     * by the value in Application.properties if 'parallel.execution'
     * in driver.properties is set to 'true'
	 *
	 * @return DUNS for current scenario
	 */
	public static String getUEI(ExecutionContext executionContext) {
//		String scenarioDataDUNS = executionContext.getCurrentScenarioObj().get("DUNS").getAsString().trim();
//		String applicationPropertiesDUNS = LoadProperties.getProperty("eoffer.duns").trim();
		String scenarioDataUEI = executionContext.getCurrentScenarioObj().get("UEI").getAsString().trim();
//		String applicationPropertiesDUNS = LoadProperties.getProperty("eoffer.duns").trim();
		
		if(scenarioDataUEI.equalsIgnoreCase("commentUEI"))	{
			scenarioDataUEI = LoadProperties.getProperty("comment.UEI");
		}
		scenarioDataUEI = GetUEIandContract.getUEIByFilter(scenarioDataUEI);
//		String parallelExecution = LoadProperties.getProperty("parallel.execution").trim();

//		if (parallelExecution != null && parallelExecution.equalsIgnoreCase("true")) {
//			if (applicationPropertiesDUNS == null || applicationPropertiesDUNS.isEmpty()) {
//				throw new RuntimeException("DUNS value in Application.properties cannot be null or empty for parallel execution");
//			}
//
//			log.info("Execute scenario with DUNS from property file: '"
//					+ applicationPropertiesDUNS + "'");
//			return applicationPropertiesDUNS;
//		}
//		log.info("Execute scenario with DUNS from data file: '"
//				+ scenarioDataUEI + "'");
		log.info("Execute scenario with UEI from data file: '" + scenarioDataUEI + " ' ");
		return scenarioDataUEI;
	}
	
	/**
	 * Inspect a PDF document to contain the specified text on a given
	 * page. Check is limited to the text being found on the page and
	 * does not check location of the line on the page.
	 *
	 * @param fileName
	 * @param pageNumber
	 * @param expectedLines
	 * @return map with validation results
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static Map<String, Boolean> doesPDFPageContainsLines(String fileName, int pageNumber, JsonArray expectedLines) throws InvalidPasswordException, IOException {
		Map<String, Boolean> resultMap = new HashMap<>();
		log.info("Reading PDF document from download location");

		File filePath = new File(DownloadUtil.setUpDownloadLocation().getPath()+ File.separator + fileName);
		PDDocument document = PDDocument.load(filePath);

		if (pageNumber > document.getNumberOfPages()) {
			throw new TestExecutionException("Specified page number does not exist in document!");
		}

		PDFTextStripper reader = new PDFTextStripper();
		reader.setStartPage(pageNumber);
		reader.setEndPage(pageNumber);

		String pageContent = reader.getText(document);
		for (JsonElement expectedLine : expectedLines) {
			log.info("Checking for expected line: " + expectedLine.getAsString() );
			Pattern pattern = Pattern.compile(expectedLine.getAsString());
			Matcher matcher = pattern.matcher(pageContent);

			String key = "file: " + fileName +", page: "+pageNumber+", expected line: "+expectedLine.getAsString();
			resultMap.put(key, matcher.find());
		}
		document.close();

		return resultMap;
	}

	/**
	 * Returns a map of DOM elements by tag (e.g. li, span, div etc.) that should
	 * be found on a page object and where each element may contain a list
	 * of possible text values.
	 *
	 * @param pageObjectName
	 * @return
	 */
	public static Map<String, List<String>> getPageObjectElements(String pageObjectName) {
		Map<String, List<String>> elementMap = new HashMap<>();
		List<String> textList = new ArrayList<>();
		try {
			JsonReader reader = new JsonReader(new FileReader(PAGE_OBJECT_ELEMENTS_FILE));
			reader.beginObject();
			while (reader.hasNext()) {
				if (reader.peek().compareTo(JsonToken.NAME) == 0) {
					if (reader.nextName().contains(pageObjectName)) {
						reader.beginObject();
						while (reader.hasNext()) {
							String elementType = reader.nextName();
							reader.beginArray();

							textList = new ArrayList<>();
							while (reader.hasNext()) {
								textList.add(reader.nextString());
							}
							reader.endArray();
							// Collect all text entries for page elements
							elementMap.put(elementType, textList);
						}
						reader.endObject();
					} else {
						reader.skipValue();
					}
				}
			}
			reader.endObject();
			reader.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return elementMap;
	}

	/**
	 * Returns formatted date string
	 * @param dateType Start or End
	 * @return
	 */
	public static String getCurrentDate(String dateType) {
		if(dateType.contains("Start"))
			return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
		else
			return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	}

	/**
	 * Returns formatted date string
	 * @param yearsAgo
	 * @return
	 */
	public static String getPastYearDate(int yearsAgo) {
		return LocalDate.now().minusYears(yearsAgo).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	}

	/**
	 * Read a file specified by the file Path
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath){
		StringBuilder sb = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filePath));

			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {

				sb.append(sCurrentLine);
			}
		} catch (Exception e) {
			log.error("Exception reading file: " + e.getMessage());
		}
		String modRequestXml = sb.toString();
		return modRequestXml;
	}
}
