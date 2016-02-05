package colman.main;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigReaderTest {

	ConfigReader configReader;

	@Before
	public void setUp() throws Exception {
		configReader = ConfigReader.getInstance();
		configReader.read("./test/colman/main/sample.conf");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadConfigFile() {
		final Poll poll = configReader.getPoll();
		assertTrue(poll.getAllPollRequestList().size() == 2);
	}

	@Test
	public void testGetNumThread() {
		assertTrue(configReader.getNumOfThread() == 10);
	}

	@Test
	public void testGetInterval() {
		assertTrue(configReader.getInterval() > 0);
	}
}
