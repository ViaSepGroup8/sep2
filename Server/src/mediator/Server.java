package server.mediator;

import client.mediator.ChatClient;
import server.model.Message;
import server.model.ServerModel;
import server.model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements ChatServer
{
  ServerModel model;
  ArrayList<ChatClient> clients;
  ArrayList<User> users;

  public Server(ServerModel model) throws RemoteException
  {
    this.model = model;
    clients = new ArrayList<ChatClient>();
    users = new ArrayList<User>();
    UnicastRemoteObject.exportObject(this, 0);
  }

  @Override public void registerClient(ChatClient client, User user) throws RemoteException
  {
    clients.add(client);
    users.add(user);
    model.log("new client connected " + client + "\n" + user);
    updateAllClientsUserList();
  }

  @Override public void broadCast(Message message, ChatClient sender) throws RemoteException
  {
    model.log("broadcasting message" + message);
    for (ChatClient client : clients)
    {
//      if(client.equals(sender))
//        continue;

      client.receiveMessage(message);
    }
  }

  @Override public void requestUserList(ChatClient client) throws RemoteException
  {
    model.log("userlist request");
    client.receiveUserList(users);
  }

  @Override public String ping() throws RemoteException
  {
    return "pong";
  }

  @Override public void updateUser(User user, ChatClient client) throws RemoteException
  {
    users.set(clients.indexOf(client), user);
    updateAllClientsUserList();
  }

  public void updateAllClientsUserList() throws RemoteException
  {
    for (ChatClient chatClient : clients)
    {
      chatClient.receiveUserList(users);
    }
  }
}
