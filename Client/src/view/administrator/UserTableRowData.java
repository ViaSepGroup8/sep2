package view.administrator;

import javafx.beans.property.SimpleStringProperty;
import model.UserAccount;

public class UserTableRowData {
  private SimpleStringProperty username;
  private SimpleStringProperty fullName;
  private SimpleStringProperty typeOfAccount;

  public UserTableRowData(UserAccount userAccount) {
    username = new SimpleStringProperty(userAccount.getUsername());
    typeOfAccount = new SimpleStringProperty(userAccount.getUserType().toString());
    fullName = new SimpleStringProperty(userAccount.getFullName());
  }

  public SimpleStringProperty usernameProperty() { return username; }
  public SimpleStringProperty userTypeProperty() { return typeOfAccount; }
  public SimpleStringProperty fullNameProperty() { return fullName; }

}
