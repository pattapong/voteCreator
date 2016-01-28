package colman.main;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

public class Voter extends Thread {

	private void executeRequest() throws ClientProtocolException, IOException,
			InterruptedException {
		final ConfigReader configReader = ConfigReader.getInstance("");
		final Poll poll = configReader.getPoll();
		final List<PollRequest> requestList = poll.getPollRequestList();

		for (final PollRequest pollRequest : requestList) {
			final long sleepTime = (long) (Math.random() * configReader
					.getInterval());
			Thread.sleep(sleepTime);
			final BasicCookieStore cookieStore = pollRequest.getCoookies();
			final HttpClient client = HttpClientBuilder.create()
					.setDefaultCookieStore(cookieStore).build();
			final HttpUriRequest request = pollRequest.getRequest();
			final HttpResponse response = client.execute(request);
			final StringBuffer result = Helper.getResponseResult(response);
			final String regex = pollRequest.getResultRegexValidator();
			if (Helper.validate(regex, result.toString())) {
				System.out.println("Success");
			} else {
				System.out.println("Failed");
			}
		}
	}

	// private static boolean sendGet() throws Exception {
	//
	// final long sleepTime = (long) (Math.random() * Creator.interval);
	// Thread.sleep(sleepTime);
	//
	// final List<NameValuePair> urlParameters = Helper.getUrlParameters();
	//
	// final BasicCookieStore cookieStore = new BasicCookieStore();
	//
	// final String paramString = URLEncodedUtils.format(urlParameters,
	// "utf-8");
	//
	// Creator.url = Creator.url + paramString;
	//
	// final HttpGet get = new HttpGet(Creator.url);
	//
	// get.setHeader("User-Agent", "Mozilla/5.0");
	//
	// final HttpClient client = HttpClientBuilder.create()
	// .setDefaultCookieStore(cookieStore).build();
	//
	// final HttpResponse response = client.execute(get);
	//
	// final StringBuffer result = Helper.getResponseResult(response);
	//
	// if (result.toString().contains("Thank you for voting")) {
	// System.out.println("Vote counted. Thanks for your vote.");
	// final String voteCount = Helper.getVoteCount(result.toString());
	// if (!voteCount.equalsIgnoreCase("")) {
	// System.out.println(voteCount);
	// }
	// } else {
	// Thread.sleep((long) (Math.random() * 100000));
	// System.out.println("Vote failed. Retry in " + sleepTime
	// + " milliseconds");
	// System.out.println(result.toString());
	// }
	// return true;
	// }

	public void run() {

		try {
			do {

				executeRequest();

			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private static boolean sendPost(final String url) throws Exception {
	//
	// final HttpPost post = new HttpPost(url);
	//
	// // add header
	// post.setHeader("User-Agent", "Mozilla/5.0");
	//
	// final List<NameValuePair> urlParameters = Helper.getUrlParameters();
	//
	// final BasicCookieStore cookieStore = new BasicCookieStore();
	//
	// final BasicClientCookie cookie = new BasicClientCookie("_gat", "1");
	//
	// cookie.setDomain("");
	//
	// cookie.setPath("/");
	//
	// cookieStore.addCookie(cookie);
	//
	// final HttpClient client = HttpClientBuilder.create()
	// .setDefaultCookieStore(cookieStore).build();
	//
	// post.setEntity(new UrlEncodedFormEntity(urlParameters));
	//
	// final HttpResponse response = client.execute(post);
	//
	// StringBuffer result = Helper.getResponseResult(response);
	//
	// System.out.println(result.toString());
	// if (result.toString().contains("success"))
	// return true;
	//
	// return false;
	// }
}
