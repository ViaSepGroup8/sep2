package logger                          ;

public class Logger                     {
  private static Logger instance        ;
  private boolean enabled = true;

  private Logger()                      {}

  public static Logger getInstance()    {
    if(instance == null)                {
      synchronized (Logger.class)       {
        if(instance == null)            {
          instance = new Logger()       ;}}}
    return instance                     ;}

  public void setEnabled(boolean value) {
    this.enabled = value                ;}

  public void addLog(String log)        {
    LogLine logLine = new LogLine(log)  ;
    if(enabled)                         {
      System.out.println(logLine)       ;
                                        }}}
