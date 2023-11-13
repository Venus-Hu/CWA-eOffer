package cukes;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.model.CucumberFeature;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class PlainOldCucumber extends BaseCuke<FeatureRunner> {
	public PlainOldCucumber(Class clazz) throws InitializationError, IOException {
		super(clazz);
		List<CucumberFeature> cucumberFeatures = this.runtimeOptions.cucumberFeatures(this.resourceLoader);
		this.addChildren(cucumberFeatures);
	}

	protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
			RuntimeOptions runtimeOptions) throws InitializationError, IOException {
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
	}

	public List<FeatureRunner> getChildren() {
		return this.children;
	}

	protected Description describeChild(FeatureRunner child) {
		return child.getDescription();
	}

	protected void runChild(FeatureRunner child, RunNotifier notifier) {
		child.run(notifier);
	}

	public void run(RunNotifier notifier) {
		super.run(notifier);
		this.jUnitReporter.done();
		this.jUnitReporter.close();
		this.runtime.printSummary();
	}

	private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
		Iterator var2 = cucumberFeatures.iterator();

		while (var2.hasNext()) {
			CucumberFeature cucumberFeature = (CucumberFeature) var2.next();
			this.children.add(new FeatureRunner(cucumberFeature, this.runtime, this.jUnitReporter));
		}

	}

}
