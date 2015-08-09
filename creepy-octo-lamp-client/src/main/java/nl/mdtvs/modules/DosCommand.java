package nl.mdtvs.modules;

import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;

public class DosCommand implements Command {

    @Override
    public String getCommandName() {
        return "bla-Action";
    }

    @Override
    public Message execute(String message) {
        return new Message("dos", "successful");
    }
}
