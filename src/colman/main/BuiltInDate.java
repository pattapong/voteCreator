package colman.main;

import java.util.Date;

public class BuiltInDate implements IBuiltInFunction {

	@Override
	public String execute() {
		return String.valueOf((new Date()).getTime());
	}

	@Override
	public String getFunctionName() {
		return "date()";
	}

}
