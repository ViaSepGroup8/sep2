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

  @Override public User login(String username, String password) throws RemoteException
  {
    //return database.getUserType(username, password);
    return database.getUser(username, password);
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

    if (false)
    {
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
    try
    { database.addOrder(order); }
    catch (InvalidDatabaseRequestException e)
    { model.log(e.getMessage()); return;}

    new Thread(() -> {
      //This is where the orders is split into smaller job objects
      //lets make this action take at least one second \_^^_/
      try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
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
        if (jobItems.size() >= 20 || (i == orderItems.size() - 1 && orderItems.size() > 0))
        {
          database.addJob(new Job("Ord" + order.getUniqueId() + "-" + "Job" + jobCount.toString(), order.getUniqueId(), jobItems));
          jobItems = new ArrayList<>();
          jobCount++;
        }
      }

      model.log("created " + jobCount + " jobs for pickers");

      //change the status of the order to job divided
      try
      {
        database.setOrderStatus(order.getUniqueId(), OrderStatus.JOBS_DIVIDED);
      }
      catch (Exception e)
      {
        model.log("database cannot find order id" + order.getUniqueId());
      }

    }).start();
  }

  @Override public ArrayList<Order> getUserOrders(User customer) throws RemoteException
  {
    return database.getUserOrders(customer);
  }

  @Override public ArrayList<Order> getOrders() throws RemoteException
  {
    return database.getAllOrders();
  }

  @Override public Order getNewPickupOrder(User user) throws RemoteException
  {
    Order order = null;
    try
    {
      order = database.getNewPickupOrder(user);
      model.log("driver " + user.getFullName() + " has been assigned to deliver order " + order.getUniqueId());
    }
    catch (InvalidDatabaseRequestException e)
    {
      model.log("no available orders for drivers");
    }
    finally
    {
      return order;
    }
  }

  @Override public void deliver(Order order, User user) throws RemoteException
  {
    try
    {
      model.log("driver " + user.getFullName() + " has delivered order " + order.getUniqueId());
      database.setOrderStatus(order.getUniqueId(), OrderStatus.DELIVERED);
    }
    catch (InvalidDatabaseRequestException e)
    {
      model.log("requested order cannot be changed to deliver, order not found");
    }

    ArrayList<Order> list = database.getAllOrders();
    for (Order o : list)
    {
      System.out.println("# " + o);
    }
    System.out.println("");
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
