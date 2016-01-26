package colman.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PollRequest {
	private String name;
	private String type;
	private String host;
	private String url;
	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	private List<HeaderPair> headerList = new ArrayList<HeaderPair>();
	private List<CookiePair> cookiePairList = new ArrayList<CookiePair>();

	public PollRequest(final JSONObject jsonObject) {
		this.name = (String) jsonObject.get("name");
		this.type = (String) jsonObject.get("type");
		this.host = (String) jsonObject.get("host");
		this.url = (String) jsonObject.get("url");
		
		final JSONArray headerArray = (JSONArray) jsonObject.get("header");
		
		final JSONArray urlParametersArray = (JSONArray) jsonObject
				.get("urlParameters");
		
		for (int i = 0; i < urlParametersArray.size(); i++) {
			final JSONObject parameter = (JSONObject) urlParametersArray.get(i);
			final Set keySet = parameter.keySet();
			final Iterator iterator = keySet.iterator();
			final String next = (String) iterator.next();
			final String value = (String) parameter.get(next);
			final NameValuePair newNameValuePair = new BasicNameValuePair(next, value);
			urlParameters.add(newNameValuePair);
		}
		
		final JSONArray cookieArray = (JSONArray) jsonObject.get("cookie");

	}

}
