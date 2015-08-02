package nl.ndts;

import nl.ndts.models.WsAction;
import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class ConvertObject {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ConvertObject() {
    }

    public static String wsActionToJson(WsAction wsAction) throws JAXBException, IOException {
        return OBJECT_MAPPER.writeValueAsString(wsAction);
    }
}
