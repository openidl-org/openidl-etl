package org.openidl.etl;

import java.io.IOException;

import org.kie.api.io.Resource;



/**
 * @author ben.hahn
 * 
 *         RulesRetriever
 */
public interface RuleRetriever {

	/**
	 * retrieve the rules based on method specified
	 * 
	 * @param key
	 *            - the rule id
	 * @return - the rule resource
	 * @throws IOException
	 */
	Resource getRules(String ruleId) throws IOException;

}
