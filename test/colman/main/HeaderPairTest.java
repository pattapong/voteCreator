package colman.main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HeaderPairTest {

	HeaderPair headerPair;
	@Before
	public void setUp() throws Exception {
		headerPair = new HeaderPair("a", "1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHeaderPair() {
		HeaderPair headerPair = new HeaderPair("c", "3");
		assertTrue(headerPair.getKey().equalsIgnoreCase("c"));
	}

	@Test
	public void testGetKey() {
		assertTrue(headerPair.getKey().equalsIgnoreCase("a"));
	}

	@Test
	public void testSetKey() {
		headerPair.setKey("b");
		assertTrue(headerPair.getKey().equalsIgnoreCase("b"));
	}

	@Test
	public void testGetValue() {
		assertTrue(headerPair.getValue().equalsIgnoreCase("1"));
	}

	@Test
	public void testSetValue() {
		headerPair.setValue("2");
		assertTrue(headerPair.getValue().equalsIgnoreCase("2"));
	}

}
