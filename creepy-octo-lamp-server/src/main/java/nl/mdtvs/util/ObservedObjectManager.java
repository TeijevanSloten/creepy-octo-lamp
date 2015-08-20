package nl.mdtvs.util;

import java.io.PrintWriter;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@ApplicationScoped
public class ObservedObjectManager {

    private final Map<String, ObservedObject> obsList = new HashMap<>();

    public void addInitialObserveObject(String key, Supplier value) {
        if(!obsList.containsKey(key) && value.get() != null) {
            obsList.put(key, new ObservedObject(value));
        }
    }     
    
    public void onValueChange(String key, PrintWriter o, Function<PrintWriter,Function<Object,Void>> f) {//, PrintWriter out, Function<,Consumer<Object>> f
        ObservedObject obs = obsList.get(key);
        if(obs.hasChanged()){
            f.apply(o).apply(obs.getWatchedValue());
        }
    }    
}
