package com.mycompany.mywebapp.server;

import com.mycompany.mywebapp.client.GreetingService;
import com.mycompany.mywebapp.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.*;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  HashMap<String, Thread> threads = new HashMap<>();
  int id = 0;

  public String stopAllGreetings(){
    Logger logger = Logger.getLogger(getServletName());
    for(Map.Entry<String,Thread> entry: threads.entrySet()){
      logger.log(Level.INFO, "Get Map<UUID,Thread>" + entry.getKey());
      Thread t = entry.getValue();
      t.interrupt();
    }
    return "Done";
  }

  public String greetServer(String input) throws IllegalArgumentException{
    // Verify that the input is valid. 
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    Random rand = new Random();
    UUID uuid = UUID.randomUUID();
    Logger logger = Logger.getLogger(getServletName());

    try{
      Thread currThread = Thread.currentThread();
      threads.put(uuid.toString(),currThread);
      logger.log(Level.INFO, "Put Map<UUID,Thread>" + uuid);
      // Thread.sleep(rand.nextInt(rand.nextInt(600))*1000);
      Thread.sleep(600_000);

    }catch(InterruptedException e){
      return "Thread sleep operation was interrupted!";
    }
    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);

    return "Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent 
        + "<br><br> The thread identifier: " + uuid;
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
