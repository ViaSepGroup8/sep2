package mediator;

import database.Database;
import database.FakeDatabase;
import model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements WarehouseServer
{
  ServerModel model;
  Database database;
//  ArrayList<WarehouseClient> clients;
//  ArrayList<User> users;

  public Server(ServerModel model) throws RemoteException
  {
    this.database = new FakeDatabase();
    this.model = model;
//    clients = new ArrayList<WarehouseClient>();
//    users = new ArrayList<User>();
    UnicastRemoteObject.exportObject(this, 0);
  }

  @Override public String ping() throws RemoteException
  {
    return "pong";
  }

  @Override public UserType login(String username, String password) throws RemoteException
  {
    return database.getUserType(username, password);
  }

  @Override public ArrayList<Item> getAllWarehouseItems()
  {
    return database.getAllWarehouseItems();
  }

  @Override public Job getNewJob() throws RemoteException
  {
    return database.getNewJob();
  }

  @Override public void completeJob(String jobId) throws RemoteException
  {
    //todo
  }
  @Override public void createNewOrder(Order order) throws RemoteException
  {
    database.addOrder(order);
  }

  @Override public ArrayList<Order> getOrderList() throws RemoteException
  {
    return database.getOrders();
  }

  //  public void answer(int profession) {
//    switch (profession) {
//      case 0:
//        System.out.println("Impossible to login, try again!");
//        //No profession (it will give and error message)
//        break;
//      case 1:
//        System.out.println("Logged in as administrator!");
//        //Will open admin view
//        break;
//      case 2:
//        System.out.println("Logged in as customer!");
//        //Will open customer view
//        break;
//      case 3:
//        System.out.println("Logged in as picker!");
//        //Will open picker view
//        break;
//      case 4:
//        System.out.println("Logged in as driver!");
//        //Will open driver view
//        break;
//    }
//  }


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
