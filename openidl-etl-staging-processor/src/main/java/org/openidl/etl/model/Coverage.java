
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * coverage level elements
 * 
 */

public class Coverage {

    /**
     * 
     */
    @SerializedName("CoverageType")
    @Expose
    private Coverage.CoverageType coverageType;
    /**
     * 
     */
    @SerializedName("EachPersonLimit")
    @Expose
    private Double eachPersonLimit;
    /**
     * 
     */
    @SerializedName("AllPersonsLimit")
    @Expose
    private Double allPersonsLimit;
    /**
     * 
     */
    @SerializedName("Limit")
    @Expose
    private Double limit;
    /**
     * 
     */
    @SerializedName("Deductible")
    @Expose
    private Double deductible;
    /**
     * 
     */
    @SerializedName("Premium")
    @Expose
    private Double premium;

    /**
     * 
     */
    public Coverage.CoverageType getCoverageType() {
        return coverageType;
    }

    /**
     * 
     */
    public void setCoverageType(Coverage.CoverageType coverageType) {
        this.coverageType = coverageType;
    }

    /**
     * 
     */
    public Double getEachPersonLimit() {
        return eachPersonLimit;
    }

    /**
     * 
     */
    public void setEachPersonLimit(Double eachPersonLimit) {
        this.eachPersonLimit = eachPersonLimit;
    }

    /**
     * 
     */
    public Double getAllPersonsLimit() {
        return allPersonsLimit;
    }

    /**
     * 
     */
    public void setAllPersonsLimit(Double allPersonsLimit) {
        this.allPersonsLimit = allPersonsLimit;
    }

    /**
     * 
     */
    public Double getLimit() {
        return limit;
    }

    /**
     * 
     */
    public void setLimit(Double limit) {
        this.limit = limit;
    }

    /**
     * 
     */
    public Double getDeductible() {
        return deductible;
    }

    /**
     * 
     */
    public void setDeductible(Double deductible) {
        this.deductible = deductible;
    }

    /**
     * 
     */
    public Double getPremium() {
        return premium;
    }

    /**
     * 
     */
    public void setPremium(Double premium) {
        this.premium = premium;
    }


    /**
     * 
     */
    
    public enum CoverageType {

        @SerializedName("BI")
        BI("BI"),
        @SerializedName("PD")
        PD("PD"),
        @SerializedName("CSL")
        CSL("CSL"),
        @SerializedName("Comprehensive")
        COMPREHENSIVE("Comprehensive"),
        @SerializedName("Collision")
        COLLISION("Collision"),
        @SerializedName("UM BI")
        UM_BI("UM BI"),
        @SerializedName("UM PD")
        UM_PD("UM PD"),
        @SerializedName("UM CSL")
        UM_CSL("UM CSL"),
        @SerializedName("Med Pay/Med Expense")
        MED_PAY_MED_EXPENSE("Med Pay/Med Expense");
        private final String value;
        private final static Map<String, Coverage.CoverageType> CONSTANTS = new HashMap<String, Coverage.CoverageType>();

        static {
            for (Coverage.CoverageType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CoverageType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Coverage.CoverageType fromValue(String value) {
            Coverage.CoverageType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
