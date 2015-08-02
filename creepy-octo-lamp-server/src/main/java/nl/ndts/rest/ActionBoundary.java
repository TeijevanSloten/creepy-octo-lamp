package nl.ndts.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/action")
public class ActionBoundary {

    @GET
    @Produces("application/json")
    public String getClichedMessage() {
        return "Iedereen haat jou rest boundary";
    }
}
