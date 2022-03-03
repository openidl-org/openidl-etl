
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Personal Auto Policy Message
 * <p>
 * 
 * 
 */
@Generated("jsonschema2pojo")
public class OpenidlAutoPolicyRecord {

    @SerializedName("$schema")
    @Expose
    private String $schema;
    /**
     * Manages the information about the processing of the message
     * 
     */
    @SerializedName("system")
    @Expose
    private System system;
    /**
     * A code identifying the broader line of insurance the transaction is to be reported as.
     * 
     */
    @SerializedName("RecordLOB")
    @Expose
    private OpenidlAutoPolicyRecord.RecordLOB recordLOB;
    /**
     * A code identifying the type of transaction being reported.
     * 
     */
    @SerializedName("RecordType")
    @Expose
    private OpenidlAutoPolicyRecord.RecordType recordType;
    /**
     * policy level elements
     * 
     */
    @SerializedName("Policy")
    @Expose
    private Policy policy;
    /**
     * driver level elements
     * 
     */
    @SerializedName("Driver")
    @Expose
    private Driver driver;
    /**
     * vehicle level elements
     * 
     */
    @SerializedName("Vehicle")
    @Expose
    private Vehicle vehicle;
    /**
     * coverage level elements
     * 
     */
    @SerializedName("Coverage")
    @Expose
    private Coverage coverage;

    public String get$schema() {
        return $schema;
    }

    public void set$schema(String $schema) {
        this.$schema = $schema;
    }

    /**
     * Manages the information about the processing of the message
     * 
     */
    public System getSystem() {
        return system;
    }

    /**
     * Manages the information about the processing of the message
     * 
     */
    public void setSystem(System system) {
        this.system = system;
    }

    /**
     * A code identifying the broader line of insurance the transaction is to be reported as.
     * 
     */
    public OpenidlAutoPolicyRecord.RecordLOB getRecordLOB() {
        return recordLOB;
    }

    /**
     * A code identifying the broader line of insurance the transaction is to be reported as.
     * 
     */
    public void setRecordLOB(OpenidlAutoPolicyRecord.RecordLOB recordLOB) {
        this.recordLOB = recordLOB;
    }

    /**
     * A code identifying the type of transaction being reported.
     * 
     */
    public OpenidlAutoPolicyRecord.RecordType getRecordType() {
        return recordType;
    }

    /**
     * A code identifying the type of transaction being reported.
     * 
     */
    public void setRecordType(OpenidlAutoPolicyRecord.RecordType recordType) {
        this.recordType = recordType;
    }

    /**
     * policy level elements
     * 
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     * policy level elements
     * 
     */
    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    /**
     * driver level elements
     * 
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * driver level elements
     * 
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * vehicle level elements
     * 
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * vehicle level elements
     * 
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * coverage level elements
     * 
     */
    public Coverage getCoverage() {
        return coverage;
    }

    /**
     * coverage level elements
     * 
     */
    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }


    /**
     * A code identifying the broader line of insurance the transaction is to be reported as.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum RecordLOB {

        @SerializedName("Auto")
        AUTO("Auto");
        private final String value;
        private final static Map<String, OpenidlAutoPolicyRecord.RecordLOB> CONSTANTS = new HashMap<String, OpenidlAutoPolicyRecord.RecordLOB>();

        static {
            for (OpenidlAutoPolicyRecord.RecordLOB c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        RecordLOB(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static OpenidlAutoPolicyRecord.RecordLOB fromValue(String value) {
            OpenidlAutoPolicyRecord.RecordLOB constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A code identifying the type of transaction being reported.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum RecordType {

        @SerializedName("Policy")
        POLICY("Policy"),
        @SerializedName("Claim")
        CLAIM("Claim");
        private final String value;
        private final static Map<String, OpenidlAutoPolicyRecord.RecordType> CONSTANTS = new HashMap<String, OpenidlAutoPolicyRecord.RecordType>();

        static {
            for (OpenidlAutoPolicyRecord.RecordType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        RecordType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static OpenidlAutoPolicyRecord.RecordType fromValue(String value) {
            OpenidlAutoPolicyRecord.RecordType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
