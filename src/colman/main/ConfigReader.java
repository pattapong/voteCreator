package colman.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigReader {

	private static ConfigReader configReader = null;

	private Poll poll = null;

	private int numOfThread = 1;

	private Integer interval = 10000;

	public static ConfigReader getInstance(final String configFile) {
		if (configReader == null) {
			configReader = new ConfigReader(configFile);
		}
		return configReader;
	}

	private ConfigReader(final String configFile) {
		final JSONParser jsonParser = new JSONParser();

		try {
			final Object obj = jsonParser.parse(new FileReader(configFile));
			final JSONObject jsonObject = (JSONObject) obj;
			this.poll = new Poll(jsonObject);
			this.numOfThread = ((Integer) jsonObject.get("numOfThread"));
			this.interval = ((Integer) jsonObject.get("interval"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public Poll getPoll() {
		return poll;
	}

	public int getNumOfThread() {
		return numOfThread;
	}

	public Integer getInterval() {
		return interval;
	}

}
