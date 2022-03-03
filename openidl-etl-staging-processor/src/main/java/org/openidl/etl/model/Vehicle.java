
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * vehicle level elements
 * 
 */
@Generated("jsonschema2pojo")
public class Vehicle {

    /**
     * The shape and model of a vehicle.
     * 
     */
    @SerializedName("BodyStyle")
    @Expose
    private String bodyStyle;
    /**
     * Type of Vehicle
     * 
     */
    @SerializedName("BodySize")
    @Expose
    private String bodySize;
    /**
     * Size of Engine in Motorcycles or Motor Scooters
     * 
     */
    @SerializedName("EngineSizeMotorcycle")
    @Expose
    private String engineSizeMotorcycle;
    /**
     * Unique combination of numbers and letters that identify each vehicle.
     * 
     */
    @SerializedName("VIN")
    @Expose
    private String vin;
    /**
     * State where the vehicle is principally garaged.
     * 
     */
    @SerializedName("GarageState")
    @Expose
    private String garageState;
    /**
     * First 5 digits of Zip Code of where the vehicle is principally garaged.
     * 
     */
    @SerializedName("GarageZIP5")
    @Expose
    private String garageZIP5;
    /**
     * Last 4 digits of Zip Code of where the vehicle is principally garaged.
     * 
     */
    @SerializedName("GarageZIP4")
    @Expose
    private String garageZIP4;
    /**
     * Model Year
     * 
     */
    @SerializedName("VehicleYear")
    @Expose
    private String vehicleYear;
    /**
     * Primary use of the vehicle.
     * 
     */
    @SerializedName("VehicleUse")
    @Expose
    private Vehicle.VehicleUse vehicleUse;
    /**
     * Abstraction of the vehicle rating symbols.  Not company rating symbols.
     * 
     */
    @SerializedName("VechicleSymbol")
    @Expose
    private String vechicleSymbol;
    /**
     * Vehicle Performance characteristic
     * 
     */
    @SerializedName("VechiclePerformance")
    @Expose
    private String vechiclePerformance;
    /**
     * Number of miles driven per commute.
     * 
     */
    @SerializedName("PerCommuteMiles")
    @Expose
    private Double perCommuteMiles;
    /**
     * Number of miles driven annually.
     * 
     */
    @SerializedName("AnnualVehicleMiles")
    @Expose
    private Double annualVehicleMiles;
    /**
     * Code indicating whether Anti-Lock Brakes discount was applied to the vehicle.
     * 
     */
    @SerializedName("AntilockBrakesDiscount")
    @Expose
    private Vehicle.AntilockBrakesDiscount antilockBrakesDiscount;
    /**
     * Code indicating whether Safety Restraint discount was applied to the vehicle.
     * 
     */
    @SerializedName("SafetyRestraintDiscount")
    @Expose
    private Vehicle.SafetyRestraintDiscount safetyRestraintDiscount;
    /**
     * Code indicating whether Anti-Theft Device discount was applied to the vehicle.
     * 
     */
    @SerializedName("AntiTheftDeviceDiscount")
    @Expose
    private Vehicle.AntiTheftDeviceDiscount antiTheftDeviceDiscount;

    /**
     * The shape and model of a vehicle.
     * 
     */
    public String getBodyStyle() {
        return bodyStyle;
    }

    /**
     * The shape and model of a vehicle.
     * 
     */
    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    /**
     * Type of Vehicle
     * 
     */
    public String getBodySize() {
        return bodySize;
    }

    /**
     * Type of Vehicle
     * 
     */
    public void setBodySize(String bodySize) {
        this.bodySize = bodySize;
    }

    /**
     * Size of Engine in Motorcycles or Motor Scooters
     * 
     */
    public String getEngineSizeMotorcycle() {
        return engineSizeMotorcycle;
    }

    /**
     * Size of Engine in Motorcycles or Motor Scooters
     * 
     */
    public void setEngineSizeMotorcycle(String engineSizeMotorcycle) {
        this.engineSizeMotorcycle = engineSizeMotorcycle;
    }

    /**
     * Unique combination of numbers and letters that identify each vehicle.
     * 
     */
    public String getVin() {
        return vin;
    }

    /**
     * Unique combination of numbers and letters that identify each vehicle.
     * 
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * State where the vehicle is principally garaged.
     * 
     */
    public String getGarageState() {
        return garageState;
    }

    /**
     * State where the vehicle is principally garaged.
     * 
     */
    public void setGarageState(String garageState) {
        this.garageState = garageState;
    }

    /**
     * First 5 digits of Zip Code of where the vehicle is principally garaged.
     * 
     */
    public String getGarageZIP5() {
        return garageZIP5;
    }

    /**
     * First 5 digits of Zip Code of where the vehicle is principally garaged.
     * 
     */
    public void setGarageZIP5(String garageZIP5) {
        this.garageZIP5 = garageZIP5;
    }

    /**
     * Last 4 digits of Zip Code of where the vehicle is principally garaged.
     * 
     */
    public String getGarageZIP4() {
        return garageZIP4;
    }

    /**
     * Last 4 digits of Zip Code of where the vehicle is principally garaged.
     * 
     */
    public void setGarageZIP4(String garageZIP4) {
        this.garageZIP4 = garageZIP4;
    }

    /**
     * Model Year
     * 
     */
    public String getVehicleYear() {
        return vehicleYear;
    }

