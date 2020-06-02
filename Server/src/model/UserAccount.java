package model;

import java.io.Serializable;

public class UserAccount implements Serializable
{
  String username;
  String fullName;
  UserType userType;

  public UserAccount(String username, String fullName, UserType userType)
  {
    this.username = username;
    this.fullName = fullName;
    this.userType = userType;
  }
  public UserAccount(String username, UserType userType)
  {
    this.username = username;
    this.userType = userType;
  }

  public UserAccount(){
    username = "unknown";
    fullName = "unknown";
    userType = UserType.UNKNOWN;
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

  @Override
  public String toString() {
    return "UserAccount{" +
            "username='" + username + '\'' +
            ", fullName='" + fullName + '\'' +
            ", userType=" + userType +
            '}';
  }
}
