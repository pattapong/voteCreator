package colman.main;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PollTest {

	private ConfigReader configReader;

	@Before
	public void setUp() throws Exception {
		configReader = ConfigReader.getInstance();
		configReader.read("./test/colman/main/sample.conf");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllPollRequest() {
		Poll poll = configReader.getPoll();
		final List<PollRequest> allRequests = poll.getAllPollRequestList();
		assertTrue(allRequests.size() == 2);
	}

	@Test
	public void testGetMainPollRequest() {
		Poll poll = configReader.getPoll();
		final List<PollRequest> allRequests = poll.getMainPollRequestList();
		assertTrue(allRequests.size() == 1);
	}
	
	@Test
	public void testGetSubPollRequest() {
		Poll poll = configReader.getPoll();
		final List<PollRequest> subRequests = poll.getSubPollRequestList();
		assertTrue(subRequests.size() == 1);
	}
}
