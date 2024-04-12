package webSocket;

import com.google.gson.Gson;
import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

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
          switch (serverMessage.getServerMessageType()){
            case NOTIFICATION: {
              ServerMessage serverMessage1 = new Gson().fromJson(message,ServerMessage.class);
              System.out.println(serverMessage1.toString());
              break;
            }
            case ERROR: {
              ServerMessage serverMessage1 = new Gson().fromJson(message,ServerMessage.class);
              System.out.println(serverMessage1.toString());
              break;
            }
            case LOAD_GAME:{
              ServerMessage serverMessage1 = new Gson().fromJson(message,ServerMessage.class);
              System.out.println(serverMessage1.toString());
              break;
            }
          }
        };
      });
    ;}catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  public void joinGame(String authToken) throws Exception{
    try {
      var client = new UserGameCommand(authToken);
      System.out.println(client.getCommandType());
      this.session.getBasicRemote().sendText(new Gson().toJson(client));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  public void observeGame(String authToken) throws Exception{
    try {
      var client = new UserGameCommand(authToken);
      System.out.println(client.getCommandType());
      this.session.getBasicRemote().sendText(new Gson().toJson(client));
    }catch(Exception e){
      throw new Exception("Could not connect");
    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
}
