package nl.mdtvs.util;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import nl.mdtvs.rest.Listener;

@ApplicationScoped
public class ObservedObjectManager implements Listener{

    private final Map<String, ObservedObject> obsList = new HashMap<>();
    private final Map<String, Function<Object,String>> conList = new HashMap<>();

    public void addInitialObserveObject(String key, Supplier value) {
        if (!obsList.containsKey(key) && value.get() != null) {
            obsList.put(key, new ObservedObject(value));
        }
    }

    public String onValueChange(String key, Function<Object,String> f) {
        if(!conList.containsKey(key)) { conList.put(key, f); }
        
        ObservedObject obs = obsList.get(key);
        if (obs.hasChanged()) {
            return f.apply(obs.getWatchedValue());
        }
        return null;
    }

    @Override
    public String onValueChange(String key){
        if(conList.containsKey(key)){
            return onValueChange(key, conList.get(key));
        } else {
            throw new NullPointerException("key does not exist");
        }
    }
}
