package server.mediator;

import client.mediator.ChatClient;
import server.model.Message;
import server.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote
{
  void registerClient(ChatClient client, User user) throws  RemoteException;
  void broadCast(Message message, ChatClient sender) throws RemoteException;
  void requestUserList(ChatClient client) throws  RemoteException;
  String ping() throws RemoteException;
  void updateUser(User user, ChatClient client) throws RemoteException;
}
