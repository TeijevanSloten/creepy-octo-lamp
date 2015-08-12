package nl.mdtvs.util;

import nl.mdtvs.models.Message;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConvertObject {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ConvertObject() {
    }

    public static String wsActionToJson(Message message) throws JAXBException, IOException {
        return OBJECT_MAPPER.writeValueAsString(message);
    }

    public static Map<String, String> jsonStringToMap(String jsonProperties) throws JAXBException, IOException {
        return new HashMap<>(OBJECT_MAPPER.readValue(jsonProperties, new TypeReference<HashMap<String,String>>() {}));
    }

    public static Message jsonStringToWsAction(String jsonProperties) throws IOException {
        return OBJECT_MAPPER.readValue(jsonProperties, Message.class);
    }

    public static String devicesToJsonString(Map map) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(map);
    }
}
