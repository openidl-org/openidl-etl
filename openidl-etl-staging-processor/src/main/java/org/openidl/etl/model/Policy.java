
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * policy level elements
 * 
 */
@Generated("jsonschema2pojo")
public class Policy {

    /**
     * A unique 4-digit code assigned by the Statistical Bureaus to identify each insurance company.
     * 
     */
    @SerializedName("CompanyID")
    @Expose
    private String companyID;
    /**
     * A code identifying the line of insurance the transaction was written under.
     * 
     */
    @SerializedName("AnnualStatementLine")
    @Expose
    private String annualStatementLine;
    /**
     * A code identifying whether the record is Personal or Commercial.
     * 
     */
    @SerializedName("PolicyCategory")
    @Expose
    private Policy.PolicyCategory policyCategory;
    /**
     * A code identifying the coverage.
     * 
     */
    @SerializedName("Subline")
    @Expose
    private String subline;
    /**
     * A unique combination of letters and numbers that identifies an insured's insurance policy.  This is the foreign key for connecting the claim and policy records.
     * 
     */
    @SerializedName("PolicyIdentifier")
    @Expose
    private String policyIdentifier;
    /**
     * The date the policy became effective.
     * 
     */
    @SerializedName("PolicyEffectiveDate")
    @Expose
    private String policyEffectiveDate;
    /**
     * A code identifying whether the record is a New Business, Change, Renewal, Cancellation, etc.
     * 
     */
    @SerializedName("TransactionType")
    @Expose
    private Policy.TransactionType transactionType;
    /**
     * Date the change becomes effective.  Not the booking date.
     * 
     */
    @SerializedName("TransactionEffectiveDate")
    @Expose
    private String transactionEffectiveDate;
    /**
     * Date the change ends.
     * 
     */
    @SerializedName("TransactionExpirationDate")
    @Expose
    private String transactionExpirationDate;
    /**
     * Market in which the policy is being written through.
     * 
     */
    @SerializedName("Market")
    @Expose
    private String market;
    /**
     * The date the transaction was financially booked.
     * 
     */
    @SerializedName("AccountingDate")
    @Expose
    private String accountingDate;
    /**
     * A code that indicates whether the basic rules and forms used in writing the policy are using certain Bureau's rules or form or if they are independent.
     * 
     */
    @SerializedName("Program")
    @Expose
    private String program;
    /**
     * A code that indicates whether there is a multi-car discount or just a single rated car.
     * 
     */
    @SerializedName("MultiCarDiscountCode")
    @Expose
    private Policy.MultiCarDiscountCode multiCarDiscountCode;
    /**
     * A code identifying whether the policy is standalone or part of a package.
     * 
     */
    @SerializedName("PackageCode")
    @Expose
    private Policy.PackageCode packageCode;
    /**
     * Identifies business written in a pool such as an assigned risk facility or joint underwriting association, and business not written in a pool.
     * 
     */
    @SerializedName("PoolAffiliation")
    @Expose
    private Policy.PoolAffiliation poolAffiliation;
    /**
     * Based on North Carolina Session 2015 House Bill 288, premiums and losses resulting from program enhancements must not comingle with basic data. This code indicates the use of enhanced endorsements.
     * 
     */
    @SerializedName("NCProgramEnhancementIndicator")
    @Expose
    private Policy.NCProgramEnhancementIndicator nCProgramEnhancementIndicator;
    /**
     * A code that distinguishes between business written under the NC Reinsurance Facility and that which is not.
     * 
     */
    @SerializedName("NCReinsuranceFacility")
    @Expose
    private Policy.NCReinsuranceFacility nCReinsuranceFacility;
    /**
     * A code that distinguishes between business written under the SC Reinsurance Facility and that which is not.
     * 
     */
    @SerializedName("SCReinsuranceFacility")
    @Expose
    private Policy.SCReinsuranceFacility sCReinsuranceFacility;
    /**
     * A field that indicates whether the multi-car risk is subject to the PIP Premium Discount and whether the Principal Auto is not Discounted. 
     * 
     */
    @SerializedName("MultiCarRisks")
    @Expose
    private Policy.MultiCarRisks multiCarRisks;

