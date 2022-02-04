package org.openidl.etl;

import java.util.Map;

/**
 * A way to generalize the running rules for each line
 * @author findlayc
 *
 */
public interface LineHelper {
	
	/**
	 * Gets the fields needed for a specific line. All fields needs to be available to run the rules
	 * @return
	 */
	public Map<String, DataFactProperty> getFields();
	
	/**
	 * Get the file associated with a particular line
	 * @return
	 */
	public String getRuleFilePath();
	

}
