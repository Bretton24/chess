package clientTests;

import dataAccess.UnauthorizedAccessException;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    public void clear() throws Exception {
        facade.clearDatabase();
    }

    @Test
    void registerPass() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void registerFail() throws Exception {
        assertThrows(Exception.class,()-> facade.registerUser(new UserData("player1", null, null)));
    }

    @Test
    void loginPass() throws Exception {
        facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var authData = facade.loginUser(new UserData("player1", "password", null));
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void loginFails() throws Exception {
        assertThrows(Exception.class,()-> facade.registerUser(new UserData(null, null, null)));
    }

    @Test
    void createGamePass() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var id = facade.gameCreate(authData,"yo");
        assertTrue(id.gameID() == 1);
    }

    @Test
    void createGameFail() throws Exception {
        var authData = new AuthData("adhlfdkjadsljk","aslkdjfas");
        assertThrows(Exception.class,()->facade.gameCreate(authData,"yo"));
    }

    @Test
    void joinGamePass() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var id = facade.gameCreate(authData,"yo");
        try{
            facade.joinGame(authData,new PlayerInfo("WHITE",id.gameID()));
        }
        catch(Exception ex){
            fail();
        }
    }

    @Test
    void joinGameFail() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var id = facade.gameCreate(authData,"yo");
        assertThrows(Exception.class,() -> facade.joinGame(authData,new PlayerInfo("WHITE",2)));
    }

    @Test
    void observeGamePass() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var id = facade.gameCreate(authData,"yo");
        try{
            facade.observeGame(authData,new PlayerInfo(null,id.gameID()));
        }
        catch(Exception ex){
            fail();
        }
    }

    @Test
    void observeGameFail() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        var id = facade.gameCreate(authData,"yo");
        assertThrows(Exception.class,() -> facade.joinGame(authData,new PlayerInfo(null,2)));
    }

    @Test
    void listGamePass() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));
        try{
            facade.listGame(authData);
        }
        catch(Exception ex){
            fail();
        }
    }

    @Test
    void listGameFail() throws Exception {
        var authData = new AuthData("aldsjkflasd","asdlfj");
        assertThrows(Exception.class,() -> facade.listGame(authData));
    }


    @Test
    void logoutPass() throws Exception {
        var authData = facade.registerUser(new UserData("player1", "password", "p1@email.com"));;
        try{
            facade.logoutUser(authData);
        }
        catch(Exception ex){
            fail();
        }
    }

    @Test
    void logoutFail() throws Exception {
        var authData = new AuthData("aldsjkflasd","asdlfj");
        assertThrows(Exception.class,() -> facade.logoutUser(authData));
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

}
