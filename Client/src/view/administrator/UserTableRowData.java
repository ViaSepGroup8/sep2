package view.administrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import model.Order;
import model.User;

public class UserTableRowData {
  private SimpleStringProperty username;
  private SimpleStringProperty typeOfAccount;

  public UserTableRowData(User user) {
    username = new SimpleStringProperty(user.getUsername());
    typeOfAccount = new SimpleStringProperty(user.getUserType().toString());
  }

  public SimpleStringProperty usernameProperty() { return username; }

  public SimpleStringProperty typeOfAccountProperty() { return typeOfAccount; }
}
