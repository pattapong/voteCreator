package colman.main;

public class RequestResultPair {

	private String requestName;
	private String result;

	public RequestResultPair(final String requestName, final String result) {
		this.setRequestName(requestName);
		this.setResult(result);
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
