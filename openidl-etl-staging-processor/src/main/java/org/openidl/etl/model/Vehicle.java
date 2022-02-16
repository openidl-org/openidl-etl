
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * vehicle level elements
 * 
 */

public class Vehicle {

    /**
     * 
     */
    @SerializedName("PrimaryDriverGender")
    @Expose
    private Vehicle.PrimaryDriverGender primaryDriverGender;
    /**
     * 
     */
    @SerializedName("LicenseNumber")
    @Expose
    private String licenseNumber;
    /**
     * 
     */
    @SerializedName("LicenseState")
    @Expose
    private String licenseState;
    /**
     * 
     */
    @SerializedName("AgeLicenseFirstIssued")
    @Expose
    private Double ageLicenseFirstIssued;
    /**
     * 
     */
    @SerializedName("DriverBirthdate")
    @Expose
    private String driverBirthdate;
    /**
     * 
     */
    @SerializedName("PrimaryDriverAge")
    @Expose
    private Double primaryDriverAge;
    /**
     * 
     */
    @SerializedName("PrimaryDriverGoodStudentDiscount")
    @Expose
    private Vehicle.PrimaryDriverGoodStudentDiscount primaryDriverGoodStudentDiscount;
    /**
     * 
     */
    @SerializedName("VIN")
    @Expose
    private Double vin;
    /**
     * 
     */
    @SerializedName("GarageZip5")
    @Expose
    private String garageZip5;
    /**
     * 
     */
    @SerializedName("GarageZip4")
    @Expose
    private String garageZip4;
    /**
     * 
     */
    @SerializedName("Make")
    @Expose
    private String make;
    /**
     * 
     */
    @SerializedName("Model")
    @Expose
    private String model;
    /**
     * 
     */
    @SerializedName("Trim")
    @Expose
    private String trim;
    /**
     * 
     */
    @SerializedName("Symbol")
    @Expose
    private String symbol;
    /**
     * 
     */
    @SerializedName("Year")
    @Expose
    private String year;
    /**
     * 
     */
    @SerializedName("Use")
    @Expose
    private Vehicle.Use use;
    /**
     * 
     */
    @SerializedName("PerCommuteMiles")
    @Expose
    private Double perCommuteMiles;
    /**
     * 
     */
    @SerializedName("AnnualMiles")
    @Expose
    private String annualMiles;
    /**
     * 
     */
    @SerializedName("AntilockBrakes")
    @Expose
    private Vehicle.AntilockBrakes antilockBrakes;
    /**
     * 
     */
    @SerializedName("SafetyRestraintDevice")
    @Expose
    private String safetyRestraintDevice;
    /**
     * 
     */
    @SerializedName("Customizations")
    @Expose
    private String customizations;
    /**
     * 
     */
    @SerializedName("FuelType")
    @Expose
    private Vehicle.FuelType fuelType;
    /**
     * 
     */
    @SerializedName("Telematics")
    @Expose
    private Vehicle.Telematics telematics;
    /**
     * 
     */
    @SerializedName("AntitheftDevice")
    @Expose
    private Vehicle.AntitheftDevice antitheftDevice;

    /**
     * 
     */
    public Vehicle.PrimaryDriverGender getPrimaryDriverGender() {
        return primaryDriverGender;
    }

    /**
     * 
     */
    public void setPrimaryDriverGender(Vehicle.PrimaryDriverGender primaryDriverGender) {
        this.primaryDriverGender = primaryDriverGender;
    }

    /**
     * 
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * 
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * 
     */
    public String getLicenseState() {
        return licenseState;
    }

