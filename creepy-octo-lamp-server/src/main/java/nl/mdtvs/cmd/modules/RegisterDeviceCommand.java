package nl.mdtvs.cmd.modules;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import nl.mdtvs.cmd.DeviceManager;
import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;
import nl.mdtvs.websocket.GuiHandler;

public class RegisterDeviceCommand implements Command {
    
    private final GuiHandler guiHandler;

    
    public RegisterDeviceCommand() {
        guiHandler = GuiHandler.getInstance();
    }
            
    @Override
    public String getCommandName() {
        return "REGISTER_DEVICE";
    }

    @Override
    public Message execute(String message, Session session) {
        try {

            DeviceManager.getInstance().registerDevice(message, session);
            guiHandler.getServerGui().entrySet().stream().forEach(s -> {
                s.getValue().getAsyncRemote().sendText("updateClients");
            });
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(RegisterDeviceCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Message();
    }

}
