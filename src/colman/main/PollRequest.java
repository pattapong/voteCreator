package colman.main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PollRequest {
	private String name;
	private String type;
	private String host;
	private String url;
	private String domain;
	private String path;
	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	private Header[] headers;
	private BasicClientCookie[] cookies;
	private String resultValidateRegex;
	private boolean isSubRequest = false;
	private String resultExtractRegex;

	public PollRequest(final JSONObject jsonObject) {
		this.name = (String) jsonObject.get("name");
		this.type = (String) jsonObject.get("type");
		this.host = (String) jsonObject.get("host");
		this.url = (String) jsonObject.get("url");
		this.domain = (String) jsonObject.get("domain");
		this.path = (String) jsonObject.get("path");
		this.resultValidateRegex = ((String) jsonObject
				.get("resultValidateRegex"));
		this.resultExtractRegex = ((String) jsonObject
				.get("resultExtractRegex"));
		this.isSubRequest = (Boolean) jsonObject.get("isSubRequest");

		final JSONArray headerArray = (JSONArray) jsonObject.get("header");
		headers = new BasicHeader[headerArray.size()];
		for (int i = 0; i < headerArray.size(); i++) {
			final JSONObject parameter = (JSONObject) headerArray.get(i);
			final Set keySet = parameter.keySet();
			final Iterator iterator = keySet.iterator();
			final String key = (String) iterator.next();
			final String value = (String) parameter.get(key);
			headers[i] = new BasicHeader(key, value);

		}

		final JSONArray urlParametersArray = (JSONArray) jsonObject
				.get("urlParameters");
		for (int i = 0; i < urlParametersArray.size(); i++) {
			final JSONObject parameter = (JSONObject) urlParametersArray.get(i);
			final Set keySet = parameter.keySet();
			final Iterator iterator = keySet.iterator();
			final String next = (String) iterator.next();
			final String value = (String) parameter.get(next);

			final NameValuePair newNameValuePair = new BasicNameValuePair(next,
					value);
			urlParameters.add(newNameValuePair);
		}

		final JSONArray cookieArray = (JSONArray) jsonObject.get("cookie");
		cookies = new BasicClientCookie[cookieArray.size()];
		for (int i = 0; i < cookieArray.size(); i++) {
			final JSONObject parameter = (JSONObject) cookieArray.get(i);
			final Set keySet = parameter.keySet();
			final Iterator iterator = keySet.iterator();
			final String key = (String) iterator.next();
			final String value = (String) parameter.get(key);
			cookies[i] = new BasicClientCookie(key, value);
			cookies[i].setDomain(this.domain);
			cookies[i].setPath(this.path);
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getHost() {
		return host;
	}

	public String getUrl() {
		return url;
	}

	public HttpUriRequest getRequest() throws UnsupportedEncodingException {

		if (this.type.equalsIgnoreCase("get")) {

			final String paramString = URLEncodedUtils.format(urlParameters,
					"utf-8");

			final String url = this.url + paramString;

			final HttpGet get = new HttpGet(url);

			get.setHeaders(headers);

			return get;

		} else {
			final HttpPost post = new HttpPost(url);

			// add header
			post.setHeaders(headers);

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			return post;
		}
	}

	public BasicCookieStore getCoookies() {
		final BasicCookieStore cookieStore = new BasicCookieStore();

		cookieStore.addCookies(cookies);

		return cookieStore;
	}

	public String getResultValidateRegex() {
		return resultValidateRegex;
	}

	public List<NameValuePair> getUrlParameters() {
		return urlParameters;
	}

	public boolean isSubRequest() {
		return isSubRequest;
	}

	public String getResultExtractRegex() {
		return resultExtractRegex;
	}

}