    /**
     * A unique 4-digit code assigned by the Statistical Bureaus to identify each insurance company.
     * 
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * A unique 4-digit code assigned by the Statistical Bureaus to identify each insurance company.
     * 
     */
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    /**
     * A code identifying the line of insurance the transaction was written under.
     * 
     */
    public String getAnnualStatementLine() {
        return annualStatementLine;
    }

    /**
     * A code identifying the line of insurance the transaction was written under.
     * 
     */
    public void setAnnualStatementLine(String annualStatementLine) {
        this.annualStatementLine = annualStatementLine;
    }

    /**
     * A code identifying whether the record is Personal or Commercial.
     * 
     */
    public Policy.PolicyCategory getPolicyCategory() {
        return policyCategory;
    }

    /**
     * A code identifying whether the record is Personal or Commercial.
     * 
     */
    public void setPolicyCategory(Policy.PolicyCategory policyCategory) {
        this.policyCategory = policyCategory;
    }

    /**
     * A code identifying the coverage.
     * 
     */
    public String getSubline() {
        return subline;
    }

    /**
     * A code identifying the coverage.
     * 
     */
    public void setSubline(String subline) {
        this.subline = subline;
    }

    /**
     * A unique combination of letters and numbers that identifies an insured's insurance policy.  This is the foreign key for connecting the claim and policy records.
     * 
     */
    public String getPolicyIdentifier() {
        return policyIdentifier;
    }

    /**
     * A unique combination of letters and numbers that identifies an insured's insurance policy.  This is the foreign key for connecting the claim and policy records.
     * 
     */
    public void setPolicyIdentifier(String policyIdentifier) {
        this.policyIdentifier = policyIdentifier;
    }

    /**
     * The date the policy became effective.
     * 
     */
    public String getPolicyEffectiveDate() {
        return policyEffectiveDate;
    }

    /**
     * The date the policy became effective.
     * 
     */
    public void setPolicyEffectiveDate(String policyEffectiveDate) {
        this.policyEffectiveDate = policyEffectiveDate;
    }

