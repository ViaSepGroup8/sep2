package mediator;

import database.Database;
import database.FakeDatabase;
import database.InvalidDatabaseRequestException;
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
    //todo database complete job
    //todo check if its the last job of the order

    if(false){
      try
      {
        database.setOrderStatus(database.getJobById(jobId).getOrderId(), OrderStatus.READY_FOR_PICKUP);
      }
      catch (InvalidDatabaseRequestException e)
      {
        model.log(e.getMessage());
      }
    }
  }
  @Override public void createNewOrder(Order order) throws RemoteException
  {
    //add the order to the database
    database.addOrder(order);

    //This is where the orders is split into smaller job objects
    ArrayList<Item> orderItems = order.getItems();
    ArrayList<Item> jobItems = new ArrayList<Item>();
    Integer jobCount = 0;

    for (int i = 0; i < orderItems.size(); i++)
    {
      Item orderItem = orderItems.get(i);
      jobItems.add(orderItem);

      //first part: if we have more 20 items create a job for a picker.
      //second part: check if it is the last item in the order list. In this case we will create a job
      //with only the items that are left. Meaning the last job will have probably less items than 20.
      if (jobItems.size() >= 20 || (i == orderItems.size()-1 && orderItems.size() > 0))
      {
        database.addJob(new Job("Ord" + order.getUniqueId() + "-" + "Job" + jobCount.toString(),order.getUniqueId(), jobItems));
        jobItems = new ArrayList<>();
        jobCount++;
      }
    }

    //change the status of the order to job divided
    try { database.setOrderStatus(order.getUniqueId(), OrderStatus.JOBS_DIVIDED); }
    catch (Exception e) { model.log("database cannot find order id" + order.getUniqueId()); }
  }

  @Override public ArrayList<Order> getOrderList() throws RemoteException
  {
    return database.getOrders();
  }

  @Override public Order getNewPickupOrder()
  {
    return null;
  }

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
