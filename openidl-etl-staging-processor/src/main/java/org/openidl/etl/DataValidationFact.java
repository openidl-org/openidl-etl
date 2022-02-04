package org.openidl.etl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataValidationFact implements Serializable {

	private static final long serialVersionUID = 474241687694856397L;
	private static final Logger logger = LoggerFactory.getLogger(DataValidationFact.class);

	private Map<String, String> data;
	private Map<String, DataFactProperty> fields;
	private List<ValidationOutput> validations;
	private Set<String> validationFields;

	/**
	 * default CTOR
	 */
	public DataValidationFact() {
		this.fields = new HashMap<String, DataFactProperty>();
		this.validations = new ArrayList<ValidationOutput>();
		this.validationFields = new HashSet<String>();
	}

	/**
	 * 
	 * @param fields
	 */
	public DataValidationFact(Map<String, DataFactProperty> fields) {
		this();
		setFields(fields);
	}

	/**
	 * 
	 * @param fields
	 * @param data
	 */
	public DataValidationFact(Map<String, DataFactProperty> fields, Map<String, String> data) {
		this(fields);
		setData(data);
	}

	/**
	 * 
	 * @param fields
	 */
	public void setFields(Map<String, DataFactProperty> fields) {

		if (fields != null) {
			Map<String, DataFactProperty> cloneFields = new HashMap<String, DataFactProperty>();
			for (Iterator<String> iterator = fields.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				cloneFields.put(key, fields.get(key).clone(true));
			}
			this.fields = cloneFields;

		}

	}

	/**
	 * 
	 * @return
	 */
	public Map<String, DataFactProperty> getFields() {

		return this.fields;

	}

	/**
	 * @return the data
	 */
	public Map<String, String> getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(Map<String, String> data) {

		this.data = data;

		if (data != null) {
			for (Iterator<String> iterator = data.keySet().iterator(); iterator.hasNext();) {
				String column = iterator.next();
				for (Iterator<DataFactProperty> factIterator = this.fields.values().iterator(); factIterator.hasNext();) {
					DataFactProperty fact = factIterator.next();
					if (fact.getColumn() != null && fact.getColumn().equalsIgnoreCase(column)) {
						fact.setValue(data.get(column));
					}
				}

			}
			for (Iterator<DataFactProperty> iterator = fields.values().iterator(); iterator.hasNext();) {
				DataFactProperty dataProp = iterator.next();
				logger.debug("DataFactProperty: " + dataProp.toString());
			}
		}

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public final String getFieldValue(final String key) {
		DataFactProperty field = this.fields.get(key);
		if (field != null)
			return field.getValue();
		else
			return null;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public final void setFieldValue(final String key, final String value) {
		DataFactProperty field = this.fields.get(key);
		if (field != null)
			field.setValue(value);

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public final String getFieldType(final String key) {
		DataFactProperty field = this.fields.get(key);
		if (field != null)
			if (field.getValidationType() != null)
				return field.getValidationType();
			else if (field.getType() != null)
				return field.getType();
			else
				return DataFactProperty.STRING;

		else
			return DataFactProperty.STRING;

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public final DataFactProperty getField(final String key) {
		return this.fields.get(key);
	}

	public final void addValidation(final ValidationOutput validation) {

		if (validation != null) {
			this.validations.add(validation);
			this.validationFields.add(validation.getField());
		}

	}

	public final boolean isFieldValid(final String field) {
		return this.validationFields.contains(field);
	}

	public final List<ValidationOutput> getValidations() {
		return this.validations;
	}

	public final ValidationOutput[] getValidationsAsArray() {
		return this.validations.toArray(new ValidationOutput[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataValidationFact other = (DataValidationFact) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public String fieldsToString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<DataFactProperty> iterator = this.fields.values().iterator(); iterator.hasNext();) {
			DataFactProperty data = iterator.next();
			sb.append(data.getName());
			sb.append(": ");
			sb.append(data.getValue());
			if (iterator.hasNext())
				sb.append(",");
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataValidationFact [fields=" + fields + ", validations=" + validations + "]";
	}

}
