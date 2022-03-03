
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * driver level elements
 * 
 */
@Generated("jsonschema2pojo")
public class Driver {

    /**
     * Gender of highest rated operator of each insured vehicle. 
     * 
     */
    @SerializedName("Gender")
    @Expose
    private Driver.Gender gender;
    /**
     * Driver's age when policy was written.
     * 
     */
    @SerializedName("DriverAge")
    @Expose
    private Double driverAge;
    /**
     * Indicates whether driver is single or married.
     * 
     */
    @SerializedName("MaritalStatus")
    @Expose
    private Driver.MaritalStatus maritalStatus;
    /**
     * Discount for the practice of using driving strategies that minimize risk and help avoid accidents, as by predicting hazards on the road 
     * 
     */
    @SerializedName("SafeDrivingDefensiveDriverDiscount")
    @Expose
    private Driver.SafeDrivingDefensiveDriverDiscount safeDrivingDefensiveDriverDiscount;
    /**
     * Discount for student getting good grades in school.
     * 
     */
    @SerializedName("GoodStudentDiscount")
    @Expose
    private Driver.GoodStudentDiscount goodStudentDiscount;
    /**
     * Number of motoring conviction codes, fixed penalty notices, endorsements against the driver.  
     * 
     */
    @SerializedName("NumberOfPenaltyPoints")
    @Expose
    private Double numberOfPenaltyPoints;
    /**
     * Discount for Driver taking Driving School lessons.
     * 
     */
    @SerializedName("DriversTrainingDiscount")
    @Expose
    private Driver.DriversTrainingDiscount driversTrainingDiscount;
    /**
     * Discount for 55 & over years olds for successful completion of the PA certified training course.
     * 
     */
    @SerializedName("55AndOverDiscount")
    @Expose
    private Driver._55AndOverDiscount _55AndOverDiscount;
    /**
     * Indicates whether the vehicle has an accident prevention credit.
     * 
     */
    @SerializedName("AccidentPreventionCredit")
    @Expose
    private Driver.AccidentPreventionCredit accidentPreventionCredit;

    /**
     * Gender of highest rated operator of each insured vehicle. 
     * 
     */
    public Driver.Gender getGender() {
        return gender;
    }

    /**
     * Gender of highest rated operator of each insured vehicle. 
     * 
     */
    public void setGender(Driver.Gender gender) {
        this.gender = gender;
    }

    /**
     * Driver's age when policy was written.
     * 
     */
    public Double getDriverAge() {
        return driverAge;
    }

    /**
     * Driver's age when policy was written.
     * 
     */
    public void setDriverAge(Double driverAge) {
        this.driverAge = driverAge;
    }

