package nl.mdtvs.util;

import java.io.PrintWriter;
import java.util.HashMap;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventPusher {
    private final HashMap<Integer,ObservedObject> obsList;
    
    public EventPusher() {
        this.obsList = new HashMap();
    }
    
    public EventPusher observeObj(Integer key,Object value) {
        if(!obsList.containsKey(key)) {
            obsList.put(key, new ObservedObject(value));
        }
        return this;
    }

    public void removeObservedObj(Integer key){
        obsList.remove(key);
    }
    
    public void execute(PrintWriter out, String event, String data) {
        out.print("event:" + event + "\n");
        out.print("data:" + data + "\n\n");
    }

    public void execute(PrintWriter out, String event) {
        out.print("event:" + event + "\n");
        out.print("data:\n\n");
    }
    
    public void onChange(Integer key, Object value, Runnable r) {
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
}
