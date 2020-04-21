public class RUN_SERVER_AND_CLIENT
{
  public static void main(String[] args)
  {
    new Thread(() -> ServerMain.main(args)).start();
    new Thread(() -> ClientMain.main(args)).start();
  }
}
