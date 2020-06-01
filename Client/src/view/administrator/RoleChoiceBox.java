package view.administrator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import model.UserType;

public class RoleChoiceBox
{
  private String role;
  private UserType userType;

  public RoleChoiceBox(String role, UserType userType)
  {
    this.role = role;
    this.userType = userType;
  }

  public static ObservableList<RoleChoiceBox> getRoleChoiceObservableList(){
    ObservableList<RoleChoiceBox> list = FXCollections.observableArrayList();
    for (int i = 0; i < UserType.values().length; i++)
    {
      list.add(new RoleChoiceBox(UserType.values()[i].toString(), UserType.values()[i]));
    }
    return list;
  }

  public static StringConverter<RoleChoiceBox> getConverter(){
    return new StringConverter<RoleChoiceBox>()
    {
      @Override public String toString(RoleChoiceBox roleChoiceBox)
      {
        return roleChoiceBox.roleString();
      }

      @Override public RoleChoiceBox fromString(String s) { return null; }
    };
  }


  public String roleString()
  {
    return role;
  }

  public UserType getUserType()
  {
    return userType;
  }
}
