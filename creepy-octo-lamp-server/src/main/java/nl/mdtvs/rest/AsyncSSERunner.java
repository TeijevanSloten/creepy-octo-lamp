package nl.mdtvs.rest;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsyncSSERunner {

    AsyncContext async;

    public AsyncSSERunner(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/event-stream, charset=UTF-8");
        async = request.startAsync();
    }

    private boolean interupted = false;

    public void start(ThrowingRunnable runnable, int delayInMilliSeconds) {
        while (!interupted) {
            try {
                runnable.run();
                Thread.sleep(delayInMilliSeconds);
            } catch (Exception e) {
                e.printStackTrace();
                async.complete();
                interupted = true;
            }
        }
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }
}
