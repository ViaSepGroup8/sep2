package database;

import model.Item;
import model.User;
import model.UserType;

import java.util.ArrayList;

public interface Database
{
  ArrayList<Item> getAllWarehouseItems();
  User getUser(String username, String password);
  UserType getUserType(String username, String password);
}
