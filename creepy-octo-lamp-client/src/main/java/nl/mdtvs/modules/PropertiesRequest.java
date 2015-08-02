package nl.mdtvs.modules;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertiesRequest {

    public static String getJsonSystemProperties() throws IOException {
        return new ObjectMapper().writeValueAsString(retrieveProperties());
    }

    private static Map<String, String> retrieveProperties() {
        Map<String, String> properties = new HashMap<>();
        System.getProperties().entrySet().stream().forEach(p -> properties.put((String) p.getKey(), (String) p.getValue()));
        return properties;
    }
}
