package comment;


import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
public class DukeSuite {
	@AfterClass
	public void tearDownClass() {
		System.out.println("here");
	}
}
