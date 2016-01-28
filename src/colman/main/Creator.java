package colman.main;

public class Creator {

	static String configFile;

	public static void main(String[] args) throws Exception {

		System.out.println("***********VoteCreator 1.0************");

		if (args.length > 0) {
			configFile = String.valueOf(args[0]);
			final ConfigReader configReader = ConfigReader
					.getInstance(configFile);
			final int numThread = configReader.getNumOfThread();
			for (int i = 1; i < numThread; i++) {
				System.out.println("Voter #" + i);
				Voter voter = new Voter();
				voter.start();
			}
		} else {
			System.out.println("java -jar voteCreator.jar sample.conf");
		}
	}
}
