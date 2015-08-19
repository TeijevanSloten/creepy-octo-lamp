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
    private final Map<String, Supplier> supplierList = new HashMap<>();

    public void addInitialObserveObject(String key, Supplier value) {
        if(!obsList.containsKey(key) && value.get() != null) {
            supplierList.put(key, value);
            obsList.put(key, new ObservedObject(value.get()));
        }
    }     
    
    public void onValueChange(String key, PrintWriter o, Function<PrintWriter,Function<Object,Void>> f) {//, PrintWriter out, Function<,Consumer<Object>> f
        Object sup = supplierList.get(key).get();
        ObservedObject obs = obsList.get(key);
        if(obs.hasChangedAndCleared(sup)){
            f.apply(o).apply(sup);
        }
    }    
}
