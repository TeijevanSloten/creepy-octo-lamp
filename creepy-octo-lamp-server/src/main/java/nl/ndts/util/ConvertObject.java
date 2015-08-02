package nl.ndts.util;

import nl.ndts.models.WsAction;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConvertObject {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ConvertObject() {
    }

    public static String wsActionToJson(WsAction wsAction) throws JAXBException, IOException {
        return OBJECT_MAPPER.writeValueAsString(wsAction);
    }

    public static Map<String, String> jsonStringToMap(String jsonProperties) throws JAXBException, IOException {
        return new HashMap<>(OBJECT_MAPPER.readValue(jsonProperties, new TypeReference<HashMap<String,String>>() {}));
    }
}
