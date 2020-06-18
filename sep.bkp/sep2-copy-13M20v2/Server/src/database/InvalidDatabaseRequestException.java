package database;

public class InvalidDatabaseRequestException extends Exception{
  public InvalidDatabaseRequestException()
  { }

  public InvalidDatabaseRequestException(String message)
  {
    super(message);
  }
}
