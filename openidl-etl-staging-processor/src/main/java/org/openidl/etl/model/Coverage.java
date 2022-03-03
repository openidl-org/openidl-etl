
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * coverage level elements
 * 
 */
@Generated("jsonschema2pojo")
public class Coverage {

    /**
     * Code representing the coverage of the vehicle the premium on the record applies to.
     * 
     */
    @SerializedName("CoverageCode")
    @Expose
    private String coverageCode;
    /**
     * Per Person Limit for the Coverage
     * 
     */
    @SerializedName("PerPersonLimit")
    @Expose
    private Double perPersonLimit;
    /**
     * Per Accident Limit for the Coverage
     * 
     */
    @SerializedName("PerAccidentLimit")
    @Expose
    private Double perAccidentLimit;
    /**
     * Physical Damage Limit for the Coverage
     * 
     */
    @SerializedName("PhysicalDamageLimit")
    @Expose
    private Double physicalDamageLimit;
    /**
     * Combined Single Limit for the Coverage
     * 
     */
    @SerializedName("CombinedSingleLimit")
    @Expose
    private String combinedSingleLimit;
    /**
     * Transactional Premium applicable to the coverage
     * 
     */
    @SerializedName("Premium")
    @Expose
    private Double premium;
    /**
     * Deductible for the Coverage
     * 
     */
    @SerializedName("Deductible")
    @Expose
    private Double deductible;
    /**
     * Indicates whether UM/UIM Limits are stacked (per vehicle) or non-stacked (per policy)
     * 
     */
    @SerializedName("UMUIMStackingIndicator")
    @Expose
    private Coverage.UMUIMStackingIndicator uMUIMStackingIndicator;
    /**
     * Deductible amount applicable to Medical Expenses.
     * 
     */
    @SerializedName("MedicalExpensesDeductibleAmount")
    @Expose
    private Double medicalExpensesDeductibleAmount;
    /**
     * Code indicating whether the insured has a Threshold and Tort Limitation on the policy.
     * 
     */
    @SerializedName("NJThresholdTortLimitation")
    @Expose
    private Coverage.NJThresholdTortLimitation nJThresholdTortLimitation;
    /**
     * Determines what type of no-fault health plan the driver has.
     * 
     */
    @SerializedName("PrimaryNoFaultHealthPlan")
    @Expose
    private String primaryNoFaultHealthPlan;
    /**
     * Code describing the combination of Medical Benefits Limit, Loss of Income $, Accidental Death $, Funeral Expense $
     * 
     */
    @SerializedName("CombinedFirstPartyBenefits")
    @Expose
    private String combinedFirstPartyBenefits;

    /**
     * Code representing the coverage of the vehicle the premium on the record applies to.
     * 
     */
    public String getCoverageCode() {
        return coverageCode;
    }

    /**
     * Code representing the coverage of the vehicle the premium on the record applies to.
     * 
     */
    public void setCoverageCode(String coverageCode) {
        this.coverageCode = coverageCode;
    }

    /**
     * Per Person Limit for the Coverage
     * 
     */
    public Double getPerPersonLimit() {
        return perPersonLimit;
    }

    /**
     * Per Person Limit for the Coverage
     * 
     */
    public void setPerPersonLimit(Double perPersonLimit) {
        this.perPersonLimit = perPersonLimit;
    }

    /**
     * Per Accident Limit for the Coverage
     * 
     */
    public Double getPerAccidentLimit() {
        return perAccidentLimit;
    }

    /**
     * Per Accident Limit for the Coverage
     * 
     */
    public void setPerAccidentLimit(Double perAccidentLimit) {
        this.perAccidentLimit = perAccidentLimit;
    }

    /**
     * Physical Damage Limit for the Coverage
     * 
     */
    public Double getPhysicalDamageLimit() {
        return physicalDamageLimit;
    }

    /**
     * Physical Damage Limit for the Coverage
     * 
     */
    public void setPhysicalDamageLimit(Double physicalDamageLimit) {
        this.physicalDamageLimit = physicalDamageLimit;
    }

    /**
     * Combined Single Limit for the Coverage
     * 
     */
    public String getCombinedSingleLimit() {
        return combinedSingleLimit;
    }

    /**
     * Combined Single Limit for the Coverage
     * 
     */
    public void setCombinedSingleLimit(String combinedSingleLimit) {
        this.combinedSingleLimit = combinedSingleLimit;
    }

