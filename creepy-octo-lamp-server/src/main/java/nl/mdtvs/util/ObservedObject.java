package nl.mdtvs.util;

import java.util.Observable;

public class ObservedObject extends Observable {

    private String watchedValue="";
    
    public ObservedObject(Object value) {
        this.watchedValue = value.toString();
    }
    
    public boolean hasWatchValue(){
        return !this.watchedValue.isEmpty();
    }
    
    public ObservedObject setValue(Object value) {
        if(value == null) {
            setChanged();
            return this;
        }
        check(value.toString());
        return this;
    }

    private void check(String value) {
        if (!watchedValue.equals(value)) {
            watchedValue = value;
            setChanged();
        }
    }

    @Override
    public void clearChanged(){
        super.clearChanged();
    }

}
