package cukes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import annotations.TestDataFiles;
import runner.DataInjectedFeatureRunner;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergedDataInjectedCucumber extends BaseCuke<DataInjectedFeatureRunner> {
	private final Logger logger = LoggerFactory.getLogger(MergedDataInjectedCucumber.class);

	public MergedDataInjectedCucumber(Class clazz) throws InitializationError, IOException {
		super(clazz);
		List<CucumberFeature> cucumberFeatures = this.runtimeOptions.cucumberFeatures(this.resourceLoader);
		TestDataFiles files = (TestDataFiles) clazz.getAnnotation(TestDataFiles.class);
		List<String> allFiles = new ArrayList();
		String[] var5 = files.files();
		int var6 = var5.length;

		int countOfScenarios;
		String dataFile;
		for (countOfScenarios = 0; countOfScenarios < var6; ++countOfScenarios) {
			dataFile = var5[countOfScenarios];
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

		List<Integer> dataSetChangeTrigger = new ArrayList();
		this.logger.info("file: " + files.files() + " " + files.files().length + " allFiles: " + allFiles.size());
		JsonObject mergedData = new JsonObject();
		Iterator var17 = allFiles.iterator();

		while (true) {
			String jsonString;
			do {
				if (!var17.hasNext()) {
					this.executionContext.getJsonTestData().add(mergedData);
					countOfScenarios = this.addChildren(cucumberFeatures, mergedData);
					int c = 0;

					int i;
					for (Iterator var20 = dataSetChangeTrigger.iterator(); var20.hasNext(); c = i) {
						i = (Integer) var20.next();
					}

					if (c == 0) {
						c = 1;
					}

					dataSetChangeTrigger.add(countOfScenarios + c);
					this.executionContext.getDataSetChangeTriggers().add(countOfScenarios + c);
					return;
				}

				dataFile = (String) var17.next();
				StringWriter writer = new StringWriter();
				IOUtils.copy(new FileInputStream(new File(dataFile)), writer);
				jsonString = writer.toString();
			} while (jsonString == null);

			JsonParser parser = new JsonParser();
			JsonObject dataObj = (JsonObject) parser.parse(jsonString);
			Iterator var13 = dataObj.entrySet().iterator();

			while (var13.hasNext()) {
				Entry<String, JsonElement> entry = (Entry) var13.next();
				mergedData.add((String) entry.getKey(), (JsonElement) entry.getValue());
			}
		}
	}

	protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
			RuntimeOptions runtimeOptions) throws InitializationError, IOException {
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
	}

	public List<DataInjectedFeatureRunner> getChildren() {
		return this.children;
	}

	protected Description describeChild(DataInjectedFeatureRunner child) {
		return child.getDescription();
	}

	protected void runChild(DataInjectedFeatureRunner child, RunNotifier notifier) {
		child.run(notifier);
	}

	public void run(RunNotifier notifier) {
		super.run(notifier);
		this.jUnitReporter.done();
		this.jUnitReporter.close();
		this.runtime.printSummary();
	}

	private int addChildren(List<CucumberFeature> cucumberFeatures, JsonObject data)
			throws InitializationError, IOException {
		int countOfScenarios = 0;
		Iterator var4 = cucumberFeatures.iterator();

		while (true) {
			while (var4.hasNext()) {
				CucumberFeature cucumberFeature = (CucumberFeature) var4.next();
				String featureName = cucumberFeature.getPath().substring(cucumberFeature.getPath().lastIndexOf("/") + 1)
						.replace(".feature", "").trim();
				this.logger.info("featureName: " + featureName);
				if (data.get(featureName) != null && "skip".equalsIgnoreCase(data.get(featureName).getAsString())) {
					this.logger.info("Skipping");
				} else {
					DataInjectedFeatureRunner runner = new DataInjectedFeatureRunner(cucumberFeature, this.runtime,
							this.jUnitReporter, data);
					countOfScenarios += runner.getCountOfScenarios();
					this.children.add(runner);
				}
			}

			return countOfScenarios;
		}
	}

}
