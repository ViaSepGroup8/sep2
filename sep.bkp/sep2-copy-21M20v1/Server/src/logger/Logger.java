package logger                          ;

public class Logger                     {
  private static Logger instance        ;

  private Logger()                      {}

  public static Logger getInstance()    {
    if(instance == null)                {
      synchronized (Logger.class)       {
        if(instance == null)            {
          instance = new Logger()       ;}}}
    return instance                     ;}

  public void addLog(String log)        {
    LogLine logLine = new LogLine(log)  ;
    System.out.println(logLine)         ;}}