    /**
     * 
     */
    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }

    /**
     * 
     */
    public Double getAgeLicenseFirstIssued() {
        return ageLicenseFirstIssued;
    }

    /**
     * 
     */
    public void setAgeLicenseFirstIssued(Double ageLicenseFirstIssued) {
        this.ageLicenseFirstIssued = ageLicenseFirstIssued;
    }

    /**
     * 
     */
    public String getDriverBirthdate() {
        return driverBirthdate;
    }

    /**
     * 
     */
    public void setDriverBirthdate(String driverBirthdate) {
        this.driverBirthdate = driverBirthdate;
    }

    /**
     * 
     */
    public Double getPrimaryDriverAge() {
        return primaryDriverAge;
    }

    /**
     * 
     */
    public void setPrimaryDriverAge(Double primaryDriverAge) {
        this.primaryDriverAge = primaryDriverAge;
    }

    /**
     * 
     */
    public Vehicle.PrimaryDriverGoodStudentDiscount getPrimaryDriverGoodStudentDiscount() {
        return primaryDriverGoodStudentDiscount;
    }

    /**
     * 
     */
    public void setPrimaryDriverGoodStudentDiscount(Vehicle.PrimaryDriverGoodStudentDiscount primaryDriverGoodStudentDiscount) {
        this.primaryDriverGoodStudentDiscount = primaryDriverGoodStudentDiscount;
    }

    /**
     * 
     */
    public Double getVin() {
        return vin;
    }

    /**
     * 
     */
    public void setVin(Double vin) {
        this.vin = vin;
    }

    /**
     * 
     */
    public String getGarageZip5() {
        return garageZip5;
    }

    /**
     * 
     */
    public void setGarageZip5(String garageZip5) {
        this.garageZip5 = garageZip5;
    }

    /**
     * 
     */
    public String getGarageZip4() {
        return garageZip4;
    }

    /**
     * 
     */
    public void setGarageZip4(String garageZip4) {
        this.garageZip4 = garageZip4;
    }

    /**
     * 
     */
    public String getMake() {
        return make;
    }

    /**
     * 
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * 
     */
    public String getModel() {
        return model;
    }

    /**
     * 
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 
     */
    public String getTrim() {
        return trim;
    }

    /**
     * 
     */
    public void setTrim(String trim) {
        this.trim = trim;
    }

    /**
     * 
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     */
    public Vehicle.Use getUse() {
        return use;
    }

    /**
     * 
     */
    public void setUse(Vehicle.Use use) {
        this.use = use;
    }

    /**
     * 
     */
    public Double getPerCommuteMiles() {
        return perCommuteMiles;
    }

    /**
     * 
     */
    public void setPerCommuteMiles(Double perCommuteMiles) {
        this.perCommuteMiles = perCommuteMiles;
    }

    /**
     * 
     */
    public String getAnnualMiles() {
        return annualMiles;
    }

    /**
     * 
     */
    public void setAnnualMiles(String annualMiles) {
        this.annualMiles = annualMiles;
    }

    /**
     * 
     */
    public Vehicle.AntilockBrakes getAntilockBrakes() {
        return antilockBrakes;
    }

    /**
     * 
     */
    public void setAntilockBrakes(Vehicle.AntilockBrakes antilockBrakes) {
        this.antilockBrakes = antilockBrakes;
    }

    /**
     * 
     */
    public String getSafetyRestraintDevice() {
        return safetyRestraintDevice;
    }

    /**
     * 
     */
    public void setSafetyRestraintDevice(String safetyRestraintDevice) {
        this.safetyRestraintDevice = safetyRestraintDevice;
    }

    /**
     * 
     */
    public String getCustomizations() {
        return customizations;
    }

    /**
     * 
     */
    public void setCustomizations(String customizations) {
        this.customizations = customizations;
    }

    /**
     * 
     */
    public Vehicle.FuelType getFuelType() {
        return fuelType;
    }

    /**
     * 
     */
    public void setFuelType(Vehicle.FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * 
     */
    public Vehicle.Telematics getTelematics() {
        return telematics;
    }

    /**
     * 
     */
    public void setTelematics(Vehicle.Telematics telematics) {
        this.telematics = telematics;
    }

    /**
     * 
     */
    public Vehicle.AntitheftDevice getAntitheftDevice() {
        return antitheftDevice;
    }

    /**
     * 
     */
    public void setAntitheftDevice(Vehicle.AntitheftDevice antitheftDevice) {
        this.antitheftDevice = antitheftDevice;
    }


    /**
     * 
     */
    
    public enum AntilockBrakes {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Vehicle.AntilockBrakes> CONSTANTS = new HashMap<String, Vehicle.AntilockBrakes>();

        static {
            for (Vehicle.AntilockBrakes c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AntilockBrakes(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.AntilockBrakes fromValue(String value) {
            Vehicle.AntilockBrakes constant = CONSTANTS.get(value);
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
    
    public enum AntitheftDevice {

        @SerializedName("Full")
        FULL("Full"),
        @SerializedName("Partial")
        PARTIAL("Partial");
        private final String value;
        private final static Map<String, Vehicle.AntitheftDevice> CONSTANTS = new HashMap<String, Vehicle.AntitheftDevice>();

        static {
            for (Vehicle.AntitheftDevice c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AntitheftDevice(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.AntitheftDevice fromValue(String value) {
            Vehicle.AntitheftDevice constant = CONSTANTS.get(value);
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
    
    public enum FuelType {

        @SerializedName("Gas")
        GAS("Gas"),
        @SerializedName("e85")
        E_85("e85"),
        @SerializedName("LNG")
        LNG("LNG"),
        @SerializedName("Diesel")
        DIESEL("Diesel"),
        @SerializedName("Electric")
        ELECTRIC("Electric"),
        @SerializedName("Hybrid")
        HYBRID("Hybrid"),
        @SerializedName("Hydrogen")
        HYDROGEN("Hydrogen"),
        @SerializedName("Experimental")
        EXPERIMENTAL("Experimental");
        private final String value;
        private final static Map<String, Vehicle.FuelType> CONSTANTS = new HashMap<String, Vehicle.FuelType>();

        static {
            for (Vehicle.FuelType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        FuelType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.FuelType fromValue(String value) {
            Vehicle.FuelType constant = CONSTANTS.get(value);
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
    
    public enum PrimaryDriverGender {

        @SerializedName("Male")
        MALE("Male"),
        @SerializedName("Female")
        FEMALE("Female");
        private final String value;
        private final static Map<String, Vehicle.PrimaryDriverGender> CONSTANTS = new HashMap<String, Vehicle.PrimaryDriverGender>();

        static {
            for (Vehicle.PrimaryDriverGender c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PrimaryDriverGender(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.PrimaryDriverGender fromValue(String value) {
            Vehicle.PrimaryDriverGender constant = CONSTANTS.get(value);
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
    
    public enum PrimaryDriverGoodStudentDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Vehicle.PrimaryDriverGoodStudentDiscount> CONSTANTS = new HashMap<String, Vehicle.PrimaryDriverGoodStudentDiscount>();

        static {
            for (Vehicle.PrimaryDriverGoodStudentDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PrimaryDriverGoodStudentDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.PrimaryDriverGoodStudentDiscount fromValue(String value) {
            Vehicle.PrimaryDriverGoodStudentDiscount constant = CONSTANTS.get(value);
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
    
    public enum Telematics {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Vehicle.Telematics> CONSTANTS = new HashMap<String, Vehicle.Telematics>();

        static {
            for (Vehicle.Telematics c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Telematics(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.Telematics fromValue(String value) {
            Vehicle.Telematics constant = CONSTANTS.get(value);
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
    
    public enum Use {

        @SerializedName("Peronal")
        PERONAL("Peronal"),
        @SerializedName("Work")
        WORK("Work"),
        @SerializedName("Delivery")
        DELIVERY("Delivery");
        private final String value;
        private final static Map<String, Vehicle.Use> CONSTANTS = new HashMap<String, Vehicle.Use>();

        static {
            for (Vehicle.Use c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Use(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.Use fromValue(String value) {
            Vehicle.Use constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
