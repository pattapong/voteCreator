package colman.main;

import static org.junit.Assert.*;

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
	public void testReadConfigFile() {
		final ConfigReader configReader = ConfigReader
				.getInstance("./test/colman/main/sample.conf");
		final Poll poll = configReader.getPoll();
		assertTrue(poll.getPollRequestList().size() == 2);
	}

}
