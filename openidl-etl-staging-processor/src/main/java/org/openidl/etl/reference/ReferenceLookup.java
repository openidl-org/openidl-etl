package org.openidl.etl.reference;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class ReferenceLookup {
    
    public String lookupLOBCode(String lob) {
        return "";
    }

    public String lookupStateCode(String stateAbbreviation) {
        return "";
    }

    private static String readFileFromResources(String fileName) throws IOException {
        return IOUtils.resourceToString(fileName, StandardCharsets.UTF_8);
    }
}
