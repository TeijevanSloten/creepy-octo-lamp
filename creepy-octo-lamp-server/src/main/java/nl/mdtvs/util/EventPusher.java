package nl.mdtvs.util;

import java.io.PrintWriter;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class EventPusher {

    private final Map<Integer, ObservedObject> obsList = new HashMap<>();

    public void addInitialObserveObject(Integer key, Object value) {
        if(!obsList.containsKey(key) && value != null) {
            obsList.put(key, new ObservedObject(value));
        }
    }

    public void removeObservedObj(Integer key){
        obsList.remove(key);
    }
    
    public void onValueChange(Integer key, Object value, Runnable r) {
        if(value != null){
            ObservedObject o = obsList.get(key).setValue(value);
            
            if(o.hasChanged()) {
                r.run();
                o.clearChanged();
            }            
        } else {
            removeObservedObj(key);
            r.run();
        }
    }
    
    public String generateEvent(String event) {
        return "event:" + event + "\n" +
                "data:\n\n";
    }
    
    public String generateEvent(String event, String data) {
        return "event:" + event + "\n" +
                "data:" + data + "\n\n";
    }    

    public boolean hasChanged(Integer key, Object value) {
        ObservedObject o = obsList.get(key).setValue(value);

        if(o.hasChanged()) {
            o.clearChanged();
            return true;
        }
        return false;
    }
}
