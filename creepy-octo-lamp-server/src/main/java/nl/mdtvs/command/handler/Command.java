package nl.mdtvs.command.handler;

import nl.mdtvs.models.WsDevice;

public interface Command {

    String getCommandName();

    void execute(String message, WsDevice device);
}
