package nl.mdtvs.command;

public interface Command {

    String getCommandName();

    Message execute(String message);
}
