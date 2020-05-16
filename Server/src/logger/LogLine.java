package logger                        ;

public class LogLine                  {
  String text;
  private DateTime dateTime           ;

  public LogLine(String text)         {
    this.text = text                  ;
    dateTime = new DateTime()         ;}

  @Override public String toString()  {
    return "[" + dateTime.getTime()   +
        "]: " + text.toLowerCase();   }}