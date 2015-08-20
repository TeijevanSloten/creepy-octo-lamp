package nl.mdtvs.util;

import java.util.Objects;
import java.util.Observable;
import java.util.function.Supplier;
import javax.validation.constraints.NotNull;

public class ObservedObject extends Observable {

    private String watchedValue;
    private Supplier s;

    public ObservedObject(@NotNull Supplier value) {
        this.s = value;
        this.watchedValue = toString(value.get());
    }

    public Object getWatchedValue() {
        return watchedValue;
    }
    
    public boolean hasChanged() {
        String value = toString(s.get());
        if (!Objects.equals(watchedValue, value)) {
            watchedValue = value;
            return true;
        }
        return false;
    }
    
    private String toString(Object value){
        return new StringBuilder().append(value).toString();
    }
    
}
