
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * This object holds the information obout schema validation.
 * 
 */
@Generated("jsonschema2pojo")
public class SchemaValidation {

    /**
     * Set to Yes if this record has already had schema validation.
     * 
     */
    @SerializedName("validated")
    @Expose
    private SchemaValidation.Validated validated;
    /**
     * Set to Yes if this record is valid for the schema.
     * 
     */
    @SerializedName("valid")
    @Expose
    private SchemaValidation.Valid valid;
    /**
     * Collect all the errors from the schema validator here.
     * 
     */
    @SerializedName("errors")
    @Expose
    private List<Error> errors = null;

    /**
     * Set to Yes if this record has already had schema validation.
     * 
     */
    public SchemaValidation.Validated getValidated() {
        return validated;
    }

    /**
     * Set to Yes if this record has already had schema validation.
     * 
     */
    public void setValidated(SchemaValidation.Validated validated) {
        this.validated = validated;
    }

    /**
     * Set to Yes if this record is valid for the schema.
     * 
     */
    public SchemaValidation.Valid getValid() {
        return valid;
    }

    /**
     * Set to Yes if this record is valid for the schema.
     * 
     */
    public void setValid(SchemaValidation.Valid valid) {
        this.valid = valid;
    }

    /**
     * Collect all the errors from the schema validator here.
     * 
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * Collect all the errors from the schema validator here.
     * 
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }


    /**
     * Set to Yes if this record is valid for the schema.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Valid {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, SchemaValidation.Valid> CONSTANTS = new HashMap<String, SchemaValidation.Valid>();

        static {
            for (SchemaValidation.Valid c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Valid(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static SchemaValidation.Valid fromValue(String value) {
            SchemaValidation.Valid constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Set to Yes if this record has already had schema validation.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Validated {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, SchemaValidation.Validated> CONSTANTS = new HashMap<String, SchemaValidation.Validated>();

        static {
            for (SchemaValidation.Validated c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Validated(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static SchemaValidation.Validated fromValue(String value) {
            SchemaValidation.Validated constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
