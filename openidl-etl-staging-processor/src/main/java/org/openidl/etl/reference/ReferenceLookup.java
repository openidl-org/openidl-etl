package org.openidl.etl.reference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReferenceLookup {
    
    private static Properties stateCodes;
    private static Properties lobCodes;
    private static Properties sublineCodes;

    static {
        try (InputStream input = ReferenceLookup.class.getClassLoader().getResourceAsStream("states.properties")) {

            stateCodes = new Properties();

            if (input == null) {
                System.out.println("Unable to load state properties.");
            }
            stateCodes.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (InputStream input = ReferenceLookup.class.getClassLoader().getResourceAsStream("lobs.properties")) {

            lobCodes = new Properties();

            if (input == null) {
                System.out.println("Unable to load lob properties.");
            }
            lobCodes.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (InputStream input = ReferenceLookup.class.getClassLoader().getResourceAsStream("sublines.properties")) {

            sublineCodes = new Properties();

            if (input == null) {
                System.out.println("Unable to load lob properties.");
            }
            sublineCodes.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String lookupLOBCode(String lob) {
        return lobCodes.getProperty(lob);
    }

    public String lookupStateCode(String stateAbbreviation) {
        return stateCodes.getProperty(stateAbbreviation);
    }

    public String lookupSubline(String subline) {
        return sublineCodes.getProperty(subline);
    }
}
