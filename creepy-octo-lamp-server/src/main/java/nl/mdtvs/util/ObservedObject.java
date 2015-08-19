package nl.mdtvs.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Observable;
import javax.validation.constraints.NotNull;

public class ObservedObject extends Observable {

    private Object watchedValue;

    public ObservedObject(@NotNull Object value) {
        this.watchedValue = deepClone(value);
    }

    public boolean hasChangedAndCleared(@NotNull Object o) {
        Object value = deepClone(o);
        if (!Objects.equals(watchedValue, value)) {
            watchedValue = value;
            return true;
        }
        return false;
    }

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
