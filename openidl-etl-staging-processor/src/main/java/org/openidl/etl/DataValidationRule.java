/**
 * 
 */
package org.openidl.etl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bhahn
 * 
 */
public class DataValidationRule implements RuleFact<DataValidationFact> {

	private static final Logger logger = LoggerFactory.getLogger(DataValidationRule.class);

	private static final String ALL = "all";
	private static final String ANY = "any";
	private static final String GREATER = ">";
	private static final String NOT_EQUALS = "<>";
	private static final String GREATER_OR_EQUALS = ">=";
	private static final String LESS_OR_EQUALS = "<=";
	private static final String LESS = "<";
	private static final String EQUALS = "=";
	private static final String REGEX = "~=";
	private static final String ODDS = "odd";
	private static final String EVENS = "even";
	private static final String DIVS = "div";
	private static final String REMAINDER = "rem";
	private static final String RANGE = "-";
	private static final String EMPTY = "blank";
	private static final String COMPARE = "comp";
	private static final String SEPARATOR = ",";
	private static final String FIELD_SUBSTR_SEPARATOR = "_";
	private static final String LIST_KEY_HOLDER = "#";
	private static final String RULESET = "ruleset";
	private static final String RELATE = "relate";

	private List<DataFactKeyedSets> keyedSets;

	private DataValidationFact input;
	private DataValidationFact output;
	private String[] substitutions;
	private String originalRecord;

	public String getOriginalRecord() {
		return originalRecord;
	}

	public void setOriginalRecord(String originalRecord) {
		this.originalRecord = originalRecord;
	}

	/**
	 * DEFAULT CTOR
	 */
	public DataValidationRule() {
		super();
	}

	/**
	 * only has 1 input param
	 * 
	 * @param input
	 */
	public DataValidationRule(DataValidationFact input) {
		this();
		this.input = input;
		this.output = input;
	}

	/**
	 * full CTOR
	 * 
	 * @param input
	 */
	public DataValidationRule(DataValidationFact input, DataValidationFact output) {
		this.input = input;
		this.output = output;
	}

	/**
	 * factory creates
	 * 
	 * @param input
	 * @return
	 */
	public DataValidationRule newInstance(DataValidationFact input) {
		DataValidationRule rule = new DataValidationRule(input);
		rule.setKeyedSets(getKeyedSets());
		return rule;
	}

	/**
	 * factory creates
	 * 
	 * @param input
	 * @return
	 */
	public DataValidationRule newInstance(DataValidationFact input, DataValidationFact output) {
		DataValidationRule rule = new DataValidationRule(input, output);
		rule.setKeyedSets(getKeyedSets());
		return rule;
	}

	/**
	 * @return the keyedSets
	 */
	public synchronized List<DataFactKeyedSets> getKeyedSets() {
		return keyedSets;
	}

