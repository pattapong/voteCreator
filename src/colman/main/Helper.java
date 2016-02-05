package colman.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

	private static final String REQUEST_REGEX = "<%=request\\((.*)\\)/%>";
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

	/**
	 * Validate the result, true if matched, false otherwise
	 * 
	 * @param regex
	 *            regular expression
	 * @param input
	 *            input string
	 * @return true if matched, false otherwise
	 */
	public static boolean validate(final String regex, final String input) {

		if (input.matches(regex)) {
			return true;
		}
		return false;
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
			return m.group(1);
		}

		return null;
	}

	public static void substituteUrlParametersWithBuiltInFunction(
			final List<NameValuePair> urlParameters) {

		subsituteUrlParameterNameWithBuiltInFunction(urlParameters);

		substituteUrlParameterValueWithBuiltInFunction(urlParameters);
	}

	public static void substituteUrlParameterValueWithBuiltInFunction(
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

	public static void subsituteUrlParameterNameWithBuiltInFunction(
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

	public static String extractRequestName(final String requestString) {

		final Pattern r = Pattern.compile(REQUEST_REGEX);

		final Matcher m = r.matcher(requestString);

		if (m.find()) {
			int a = m.groupCount();
			return m.group(1);
		}

		return null;
	}
}
