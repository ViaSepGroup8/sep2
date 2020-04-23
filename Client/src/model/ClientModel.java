package model;

import utility.ObserverSubject;
import java.util.ArrayList;

public interface ClientModel extends ObserverSubject
{
  void logOut();
  void userError(String message);
  void debugLog(String message);
  void fatalError(String message);

  //Login
  UserType login(String username, String password);

  //Administrator

  //Costumer
  ArrayList<Item> getAllWarehouseItems();

  //Driver

  //Picker
  void completeJob(String jobId) throws RemoteException;
  Job getNewJob() throws RemoteException;

  //local model
  //  void setUser(User user);
  //  User getUser();
  //
  //  //server side
  //  void registerClient();
  //  void updateUser();
  //  void broadCast(Message message);
  //  void requestUserList();
  //
  //  //client side
  //  void receiveMessage(Message message);
  //  void receiveUserList(ArrayList<User> users);
  //  void answer(int profession);
}
