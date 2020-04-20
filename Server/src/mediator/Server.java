package mediator;

import model.ServerModel;
import model.ServerModelManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements WarehouseServer
{
  ServerModel model;
//  ArrayList<WarehouseClient> clients;
//  ArrayList<User> users;
//
  public Server(ServerModelManager model) throws RemoteException
  {
    this.model = model;
//    clients = new ArrayList<WarehouseClient>();
//    users = new ArrayList<User>();
    UnicastRemoteObject.exportObject(this, 0);
  }
//
//  @Override public void registerClient(WarehouseClient client, User user) throws RemoteException
//  {
//    clients.add(client);
//    users.add(user);
//    model.log("new client connected " + client + "\n" + user);
//    updateAllClientsUserList();
//  }
//
//  @Override public void broadCast(Message message, WarehouseClient sender) throws RemoteException
//  {
//    model.log("broadcasting message" + message);
//    for (WarehouseClient client : clients)
//    {
////      if(client.equals(sender))
////        continue;
//
//      client.receiveMessage(message);
//    }
//  }
//
//  @Override public void requestUserList(WarehouseClient client) throws RemoteException
//  {
//    model.log("userlist request");
//    client.receiveUserList(users);
//  }
//
//  @Override public String ping() throws RemoteException
//  {
//    return "pong";
//  }
//
//  @Override public void updateUser(User user, WarehouseClient client) throws RemoteException
//  {
//    users.set(clients.indexOf(client), user);
//    updateAllClientsUserList();
//  }
//
//  public void updateAllClientsUserList() throws RemoteException
//  {
//    for (WarehouseClient warehouseClient : clients)
//    {
//      warehouseClient.receiveUserList(users);
//    }
//  }
}
