package comment;

import org.openqa.selenium.WebDriver;

public abstract class Page {
	protected WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
	}

	public Page get() {
		if (!this.isLoaded()) {
			this.load();
		}

		return this;
	}

	protected abstract void load();

	protected abstract boolean isLoaded();
}
