package nl.mdtvs.command;

public interface Command {

    String getCommandName();

    Command input(Object[] args);

    Message execute(String message);
}
