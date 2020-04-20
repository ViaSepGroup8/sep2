package server.model;

import utility.ObserverSubject;

public interface ServerModel extends ObserverSubject
{
  void start();
  void stop();
  void log(String line);
}
