package colman.main;

import static org.junit.Assert.*;

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
	public void testValidate() {
		String regex = ".*(Passed).*";
		String input = "<div> Passed </div>";
		assertTrue(Helper.validate(regex, input));
	}

	@Test
	public void testValidateWhenRegexDoesnotMatch() {
		String regex = ".*(Failed).*";
		String input = "<div> Passed </div>";
		assertFalse(Helper.validate(regex, input));
	}

	@Test
	public void testExtractResult() {
		String pattern = "<div> (.*) </div>";
		String result = "<div> 123 </div>";
		String extractResult = Helper.extractResult(pattern, result);
		assertTrue(extractResult.equalsIgnoreCase("123"));
	}

	@Test
	public void testExtractResultNotFound() {
		String pattern = "<div> (.*) </div>";
		String result = "123 </div>";
		String extractResult = Helper.extractResult(pattern, result);
		assertTrue(extractResult == null);
	}

	@Test
	public void testSubstituteUrlParameterValueWithBuiltInFunction() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("", "<%=function(date())/%>"));
		Helper.substituteUrlParameterValueWithBuiltInFunction(urlParameters);
		String name = urlParameters.get(0).getName();
		assertTrue(name != null);
	}

	@Test
	public void testSubstituteUrlParameterNameWithBuiltInFunction() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("<%=function(date())/%>", ""));
		Helper.subsituteUrlParameterNameWithBuiltInFunction(urlParameters);
		String name = urlParameters.get(0).getName();
		assertTrue(name != null);
	}

	@Test
	public void testSubstituteUrlParametersWithBuiltInFunction() {

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("<%=function(date())/%>", ""));
		Helper.substituteUrlParametersWithBuiltInFunction(urlParameters);
		String name = urlParameters.get(0).getName();
		assertTrue(name != null);
	}

	@Test
	public void testSubStituteUrlParametersWithRequestResultPair() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters
				.add(new BasicNameValuePair("n", "<%=request(requestA)/%>"));
		List<RequestResultPair> requestResultPairs = new ArrayList<RequestResultPair>();
		requestResultPairs.add(new RequestResultPair("requestA", "result"));
		Helper.subStituteUrlParametersWithRequestResultPair(urlParameters,
				requestResultPairs);
		final NameValuePair nameValuePair = urlParameters.get(0);
		assertTrue(nameValuePair.getValue().equalsIgnoreCase("result"));
	}

	@Test
	public void testExtractRequestName() {
		final String requestName = Helper
				.extractRequestName("<%=request(test)/%>)");
		assertTrue(requestName.equalsIgnoreCase("test"));
	}

	@Test
	public void testExtractRequestNameNotFound() {
		final String requestName = Helper
				.extractRequestName("<%=typo(test)/%>)");
		assertTrue(requestName == null);
	}
}
