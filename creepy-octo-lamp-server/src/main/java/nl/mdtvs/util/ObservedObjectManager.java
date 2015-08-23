package nl.mdtvs.util;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ApplicationScoped
public class ObservedObjectManager {

    private final Map<String, ObservedObject> obsList = new HashMap<>();

    public void addInitialObserveObject(String key, Supplier value) {
        if (!obsList.containsKey(key) && value.get() != null) {
            obsList.put(key, new ObservedObject(value));
        }
    }

    public void onValueChange(String key, ServletOutputStream o, ThrowableBiConsumer<ServletOutputStream, Object> f) throws Throwable {
        ObservedObject obs = obsList.get(key);
        if (obs.hasChanged()) {
            f.consume(o, obs.getWatchedValue());
        }
    }
}
