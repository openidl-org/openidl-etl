
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * policy level elements
 * 
 */

public class Policy {

    /**
     * 
     */
    @SerializedName("RecordLOB")
    @Expose
    private String recordLOB;
    /**
     * 
     */
    @SerializedName("InsurerNAIC")
    @Expose
    private String insurerNAIC;
    /**
     * 
     */
    @SerializedName("AnnualStatementLine")
    @Expose
    private String annualStatementLine;
    /**
     * 
     */
    @SerializedName("Subline")
    @Expose
    private Policy.Subline subline;
    /**
     * 
     */
    @SerializedName("PolicyNumber")
    @Expose
    private String policyNumber;
    /**
     * Effective Date of the Policy
     * 
     */
    @SerializedName("PolicyEffectiveDate")
    @Expose
    private String policyEffectiveDate;
    /**
     * Effective Date of the Policy
     * 
     */
    @SerializedName("PolicyExpirationDate")
    @Expose
    private String policyExpirationDate;
    /**
     * 
     */
    @SerializedName("VoluntaryResidual")
    @Expose
    private Policy.VoluntaryResidual voluntaryResidual;
    /**
     * 
     */
    @SerializedName("PolicyAddress1")
    @Expose
    private String policyAddress1;
    /**
     * 
     */
    @SerializedName("PolicyAddress2")
    @Expose
    private String policyAddress2;
    /**
     * 
     */
    @SerializedName("PolicyState")
    @Expose
    private String policyState;
    /**
     * 
     */
    @SerializedName("PolicyZip5")
    @Expose
    private String policyZip5;
    /**
     * 
     */
    @SerializedName("PolicyZip4")
    @Expose
    private String policyZip4;
    /**
     * 
     */
    @SerializedName("CreditScored")
    @Expose
    private Policy.CreditScored creditScored;

    /**
     * 
     */
    public String getRecordLOB() {
        return recordLOB;
    }

    /**
     * 
     */
    public void setRecordLOB(String recordLOB) {
        this.recordLOB = recordLOB;
    }

    /**
     * 
     */
    public String getInsurerNAIC() {
        return insurerNAIC;
    }

    /**
     * 
     */
    public void setInsurerNAIC(String insurerNAIC) {
        this.insurerNAIC = insurerNAIC;
    }

    /**
     * 
     */
    public String getAnnualStatementLine() {
        return annualStatementLine;
    }

    /**
     * 
     */
    public void setAnnualStatementLine(String annualStatementLine) {
        this.annualStatementLine = annualStatementLine;
    }

    /**
     * 
     */
    public Policy.Subline getSubline() {
        return subline;
    }

    /**
     * 
     */
    public void setSubline(Policy.Subline subline) {
        this.subline = subline;
    }

    /**
     * 
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * 
     */
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    /**
     * Effective Date of the Policy
     * 
     */
    public String getPolicyEffectiveDate() {
        return policyEffectiveDate;
    }

    /**
     * Effective Date of the Policy
     * 
     */
    public void setPolicyEffectiveDate(String policyEffectiveDate) {
        this.policyEffectiveDate = policyEffectiveDate;
    }

    /**
     * Effective Date of the Policy
     * 
     */
    public String getPolicyExpirationDate() {
        return policyExpirationDate;
    }

    /**
     * Effective Date of the Policy
     * 
     */
    public void setPolicyExpirationDate(String policyExpirationDate) {
        this.policyExpirationDate = policyExpirationDate;
    }

    /**
     * 
     */
    public Policy.VoluntaryResidual getVoluntaryResidual() {
        return voluntaryResidual;
    }

    /**
     * 
     */
    public void setVoluntaryResidual(Policy.VoluntaryResidual voluntaryResidual) {
        this.voluntaryResidual = voluntaryResidual;
    }

    /**
     * 
     */
    public String getPolicyAddress1() {
        return policyAddress1;
    }

    /**
     * 
     */
    public void setPolicyAddress1(String policyAddress1) {
        this.policyAddress1 = policyAddress1;
    }

    /**
     * 
     */
    public String getPolicyAddress2() {
        return policyAddress2;
    }

    /**
     * 
     */
    public void setPolicyAddress2(String policyAddress2) {
        this.policyAddress2 = policyAddress2;
    }

    /**
     * 
     */
    public String getPolicyState() {
        return policyState;
    }

    /**
     * 
     */
    public void setPolicyState(String policyState) {
        this.policyState = policyState;
    }

    /**
     * 
     */
    public String getPolicyZip5() {
        return policyZip5;
    }

    /**
     * 
     */
    public void setPolicyZip5(String policyZip5) {
        this.policyZip5 = policyZip5;
    }

    /**
     * 
     */
    public String getPolicyZip4() {
        return policyZip4;
    }

    /**
     * 
     */
    public void setPolicyZip4(String policyZip4) {
        this.policyZip4 = policyZip4;
    }

    /**
     * 
     */
    public Policy.CreditScored getCreditScored() {
        return creditScored;
    }

    /**
     * 
     */
    public void setCreditScored(Policy.CreditScored creditScored) {
        this.creditScored = creditScored;
    }


    /**
     * 
     */
    
    public enum CreditScored {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Policy.CreditScored> CONSTANTS = new HashMap<String, Policy.CreditScored>();

        static {
            for (Policy.CreditScored c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CreditScored(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.CreditScored fromValue(String value) {
            Policy.CreditScored constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * 
     */
    
    public enum Subline {

        @SerializedName("Personal")
        PERSONAL("Personal"),
        @SerializedName("Commercial")
        COMMERCIAL("Commercial");
        private final String value;
        private final static Map<String, Policy.Subline> CONSTANTS = new HashMap<String, Policy.Subline>();

        static {
            for (Policy.Subline c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Subline(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.Subline fromValue(String value) {
            Policy.Subline constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * 
     */
    
    public enum VoluntaryResidual {

        @SerializedName("Voluntary")
        VOLUNTARY("Voluntary"),
        @SerializedName("Residual")
        RESIDUAL("Residual");
        private final String value;
        private final static Map<String, Policy.VoluntaryResidual> CONSTANTS = new HashMap<String, Policy.VoluntaryResidual>();

        static {
            for (Policy.VoluntaryResidual c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        VoluntaryResidual(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.VoluntaryResidual fromValue(String value) {
            Policy.VoluntaryResidual constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
