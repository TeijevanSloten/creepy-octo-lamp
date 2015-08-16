package nl.mdtvs.util;

import nl.mdtvs.models.Message;
import nl.mdtvs.models.WsDevice;
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

    public static String messageToJson(Message message) throws JAXBException, IOException {
        return OBJECT_MAPPER.writeValueAsString(message);
    }

    public static Map<String, String> jsonToMap(String jsonProperties) throws JAXBException, IOException {
        return new HashMap<>(OBJECT_MAPPER.readValue(jsonProperties, new TypeReference<HashMap<String,String>>() {}));
    }

    public static Message jsonToMessage(String jsonProperties) throws IOException {
        return OBJECT_MAPPER.readValue(jsonProperties, Message.class);
    }

    public static String devicesToJson(Map<String, WsDevice> map) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(map);
    }

    public static String deviceToJson(WsDevice wsDevice) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(wsDevice);
    }
}
