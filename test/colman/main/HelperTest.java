package colman.main;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetResponseResult() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUrlParameters() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVoteCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testExtractNFactor() {
		fail("Not yet implemented");
	}

	@Test
	public void testValidate() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

	@Test
	public void testExtractResult() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubstituteUrlParametersWithBuiltInFunction() {

		fail("Not yet implemented");
	}

	@Test
	public void testSubStituteUrlParametersWithRequestResultPair() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("n", "<%=request(requestA)/%>"));
		List<RequestResultPair> requestResultPairs = new ArrayList<RequestResultPair>();
		requestResultPairs.add(new RequestResultPair("requestA", "result"));
		Helper.subStituteUrlParametersWithRequestResultPair(urlParameters, requestResultPairs);
		final NameValuePair nameValuePair = urlParameters.get(0);
		assertTrue(nameValuePair.getValue().equalsIgnoreCase("result"));
	}

	@Test
	public void testExtractRequestName() {
		final String requestName = Helper
				.extractRequestName("<%=request(test)/%>)");
		assertTrue(requestName.equalsIgnoreCase("test"));
	}
}
