
package org.openidl.etl.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Manages the information about the processing of the message
 * 
 */
@Generated("jsonschema2pojo")
public class System {

    /**
     * This object holds the information obout schema validation.
     * 
     */
    @SerializedName("schemaValidation")
    @Expose
    private SchemaValidation schemaValidation;
    /**
     * This object holds the informatioin about the rule validation.
     * 
     */
    @SerializedName("ruleValidation")
    @Expose
    private RuleValidation ruleValidation;
    @SerializedName("log")
    @Expose
    private List<Log> log = null;

    /**
     * This object holds the information obout schema validation.
     * 
     */
    public SchemaValidation getSchemaValidation() {
        return schemaValidation;
    }

    /**
     * This object holds the information obout schema validation.
     * 
     */
    public void setSchemaValidation(SchemaValidation schemaValidation) {
        this.schemaValidation = schemaValidation;
    }

    /**
     * This object holds the informatioin about the rule validation.
     * 
     */
    public RuleValidation getRuleValidation() {
        return ruleValidation;
    }

    /**
     * This object holds the informatioin about the rule validation.
     * 
     */
    public void setRuleValidation(RuleValidation ruleValidation) {
        this.ruleValidation = ruleValidation;
    }

    public List<Log> getLog() {
        return log;
    }

    public void setLog(List<Log> log) {
        this.log = log;
    }

}