	/**
	 * @param keyedSets
	 *            the keyedSets to set
	 */
	public synchronized void setKeyedSets(List<DataFactKeyedSets> keyedSets) {
		this.keyedSets = keyedSets;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public final boolean isInSet(final String key, final String value) {
		if (this.keyedSets != null && StringUtils.isNotBlank(key)) {
			for (Iterator<DataFactKeyedSets> iterator = this.keyedSets.iterator(); iterator.hasNext();) {
				DataFactKeyedSets keyedSet = iterator.next();
				if (keyedSet.hasSet(key)) {
					return keyedSet.isInSet(key, value);
				}
			}
			logger.error("Keyed set " + key + " does not exist");
			throw new RulesEngineRuntimeException("Keyed set " + key + " does not exist");
		}
		return false;
	}

	/**
	 * the eval expression method
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public final boolean isFieldvalid(final String field) {
		return this.output.isFieldValid(field);
	}

	/**
	 * the eval expression method
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public final boolean eval(final String field, final String value) {
		logger.debug("Evaluating rule: " + value + " for field " + field);
		boolean result = false;
		if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(value)) {

			try {
				DataFactProperty prop = reconcileField(field);
				String val = value.trim();
				String oper = null;
				if (val.equalsIgnoreCase(ALL) || val.equalsIgnoreCase(ANY)) {
					result = true;
				} else {
					if (val.startsWith(EQUALS)) {
						oper = val.substring(0, EQUALS.length());
					} else if (val.startsWith(NOT_EQUALS)) {
						oper = val.substring(0, NOT_EQUALS.length());
					} else if (val.startsWith(GREATER_OR_EQUALS)) {
						oper = val.substring(0, GREATER_OR_EQUALS.length());
					} else if (val.startsWith(GREATER)) {
						oper = val.substring(0, GREATER.length());
					} else if (val.startsWith(LESS_OR_EQUALS)) {
						oper = val.substring(0, LESS_OR_EQUALS.length());
					} else if (val.startsWith(LESS)) {
						oper = val.substring(0, LESS.length());
					} else if (val.startsWith(REGEX)) {
						oper = val.substring(0, REGEX.length());
					} else if (val.startsWith(ODDS)) {
						oper = val.substring(0, ODDS.length());
					} else if (val.startsWith(EVENS)) {
						oper = val.substring(0, EVENS.length());
					} else if (val.startsWith(DIVS)) {
						oper = val.substring(0, DIVS.length());
					} else if (val.startsWith(REMAINDER)) {
						oper = val.substring(0, REMAINDER.length());
					} else if (val.startsWith(COMPARE)) {
						oper = val.substring(0, COMPARE.length());
					} else if (val.startsWith(RULESET)) {
						oper = val.substring(0, RULESET.length());
					} else if (val.startsWith(RELATE)) {
						oper = val.substring(0, RELATE.length());
					} else {
						oper = "";
					}

					val = val.substring(oper.length()).trim();

					if (StringUtils.isBlank(val)) {
						val = oper;
					}

					if (StringUtils.isNotBlank(val)) {

						if (StringUtils.isBlank(oper))
							oper = EQUALS;
						result = compareValues(prop, val, oper);

					}

				}

			} catch (RulesEngineRuntimeException re) {
				throw re;
			} catch (Exception e) {
				logger.error("Error evaluating rule", e);
				throw new RulesEngineRuntimeException("Error evaluating rule", e);
			}

		}

		return result;
	}

	/**
	 * evaluates the list or range value against the field
	 * 
	 * @param match
	 * @param compare
	 * @param oper
	 * @return
	 */
	private final boolean compareValues(final DataFactProperty match, final String compare, final String oper) {

		String val = compare;
		if (compare.equals(EMPTY)) {
			val = "";
		}

		if (val.contains(SEPARATOR)) {
			if (oper.equals(EQUALS) || oper.equals(NOT_EQUALS)) {

				String tokens[] = (String[]) StringUtils.split(val, ',');
				for (int i = 0; i < tokens.length; i++) {
					boolean comp = compareValue(match, tokens[i].trim(), oper, false);
					if (comp && oper.equals(EQUALS)) {
						return true;
					} else if (!comp && oper.equals(NOT_EQUALS)) {
						return false;
					}
				}
				return oper.equals(NOT_EQUALS);
			} else if (oper.equals(GREATER) || oper.equals(GREATER_OR_EQUALS) || oper.equals(LESS) || oper.equals(LESS_OR_EQUALS)) {
				logger.error("List values can only be used with = or <> operator");
				throw new RulesEngineRuntimeException("List can only be used with = or <> operator");
			} else {
				return compareValue(match, val, oper, false);
			}
		} else {
			return compareValue(match, val, oper, false);
		}

	}

	/**
	 * evaluates single value against field
	 * 
	 * @param match
	 * @param compare
	 * @param oper
	 * @param asis
	 * @return
	 */
	private final boolean compareValue(final DataFactProperty match, final String compare, final String oper, final boolean asis) {

		String compares = compare;
		boolean inList = false;
		if (compare.equals(EMPTY)) {
			compares = "";
		}
		boolean isRange = false;
		String[] ranges = null;
		if (!asis && (oper.equals(EQUALS) || oper.equals(NOT_EQUALS))) {
			if (compares.startsWith(LIST_KEY_HOLDER) && compares.endsWith(LIST_KEY_HOLDER) && compares.length() > 2) {
				compares = compares.substring(LIST_KEY_HOLDER.length(), compares.length() - LIST_KEY_HOLDER.length());
				inList = true;
			} else {
				ranges = reconcileRange(compares);
				isRange = ranges.length > 1;
			}
		}
		String match_val = "";
		if (!isRange) {
			if (match.isFieldNumeric() && !inList && !oper.equals(REGEX) && !oper.equals(ODDS) && !oper.equals(EVENS) && !oper.equals(DIVS)
					&& !oper.equals(COMPARE) && !oper.equals(REMAINDER) && !oper.equals(RULESET) && !oper.equals(RELATE)) {
				// Number number_match = match.getNumericValue();
				// if (number_match == null) {
				// return StringUtils.isEmpty(compares);
				// }
				// Number number_compare = null;
				// try {
				// number_compare = new Double(compares);
				// } catch (NumberFormatException nfe) {
				// return false;
				// }

				Number number_match = match.getNumericValue();
				Number number_compare = null;
				if (!StringUtils.isEmpty(compares)) {
					try {
						number_compare = new Double(compares);
					} catch (NumberFormatException nfe) {
						return false;
					}
				}

				// numeric comparison for =blank, blank, <>blank
				if (number_match == null || number_compare == null) {
					if (oper.equals(NOT_EQUALS)) {
						return number_match != number_compare;
					} else if (oper.equals(EQUALS)) {
						return number_match == number_compare;
					} else {
						return false;
					}
				}

				if (oper.equals(NOT_EQUALS)) {
					return number_match.doubleValue() != number_compare.doubleValue();
				} else if (oper.equals(GREATER_OR_EQUALS)) {
					return number_match.doubleValue() >= number_compare.doubleValue();
				} else if (oper.equals(GREATER)) {
					return number_match.doubleValue() > number_compare.doubleValue();
				} else if (oper.equals(LESS_OR_EQUALS)) {
					return number_match.doubleValue() <= number_compare.doubleValue();
				} else if (oper.equals(LESS)) {
					return number_match.doubleValue() < number_compare.doubleValue();
				} else {
					return number_match.doubleValue() == number_compare.doubleValue();
				}

			} else {
				if (match != null) {
					match_val = match.getValue();
				}
			}
			if (oper.equals(NOT_EQUALS)) {
				if (inList) {
					return !isInSet(compares, match_val);
				} else {
					if (compares.length() == 0) {
						return StringUtils.isNotBlank(match_val);
					}
					return !match_val.equals(compares);
				}
			} else if (oper.equals(GREATER_OR_EQUALS)) {
				return match_val.compareTo(compares) >= 0;
			} else if (oper.equals(GREATER)) {
				return match_val.compareTo(compares) > 0;
			} else if (oper.equals(LESS_OR_EQUALS)) {
				return match_val.compareTo(compares) <= 0;
			} else if (oper.equals(LESS)) {
				return match_val.compareTo(compares) < 0;
			} else if (oper.equals(REGEX)) {
				return compareRegex(match_val, compares);
			} else if (oper.equals(ODDS)) {
				return compareDivs(match_val, compares, 2, 1);
			} else if (oper.equals(EVENS)) {
				return compareDivs(match_val, compares, 2, 0);
			} else if (oper.equals(DIVS)) {
				return compareDivs(match_val, compares);
			} else if (oper.equals(REMAINDER)) {
				return compareRems(match_val, compares);
			} else if (oper.equals(COMPARE)) {
				return compareFields(match_val, compares);
			} else if (oper.equals(RULESET)) {
				return executeRules(compares);
			} else if (oper.equals(RELATE)) {
				return compareRelatedSets(match, compares);
			} else {
				if (inList) {
					return isInSet(compares, match_val);
				} else {
					if (compares.length() == 0) {
						return StringUtils.isBlank(match_val);
					}
					return match_val.equals(compares);
				}
			}
		} else {
			if (oper.equals(EQUALS)) {
				// numeric ranges
				if (match.isFieldAlphaNumeric() && StringUtils.isNumeric(ranges[0]) && StringUtils.isNumeric(ranges[1])) {
					if (!StringUtils.isNumeric(match.getValue())) {
						return false;
					} else {
						// for strings let's also match on length as well
						if (match.getValue().trim().length() < ranges[0].length() || match.getValue().trim().length() > ranges[1].length()) {
							return false;
						}
						return NumberUtils.toInt(match.getValue()) >= NumberUtils.toInt(ranges[0])
								&& NumberUtils.toInt(match.getValue()) <= NumberUtils.toInt(ranges[1]);
					}
				}
				if (isNumeric(ranges[0]) && isNumeric(ranges[1])) {
					if (!isNumeric(match.getValue())) {
						return false;
					} else {
						return NumberUtils.toDouble(match.getValue()) >= NumberUtils.toDouble(ranges[0])
								&& NumberUtils.toDouble(match.getValue()) <= NumberUtils.toDouble(ranges[1]);
					}
				}

				if (match.isFieldNumeric()) {
					logger.error("You cannot use a non-numeric range on a numeric field - " + match.getName() + ", range: " + compares);
					throw new RulesEngineRuntimeException("You cannot use a non-numeric range on a numeric field - " + match.getName() + ", range: " + compares);
				}
				if ((match.getValue().length() < ranges[0].length() || match.getValue().length() > ranges[1].length())) {
					return false;
				}
				return compareValue(match, ranges[0], GREATER_OR_EQUALS, true) && compareValue(match, ranges[1], LESS_OR_EQUALS, true);
			} else if (oper.equals(NOT_EQUALS)) {
				// numeric ranges
				if (match.isFieldAlphaNumeric() && StringUtils.isNumeric(ranges[0]) && StringUtils.isNumeric(ranges[1])) {
					if (!StringUtils.isNumeric(match.getValue())) {
						return true;
					} else {
						// for strings let's also match on length as well
						if (match.getValue().trim().length() < ranges[0].length() || match.getValue().trim().length() > ranges[1].length()) {
							return true;
						}
						return !(NumberUtils.toInt(match.getValue()) >= NumberUtils.toInt(ranges[0]) && NumberUtils.toInt(match.getValue()) <= NumberUtils
								.toInt(ranges[1]));
					}
				}
				if (isNumeric(ranges[0]) && isNumeric(ranges[1])) {
					if (!isNumeric(match.getValue())) {
						return true;
					} else {
						return !(NumberUtils.toDouble(match.getValue()) >= NumberUtils.toDouble(ranges[0]) && NumberUtils.toDouble(match.getValue()) <= NumberUtils
								.toDouble(ranges[1]));
					}
				}

				if (match.isFieldNumeric()) {
					logger.error("You cannot use a non-numeric range on a numeric field - " + match.getName() + ", range: " + compares);
					throw new RulesEngineRuntimeException("You cannot use a non-numeric range on a numeric field - " + match.getName() + ", range: " + compares);
				}

				if ((match.getValue().length() < ranges[0].length() || match.getValue().length() > ranges[1].length())) {
					return true;
				}
				return !(compareValue(match, ranges[0], GREATER_OR_EQUALS, true) && compareValue(match, ranges[1], LESS_OR_EQUALS, true));
			} else {
				logger.error("Range values can only be used with = or <> operator");
				throw new RulesEngineRuntimeException("Range values can only be used with = or <> operator");
			}
		}

	}

	/**
	 * checks and gets hi/lo range values
	 * 
	 * @param compare
	 * @return
	 */
	private final String[] reconcileRange(final String compare) {
		String lo = null;
		String hi = null;
		if (StringUtils.isNotBlank(compare) && compare.contains(RANGE)) {
			for (int i = 0; i < compare.length(); i++) {
				if (compare.charAt(i) == RANGE.charAt(0) && i > 0) {
					lo = compare.substring(0, i);
					if (i + 1 < compare.length()) {
						hi = compare.substring(i + 1);
					}

					break;
				}
			}
		}

		if (lo != null && hi != null)
			return new String[] { lo, hi };
		else
			return new String[] { compare };

	}

	/**
	 * compares regex
	 * 
	 * @param match
	 * @param compare
	 * @return
	 */
	private final boolean compareRegex(final String match, final String compare) {

		boolean result = false;
		if (StringUtils.isNotBlank(compare)) {

			try {
				result = Pattern.matches(compare, match);
			} catch (Exception e) {
				logger.error("Regex parsing error", e);
				throw new RulesEngineRuntimeException("Regex parsing error", e);
			}
		}

		return result;
	}

	/**
	 * compare with fields
	 * 
	 * @param match
	 * @param compare
	 * @return
	 */
	private final boolean compareFields(final String match, final String compare) {

		boolean result = false;
		String val = compare;
		String oper = EQUALS;
		DataFactProperty field = null;
		if (StringUtils.isNotBlank(compare)) {
			if (compare.startsWith(EQUALS)) {
				oper = val.substring(0, EQUALS.length());
			} else if (val.startsWith(NOT_EQUALS)) {
				oper = val.substring(0, NOT_EQUALS.length());
			} else if (val.startsWith(GREATER_OR_EQUALS)) {
				oper = val.substring(0, GREATER_OR_EQUALS.length());
			} else if (val.startsWith(GREATER)) {
				oper = val.substring(0, GREATER.length());
			} else if (val.startsWith(LESS_OR_EQUALS)) {
				oper = val.substring(0, LESS_OR_EQUALS.length());
			} else if (val.startsWith(LESS)) {
				oper = val.substring(0, LESS.length());
			} else {
				logger.error("Compare fields has no operator, either =, <>, >=, > , <=, < must be specified");
				throw new RulesEngineRuntimeException("Compare fields has no operator, either =, <>, >=, > , <=, < must be specified");
			}
			double multiplier = 1;
			double additive = 0;
			val = val.substring(oper.length()).trim();
			String[] tokens = StringUtils.split(val, SEPARATOR);
			if (tokens.length > 2) {
				try {
					additive = Double.parseDouble(tokens[2]);
				} catch (NumberFormatException nfe) {
					logger.error("Compare additive is not a numeric value");
					throw new RulesEngineRuntimeException("Compare additive is not a numeric value", nfe);
				}
			}
			if (tokens.length > 1) {
				try {
					multiplier = Double.parseDouble(tokens[1]);
				} catch (NumberFormatException nfe) {
					logger.error("Compare mulitplier is not a numeric value");
					throw new RulesEngineRuntimeException("Compare mulitplier is not a numeric value", nfe);
				}
			}
			if (tokens.length > 0) {
				field = reconcileField(tokens[0]);
			}

			String fieldValue = field.getValue();
			String matchValue = match;

			// try to use numeric as much as possible

			if (StringUtils.isBlank(matchValue)) {
				matchValue = "0";
			}
			if (StringUtils.isBlank(fieldValue)) {
				fieldValue = "0";
			}
			if (!isNumeric(matchValue) || !isNumeric(fieldValue)) {
				if (oper.equals(NOT_EQUALS)) {
					result = !matchValue.equals(fieldValue);
				} else if (oper.equals(GREATER_OR_EQUALS)) {
					result = matchValue.compareTo(fieldValue) >= 0;
				} else if (oper.equals(GREATER)) {
					result = matchValue.compareTo(fieldValue) > 0;
				} else if (oper.equals(LESS_OR_EQUALS)) {
					result = matchValue.compareTo(fieldValue) <= 0;
				} else if (oper.equals(LESS)) {
					result = matchValue.compareTo(fieldValue) < 0;
				} else {
					result = matchValue.equals(fieldValue);
				}
			} else {
				double matchNum = NumberUtils.toDouble(matchValue);
				double fieldNum = (NumberUtils.toDouble(fieldValue) * multiplier) + additive;
				if (oper.equals(NOT_EQUALS)) {
					result = matchNum != fieldNum;
				} else if (oper.equals(GREATER_OR_EQUALS)) {
					result = matchNum >= fieldNum;
				} else if (oper.equals(GREATER)) {
					result = matchNum > fieldNum;
				} else if (oper.equals(LESS_OR_EQUALS)) {
					result = matchNum <= fieldNum;
				} else if (oper.equals(LESS)) {
					result = matchNum < fieldNum;
				} else {
					result = matchNum == fieldNum;
				}

			}

		}

		return result;
	}

	/**
	 * division with range
	 * 
	 * @param match
	 * @param compare
	 * @return
	 */
	private final boolean compareDivs(final String match, final String compare) {

		boolean result = false;
		if (StringUtils.isNotBlank(compare)) {
			int divisor = 1;
			int remainder = 0;
			String range = compare;
			String[] tokens = StringUtils.split(compare, SEPARATOR);
			if (tokens.length > 2) {
				try {
					remainder = Integer.parseInt(tokens[2]);
				} catch (NumberFormatException nfe) {
					logger.error("Div remainder is not a numeric value");
					throw new RulesEngineRuntimeException("Div remainder is not a numeric value", nfe);
				}
			}
			if (tokens.length > 1) {
				try {
					divisor = Integer.parseInt(tokens[0]);
				} catch (NumberFormatException nfe) {
					logger.error("Div divisor is not a numeric value");
					throw new RulesEngineRuntimeException("Div divisor is not a numeric value", nfe);
				}
				range = tokens[1];
			}

			result = compareDivs(match, range, divisor, remainder);
		}

		return result;
	}

	/**
	 * remainder
	 * 
	 * @param match
	 * @param compare
	 * @return
	 */
	private final boolean compareRems(final String match, final String compare) {

		boolean result = false;
		if (StringUtils.isNotBlank(compare)) {
			int divisor = 1;
			String range = "";
			String[] tokens = StringUtils.split(compare, SEPARATOR);
			if (tokens.length > 1) {
				try {
					divisor = Integer.parseInt(tokens[0]);
				} catch (NumberFormatException nfe) {
					divisor = 1;
				}
				range = tokens[1];
			}

			if (StringUtils.isNotBlank(range)) {
				String[] range_tokens = reconcileRange(range);
				int hival = 0;
				int lowval = 0;
				if (range_tokens.length > 1) {
					try {
						hival = Integer.parseInt(range_tokens[1].trim());
					} catch (NumberFormatException nfe) {
						hival = 0;
					}
					try {
						lowval = Integer.parseInt(range_tokens[0].trim());
					} catch (NumberFormatException nfe) {
						lowval = 0;
					}
				} else {
					try {
						lowval = Integer.parseInt(range_tokens[0].trim());
					} catch (NumberFormatException nfe) {
						lowval = 0;
					}
					hival = lowval;
				}
				int match_val = 0;

				try {
					match_val = Integer.parseInt(match.toString());
				} catch (NumberFormatException nfe) {
					logger.warn("Div operation on non-numeric value", nfe);
					return false;
				}

				result = (match_val >= lowval && match_val <= hival && match_val % divisor != 0);
			} else {

				int match_val = 0;

				try {
					match_val = Integer.parseInt(match.toString());
				} catch (NumberFormatException nfe) {
					logger.warn("Div operation on non-numeric value", nfe);
					return false;
				}

				result = match_val % divisor != 0;
			}

		}

		return result;
	}

	/**
	 * divisior
	 * 
	 * @param match
	 * @param compare
	 * @param divisor
	 * @param remainder
	 * @return
	 */
	private final boolean compareDivs(final String match, final String compare, final int divisor, final int remainder) {

		if (StringUtils.isNotBlank(compare)) {
			String[] tokens = reconcileRange(compare);
			int hival = 0;
			int lowval = 0;
			if (tokens.length > 1) {
				try {
					hival = Integer.parseInt(tokens[1].trim());
				} catch (NumberFormatException nfe) {
					hival = 0;
				}
				try {
					lowval = Integer.parseInt(tokens[0].trim());
				} catch (NumberFormatException nfe) {
					lowval = 0;
				}
			} else {
				try {
					lowval = Integer.parseInt(tokens[0].trim());
				} catch (NumberFormatException nfe) {
					lowval = 0;
				}
				hival = lowval;
			}
			int match_val = 0;

			try {
				match_val = Integer.parseInt(match.toString());
			} catch (NumberFormatException nfe) {
				logger.warn("Div operation on non-numeric value", nfe);
				return false;
			}

			return (match_val >= lowval && match_val <= hival && match_val % divisor == remainder);
		}

		return false;
	}

	/**
	 * reconcile to actual field allowing for substrings
	 * 
	 * @param field
	 * @return
	 */
	private final DataFactProperty reconcileField(final String field) {

		DataFactProperty fieldProp;
		String value = null;
		String fieldName = field;
		if (StringUtils.isNotBlank(field)) {
			int length = -1;
			int start = -1;
			if (field.contains(FIELD_SUBSTR_SEPARATOR)) {
				String[] tokens = StringUtils.splitPreserveAllTokens(field, FIELD_SUBSTR_SEPARATOR);
				int limit = tokens.length;
				if (limit > 1) {
					if (StringUtils.isNumeric(tokens[tokens.length - 1])) {
						length = Integer.parseInt(tokens[tokens.length - 1]);
						if (limit > 2 && StringUtils.isNumeric(tokens[tokens.length - 2])) {
							start = Integer.parseInt(tokens[tokens.length - 2]) - 1;
							limit -= 2;
						} else {
							start = length - 1;
							length = -1;
							limit--;
						}
					}

					StringBuffer fieldBuffer = new StringBuffer(tokens[0]);
					for (int i = 1; i < limit; i++) {
						fieldBuffer.append(FIELD_SUBSTR_SEPARATOR);
						fieldBuffer.append(tokens[i]);
					}

					fieldName = fieldBuffer.toString();

				}

			}

			fieldProp = this.input.getField(fieldName);
			if (fieldProp == null) {
				logger.error("Field Name " + fieldName + " is not valid");
				throw new IllegalArgumentException("Field Name " + fieldName + " is not valid");
			}

			value = fieldProp.getValue();

			if (start >= 0 && value != null) {
				if (start < value.length()) {
					if (length > 0 && start + length <= value.length()) {
						value = value.substring(start, start + length);
					} else {
						value = value.substring(start);
					}
				} else {
					value = "";
				}
				fieldProp = fieldProp.clone();
				fieldProp.setValue(value);
			}

		} else {
			logger.error("Field Name is null");
			throw new IllegalArgumentException("Field Name is null");
		}

		return fieldProp;
	}

	/**
	 */
	private final boolean compareRelatedSets(final DataFactProperty match, final String compare) {

		String val = compare;
		String oper = EQUALS;
		boolean results = false;
		if (StringUtils.isNotBlank(compare)) {
			String params = compare.trim();
			if (compare.startsWith(EQUALS)) {
				oper = val.substring(0, EQUALS.length());
				params = val.substring(oper.length()).trim();
			} else if (val.startsWith(NOT_EQUALS)) {
				oper = val.substring(0, NOT_EQUALS.length());
				params = val.substring(oper.length()).trim();
			}

			String tokens[] = StringUtils.split(params, SEPARATOR);

			if (tokens.length > 1) {
				String classKey = tokens[0].trim();
				List<DataFactProperty> fields = new ArrayList<DataFactProperty>();
				fields.add(match);
				for (int i = 1; i < tokens.length; i++) {
					String tokenFieldName = tokens[i].trim();
					boolean any = false;
					if (tokenFieldName.endsWith("*")) {
						tokenFieldName = tokenFieldName.substring(0, tokenFieldName.length() - 1);
						any = true;
					}
					DataFactProperty reconciledField = reconcileField(tokenFieldName);
					if (any) {
						reconciledField = reconciledField.clone();
						reconciledField.setValue(DataFactKeyedSets.FIELD_WILDCARD);
					}
					fields.add(reconciledField);
				}
				boolean noSet = true;
				if (this.keyedSets != null && StringUtils.isNotBlank(classKey)) {
					for (Iterator<DataFactKeyedSets> iterator = this.keyedSets.iterator(); iterator.hasNext();) {
						DataFactKeyedSets keyedSet = iterator.next();
						if (keyedSet.isInRelatedSet(classKey)) {
							String errorFields = keyedSet.isValidRelation(classKey, fields);
							if (oper.equals(EQUALS)) {
								results = StringUtils.isBlank(errorFields);
							} else {
								results = StringUtils.isNotBlank(errorFields);
								if (results) {
									if (this.substitutions == null) {
										this.substitutions = new String[] { errorFields };
									} else {
										this.substitutions = (String[]) ArrayUtils.add(this.substitutions, errorFields);
									}
								}
							}
							noSet = false;
							break;
						}
					}
					if (noSet) {
						logger.error("Related Code set " + classKey + " does not exist");
						throw new RulesEngineRuntimeException("Related Code set " + classKey + " does not exist");
					}
				} else {
					logger.error("Related Code set " + classKey + " does not exist");
					throw new RulesEngineRuntimeException("Related Code set " + classKey + " does not exist");
				}

			} else if (tokens.length < 2) {
				logger.error("At least one field must be specified for the dependent fields");
				throw new RulesEngineRuntimeException("At least one field must be specified for the dependent fields");
			} else {
				logger.error("Related Code set not specified");
				throw new RulesEngineRuntimeException("Related Code set not specified");
			}

		}

		return results;
	}

	/**
	 * 
	 * @param ruleKey
	 * @return
	 */
	private final boolean executeRules(final String compare) {

		String val = compare;
		String oper = EQUALS;
		if (StringUtils.isNotBlank(compare)) {
			String ruleKey = compare.trim();
			if (compare.startsWith(EQUALS)) {
				oper = val.substring(0, EQUALS.length());
				ruleKey = val.substring(oper.length()).trim();
			} else if (val.startsWith(NOT_EQUALS)) {
				oper = val.substring(0, NOT_EQUALS.length());
				ruleKey = val.substring(oper.length()).trim();
			}
			
			throw new RuntimeException("Commented out to compile for migration. Function not implemented");

//			RuleSetFactory ruleset = RuleSetFactory.getRuleSet(ruleKey);
//			if (ruleset == null) {
//				logger.error("Ruleset " + ruleKey + " does not exist or is not callable");
//				throw new RulesEngineRuntimeException("Ruleset " + ruleKey + " does not exist or is not callable");
//			}
//			DataValidationFact output = (DataValidationFact) RuleSetFactory.evaluateRule(ruleset, new DataValidationFact(this.input.getFields()));
//			if (output != null) {
//				if (oper.equals(NOT_EQUALS))
//					return output.getValidationsAsArray().length == 0;
//				else
//					return output.getValidationsAsArray().length > 0;
//			}

		}

		return false;
	}

	/**
	 * only numeric values (no hex,Java formats)
	 * 
	 * @param value
	 * @return
	 */
	private final boolean isNumeric(final String value) {
		if (StringUtils.isNumeric(value)) {
			return true;
		} else {
			if (StringUtils.isBlank(value)) {
				return false;
			} else {
				if (value.startsWith("-") && value.length() > 1) {
					if (StringUtils.countMatches(value, ".") == 1) {
						String temp = value.substring(1).replace(".", "");
						return StringUtils.isNotEmpty(temp) && StringUtils.isNumeric(temp);
					} else {
						return StringUtils.isNumeric(value.substring(1));
					}
				} else if (StringUtils.countMatches(value, ".") == 1) {
					String temp = value.substring(1).replace(".", "");
					return StringUtils.isNotEmpty(temp) && StringUtils.isNumeric(temp);
				} else {
					return false;
				}
			}
		}
	}

	public final void addFieldName(final String fieldName) {
		if (fieldName != null) {
			this.output.addValidation(new ValidationOutput(fieldName, null, null));
		}
	}

	public final void addErrorCode(final String errorCode) {
		if (errorCode != null) {
			if (this.output.getValidations().size() > 0)
				this.output.getValidations().get(this.output.getValidations().size() - 1).setErrorCode(errorCode);
		}

	}

	public final void addErrorMessage(final String errorMessage) {
		if (errorMessage != null) {
			if (this.output.getValidations().size() > 0) {
				if (this.substitutions != null && this.substitutions.length > 0) {
					this.output.getValidations().get(this.output.getValidations().size() - 1)
							.setErrorMessage(MessageFormat.format(errorMessage, (Object[]) this.substitutions));
				} else {
					this.output.getValidations().get(this.output.getValidations().size() - 1).setErrorMessage(errorMessage);
				}
			}

		}
	}

	public final void addSeverity(final String severity) {
		if (severity != null) {
			if (this.output.getValidations().size() > 0) {
				this.output.getValidations().get(this.output.getValidations().size() - 1).setSeverity(severity);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wvus.esb.rules.RuleFact#getInput()
	 */
	@Override
	public DataValidationFact getInput() {
		return this.input;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wvus.esb.rules.RuleFact#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(DataValidationFact obj) {
		this.input = obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wvus.esb.rules.RuleFact#getOutput()
	 */
	@Override
	public DataValidationFact getOutput() {
		return this.output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wvus.esb.rules.RuleFact#setOutput(java.lang.Object)
	 */
	@Override
	public void setOutput(DataValidationFact obj) {
		this.output = obj;
	}
	
	public static void main(String[] args) {
		KieServices kieServices = KieServices.Factory.get();
		
		LineHelper lineHelper = new AutoHelper();
		
		Resource dt 
		  = ResourceFactory
		    .newClassPathResource(lineHelper.getRuleFilePath(),
		      DataValidationRule.class);

		KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
		
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();
		
		KieRepository kieRepository = kieServices.getRepository();
		
		ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
		KieContainer kieContainer 
		  = kieServices.newKieContainer(krDefaultReleaseId);
		
		KieSession kieSession = kieContainer.newKieSession();
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("LOB", "56");
		data.put("state", "37");
		data.put("coverageCode", "5");
		
		
		Map<String, DataFactProperty> fields = lineHelper.getFields();
		
		DataValidationFact fact = new DataValidationFact(fields, data);
		
		DataValidationRule record1 = new DataValidationRule(fact);
		kieSession.insert(record1);

		kieSession.fireAllRules();
		
		List<ValidationOutput> outputs = record1.getOutput().getValidations();
		
		logger.info("Found Errors: " + outputs.size());
		System.out.println("Found Errors: " + outputs.size());
		 
		for(ValidationOutput o : outputs){
			logger.info(o.toString());
			System.out.println(o.toString());
		}
		
		
		

	}

}
