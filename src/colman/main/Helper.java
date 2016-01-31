package colman.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class Helper {

	private static final String REQUEST_REGEX = "<%=request\\(.*\\)/%>)";
	private static final String FUNCTION_REGEX = "<%=function\\((.*)\\)/%>";

	public static StringBuffer getResponseResult(final HttpResponse response)
			throws IOException {
		final BufferedReader rd = new BufferedReader(new InputStreamReader(
				response.getEntity().getContent()));
		final StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result;
	}

	public static List<NameValuePair> getUrlParameters() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("", ""));
		return urlParameters;
	}

	public static String getVoteCount(String text) {
		final String patternString = "<Vote Name>.*?\\((.*?)\\)";
		final Pattern pattern = Pattern.compile(patternString);
		final Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	public static String extractNFactor(String result) {
		String temp = result
				.substring(result.indexOf("'") + 1, result.length());
		String temp2 = temp.substring(0, temp.indexOf("'"));
		return temp2;
	}

	public static boolean validate(final String regex, final String input) {

		final Pattern pattern = Pattern.compile(regex);
		if (!pattern.matcher(input).matches()) {
			return false;
		}
		return true;
	}

	public static String execute(final PollRequest pollRequest)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {

		final BasicCookieStore cookieStore = pollRequest.getCoookies();

		final HttpClient client = HttpClientBuilder.create()
				.setDefaultCookieStore(cookieStore).build();

		final HttpUriRequest request = pollRequest.getRequest();

		final HttpResponse response = client.execute(request);

		final StringBuffer result = Helper.getResponseResult(response);

		return result.toString();
	}

	public static String extractResult(final String pattern, final String result) {

		final Pattern r = Pattern.compile(pattern);

		final Matcher m = r.matcher(result);

		if (m.find()) {
			return m.group(0);
		}

		return null;
	}

	public static void substituteUrlParametersWithBuiltInFunction(
			final List<NameValuePair> urlParameters) {

		subsituteUrlParameterNameWithBuiltInFunction(urlParameters);

		substituteUrlParameterValueWithBuiltInFunction(urlParameters);
	}

	private static void substituteUrlParameterValueWithBuiltInFunction(
			final List<NameValuePair> urlParameters) {
		for (int i = 0; i < urlParameters.size(); i++) {

			final NameValuePair next = urlParameters.get(i);

			IBuiltInFunction function = null;

			if (next.getValue().matches(FUNCTION_REGEX)) {
				function = FunctionFactory.getFuction(next.getValue());
				final String result = function.execute();

				final NameValuePair newNameValuePair = new BasicNameValuePair(
						next.getName(), result);

				urlParameters.set(i, newNameValuePair);
			}

		}
	}

	private static void subsituteUrlParameterNameWithBuiltInFunction(
			final List<NameValuePair> urlParameters) {
		for (int i = 0; i < urlParameters.size(); i++) {

			final NameValuePair next = urlParameters.get(i);

			IBuiltInFunction function = null;

			if (next.getName().matches(FUNCTION_REGEX)) {

				function = FunctionFactory.getFuction(next.getName());

				final String result = function.execute();

				final NameValuePair newNameValuePair = new BasicNameValuePair(
						result, next.getValue());

				urlParameters.set(i, newNameValuePair);

			}

		}
	}

	public static void subStituteUrlParametersWithRequestResultPair(
			final List<NameValuePair> urlParameters,
			final List<RequestResultPair> requestResultPairs) {

		for (int i = 0; i < urlParameters.size(); i++) {
			final NameValuePair nextValuePair = urlParameters.get(i);
			if (nextValuePair.getValue().matches(REQUEST_REGEX)) {
				final String requestName = extractRequestName(nextValuePair
						.getValue());
				for (final RequestResultPair nextRequestResultPair : requestResultPairs) {
					if (nextRequestResultPair.getRequestName()
							.equalsIgnoreCase(requestName)) {
						NameValuePair newNameValuePair = new BasicNameValuePair(
								nextValuePair.getName(),
								nextRequestResultPair.getResult());
						urlParameters.set(i, newNameValuePair);
					}
				}

			}

		}

	}

	private static String extractRequestName(final String requestString) {
		
		final Pattern r = Pattern.compile(REQUEST_REGEX);

		final Matcher m = r.matcher(requestString);

		if (m.find()) {
			return m.group(0);
		}

		return null;
	}
}
