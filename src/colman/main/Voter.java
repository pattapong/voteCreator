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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

public class Voter extends Thread {

	private static boolean sendGet() throws Exception {

		final long sleepTime = (long) (Math.random() * Creator.interval);
		Thread.sleep(sleepTime);

		final List<NameValuePair> urlParameters = getUrlParameters();

		final BasicCookieStore cookieStore = new BasicCookieStore();

		final String paramString = URLEncodedUtils.format(urlParameters,
				"utf-8");

		Creator.url = Creator.url + paramString;

		final HttpGet get = new HttpGet(Creator.url);

		get.setHeader("User-Agent", "Mozilla/5.0");

		final HttpClient client = HttpClientBuilder.create()
				.setDefaultCookieStore(cookieStore).build();

		final HttpResponse response = client.execute(get);

		final StringBuffer result = getResponseResult(response);

		if (result.toString().contains("Thank you for voting")) {
			System.out.println("Vote counted. Thanks for your vote.");
			final String voteCount = Voter.getVoteCount(result.toString());
			if (!voteCount.equalsIgnoreCase("")) {
				System.out.println(voteCount);
			}
		} else {
			Thread.sleep((long) (Math.random() * 100000));
			System.out.println("Vote failed. Retry in " + sleepTime
					+ " milliseconds");
			System.out.println(result.toString());
		}
		return true;
	}

	private static StringBuffer getResponseResult(final HttpResponse response)
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

	private static List<NameValuePair> getUrlParameters() {
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

	public void run() {

		try {
			do {
				sendGet();
			} while (true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean sendPost(final String url) throws Exception {

		final HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("User-Agent", "Mozilla/5.0");

		final List<NameValuePair> urlParameters = getUrlParameters();

		final BasicCookieStore cookieStore = new BasicCookieStore();

		final BasicClientCookie cookie = new BasicClientCookie("_gat", "1");

		cookie.setDomain("");

		cookie.setPath("/");

		cookieStore.addCookie(cookie);

		final HttpClient client = HttpClientBuilder.create()
				.setDefaultCookieStore(cookieStore).build();

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		final HttpResponse response = client.execute(post);

		StringBuffer result = getResponseResult(response);

		System.out.println(result.toString());
		if (result.toString().contains("success"))
			return true;

		return false;
	}
}
