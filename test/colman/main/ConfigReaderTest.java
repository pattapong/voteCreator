package colman.main;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		final ConfigReader configReader = ConfigReader
				.getInstance("./test/colman/main/sample.conf");
		final Poll poll = configReader.getPoll();
		fail("Not yet implemented");
	}

}
