package nl.mdtvs.cmd.modules;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import nl.mdtvs.cmd.DeviceManager;
import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;

public class RegisterDeviceCommand implements Command {

    Object[] args;

    @Override
    public String getCommandName() {
        return "REGISTER_DEVICE";
    }

    @Override
    public Command input(Object[] args) {
        if (args[0] instanceof Session && args[1] instanceof Session && args.length == 2) {
            this.args = args;
        }
        return this;
    }

    @Override
    public Message execute(String message) {
        if (args == null || args.length < 2) {
            return new Message("Error", "invalid arguments given for " + getCommandName());
        }
        try {
            DeviceManager.getInstance().registerDevice(message, (Session) args[0]);
            Session s = (Session) args[1];
            s.getAsyncRemote().sendText("updateClients");
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(RegisterDeviceCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        args = null;
        return new Message();
    }

}
