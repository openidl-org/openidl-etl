
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * This object holds the informatioin about the rule validation.
 * 
 */
@Generated("jsonschema2pojo")
public class RuleValidation {

    /**
     * Set to Yes if this record has been validated against the rules.
     * 
     */
    @SerializedName("validated")
    @Expose
    private RuleValidation.Validated validated;
    /**
     * Set to Yes if this record is valid based on the rules.
     * 
     */
    @SerializedName("valid")
    @Expose
    private RuleValidation.Valid valid;
    @SerializedName("errors")
    @Expose
    private List<Error__1> errors = null;

    /**
     * Set to Yes if this record has been validated against the rules.
     * 
     */
    public RuleValidation.Validated getValidated() {
        return validated;
    }

    /**
     * Set to Yes if this record has been validated against the rules.
     * 
     */
    public void setValidated(RuleValidation.Validated validated) {
        this.validated = validated;
    }

    /**
     * Set to Yes if this record is valid based on the rules.
     * 
     */
    public RuleValidation.Valid getValid() {
        return valid;
    }

    /**
     * Set to Yes if this record is valid based on the rules.
     * 
     */
    public void setValid(RuleValidation.Valid valid) {
        this.valid = valid;
    }

    public List<Error__1> getErrors() {
        return errors;
    }

    public void setErrors(List<Error__1> errors) {
        this.errors = errors;
    }


    /**
     * Set to Yes if this record is valid based on the rules.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Valid {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, RuleValidation.Valid> CONSTANTS = new HashMap<String, RuleValidation.Valid>();

        static {
            for (RuleValidation.Valid c: values()) {
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

        public static RuleValidation.Valid fromValue(String value) {
            RuleValidation.Valid constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Set to Yes if this record has been validated against the rules.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Validated {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, RuleValidation.Validated> CONSTANTS = new HashMap<String, RuleValidation.Validated>();

        static {
            for (RuleValidation.Validated c: values()) {
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

        public static RuleValidation.Validated fromValue(String value) {
            RuleValidation.Validated constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
