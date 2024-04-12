package webSocket;

import com.google.gson.Gson;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  ServerMessageHandler  serverMessageHandler;
  public WebSocketFacade(String url,ServerMessageHandler serverMessageHandler) throws Exception {
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
          ServerMessage serverMessage=new Gson().fromJson(message, ServerMessage.class);
          serverMessageHandler.notify(serverMessage);
        };
      });
    ;}catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  public void send(String msg) throws Exception {
    this.session.getBasicRemote().sendText(msg);
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}
