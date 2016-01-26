package colman.main;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Poll {

	final List<PollRequest> pollRequestList = new ArrayList<PollRequest>();

	public Poll(final JSONObject jsonObject) {
		final JSONArray poll = (JSONArray) jsonObject.get("poll");

		for (int i = 0; i < poll.size(); i++) {
			final JSONObject request = (JSONObject) poll.get(i);
			final PollRequest newPollRequest = new PollRequest(request);
			pollRequestList.add(newPollRequest);
		}
	}
}
