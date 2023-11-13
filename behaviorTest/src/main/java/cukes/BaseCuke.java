package cukes;

import comment.ExecutionContext;
import driver.WebDriverFactory;
import cucumber.api.java.ObjectFactory;
import cucumber.runtime.Backend;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Reflections;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.java.JavaBackend;
import cucumber.runtime.junit.Assertions;
import cucumber.runtime.junit.JUnitReporter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public abstract class BaseCuke<T> extends ParentRunner<T> {
	private final Logger logger = LoggerFactory.getLogger(BaseCuke.class);
	protected final Runtime runtime;
	protected final JUnitReporter jUnitReporter;
	protected final List<T> children = new ArrayList();
	protected RuntimeOptions runtimeOptions;
	protected ResourceLoader resourceLoader;
	protected ExecutionContext executionContext;

	protected BaseCuke(Class clazz) throws InitializationError {
		super(clazz);
		ClassLoader classLoader = clazz.getClassLoader();
		Assertions.assertNoCucumberAnnotatedMethods(clazz);
		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
		this.runtimeOptions = runtimeOptionsFactory.create();
		this.resourceLoader = new MultiLoader(classLoader);
		ClassFinder classFinder = new ResourceLoaderClassFinder(this.resourceLoader, classLoader);
		Reflections reflections = new Reflections(classFinder);
		ObjectFactory objectFactory = (ObjectFactory) reflections.instantiateExactlyOneSubclass(ObjectFactory.class,
				"cucumber.runtime", new Class[0], new Object[0]);
		this.logger.debug("objectFactory: " + objectFactory);
		objectFactory.start();
		JavaBackend backend = new JavaBackend(objectFactory);
		List<Backend> backends = new ArrayList();
		backends.add(backend);
		this.runtime = new Runtime(this.resourceLoader, classLoader, backends, this.runtimeOptions);
		this.jUnitReporter = new JUnitReporter(this.runtimeOptions.reporter(classLoader),
				this.runtimeOptions.formatter(classLoader), this.runtimeOptions.isStrict());

		try {
			Field of = objectFactory.getClass().getDeclaredField("beanFactory");
			this.logger.debug("of: " + of.getName());
			of.setAccessible(true);
			ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) of.get(objectFactory);
			this.executionContext = (ExecutionContext) beanFactory.getBean(ExecutionContext.class); 
			WebDriverFactory webDriverFactory = (WebDriverFactory) beanFactory.getBean(WebDriverFactory.class);
			this.executionContext.init(webDriverFactory, clazz);
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new InstantiationError("Error initiating context.");
		}
	}
}