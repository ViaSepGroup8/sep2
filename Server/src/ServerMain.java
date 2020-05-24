import model.ServerModel;
import model.ServerModelManager;
import view.SimpleConsoleView;

public class ServerMain {
    public static void main(String[] args)
    {
        //setup view and server
        ServerModel server = new ServerModelManager();

        Thread view = new Thread(new SimpleConsoleView(server));
        view.setDaemon(true);

        //start them
        server.start();
        view.start();
    }
}
