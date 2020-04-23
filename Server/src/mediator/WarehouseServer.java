package mediator;
import model.Item;
import model.UserType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface WarehouseServer extends Remote
{
  String ping() throws RemoteException;
  UserType login(String username, String password) throws RemoteException;
  ArrayList<Item> getAllWarehouseItems() throws RemoteException;
//  void registerClient(WarehouseClient client, User user) throws  RemoteException;
//  void broadCast(Message message, WarehouseClient sender) throws RemoteException;
//  void requestUserList(WarehouseClient client) throws  RemoteException;
//  void updateUser(User user, WarehouseClient client) throws RemoteException;
}
