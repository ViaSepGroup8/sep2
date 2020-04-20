package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface WarehouseClientModel extends Remote {
<<<<<<< HEAD
  //Login
  public void login(String username, String password) { System.out.println("Logging in!"); }

  //Administrator

  //Costumer

  //Driver

  //Picker

  //local model
  void connect();
  void setUser(User user);
  User getUser();

  //server side
  void registerClient();
  void updateUser();
  void broadCast(Message message);
  void requestUserList();

  //client side
  void receiveMessage(Message message);
  void receiveUserList(ArrayList<User> users);
=======
    void answer(int profession);
>>>>>>> 8b1cb92f5d9a9108d113139b41b49733ea99d802
}
