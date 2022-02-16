
package org.openidl.etl.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Personal Auto Policy Message
 * <p>
 * 
 * 
 */

public class OpenidlAutoPolicyRecord extends OpenidlRecord {

    @SerializedName("$schema")
    @Expose
    private String $schema;
    /**
     * policy level elements
     * 
     */
    @SerializedName("Policy")
    @Expose
    private Policy policy;
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

}
