package nl.mdtvs.modules;

import nl.mdtvs.command.Command;
import nl.mdtvs.command.Message;

public class EncryptionCommand implements Command {

    @Override
    public String getCommandName() {
        return "bla-Action2";
    }

    @Override
    public Message execute(String message) {
        return new Message("encryption", "successful");
    }
}
