package colman.main;

import java.util.Date;

public class BuiltInDate implements IBuiltInFunction {

	@Override
	public String execute() {
		return (new Date()).toString();
	}

}
