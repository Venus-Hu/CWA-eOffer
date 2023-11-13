package runner;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Step;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.google.gson.JsonObject;

import cucumber.runtime.CucumberException;
import cucumber.runtime.Runtime;
import cucumber.runtime.junit.ExecutionUnitRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.junit.ScenarioOutlineRunner;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberScenario;
import cucumber.runtime.model.CucumberScenarioOutline;
import cucumber.runtime.model.CucumberTagStatement;

@SuppressWarnings("rawtypes")
public class DataInjectedFeatureRunner extends ParentRunner<ParentRunner> {
    private final List<ParentRunner> children = new ArrayList<ParentRunner>();
    
    private final CucumberFeature cucumberFeature;
    private final Runtime runtime;
    private final JUnitReporter jUnitReporter;
    private Description description;
    private int countOfScenarios = 0;

    public DataInjectedFeatureRunner(CucumberFeature cucumberFeature, Runtime runtime, JUnitReporter jUnitReporter, JsonObject data) throws InitializationError {
        super(null);
        this.cucumberFeature = cucumberFeature;
        this.runtime = runtime;
        this.jUnitReporter = jUnitReporter;
        buildFeatureElementRunners(data);
    }
    
    public int getCountOfScenarios() {
    	return countOfScenarios;
    }

    @Override
    public String getName() {
        Feature feature = cucumberFeature.getGherkinFeature();
        return feature.getKeyword() + ": " + feature.getName();
    }

    @Override
    public Description getDescription() {
        if (description == null) {
            description = Description.createSuiteDescription(getName(), cucumberFeature.getGherkinFeature());
            for (ParentRunner child : getChildren()) {
                description.addChild(describeChild(child));
            }
        }
        return description;
    }

    @Override
    protected List<ParentRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(ParentRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(ParentRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    @Override
    public void run(RunNotifier notifier) {
        jUnitReporter.uri(cucumberFeature.getPath());
        jUnitReporter.feature(cucumberFeature.getGherkinFeature());
        super.run(notifier);
        jUnitReporter.eof();
    }

    private void buildFeatureElementRunners(JsonObject data) {
        for (CucumberTagStatement cucumberTagStatement : cucumberFeature.getFeatureElements()) {	
            try {
                ParentRunner featureElementRunner;
                if (cucumberTagStatement instanceof CucumberScenario) {
                	
                	int timesToAdd = 0;
                	if(data != null) {
	                	CucumberScenario scenario = (CucumberScenario) cucumberTagStatement;
	                	List<Step> steps = scenario.getSteps();
	                	
	                	
	                	for(Step s : steps) {
	                		String step = s.getName();
	                		String[] parts = step.split("\"", 1000);
	                		for(String p : parts) {
	                			
	                			if(data.get(p.trim()) != null) {
	                				if(data.get(p.trim()).isJsonArray()) {
	                					timesToAdd = data.get(p.trim()).getAsJsonArray().size();
	                				}
	                			}
	                		}
	                	}
                	}
                	
                	if(timesToAdd > 0) {
                		for(int i = 0; i < timesToAdd; i++) {
                			countOfScenarios++;
                			featureElementRunner = new ExecutionUnitRunner(runtime, (CucumberScenario) cucumberTagStatement, jUnitReporter);
                			children.add(featureElementRunner);
                		}
                	} else {
                		featureElementRunner = new ExecutionUnitRunner(runtime, (CucumberScenario) cucumberTagStatement, jUnitReporter);
                		children.add(featureElementRunner);
                		countOfScenarios++;
                	}
                    
                } else {
                    featureElementRunner = new ScenarioOutlineRunner(runtime, (CucumberScenarioOutline) cucumberTagStatement, jUnitReporter);
                    children.add(featureElementRunner);
                    countOfScenarios++;
                }
            } catch (InitializationError e) {
                throw new CucumberException("Failed to create scenario runner", e);
            }
        }
    }

}
