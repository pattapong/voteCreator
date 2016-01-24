public class Creator {

	static int interval = 60000;
	static int numThread = 10;
	static String url = "";
	
	public static void main(String[] args) throws Exception {

		System.out.println("***********VoteCreator 1.0************");

		if (args.length > 0) {
			numThread = Integer.valueOf(args[0]);
			interval = Integer.valueOf(args[1]);
			url = String.valueOf(args[2]);
			
			for (int i = 1; i < numThread; i++) {
				System.out.println("Voter #" + i);
				Voter voter = new Voter();
				voter.start();
			}
		} else {
			System.out.println("java -jar voteCreator.jar 5 60000");
			System.out
					.println("The above sample will create 5 eligible voters and each voter will vote every 0-60 seconds");
		}
	}
}
