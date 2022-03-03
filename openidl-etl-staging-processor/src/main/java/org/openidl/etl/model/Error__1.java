
package org.openidl.etl.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Error__1 {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("argument")
    @Expose
    private String argument;
    @SerializedName("level")
    @Expose
    private Error__1 .Level level;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("schema")
    @Expose
    private String schema;
    @SerializedName("stack")
    @Expose
    private String stack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Error__1 .Level getLevel() {
        return level;
    }

    public void setLevel(Error__1 .Level level) {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
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
        private final static Map<String, Error__1 .Level> CONSTANTS = new HashMap<String, Error__1 .Level>();

        static {
            for (Error__1 .Level c: values()) {
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

        public static Error__1 .Level fromValue(String value) {
            Error__1 .Level constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
