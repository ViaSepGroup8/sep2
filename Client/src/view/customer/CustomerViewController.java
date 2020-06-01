package view.customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.converter.NumberStringConverter;
import view.ViewHandler;
import viewmodel.CustomerViewModel;

public class CustomerViewController {
  //Labels
  @FXML private Label customerName;
  @FXML private Label userError;
  @FXML private  TextField deliveryAddress;
  //@FXML private Label totalSum;

  //Table 1
  @FXML private TableView<ItemTableRowData> itemsTable;
  @FXML private TableColumn<ItemTableRowData, Number> id;
  @FXML private TableColumn<ItemTableRowData, String> item;
  @FXML private TableColumn<ItemTableRowData, Number> quantity;
  @FXML private TableColumn<ItemTableRowData, Number> price;

  //Table 2
  @FXML private TableView<OrderTableRowData> ordersTable;
  @FXML private TableColumn<OrderTableRowData, String> customerColumn;
  @FXML private TableColumn<OrderTableRowData, String> orderColumn;
  @FXML private TableColumn<OrderTableRowData, String> AddressColumn;
  @FXML private TableColumn<OrderTableRowData, Number> sumColumn;
  @FXML private TableColumn<OrderTableRowData, Number> itemsColumn;
  @FXML private TableColumn<OrderTableRowData, String> statusColumn;

  //Other
  private ViewHandler viewHandler;
  private CustomerViewModel viewModel;
  private Region root;

  public void init(ViewHandler viewHandler, CustomerViewModel viewModel, Region root) {
  this.viewHandler = viewHandler;
  this.viewModel = viewModel;
  this.root = root;

  customerName.textProperty().bind(viewModel.getUsername());  //bind the customer name
  ordersTable.setItems(viewModel.getOrderList());             //bind orders list
  itemsTable.setItems(viewModel.getProductList());            //bind products list
  userError.textProperty().bind(viewModel.getUserError());    //bind the error label property
  deliveryAddress.textProperty().bindBidirectional(viewModel.getDeliveryAddress());
  //Products table
  id.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
  item.setCellValueFactory(CellData -> CellData.getValue().nameProperty());
  quantity.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
  quantity.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
  price.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().priceProperty());
  itemsTable.setEditable(true); //to be editable

  //Orders Table
  customerColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().customerNameProperty());
  orderColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderIdProperty());
  sumColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalSumProperty());
  AddressColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().deliveryAddressProperty());
  itemsColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalItemsProperty());
  statusColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderStatusProperty());

  viewModel.refreshOrderList();
  }

  public Region getRoot() { return root; }

  public void onNewOrder (ActionEvent actionEvent) {
    viewModel.refreshProductList();
  }

  public void onAcceptOrder (ActionEvent actionEvent) {
    viewModel.createNewOrder();
  }

  public void onLogOut (ActionEvent actionEvent) {
      viewModel.logOut();
      viewHandler.openView("Login");
  }

  public void onRefreshButton(ActionEvent actionEvent) {
      viewModel.refreshOrderList();
  }
}