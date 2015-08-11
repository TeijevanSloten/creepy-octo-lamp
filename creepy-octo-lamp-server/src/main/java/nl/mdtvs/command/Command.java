package nl.mdtvs.command;

import javax.websocket.Session;

public interface Command {

    String getCommandName();

    Message execute(String message, Session session);
}
