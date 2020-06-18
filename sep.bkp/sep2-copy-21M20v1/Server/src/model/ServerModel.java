package model;

import utility.ObserverSubject;

public interface ServerModel extends ObserverSubject
{
  void start();
  void stop();
}
