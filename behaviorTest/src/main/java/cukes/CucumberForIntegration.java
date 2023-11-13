package cukes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import annotations.IntegrationTestSpec;
import annotations.TestDataFiles;
import runner.IntegrationFeatureRunner;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberForIntegration extends BaseCuke<IntegrationFeatureRunner> {
	private final Logger logger = LoggerFactory.getLogger(CucumberForIntegration.class);

	public CucumberForIntegration(Class clazz) throws InitializationError, IOException {
		super(clazz);
		List<CucumberFeature> cucumberFeatures = this.runtimeOptions.cucumberFeatures(this.resourceLoader);
		TestDataFiles files = (TestDataFiles) clazz.getAnnotation(TestDataFiles.class);
		List<String> allFiles = new ArrayList();
		String[] var5 = files.files();
		int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			String dataFile = var5[var7];
			File file = new File(dataFile);
			if (file.isDirectory()) {
				Collection<File> filesInDir = FileUtils.listFiles(new File(dataFile), TrueFileFilter.INSTANCE,
						TrueFileFilter.INSTANCE);
				Iterator var11 = filesInDir.iterator();

				while (var11.hasNext()) {
					File f = (File) var11.next();
					allFiles.add(f.getAbsolutePath());
				}
			} else {
				allFiles.add(dataFile);
			}
		}

		IntegrationTestSpec itSpec = (IntegrationTestSpec) clazz.getAnnotation(IntegrationTestSpec.class);
		String itFile = itSpec.value();
		List<String> requiredScenarios = readFileAndLoadIntoList(itFile);
		List<Integer> dataSetChangeTrigger = new ArrayList();
		this.logger.info("file: " + files.files() + " " + files.files().length + " allFiles: " + allFiles.size());
		JsonObject dataObj = new JsonObject();
		Iterator var23 = allFiles.iterator();

		while (var23.hasNext()) {
			String dataFile = (String) var23.next();
			StringWriter writer = new StringWriter();
			IOUtils.copy(new FileInputStream(new File(dataFile)), writer);
			String jsonString = writer.toString();
			if (jsonString != null) {
				JsonParser parser = new JsonParser();
				dataObj = (JsonObject) parser.parse(jsonString);
				this.executionContext.getJsonTestData().add(dataObj);
			}

			int countOfScenarios = this.addChildren(cucumberFeatures, dataObj, requiredScenarios);
			int c = 0;

			int i;
			for (Iterator var16 = dataSetChangeTrigger.iterator(); var16.hasNext(); c = i) {
				i = (Integer) var16.next();
			}

			if (c == 0) {
				c = 1;
			}

			dataSetChangeTrigger.add(countOfScenarios + c);
			this.executionContext.getDataSetChangeTriggers().add(countOfScenarios + c);
		}

	}

	public static List<String> readFileAndLoadIntoList(String fileName) throws IOException {
		List<String> result = new ArrayList();
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String line;
		while ((line = br.readLine()) != null) {
			result.add(line);
		}

		br.close();
		return result;
	}

	protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
			RuntimeOptions runtimeOptions) throws InitializationError, IOException {
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
	}

	public List<IntegrationFeatureRunner> getChildren() {
		return this.children;
	}

	protected Description describeChild(IntegrationFeatureRunner child) {
		return child.getDescription();
	}

	protected void runChild(IntegrationFeatureRunner child, RunNotifier notifier) {
		child.run(notifier);
	}

	public void run(RunNotifier notifier) {
		super.run(notifier);
		this.jUnitReporter.done();
		this.jUnitReporter.close();
		this.runtime.printSummary();
	}

	private int addChildren(List<CucumberFeature> cucumberFeatures, JsonObject data, List<String> requiredScenarios)
			throws InitializationError, IOException {
		int countOfScenarios = 0;
		Iterator var5 = cucumberFeatures.iterator();

		while (var5.hasNext()) {
			CucumberFeature cucumberFeature = (CucumberFeature) var5.next();
			String featureName = cucumberFeature.getPath().substring(cucumberFeature.getPath().lastIndexOf("/") + 1)
					.replace(".feature", "").trim();
			this.logger.info("featureName: " + featureName);
			IntegrationFeatureRunner runner = new IntegrationFeatureRunner(cucumberFeature, this.runtime,
					this.jUnitReporter, data, this.getListOfScenarioForFeatures(requiredScenarios, featureName));
			countOfScenarios += runner.getCountOfScenarios();
			this.children.add(runner);
		}

		return countOfScenarios;
	}

	private List<String> getListOfScenarioForFeatures(List<String> requiredScenarios, String featureName) {
		List<String> result = new ArrayList();
		Iterator var4 = requiredScenarios.iterator();

		while (var4.hasNext()) {
			String s = (String) var4.next();
			if (s.contains(featureName)) {
				String scenarioName = s.replace(featureName, "").trim();
				result.add(scenarioName);
			}
		}

		return result;
	}

}
