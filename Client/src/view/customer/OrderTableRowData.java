package view.customer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Order;

public class OrderTableRowData
{
  SimpleStringProperty customerName;
  SimpleStringProperty orderId;
  SimpleStringProperty deliveryAddress;
  SimpleIntegerProperty totalSum;
  SimpleIntegerProperty totalItems;
  SimpleStringProperty orderStatus;

  public OrderTableRowData(Order order)
  {
    customerName = new SimpleStringProperty();
    orderId = new SimpleStringProperty();
    deliveryAddress = new SimpleStringProperty();
    totalSum = new SimpleIntegerProperty();
    totalItems = new SimpleIntegerProperty();
    orderStatus = new SimpleStringProperty();
    set(order);
  }

  public void set(Order order){
    customerName.set(order.getCustomer().getFullName());
    orderId.set(order.getUniqueId());
    deliveryAddress.set(order.getDeliverAddress());
    totalSum.set(order.totalSum());
    totalItems.set(order.totalItemsNumber());
    orderStatus.set(order.getStatus().name());
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

  public SimpleStringProperty orderStatusProperty()
  {
    return orderStatus;
  }
}
