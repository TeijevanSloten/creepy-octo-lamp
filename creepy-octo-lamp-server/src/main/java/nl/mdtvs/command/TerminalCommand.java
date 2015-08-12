package nl.mdtvs.command;

import nl.mdtvs.command.handler.Command;
import nl.mdtvs.models.WsDevice;

public class TerminalCommand implements Command {

    @Override
    public String getCommandName() {
        return "TERMINAL_RESPONSE";
    }

    @Override
    public void execute(String message, WsDevice device) {
        device.setTerminalResponse(message);
    }
}
