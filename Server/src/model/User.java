package model;

import java.io.Serializable;

public class User implements Serializable
{
  String username;
  String fullName;
  UserType userType;

  public User(String username, String fullName, UserType userType)
  {
    this.username = username;
    this.fullName = fullName;
    this.userType = userType;
  }
  public User(String username, UserType userType)
  {
    this.username = username;
    this.userType = userType;
  }

  public String getUsername()
  {
    return username;
  }

  public String getFullName()
  {
    return fullName;
  }

  public UserType getUserType()
  {
    return userType;
  }

  @Override public String toString()
  {
    return userType.name() + " " + fullName + " " + username;
  }
}
