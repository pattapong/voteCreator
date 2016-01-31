package colman.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

public class Voter extends Thread {

	private void executeRequest() throws ClientProtocolException, IOException,
			InterruptedException {

		final List<RequestResultPair> requestResultPair = new ArrayList<RequestResultPair>();

		executeSubRequest(requestResultPair);

		executeMainRequest(requestResultPair);
	}

	private void executeSubRequest(
			final List<RequestResultPair> requestResultPair)
			throws UnsupportedEncodingException, ClientProtocolException,
			IOException {

		final ConfigReader configReader = ConfigReader.getInstance();

		final Poll poll = configReader.getPoll();

		final List<PollRequest> subRequestList = poll.getSubPollRequestList();

		for (final PollRequest subRequest : subRequestList) {

			// TODO Replace function with built in function
			Helper.substituteUrlParametersWithBuiltInFunction(subRequest
					.getUrlParameters());

			final String result = Helper.execute(subRequest);

			String pattern = subRequest.getResultExtractRegex();

			final String extractedResult = Helper
					.extractResult(pattern, result);

			requestResultPair.add(new RequestResultPair(subRequest.getName(),
					extractedResult));
		}

	}

	private void executeMainRequest(
			final List<RequestResultPair> requestResultPair)
			throws InterruptedException, UnsupportedEncodingException,
			IOException, ClientProtocolException {

		final ConfigReader configReader = ConfigReader.getInstance();

		final Poll poll = configReader.getPoll();

		final List<PollRequest> mainRequestList = poll.getMainPollRequestList();

		for (final PollRequest mainRequest : mainRequestList) {

			Thread.sleep(configReader.getInterval());

			Helper.subStituteUrlParametersWithRequestResultPair(
					mainRequest.getUrlParameters(), requestResultPair);

			final String result = Helper.execute(mainRequest);

			final String regex = mainRequest.getResultValidateRegex();

			if (Helper.validate(regex, result)) {
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
