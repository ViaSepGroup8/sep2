package mediator;

import model.ServerModel;
import model.ServerModelManager;
import model.UserType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements WarehouseServer
{
  ServerModel model;
//  ArrayList<WarehouseClient> clients;
//  ArrayList<User> users;

  public Server(ServerModel model) throws RemoteException
  {
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
    //todo implement with database
    switch (username)
    {
      case "admin":{ return UserType.ADMIN; }
      case "picker": { return UserType.PICKER; }
      case "driver": { return UserType.DRIVER; }
      case "customer": { return UserType.CUSTOMER; }
    }

    //wrong username or password, return null
    return null;
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
