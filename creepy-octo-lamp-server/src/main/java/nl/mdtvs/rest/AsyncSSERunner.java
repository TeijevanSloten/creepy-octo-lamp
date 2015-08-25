package nl.mdtvs.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsyncSSERunner {

    private AsyncContext async;
    private ServletOutputStream os;
    private Map<String,Listener> listeners;
       
    public AsyncSSERunner(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream, charset=UTF-8");
        os = response.getOutputStream();
        listeners = new HashMap<>();
        async = request.startAsync();
    }

    private boolean clientRequestIsOpen(){
        try {
            os.print("\n");
            os.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private void print(String input) {
        try {
            os.print(input);
            os.flush();            
        } catch (Exception e) {
            close();
        }
    }
    
    private void close() {
        async.complete();
        removeListener();
    }
    
    public void start(int delayInMilliSeconds) {
        try {
            while (clientRequestIsOpen()) {
                List<String> events = onValueChange();
                events.stream().forEach((e) -> print(e));
                Thread.sleep(delayInMilliSeconds);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    
    public void addListener(String k, Listener listnerObj) {
        listeners.put(k, listnerObj);
    }
    
    public void removeListener(String... keys) {
        if(keys == null) {
            listeners.clear();
        } else {
            Arrays.asList(keys).stream().forEach(k -> listeners.remove(k));
        }
    }

    private List<String> onValueChange(){
        ArrayList<String> events = listeners.entrySet().stream().collect(
                ArrayList<String>::new, (l,e)->{
                    String event = e.getValue().onValueChange(e.getKey());
                    if(event != null) { l.add(event); }
                }, ArrayList<String>::addAll
        );
        return events;
    }
}
