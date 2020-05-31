import javafx.collections.ObservableList;
import mediator.Client;
import model.ClientModel;
import model.ClientModelManager;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.customer.ItemTableRowData;
import view.customer.OrderTableRowData;
import viewmodel.CustomerViewModel;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerViewModelTest
{
  private static boolean needToSetup = true;
  private static ClientModel model;

  private CustomerViewModel customerViewModel = null;

  @BeforeEach void setUp ()
  {
    if(needToSetup){
      String[] args = new String[0];
      RUN_SERVER_ONLY.main(args);

      try { model = new ClientModelManager(); }
      catch (RemoteException e) { e.printStackTrace(); }

      model.login("customer", "pass");

      needToSetup = false;
    }

    customerViewModel = new CustomerViewModel(model);
  }

  @AfterEach void tearDown ()
  {
    customerViewModel = null;
  }

  @Test void newOrderButton ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
//    for (ItemTableRowData thing : products)
//    {
//      System.out.println("Products: " + thing);
//    }
    assertEquals(true, products.size() > 0);
  }


  // Zero
  @Test void completeWithZeroItems ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
    products.get(0).quantityProperty().set(0);
    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();
    assertEquals("Cannot create order with zero items.", error);
  }

  // One
  @Test void createOrderWithOneItem ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
    products.get(10).quantityProperty().set(1);
    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();
    assertEquals("", error);
  }

  // Many
  @Test void createOrderWithManyItems ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
    products.get(10).quantityProperty().set(1);
    products.get(14).quantityProperty().set(2);
    products.get(12).quantityProperty().set(10);
    products.get(11).quantityProperty().set(200);

    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();
    assertEquals("", error);
  }


  // Boundary
  @Test void createOrderWithMinusOneItem ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
    products.get(10).quantityProperty().set(1);
    products.get(14).quantityProperty().set(2);
    products.get(12).quantityProperty().set(-10);
    products.get(11).quantityProperty().set(200);

    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();
    assertEquals("Cannot create order with negative quantity.", error);
  }

//  @Test void createOrderWithIllegalCharacter ()
//  {
//    customerViewModel.refreshProductList();
//    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
//    products.get(10).quantityProperty().set(1);
//    products.get(14).quantityProperty().set(2);
//    products.get(12).quantityProperty().set("hey");
//    products.get(11).quantityProperty().set(200);
//
//    customerViewModel.createNewOrder();
//    String error = customerViewModel.getUserError().get();
//    assertEquals("Cannot create order with zero items.", error);
//  }

  @Test void addNewOrderToTheList ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    //products table
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();

    //orders table
    customerViewModel.refreshOrderList();
    ObservableList<OrderTableRowData> orders = customerViewModel.getOrderList();
    int numberOfOrders = orders.size();

    products.get(10).quantityProperty().set(1);
    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();

    boolean isAdded = numberOfOrders<orders.size();
    boolean hasNoError = error.equals("");

    assertEquals(true, isAdded & hasNoError);
  }

  @Test void createOrderWithEmptyAddress ()
  {
    customerViewModel.getDeliveryAddress().set("");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
    products.get(0).quantityProperty().set(10);
    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();
    assertEquals("Cannot create order without specifying the address.", error);
  }

  @Test void createOrderWithGoodAddress ()
  {
    customerViewModel.getDeliveryAddress().set("VIA");
    customerViewModel.refreshProductList();
    ObservableList<ItemTableRowData> products = customerViewModel.getProductList();
    products.get(0).quantityProperty().set(10);
    customerViewModel.createNewOrder();
    String error = customerViewModel.getUserError().get();
    assertEquals("", error);
  }
}