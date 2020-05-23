package view.administrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Order;

public class OrderTableRowData
{
  private SimpleStringProperty customerName;
  private SimpleStringProperty orderId;
  private SimpleStringProperty deliveryAddress;
  private SimpleIntegerProperty totalSum;
  private SimpleIntegerProperty totalItems;
  private SimpleStringProperty orderStatus;

  public OrderTableRowData(Order order) {
    customerName = new SimpleStringProperty(order.getCustomer().getFullName());
    orderId = new SimpleStringProperty(order.getUniqueId());
    deliveryAddress = new SimpleStringProperty(order.getDeliverAddress());
    totalSum = new SimpleIntegerProperty(order.totalSum());
    totalItems = new SimpleIntegerProperty(order.totalItemsNumber());
    orderStatus = new SimpleStringProperty(order.getStatus().name());
  }

  public SimpleStringProperty customerNameProperty()
  {
    return customerName;
  }

  public SimpleStringProperty orderIdProperty()
  {
    return orderId;
  }

  public SimpleStringProperty deliveryAddressProperty()
  {
    return deliveryAddress;
  }

  public SimpleIntegerProperty totalSumProperty()
  {
    return totalSum;
  }

  public SimpleIntegerProperty totalItemsProperty()
  {
    return totalItems;
  }

  public SimpleStringProperty orderStatusProperty() { return orderStatus; }
}
