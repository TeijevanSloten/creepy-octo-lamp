package nl.mdtvs.util;

import java.util.Observable;

public class ObservedObject extends Observable {

    private String watchedValue="";
    private static ObservedObject obs = new ObservedObject();
    
    private ObservedObject(){}
    
    public static ObservedObject getInstance(){
        return obs;
    }
    
    public void setWatcher(Object value) {
        this.watchedValue = value.toString();
    }
    
    public void setWatcher(String value) {
        this.watchedValue = value;
    }
    
    public boolean hasWatchValue(){
        return !this.watchedValue.isEmpty();
    }
    
    public void setValue(Object value) {
        check(value.toString());
    }

    public void setValue(String value) {
        check(value);
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
