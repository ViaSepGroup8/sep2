import static javafx.application.Application.launch;

public class RUN_SERVER_AND_CLIENT
{
  public static void main(String[] args)
  {
    new Thread(() -> ServerMain.main(args)).start();
    new Thread(() -> launch(ClientApp.class, args)).start();
  }
}