package colman.main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CookiePairTest {

	private CookiePair cookiePair;

	@Before
	public void setUp() throws Exception {
		cookiePair = new CookiePair("a", "1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCookiePair() {
		CookiePair cookiePair = new CookiePair("c", "3");
		assertTrue(cookiePair.getKey().equalsIgnoreCase("c"));
	}

	@Test
	public void testGetKey() {
		assertTrue(cookiePair.getKey().equalsIgnoreCase("a"));
	}

	@Test
	public void testSetKey() {
		cookiePair.setKey("b");
		assertTrue(cookiePair.getKey().equalsIgnoreCase("b"));
	}

	@Test
	public void testGetValue() {
		assertTrue(cookiePair.getValue().equalsIgnoreCase("1"));
	}

	@Test
	public void testSetValue() {
		cookiePair.setValue("2");
		assertTrue(cookiePair.getValue().equalsIgnoreCase("2"));
	}

}
