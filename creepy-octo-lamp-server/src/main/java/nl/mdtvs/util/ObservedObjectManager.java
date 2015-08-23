package nl.mdtvs.util;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.ServletOutputStream;
import nl.mdtvs.rest.ServerSentEventBoundary.ThrowableFunction;

@ApplicationScoped
public class ObservedObjectManager {

    private final Map<String, ObservedObject> obsList = new HashMap<>();

    public void addInitialObserveObject(String key, Supplier value) {
        if(!obsList.containsKey(key) && value.get() != null) {
            obsList.put(key, new ObservedObject(value));
        }
    }     
    
    public void onValueChange(String key, ServletOutputStream o, ThrowableFunction<ServletOutputStream,ThrowableFunction<Object,Void>> f) throws Exception {
        ObservedObject obs = obsList.get(key);
        if(obs.hasChanged()){
            f.apply(o).apply(obs.getWatchedValue());
        }
    }    
}
