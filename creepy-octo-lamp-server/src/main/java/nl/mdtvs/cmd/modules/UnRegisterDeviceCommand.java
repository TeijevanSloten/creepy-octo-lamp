package nl.mdtvs.cmd.modules;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import nl.mdtvs.cmd.DeviceManager;
import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;
import nl.mdtvs.websocket.GuiHandler;

public class UnRegisterDeviceCommand implements Command {

    private final GuiHandler guiHandler;

    public UnRegisterDeviceCommand() {
        guiHandler = GuiHandler.getInstance();
    }

    @Override
    public String getCommandName() {
        return "UNREGISTER_DEVICE";
    }

    @Override
    public Message execute(String message, Session session) {
        try {
            DeviceManager.getInstance().unRegisterDevice(session);
            guiHandler.getServerGui().entrySet().stream().forEach(s -> {
                s.getValue().getAsyncRemote().sendText("updateClients");
            });
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(UnRegisterDeviceCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Message();
    }

}
