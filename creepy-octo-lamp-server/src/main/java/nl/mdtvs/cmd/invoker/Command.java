package nl.mdtvs.cmd.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command {

    private Object receiver;
    private Method action;

    public Command(Object obj, String methodName, Class[] argTypes) {
        try {
            receiver = obj;
            action = obj.getClass().getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void execute(Object[] arguments) {
        try {
            action.invoke(receiver, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
