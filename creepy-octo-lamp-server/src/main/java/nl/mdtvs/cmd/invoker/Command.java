package nl.mdtvs.cmd.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command {

    private Object receiver;
    private Method action;

    public Command(Object obj, String methodName, Class[] argTypes) {
        receiver = obj;
        try {
            action = obj.getClass().getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            System.out.println(e);
        }
    }

    public Object execute(Object[] a) {
        try {
            return action.invoke(receiver, a);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }
        return null;
    }
}
