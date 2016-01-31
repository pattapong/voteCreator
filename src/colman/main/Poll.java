package colman.main;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Poll {

	final List<PollRequest> pollRequestList = new ArrayList<PollRequest>();

	public List<PollRequest> getAllPollRequestList() {
		return pollRequestList;
	}

	public Poll(final JSONObject jsonObject) {
		final JSONArray poll = (JSONArray) jsonObject.get("poll");

		for (int i = 0; i < poll.size(); i++) {
			final JSONObject request = (JSONObject) poll.get(i);
			final PollRequest newPollRequest = new PollRequest(request);
			pollRequestList.add(newPollRequest);
		}
	}

	public List<PollRequest> getMainPollRequestList() {
		final List<PollRequest> mainRequestList = new ArrayList<PollRequest>();
		for (final PollRequest next : pollRequestList) {
			if (!next.isSubRequest()) {
				mainRequestList.add(next);
			}
		}
		return mainRequestList;
	}

	public List<PollRequest> getSubPollRequestList() {
		final List<PollRequest> subRequestList = new ArrayList<PollRequest>();
		for (final PollRequest next : pollRequestList) {
			if (next.isSubRequest()) {
				subRequestList.add(next);
			}
		}
		return subRequestList;
	}
}