    /**
     * Indicates whether driver is single or married.
     * 
     */
    public Driver.MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Indicates whether driver is single or married.
     * 
     */
    public void setMaritalStatus(Driver.MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Discount for the practice of using driving strategies that minimize risk and help avoid accidents, as by predicting hazards on the road 
     * 
     */
    public Driver.SafeDrivingDefensiveDriverDiscount getSafeDrivingDefensiveDriverDiscount() {
        return safeDrivingDefensiveDriverDiscount;
    }

    /**
     * Discount for the practice of using driving strategies that minimize risk and help avoid accidents, as by predicting hazards on the road 
     * 
     */
    public void setSafeDrivingDefensiveDriverDiscount(Driver.SafeDrivingDefensiveDriverDiscount safeDrivingDefensiveDriverDiscount) {
        this.safeDrivingDefensiveDriverDiscount = safeDrivingDefensiveDriverDiscount;
    }

    /**
     * Discount for student getting good grades in school.
     * 
     */
    public Driver.GoodStudentDiscount getGoodStudentDiscount() {
        return goodStudentDiscount;
    }

    /**
     * Discount for student getting good grades in school.
     * 
     */
    public void setGoodStudentDiscount(Driver.GoodStudentDiscount goodStudentDiscount) {
        this.goodStudentDiscount = goodStudentDiscount;
    }

    /**
     * Number of motoring conviction codes, fixed penalty notices, endorsements against the driver.  
     * 
     */
    public Double getNumberOfPenaltyPoints() {
        return numberOfPenaltyPoints;
    }

    /**
     * Number of motoring conviction codes, fixed penalty notices, endorsements against the driver.  
     * 
     */
    public void setNumberOfPenaltyPoints(Double numberOfPenaltyPoints) {
        this.numberOfPenaltyPoints = numberOfPenaltyPoints;
    }

    /**
     * Discount for Driver taking Driving School lessons.
     * 
     */
    public Driver.DriversTrainingDiscount getDriversTrainingDiscount() {
        return driversTrainingDiscount;
    }

    /**
     * Discount for Driver taking Driving School lessons.
     * 
     */
    public void setDriversTrainingDiscount(Driver.DriversTrainingDiscount driversTrainingDiscount) {
        this.driversTrainingDiscount = driversTrainingDiscount;
    }

    /**
     * Discount for 55 & over years olds for successful completion of the PA certified training course.
     * 
     */
    public Driver._55AndOverDiscount get55AndOverDiscount() {
        return _55AndOverDiscount;
    }

    /**
     * Discount for 55 & over years olds for successful completion of the PA certified training course.
     * 
     */
    public void set55AndOverDiscount(Driver._55AndOverDiscount _55AndOverDiscount) {
        this._55AndOverDiscount = _55AndOverDiscount;
    }

    /**
     * Indicates whether the vehicle has an accident prevention credit.
     * 
     */
    public Driver.AccidentPreventionCredit getAccidentPreventionCredit() {
        return accidentPreventionCredit;
    }

    /**
     * Indicates whether the vehicle has an accident prevention credit.
     * 
     */
    public void setAccidentPreventionCredit(Driver.AccidentPreventionCredit accidentPreventionCredit) {
        this.accidentPreventionCredit = accidentPreventionCredit;
    }


    /**
     * Indicates whether the vehicle has an accident prevention credit.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AccidentPreventionCredit {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Driver.AccidentPreventionCredit> CONSTANTS = new HashMap<String, Driver.AccidentPreventionCredit>();

        static {
            for (Driver.AccidentPreventionCredit c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AccidentPreventionCredit(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver.AccidentPreventionCredit fromValue(String value) {
            Driver.AccidentPreventionCredit constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Discount for Driver taking Driving School lessons.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum DriversTrainingDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Driver.DriversTrainingDiscount> CONSTANTS = new HashMap<String, Driver.DriversTrainingDiscount>();

        static {
            for (Driver.DriversTrainingDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        DriversTrainingDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver.DriversTrainingDiscount fromValue(String value) {
            Driver.DriversTrainingDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Gender of highest rated operator of each insured vehicle. 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Gender {

        @SerializedName("Male")
        MALE("Male"),
        @SerializedName("Female")
        FEMALE("Female"),
        @SerializedName("Not Disclosed")
        NOT_DISCLOSED("Not Disclosed");
        private final String value;
        private final static Map<String, Driver.Gender> CONSTANTS = new HashMap<String, Driver.Gender>();

        static {
            for (Driver.Gender c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Gender(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver.Gender fromValue(String value) {
            Driver.Gender constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Discount for student getting good grades in school.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum GoodStudentDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Driver.GoodStudentDiscount> CONSTANTS = new HashMap<String, Driver.GoodStudentDiscount>();

        static {
            for (Driver.GoodStudentDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        GoodStudentDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver.GoodStudentDiscount fromValue(String value) {
            Driver.GoodStudentDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Indicates whether driver is single or married.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum MaritalStatus {

        @SerializedName("Married")
        MARRIED("Married"),
        @SerializedName("Single")
        SINGLE("Single"),
        @SerializedName("Divorced")
        DIVORCED("Divorced");
        private final String value;
        private final static Map<String, Driver.MaritalStatus> CONSTANTS = new HashMap<String, Driver.MaritalStatus>();

        static {
            for (Driver.MaritalStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        MaritalStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver.MaritalStatus fromValue(String value) {
            Driver.MaritalStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Discount for the practice of using driving strategies that minimize risk and help avoid accidents, as by predicting hazards on the road 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum SafeDrivingDefensiveDriverDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Driver.SafeDrivingDefensiveDriverDiscount> CONSTANTS = new HashMap<String, Driver.SafeDrivingDefensiveDriverDiscount>();

        static {
            for (Driver.SafeDrivingDefensiveDriverDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SafeDrivingDefensiveDriverDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver.SafeDrivingDefensiveDriverDiscount fromValue(String value) {
            Driver.SafeDrivingDefensiveDriverDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Discount for 55 & over years olds for successful completion of the PA certified training course.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum _55AndOverDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Driver._55AndOverDiscount> CONSTANTS = new HashMap<String, Driver._55AndOverDiscount>();

        static {
            for (Driver._55AndOverDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        _55AndOverDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Driver._55AndOverDiscount fromValue(String value) {
            Driver._55AndOverDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
