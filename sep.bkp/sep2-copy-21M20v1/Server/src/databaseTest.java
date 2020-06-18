import database.Database;
import database.FakeDatabase;

public class databaseTest
{
  public static void main(String[] args)
  {
    Database database = new FakeDatabase();
  }
}
