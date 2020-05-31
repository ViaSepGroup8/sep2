package model;

import utility.ObserverSubject;

public interface ServerModel extends ObserverSubject
{
  void addUser(String username, String fullName, UserType userType, String password);
  void removeUser(String username);

  void start();
  void stop();
}
