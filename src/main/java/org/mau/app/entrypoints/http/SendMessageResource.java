package org.mau.app.entrypoints.http;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@Path("/send")
public class SendMessageResource {
  
  private static final Logger LOG = Logger.getLogger(SendMessageResource.class);

  @Inject
  @Channel("default")
  Emitter<Object> emitter;

  @Inject
  @Channel("default-string")
  Emitter<String> em;

  @POST
  public Object execute(Object message) {
    LOG.infov("Send on channel [default] message: {0}", message);
    emitter.send(message);
    return message;
  }
  
  @POST
  @Path("/{message}")
  public String execute(@PathParam("message") final String message) {
    LOG.infov("Send on channel [default] message: {0}", message);
    em.send(message);
    return message;
  }

}
