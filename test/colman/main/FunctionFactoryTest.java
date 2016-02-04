package colman.main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FunctionFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFuction() {
		 IBuiltInFunction functionName = FunctionFactory.getFuction("<%=function(date())/%>");
		assertTrue(functionName.getFunctionName().equalsIgnoreCase("date()"));
	}

}
