package compiance;

import com.deque.axe.AXE;
import com.deque.axe.AXE.Builder;
import cucumber.api.Scenario;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class Accessibility {
	public void testAccessibility(Scenario currentScenario, WebDriver driver, String rules, String pageName, int i)
			throws IOException, Exception {
		JSONObject responseJSON = null;
		if (rules.equalsIgnoreCase("508")) {
			responseJSON = (new Builder(driver, this.getClass().getResource("/axe.min.js")))
					.options("{runOnly: {type: \"tag\",values: [\"section508\"]}}").analyze();
		} else if (rules.equalsIgnoreCase("AllRules")) {
			responseJSON = (new Builder(driver, this.getClass().getResource("/axe.min.js"))).options(
					"{runOnly: {type: \"tag\",values: [\"wcag2a\", \"wcag2aa\",\"section508\",\"best-practice\"]}}")
					.analyze();
		} else {
			responseJSON = (new Builder(driver, this.getClass().getResource("/axe.min.js"))).options(
					"{runOnly: {type: \"tag\",values: [\"wcag2a\", \"wcag2aa\",\"section508\",\"best-practice\"]}}")
					.analyze();
		}

		JSONArray violations = responseJSON.getJSONArray("violations");
		if (violations.length() != 0) {
			AXE.writeResults("build/AccessibilityReport/" + pageName + "_" + i, responseJSON);
			File file = new File(
					"build/AccessibilityReport/" + rules + "-violations_for_" + pageName + "_" + i + ".txt");
			FileUtils.writeStringToFile(file, AXE.report(violations), "UTF-16");
			++i;
			currentScenario.write("Accessibility Violations found with AXE - Report Created as " + pageName + "_" + i
					+ ".json and violations created in " + rules + "--violations_for_" + pageName + "_" + i + ".txt");
		} else {
			currentScenario.write("Accessibility test was performed and No Violations found with AXE");
		}

	}
}