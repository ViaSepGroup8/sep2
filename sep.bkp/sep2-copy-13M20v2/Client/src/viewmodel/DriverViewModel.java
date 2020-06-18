package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import model.ClientModel;
import model.Order;
import model.OrderStatus;

public class DriverViewModel {
    private ClientModel model;

    private SimpleStringProperty orderId;
    private SimpleStringProperty orderStatus;
    private SimpleStringProperty gate;
    private SimpleStringProperty customerName;
    private SimpleStringProperty deliverAddress;

    private Order order;


    public DriverViewModel(ClientModel model) {
        this.model = model;

        orderId = new SimpleStringProperty("-");
        orderStatus = new SimpleStringProperty("-");
        gate = new SimpleStringProperty("-");
        customerName = new SimpleStringProperty("-");
        deliverAddress = new SimpleStringProperty("-");
    }

    public void logOut() { model.logOut(); }

  public void deliver()
  {
    model.deliver(order);
    orderStatus.set(OrderStatus.DELIVERED.name());
  }

  public void ask()
  {
    order = model.getNewPickupOrder();
    if(order == null){
      model.debugLog("did not receive any orders, seems like there are none available");

      orderId.set("-");
      orderStatus.set("-");
      gate.set("-");
      customerName.set("-");
      deliverAddress.set("-");
    }
    else{
      orderId.set(order.getUniqueId());
      orderStatus.set(order.getStatus().name());
      gate.set(order.getGate());
      customerName.set(order.getCustomer().getFullName());
      deliverAddress.set(order.getDeliverAddress());
    }
  }

  public ClientModel getModel()
  {
    return model;
  }

  public SimpleStringProperty orderIdProperty()
  {
    return orderId;
  }

  public SimpleStringProperty orderStatusProperty()
  {
    return orderStatus;
  }

  public SimpleStringProperty gateProperty()
  {
    return gate;
  }

  public SimpleStringProperty customerNameProperty()
  {
    return customerName;
  }

  public SimpleStringProperty deliverAddressProperty()
  {
    return deliverAddress;
  }
}
