package org.openidl.etl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.lang3.StringUtils;
import org.openidl.etl.RulesEngineRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFactKeyedSets {

	public static final String FIELD_WILDCARD = "#any#";
	public static final String VALUE_WILDCARD = "*";

	private static final Logger logger = LoggerFactory.getLogger(DataFactKeyedSets.class);

	private Map<String, MultiMap> relational;
	private Map<String, Set<String>> keyedSets;

	private String fileLocation;
	private long timestamp;

	public DataFactKeyedSets() {
		super();
		this.relational = new HashMap<String, MultiMap>();
	}

	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation
	 *            the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the keyedSets
	 */
	public Map<String, Set<String>> getKeyedSets() {
		return keyedSets;
	}

	/**
	 * @param keyedSets
	 *            the keyedSets to set
	 */
	public void setKeyedSets(Map<String, Set<String>> keyedSets) {
		this.keyedSets = keyedSets;
	}

	/**
	 * @param relationalSets
	 *            the relationalSets to set
	 */
	public void setRelationalSets(Map<String, Set<String>> relationalSets) {
		for (Iterator<String> iterator = relationalSets.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Set values = relationalSets.get(key);
			setRelationalMaps(key, values);
		}
	}

	/**
	 * @param keyedSets
	 *            the keyedSets to set
	 */
	private final void setRelationalMaps(final String key, final Set<String> values) {
		if (key != null && values != null) {
			throw new RuntimeException("Method not implemented due to migration 1/13/2022. FC");
//			MultiHashMap maps = new MultiHashMap();
//			for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
//				String value = iterator.next();
//				String[] tokens = StringUtils.splitPreserveAllTokens(value, ",");
//				if (tokens[0] == null) {
//					tokens[0] = "";
//				}
//				maps.put(tokens[0], value);
//			}
//			this.relational.put(key, maps);
		}

	}

	/**
	 * 
	 * @param key
	 * @param keyField
	 * @param fields
	 * @return
	 */
	public String isValidRelation(String key, List<DataFactProperty> fields) {

		String results = "";

		if (StringUtils.isNotBlank(key) && fields != null && fields.size() > 0) {
			if (this.relational.containsKey(key)) {

				DataFactProperty primaryKey = fields.get(0);

				if (primaryKey != null) {

					String primaryField = primaryKey.getValue();
					MultiMap primaryMap = this.relational.get(key);

					if (primaryMap.containsKey(primaryField)) {

						Collection values = (Collection) primaryMap.get(primaryField);
						int bestMatches = 0;
						int bestWeights = 0;
						int bestMatch = 0;
						int j = 0;

						// all unmatched
						List<DataFactProperty> unmatchedFields = new ArrayList<DataFactProperty>();
						unmatchedFields.addAll(fields);
						// remove the first field
						unmatchedFields.remove(0);

						for (Iterator iterator = values.iterator(); iterator.hasNext(); j++) {

							String set = (String) iterator.next();
							String[] tokens = StringUtils.splitPreserveAllTokens(set, ",");

							int i = 0;
							// how many matched fields?
							int matches = 0;
							// significance of field
							int weight = 0;

							// copy list of fields
							List<DataFactProperty> unmatched = new ArrayList<DataFactProperty>();
							unmatched.addAll(fields);
							// remove the first field
							unmatched.remove(0);

							// iterate through the fields to see which matches
							for (Iterator<DataFactProperty> iter = fields.iterator(); iter.hasNext(); i++) {
								DataFactProperty nv = iter.next();
								if (i > 0 && i < tokens.length) {
									String tokenValue = tokens[i].trim();
									if ((nv.getValue() != null && nv.getValue().equals(FIELD_WILDCARD)) || (tokenValue.equals(VALUE_WILDCARD))
											|| tokenValue.equals(nv.getValue())) {
										matches++;
										weight += tokens.length - i;
										unmatched.remove(nv);
									}
								}
							}
							if (matches > bestMatches || (matches == bestMatches && weight > bestWeights)) {
								bestMatches = matches;
								bestWeights = weight;
								bestMatch = j;
								unmatchedFields = unmatched;
							}

						}

						// set the results
						StringBuffer combineFields = new StringBuffer();
						int k = 0;
						for (Iterator<DataFactProperty> iterator = unmatchedFields.iterator(); iterator.hasNext(); k++) {
							DataFactProperty nameValuePair = iterator.next();
							if (k > 0) {
								combineFields.append(", ");
							}
							combineFields.append(nameValuePair.getName());
						}

						results = combineFields.toString();

					} else {

						results = primaryKey.getName();

					}

				} else {

					logger.error("Primary field for relational set " + key + " not passed or null");
					throw new RulesEngineRuntimeException("Key field for relational set " + key + " not passed or null");

				}

			} else {

				logger.error("Relational set " + key + " does not exist");
				throw new RulesEngineRuntimeException("Relational set " + key + " does not exist");

			}

		} else {

			logger.error("Relational set key is blank or no fields to relate");
			throw new RulesEngineRuntimeException("Relational set key is blank or no fields to relate");

		}

		return results;

	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean isInSet(String key, String value) {
		if (this.keyedSets != null && StringUtils.isNotBlank(key)) {
			Set<String> keyedSet = this.keyedSets.get(key);
			if (keyedSet != null) {
				return keyedSet.contains(value);
			} else {
				logger.error("Keyed set " + key + " does not exist");
				throw new RulesEngineRuntimeException("Keyed set " + key + " does not exist");
			}
		}
		return false;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean hasSet(String key) {
		if (this.keyedSets != null && StringUtils.isNotBlank(key)) {
			return this.keyedSets.containsKey(key);
		}
		return false;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean isInRelatedSet(String key) {
		if (this.relational != null)
			return this.relational.containsKey(key);
		else
			return false;
	}

}
