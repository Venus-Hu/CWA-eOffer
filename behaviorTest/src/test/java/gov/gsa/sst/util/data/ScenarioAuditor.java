package gov.gsa.sst.util.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Extracts scenario names from feature files in directory(ies) and from the
 * specified JSON data file(s). It will then print:
 * 
 * 1) Unique scenario names collected from all files 
 * 2) Name of file(s) where scenario is found along with a 
 * count of how many times it appears in each file
 * 
 * The information will help find inconsistencies across feature files and
 * corresponding data file.
 * 
 * @param args
 *            path/to/featuresDirectory1, ... ,path/to/featuresDirectoryN
 *            path/to/data1.json, ... ,path/to/dataN.json
 */
public class ScenarioAuditor {
	private static Logger logger = LoggerFactory.getLogger(ScenarioAuditor.class);
	private static Map<String, Scenario> scenarios = new HashMap<>();
	private static List<String> dataFiles;

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Example Usage: path/to/featuresDir1,path/to/featureDir2 path/to/data1.json,path/to/data2.json");
		}
		
		String[] featureDirectories = args[0].split(",");
		for (String featureDir : featureDirectories) {
			ScenarioAuditor.getScenariosFromFeatures(featureDir);
		}
		
		dataFiles = Arrays.asList(args[1].split(","));
		for (String dataFile : dataFiles) {
			ScenarioAuditor.getScenariosFromData(dataFile);
		}
		
		try {
			ScenarioAuditor.printCSVReport();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ScenarioAuditor.writeReport();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Generates CSV output file with scenario names and whether or not
	 * they are present in the input file paths.
	 * 
	 * Output directory is project folder.
	 * 
	 * @throws IOException
	 */
	private static void printCSVReport() throws IOException {
		Path path = Paths.get("scenario-auditor-report.csv");
		try (BufferedWriter writer = Files.newBufferedWriter(path);
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL);) {

			csvPrinter.print("Scenario Name");
			for (String fileName : dataFiles) {
				csvPrinter.print(fileName);
			}
			csvPrinter.println();

			Iterator<String> scenarioItr = scenarios.keySet().iterator();
			while (scenarioItr.hasNext()) {
				String scenarioName = scenarioItr.next();
				csvPrinter.print(scenarioName);

				for (String fileNameHeader : dataFiles) {
					if (scenarios.get(scenarioName).fileNames.containsKey(fileNameHeader)) {
						csvPrinter.print("X");
					} else {
						csvPrinter.print("");
					}
				}
				csvPrinter.println();
			}
		}
	}

	/**
	 * Write map into JSON to console.
	 * Reference: https://sites.google.com/site/gson/streaming
	 * 
	 * @throws IOException
	 */
	private static void writeReport() throws IOException {
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out, "UTF-8"));
		writer.setIndent("  ");
		writer.beginArray();
		Gson gson = new Gson();
		scenarios.forEach((scenarioName, scenario) -> {
			gson.toJson(scenario, Scenario.class, writer);
		});
		writer.endArray();
		writer.close();
	}

	/**
	 * Read JSON data file and add to map
	 * of scenarios.
	 * 
	 * @param path
	 */
	private static void getScenariosFromData(String path) {
		try {
			JsonReader reader = new JsonReader(new FileReader(path));
			reader.beginObject();
			while (reader.hasNext()) {
				addScenario(reader.nextName(), path);
				// Skip nested JSON objects since we're
				// only interested in scenario names
				reader.skipValue();
			}
			reader.endObject();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read scenarios from feature files and
	 * add to scenario map
	 * 
	 * @param path
	 */
	private static void getScenariosFromFeatures(String path) {
		File featurePath = new File(path);
		FilenameFilter filter = (dir, name) -> {
			return dir.getAbsolutePath().contains("feature");
		};
		for (File file : featurePath.listFiles(filter)) {
			if (file.isDirectory()) {
				logger.debug("Skipping directory " + file.getName());
				continue;
			}
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.contains("Scenario:")) {
						addScenario(line.replace("Scenario:", "").trim(), file.getName());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}
		}
	}
	
	/**
	 * Adds new scenario or update previously
	 * existing scenario.
	 * 
	 * @param scenarioName
	 * @param fileName
	 */
	private static void addScenario(String scenarioName, String fileName) {
		if (!scenarios.containsKey(scenarioName)) {
			// New scenario not found before
			Scenario scenario = new ScenarioAuditor.Scenario(scenarioName);
			scenario.fileNames.put(fileName, 1);
			scenarios.put(scenario.scenarioName, scenario);
		} else {
			Scenario existingScenario = scenarios.get(scenarioName);
			if (existingScenario.fileNames.containsKey(fileName)) {
				int count = existingScenario.fileNames.get(fileName);
				existingScenario.fileNames.replace(fileName, ++count);
			} else {
				existingScenario.fileNames.put(fileName, 1);
			}
			
		}
	}

	/**
	 * Represents a scenario, the feature and/or
	 * data files where it is found, and the number
	 * of times it is found in each file.
	 * 
	 */
	static class Scenario {
		String scenarioName;
		Map<String, Integer> fileNames = new HashMap<>();
		public Scenario(String name) {
			this.scenarioName = name;
		}
	}
}
