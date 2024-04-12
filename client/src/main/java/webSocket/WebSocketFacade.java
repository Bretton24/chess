package webSocket;

import com.google.gson.Gson;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;
import webSocketMessages.Action;
import webSocketMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  ServerMessageHandler  serverMessageHandler;
  public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) throws ResponseException {
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.serverMessageHandler = serverMessageHandler;

      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      this.session = container.connectToServer(this, socketURI);

      //set message handler
      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          ServerMessage serverMessage = new Gson().fromJson(message,ServerMessage.class);
          switch(serverMessage.getServerMessageType()) {
            case NOTIFICATION:{
              Notification notification = new Gson().fromJson(message,Notification.class);
              System.out.println(notification.toString());
              break;
            }
            case ERROR: {

            }
            case LOAD_GAME: {
              String errorMessage
            }
          }
          Notification notification = new Gson().fromJson(message, Notification.class);
          notificationHandler.notify(notification);
        }
      });
    } catch (DeploymentException | IOException | URISyntaxException | URISyntaxException ex) {
      throw new ResponseException(500, ex.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}
