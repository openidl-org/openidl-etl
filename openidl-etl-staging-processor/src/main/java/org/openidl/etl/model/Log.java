
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Log {

    @SerializedName("level")
    @Expose
    private Log.Level level;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    public Log.Level getLevel() {
        return level;
    }

    public void setLevel(Log.Level level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Generated("jsonschema2pojo")
    public enum Level {

        @SerializedName("Error")
        ERROR("Error"),
        @SerializedName("Warning")
        WARNING("Warning"),
        @SerializedName("Info")
        INFO("Info");
        private final String value;
        private final static Map<String, Log.Level> CONSTANTS = new HashMap<String, Log.Level>();

        static {
            for (Log.Level c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Level(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Log.Level fromValue(String value) {
            Log.Level constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
