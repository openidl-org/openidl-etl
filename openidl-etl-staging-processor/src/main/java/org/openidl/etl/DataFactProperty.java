package org.openidl.etl;

/**
 * METADATA field names and types
 * 
 * @author bhahn
 * 
 */
public class DataFactProperty {

	public static final String STRING = "STRING";
	public static final String NUMBER = "NUMBER";

	private String name;
	private String column;
	private long length;
	private String type;
	private String validationType;
	private String value;

	public DataFactProperty() {
		super();

	}

	/**
	 * @param name
	 * @param column
	 * @param length
	 * @param type
	 * @param validationType
	 * @param value
	 */
	public DataFactProperty(String name, String column, long length, String type, String validationType, String value) {
		super();
		this.name = name;
		this.column = column;
		this.length = length;
		this.type = type;
		this.validationType = validationType;
		this.value = value;
	}

	/**
	 * @param name
	 * @param column
	 * @param length
	 * @param type
	 * @param validationType
	 * @param value
	 */
	protected DataFactProperty(DataFactProperty clone, boolean wipeValue) {
		super();
		if (clone != null) {
			this.name = clone.name;
			this.column = clone.column;
			this.length = clone.length;
			this.type = clone.type;
			this.validationType = clone.validationType;
			if (!wipeValue)
				this.value = clone.value;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidationType() {
		return validationType;
	}

	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		if (value == null)
			return "";
		else
			return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getFieldType() {
		if (this.validationType == null || !(this.validationType.equalsIgnoreCase(STRING) || this.validationType.equalsIgnoreCase(NUMBER))) {
			return STRING;
		} else {
			return this.validationType;
		}
	}

	public boolean isFieldNumeric() {
		return getFieldType().equals(NUMBER);
	}

	public boolean isFieldAlphaNumeric() {
		return getFieldType().equals(STRING);
	}

	public Number getNumericValue() {
		try {
			return new Double(getValue());
		} catch (NumberFormatException nfe) {
			return null;
		}
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
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DataFactProperty other = (DataFactProperty) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataFactProperty [name=" + name + ", column=" + column + ", length=" + length + ", type=" + type + ", validationType=" + validationType
				+ ", value=" + value + "]";
	}

	/**
	 * clones this object
	 */
	public final DataFactProperty clone() {
		return new DataFactProperty(this, false);
	}

	/**
	 * clones this object
	 */
	public final DataFactProperty clone(boolean wipeValue) {
		return new DataFactProperty(this, wipeValue);
	}

}
