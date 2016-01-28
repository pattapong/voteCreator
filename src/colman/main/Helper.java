package colman.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Helper {

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

}
