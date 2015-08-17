package nl.mdtvs.util;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class EventPusher {

    private final Map<Integer, ObservedObject> obsList = new HashMap<>();

    public void addInitialObserveObject(Integer key, Object value) {
        if(!obsList.containsKey(key)) {
            obsList.put(key, new ObservedObject(value));
        }
    }

    public String generateEvent(String event) {
        return "event:" + event + "\n" +
                "data:\n\n";
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