

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VoterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		final String nFactor = Voter.extractNFactor("PDV_n9277369='16da6e6e49|695';PD_vote9277369(0);");
		assertTrue(nFactor.equalsIgnoreCase("16da6e6e49|695"));
	}

}
