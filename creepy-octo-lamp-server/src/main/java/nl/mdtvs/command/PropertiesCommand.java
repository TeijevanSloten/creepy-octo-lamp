package nl.mdtvs.command;

import nl.mdtvs.command.handler.Command;
import nl.mdtvs.models.WsDevice;
import nl.mdtvs.util.ConvertObject;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class PropertiesCommand implements Command {

    @Override
    public String getCommandName() {
        return "REGISTER_DEVICE";
    }

    @Override
    public void execute(String message, WsDevice device) {
        try {
            device.setProperties(ConvertObject.jsonToMap(message));
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
