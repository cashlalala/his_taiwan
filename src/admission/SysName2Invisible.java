package admission;

import org.jdesktop.beansbinding.Converter;

public class SysName2Invisible extends Converter<String, Boolean> {

	@Override
	public Boolean convertForward(String arg0) {
		// TODO Auto-generated method stub
		return !arg0.equalsIgnoreCase("inp");
	}

	@Override
	public String convertReverse(Boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
