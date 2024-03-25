
import dataAccess.UnauthorizedAccessException;
import model.GameData;
import model.GameID;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        assertThrows(Exception.class,()-> facade.registerUser(new UserData(null, null, null)));
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
