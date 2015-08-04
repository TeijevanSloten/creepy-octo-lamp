package nl.mdtvs.modules;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;

public class PropertiesRequest {

    public static String getJsonSystemProperties() throws IOException {
        return new ObjectMapper().writeValueAsString(retrieveProperties());
    }

    private static Map<String, String> retrieveProperties() throws UnknownHostException {
        Map<String, String> properties = new HashMap<>();
        System.getProperties().entrySet().stream().forEach(p -> properties.put((String) p.getKey(), (String) p.getValue()));
        properties.put("IP", Inet4Address.getLocalHost().getHostAddress());
        return properties;
    }
}
