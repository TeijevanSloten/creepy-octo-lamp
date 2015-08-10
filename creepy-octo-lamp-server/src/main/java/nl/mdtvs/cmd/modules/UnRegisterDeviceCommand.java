package nl.mdtvs.cmd.modules;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import javax.xml.bind.JAXBException;
import nl.mdtvs.cmd.DeviceManager;
import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;

public class UnRegisterDeviceCommand implements Command{

    private Object[] args;
    @Override
    public String getCommandName() {
        return "UNREGISTER_DEVICE";
    }

    @Override
    public Command input(Object[] args) {
        if(args[0] instanceof Session && args[1] instanceof Session && args.length == 2) this.args = args;
        return this;
    }

    @Override
    public Message execute(String message) {
        try {
            DeviceManager.getInstance().unRegisterDevice((Session) args[0]);
            Session s = (Session) args[1];
            s.getAsyncRemote().sendText("updateClients");
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(UnRegisterDeviceCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        args = null;
        return new Message();
    }
    
}
