package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import javax.management.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

  public void add(String authToken, Session session) {
    var connection = new Connection(authToken, session);
    connections.put(authToken, connection);
  }

  public void respondToSender(String authToken,ServerMessage message) throws IOException {
    var currSession = connections.get(authToken);
    var mess = new Gson().toJson(message);
    currSession.send(mess);
  }

  public void remove(String authToken) {
    connections.remove(authToken);
  }

  public void broadcast(String excludeAuthToken, ServerMessage serverMessage) throws IOException {
    var removeList = new ArrayList<Connection>();
    var message = new Gson().toJson(serverMessage);
    for (var c : connections.values()) {
      if (c.session.isOpen()) {
        if (!c.authToken.equals(excludeAuthToken)) {
          c.send(message);
        }
      } else {
        removeList.add(c);
      }
    }

    // Clean up any connections that were left open.
    for (var c : removeList) {
      connections.remove(c.authToken);
    }
  }
}