    /**
     * Model Year
     * 
     */
    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    /**
     * Primary use of the vehicle.
     * 
     */
    public Vehicle.VehicleUse getVehicleUse() {
        return vehicleUse;
    }

    /**
     * Primary use of the vehicle.
     * 
     */
    public void setVehicleUse(Vehicle.VehicleUse vehicleUse) {
        this.vehicleUse = vehicleUse;
    }

    /**
     * Abstraction of the vehicle rating symbols.  Not company rating symbols.
     * 
     */
    public String getVechicleSymbol() {
        return vechicleSymbol;
    }

    /**
     * Abstraction of the vehicle rating symbols.  Not company rating symbols.
     * 
     */
    public void setVechicleSymbol(String vechicleSymbol) {
        this.vechicleSymbol = vechicleSymbol;
    }

    /**
     * Vehicle Performance characteristic
     * 
     */
    public String getVechiclePerformance() {
        return vechiclePerformance;
    }

    /**
     * Vehicle Performance characteristic
     * 
     */
    public void setVechiclePerformance(String vechiclePerformance) {
        this.vechiclePerformance = vechiclePerformance;
    }

    /**
     * Number of miles driven per commute.
     * 
     */
    public Double getPerCommuteMiles() {
        return perCommuteMiles;
    }

    /**
     * Number of miles driven per commute.
     * 
     */
    public void setPerCommuteMiles(Double perCommuteMiles) {
        this.perCommuteMiles = perCommuteMiles;
    }

    /**
     * Number of miles driven annually.
     * 
     */
    public Double getAnnualVehicleMiles() {
        return annualVehicleMiles;
    }

    /**
     * Number of miles driven annually.
     * 
     */
    public void setAnnualVehicleMiles(Double annualVehicleMiles) {
        this.annualVehicleMiles = annualVehicleMiles;
    }

    /**
     * Code indicating whether Anti-Lock Brakes discount was applied to the vehicle.
     * 
     */
    public Vehicle.AntilockBrakesDiscount getAntilockBrakesDiscount() {
        return antilockBrakesDiscount;
    }

    /**
     * Code indicating whether Anti-Lock Brakes discount was applied to the vehicle.
     * 
     */
    public void setAntilockBrakesDiscount(Vehicle.AntilockBrakesDiscount antilockBrakesDiscount) {
        this.antilockBrakesDiscount = antilockBrakesDiscount;
    }

    /**
     * Code indicating whether Safety Restraint discount was applied to the vehicle.
     * 
     */
    public Vehicle.SafetyRestraintDiscount getSafetyRestraintDiscount() {
        return safetyRestraintDiscount;
    }

    /**
     * Code indicating whether Safety Restraint discount was applied to the vehicle.
     * 
     */
    public void setSafetyRestraintDiscount(Vehicle.SafetyRestraintDiscount safetyRestraintDiscount) {
        this.safetyRestraintDiscount = safetyRestraintDiscount;
    }

    /**
     * Code indicating whether Anti-Theft Device discount was applied to the vehicle.
     * 
     */
    public Vehicle.AntiTheftDeviceDiscount getAntiTheftDeviceDiscount() {
        return antiTheftDeviceDiscount;
    }

    /**
     * Code indicating whether Anti-Theft Device discount was applied to the vehicle.
     * 
     */
    public void setAntiTheftDeviceDiscount(Vehicle.AntiTheftDeviceDiscount antiTheftDeviceDiscount) {
        this.antiTheftDeviceDiscount = antiTheftDeviceDiscount;
    }


    /**
     * Code indicating whether Anti-Lock Brakes discount was applied to the vehicle.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AntilockBrakesDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Vehicle.AntilockBrakesDiscount> CONSTANTS = new HashMap<String, Vehicle.AntilockBrakesDiscount>();

        static {
            for (Vehicle.AntilockBrakesDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AntilockBrakesDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.AntilockBrakesDiscount fromValue(String value) {
            Vehicle.AntilockBrakesDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Code indicating whether Anti-Theft Device discount was applied to the vehicle.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AntiTheftDeviceDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Vehicle.AntiTheftDeviceDiscount> CONSTANTS = new HashMap<String, Vehicle.AntiTheftDeviceDiscount>();

        static {
            for (Vehicle.AntiTheftDeviceDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AntiTheftDeviceDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.AntiTheftDeviceDiscount fromValue(String value) {
            Vehicle.AntiTheftDeviceDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Code indicating whether Safety Restraint discount was applied to the vehicle.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum SafetyRestraintDiscount {

        @SerializedName("Yes")
        YES("Yes"),
        @SerializedName("No")
        NO("No");
        private final String value;
        private final static Map<String, Vehicle.SafetyRestraintDiscount> CONSTANTS = new HashMap<String, Vehicle.SafetyRestraintDiscount>();

        static {
            for (Vehicle.SafetyRestraintDiscount c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SafetyRestraintDiscount(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.SafetyRestraintDiscount fromValue(String value) {
            Vehicle.SafetyRestraintDiscount constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Primary use of the vehicle.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum VehicleUse {

        @SerializedName("Personal")
        PERSONAL("Personal"),
        @SerializedName("Work")
        WORK("Work"),
        @SerializedName("Delivery")
        DELIVERY("Delivery");
        private final String value;
        private final static Map<String, Vehicle.VehicleUse> CONSTANTS = new HashMap<String, Vehicle.VehicleUse>();

        static {
            for (Vehicle.VehicleUse c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        VehicleUse(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Vehicle.VehicleUse fromValue(String value) {
            Vehicle.VehicleUse constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