    /**
     * A code identifying whether the record is a New Business, Change, Renewal, Cancellation, etc.
     * 
     */
    public Policy.TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * A code identifying whether the record is a New Business, Change, Renewal, Cancellation, etc.
     * 
     */
    public void setTransactionType(Policy.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Date the change becomes effective.  Not the booking date.
     * 
     */
    public String getTransactionEffectiveDate() {
        return transactionEffectiveDate;
    }

    /**
     * Date the change becomes effective.  Not the booking date.
     * 
     */
    public void setTransactionEffectiveDate(String transactionEffectiveDate) {
        this.transactionEffectiveDate = transactionEffectiveDate;
    }

    /**
     * Date the change ends.
     * 
     */
    public String getTransactionExpirationDate() {
        return transactionExpirationDate;
    }

    /**
     * Date the change ends.
     * 
     */
    public void setTransactionExpirationDate(String transactionExpirationDate) {
        this.transactionExpirationDate = transactionExpirationDate;
    }

    /**
     * Market in which the policy is being written through.
     * 
     */
    public String getMarket() {
        return market;
    }

    /**
     * Market in which the policy is being written through.
     * 
     */
    public void setMarket(String market) {
        this.market = market;
    }

    /**
     * The date the transaction was financially booked.
     * 
     */
    public String getAccountingDate() {
        return accountingDate;
    }

    /**
     * The date the transaction was financially booked.
     * 
     */
    public void setAccountingDate(String accountingDate) {
        this.accountingDate = accountingDate;
    }

    /**
     * A code that indicates whether the basic rules and forms used in writing the policy are using certain Bureau's rules or form or if they are independent.
     * 
     */
    public String getProgram() {
        return program;
    }

    /**
     * A code that indicates whether the basic rules and forms used in writing the policy are using certain Bureau's rules or form or if they are independent.
     * 
     */
    public void setProgram(String program) {
        this.program = program;
    }

    /**
     * A code that indicates whether there is a multi-car discount or just a single rated car.
     * 
     */
    public Policy.MultiCarDiscountCode getMultiCarDiscountCode() {
        return multiCarDiscountCode;
    }

    /**
     * A code that indicates whether there is a multi-car discount or just a single rated car.
     * 
     */
    public void setMultiCarDiscountCode(Policy.MultiCarDiscountCode multiCarDiscountCode) {
        this.multiCarDiscountCode = multiCarDiscountCode;
    }

    /**
     * A code identifying whether the policy is standalone or part of a package.
     * 
     */
    public Policy.PackageCode getPackageCode() {
        return packageCode;
    }

    /**
     * A code identifying whether the policy is standalone or part of a package.
     * 
     */
    public void setPackageCode(Policy.PackageCode packageCode) {
        this.packageCode = packageCode;
    }

    /**
     * Identifies business written in a pool such as an assigned risk facility or joint underwriting association, and business not written in a pool.
     * 
     */
    public Policy.PoolAffiliation getPoolAffiliation() {
        return poolAffiliation;
    }

    /**
     * Identifies business written in a pool such as an assigned risk facility or joint underwriting association, and business not written in a pool.
     * 
     */
    public void setPoolAffiliation(Policy.PoolAffiliation poolAffiliation) {
        this.poolAffiliation = poolAffiliation;
    }

    /**
     * Based on North Carolina Session 2015 House Bill 288, premiums and losses resulting from program enhancements must not comingle with basic data. This code indicates the use of enhanced endorsements.
     * 
     */
    public Policy.NCProgramEnhancementIndicator getNCProgramEnhancementIndicator() {
        return nCProgramEnhancementIndicator;
    }

    /**
     * Based on North Carolina Session 2015 House Bill 288, premiums and losses resulting from program enhancements must not comingle with basic data. This code indicates the use of enhanced endorsements.
     * 
     */
    public void setNCProgramEnhancementIndicator(Policy.NCProgramEnhancementIndicator nCProgramEnhancementIndicator) {
        this.nCProgramEnhancementIndicator = nCProgramEnhancementIndicator;
    }

    /**
     * A code that distinguishes between business written under the NC Reinsurance Facility and that which is not.
     * 
     */
    public Policy.NCReinsuranceFacility getNCReinsuranceFacility() {
        return nCReinsuranceFacility;
    }

    /**
     * A code that distinguishes between business written under the NC Reinsurance Facility and that which is not.
     * 
     */
    public void setNCReinsuranceFacility(Policy.NCReinsuranceFacility nCReinsuranceFacility) {
        this.nCReinsuranceFacility = nCReinsuranceFacility;
    }

    /**
     * A code that distinguishes between business written under the SC Reinsurance Facility and that which is not.
     * 
     */
    public Policy.SCReinsuranceFacility getSCReinsuranceFacility() {
        return sCReinsuranceFacility;
    }

    /**
     * A code that distinguishes between business written under the SC Reinsurance Facility and that which is not.
     * 
     */
    public void setSCReinsuranceFacility(Policy.SCReinsuranceFacility sCReinsuranceFacility) {
        this.sCReinsuranceFacility = sCReinsuranceFacility;
    }

    /**
     * A field that indicates whether the multi-car risk is subject to the PIP Premium Discount and whether the Principal Auto is not Discounted. 
     * 
     */
    public Policy.MultiCarRisks getMultiCarRisks() {
        return multiCarRisks;
    }

    /**
     * A field that indicates whether the multi-car risk is subject to the PIP Premium Discount and whether the Principal Auto is not Discounted. 
     * 
     */
    public void setMultiCarRisks(Policy.MultiCarRisks multiCarRisks) {
        this.multiCarRisks = multiCarRisks;
    }


    /**
     * A code that indicates whether there is a multi-car discount or just a single rated car.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum MultiCarDiscountCode {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Policy.MultiCarDiscountCode> CONSTANTS = new HashMap<String, Policy.MultiCarDiscountCode>();

        static {
            for (Policy.MultiCarDiscountCode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        MultiCarDiscountCode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.MultiCarDiscountCode fromValue(String value) {
            Policy.MultiCarDiscountCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A field that indicates whether the multi-car risk is subject to the PIP Premium Discount and whether the Principal Auto is not Discounted. 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum MultiCarRisks {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Policy.MultiCarRisks> CONSTANTS = new HashMap<String, Policy.MultiCarRisks>();

        static {
            for (Policy.MultiCarRisks c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        MultiCarRisks(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.MultiCarRisks fromValue(String value) {
            Policy.MultiCarRisks constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Based on North Carolina Session 2015 House Bill 288, premiums and losses resulting from program enhancements must not comingle with basic data. This code indicates the use of enhanced endorsements.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum NCProgramEnhancementIndicator {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Policy.NCProgramEnhancementIndicator> CONSTANTS = new HashMap<String, Policy.NCProgramEnhancementIndicator>();

        static {
            for (Policy.NCProgramEnhancementIndicator c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        NCProgramEnhancementIndicator(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.NCProgramEnhancementIndicator fromValue(String value) {
            Policy.NCProgramEnhancementIndicator constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A code that distinguishes between business written under the NC Reinsurance Facility and that which is not.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum NCReinsuranceFacility {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Policy.NCReinsuranceFacility> CONSTANTS = new HashMap<String, Policy.NCReinsuranceFacility>();

        static {
            for (Policy.NCReinsuranceFacility c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        NCReinsuranceFacility(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.NCReinsuranceFacility fromValue(String value) {
            Policy.NCReinsuranceFacility constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A code identifying whether the policy is standalone or part of a package.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum PackageCode {

        @SerializedName("Standalone")
        STANDALONE("Standalone"),
        @SerializedName("Package")
        PACKAGE("Package");
        private final String value;
        private final static Map<String, Policy.PackageCode> CONSTANTS = new HashMap<String, Policy.PackageCode>();

        static {
            for (Policy.PackageCode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PackageCode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.PackageCode fromValue(String value) {
            Policy.PackageCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A code identifying whether the record is Personal or Commercial.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum PolicyCategory {

        @SerializedName("Personal")
        PERSONAL("Personal"),
        @SerializedName("Commercial")
        COMMERCIAL("Commercial");
        private final String value;
        private final static Map<String, Policy.PolicyCategory> CONSTANTS = new HashMap<String, Policy.PolicyCategory>();

        static {
            for (Policy.PolicyCategory c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PolicyCategory(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.PolicyCategory fromValue(String value) {
            Policy.PolicyCategory constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Identifies business written in a pool such as an assigned risk facility or joint underwriting association, and business not written in a pool.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum PoolAffiliation {

        @SerializedName("Assigned Risk")
        ASSIGNED_RISK("Assigned Risk"),
        @SerializedName("JUA")
        JUA("JUA"),
        @SerializedName("None")
        NONE("None");
        private final String value;
        private final static Map<String, Policy.PoolAffiliation> CONSTANTS = new HashMap<String, Policy.PoolAffiliation>();

        static {
            for (Policy.PoolAffiliation c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PoolAffiliation(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.PoolAffiliation fromValue(String value) {
            Policy.PoolAffiliation constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A code that distinguishes between business written under the SC Reinsurance Facility and that which is not.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum SCReinsuranceFacility {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Policy.SCReinsuranceFacility> CONSTANTS = new HashMap<String, Policy.SCReinsuranceFacility>();

        static {
            for (Policy.SCReinsuranceFacility c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SCReinsuranceFacility(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.SCReinsuranceFacility fromValue(String value) {
            Policy.SCReinsuranceFacility constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * A code identifying whether the record is a New Business, Change, Renewal, Cancellation, etc.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum TransactionType {

        @SerializedName("NL")
        NL("NL"),
        @SerializedName("CHG")
        CHG("CHG"),
        @SerializedName("RENL")
        RENL("RENL"),
        @SerializedName("CANC")
        CANC("CANC"),
        @SerializedName("REIN")
        REIN("REIN");
        private final String value;
        private final static Map<String, Policy.TransactionType> CONSTANTS = new HashMap<String, Policy.TransactionType>();

        static {
            for (Policy.TransactionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TransactionType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Policy.TransactionType fromValue(String value) {
            Policy.TransactionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
