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
import nl.mdtvs.websocket.SessionHandler;

public class UnRegisterDeviceCommand implements Command{

    private Object[] args;
    
    @Inject
    SessionHandler sessionHandler;
    
    @Override
    public String getCommandName() {
        return "UNREGISTER_DEVICE";
    }

    @Override
    public Command input(Object[] args) {
        if(args[0] instanceof Session && args.length == 1) this.args = args;
        return this;
    }

    @Override
    public Message execute(String message) {
        try {
            DeviceManager.getInstance().unRegisterDevice((Session) args[0]);
            sessionHandler.getServerGui().entrySet().stream().forEach(session -> {
                session.getValue().getAsyncRemote().sendText("updateClients");
            });
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(UnRegisterDeviceCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        args = null;
        return new Message();
    }
    
}
