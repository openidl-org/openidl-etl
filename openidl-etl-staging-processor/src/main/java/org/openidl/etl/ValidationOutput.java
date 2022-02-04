package org.openidl.etl;

import java.io.Serializable;

public class ValidationOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8325188559081296744L;

	private String field;
	private String errorCode;
	private String errorMessage;
	private String severity;

	public ValidationOutput() {
		super();

	}

	public ValidationOutput(String field) {
		this();
		this.field = field;
	}

	public ValidationOutput(String field, String errorCode, String errorMessage) {
		this(field);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ValidationOutput(String field, String errorCode, String errorMessage, String severity) {
		this(field);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.severity = severity;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());

		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
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
		ValidationOutput other = (ValidationOutput) obj;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
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
		return "ValidationOutput [field=" + field + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", severity=" + severity + "]";
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

}
