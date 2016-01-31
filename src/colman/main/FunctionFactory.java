package colman.main;

public class FunctionFactory {

	public static IBuiltInFunction getFuction(final String functionString) {
		
		IBuiltInFunction builtInFunction = null;
		
		if (functionString.matches("<%=function(date())/%>")){
			builtInFunction = new BuiltInDate();
		}
		
		return builtInFunction;
	}

}