    /**
     * Transactional Premium applicable to the coverage
     * 
     */
    public Double getPremium() {
        return premium;
    }

    /**
     * Transactional Premium applicable to the coverage
     * 
     */
    public void setPremium(Double premium) {
        this.premium = premium;
    }

    /**
     * Deductible for the Coverage
     * 
     */
    public Double getDeductible() {
        return deductible;
    }

    /**
     * Deductible for the Coverage
     * 
     */
    public void setDeductible(Double deductible) {
        this.deductible = deductible;
    }

    /**
     * Indicates whether UM/UIM Limits are stacked (per vehicle) or non-stacked (per policy)
     * 
     */
    public Coverage.UMUIMStackingIndicator getUMUIMStackingIndicator() {
        return uMUIMStackingIndicator;
    }

    /**
     * Indicates whether UM/UIM Limits are stacked (per vehicle) or non-stacked (per policy)
     * 
     */
    public void setUMUIMStackingIndicator(Coverage.UMUIMStackingIndicator uMUIMStackingIndicator) {
        this.uMUIMStackingIndicator = uMUIMStackingIndicator;
    }

    /**
     * Deductible amount applicable to Medical Expenses.
     * 
     */
    public Double getMedicalExpensesDeductibleAmount() {
        return medicalExpensesDeductibleAmount;
    }

    /**
     * Deductible amount applicable to Medical Expenses.
     * 
     */
    public void setMedicalExpensesDeductibleAmount(Double medicalExpensesDeductibleAmount) {
        this.medicalExpensesDeductibleAmount = medicalExpensesDeductibleAmount;
    }

    /**
     * Code indicating whether the insured has a Threshold and Tort Limitation on the policy.
     * 
     */
    public Coverage.NJThresholdTortLimitation getNJThresholdTortLimitation() {
        return nJThresholdTortLimitation;
    }

    /**
     * Code indicating whether the insured has a Threshold and Tort Limitation on the policy.
     * 
     */
    public void setNJThresholdTortLimitation(Coverage.NJThresholdTortLimitation nJThresholdTortLimitation) {
        this.nJThresholdTortLimitation = nJThresholdTortLimitation;
    }

    /**
     * Determines what type of no-fault health plan the driver has.
     * 
     */
    public String getPrimaryNoFaultHealthPlan() {
        return primaryNoFaultHealthPlan;
    }

    /**
     * Determines what type of no-fault health plan the driver has.
     * 
     */
    public void setPrimaryNoFaultHealthPlan(String primaryNoFaultHealthPlan) {
        this.primaryNoFaultHealthPlan = primaryNoFaultHealthPlan;
    }

    /**
     * Code describing the combination of Medical Benefits Limit, Loss of Income $, Accidental Death $, Funeral Expense $
     * 
     */
    public String getCombinedFirstPartyBenefits() {
        return combinedFirstPartyBenefits;
    }

    /**
     * Code describing the combination of Medical Benefits Limit, Loss of Income $, Accidental Death $, Funeral Expense $
     * 
     */
    public void setCombinedFirstPartyBenefits(String combinedFirstPartyBenefits) {
        this.combinedFirstPartyBenefits = combinedFirstPartyBenefits;
    }


    /**
     * Code indicating whether the insured has a Threshold and Tort Limitation on the policy.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum NJThresholdTortLimitation {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Coverage.NJThresholdTortLimitation> CONSTANTS = new HashMap<String, Coverage.NJThresholdTortLimitation>();

        static {
            for (Coverage.NJThresholdTortLimitation c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        NJThresholdTortLimitation(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Coverage.NJThresholdTortLimitation fromValue(String value) {
            Coverage.NJThresholdTortLimitation constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Indicates whether UM/UIM Limits are stacked (per vehicle) or non-stacked (per policy)
     * 
     */
    @Generated("jsonschema2pojo")
    public enum UMUIMStackingIndicator {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Coverage.UMUIMStackingIndicator> CONSTANTS = new HashMap<String, Coverage.UMUIMStackingIndicator>();

        static {
            for (Coverage.UMUIMStackingIndicator c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        UMUIMStackingIndicator(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Coverage.UMUIMStackingIndicator fromValue(String value) {
            Coverage.UMUIMStackingIndicator constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
