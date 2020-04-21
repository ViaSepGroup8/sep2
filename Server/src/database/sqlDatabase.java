package database;

import model.Item;
import model.User;
import model.UserType;

import java.util.ArrayList;

public class sqlDatabase implements Database
{
  @Override public ArrayList<Item> getAllWarehouseItems()
  {
    return null;
  }

  @Override public User getUser(String username, String password)
  {
    return null;
  }

  @Override public UserType getUserType(String username, String password)
  {
    return null;
  }
}
