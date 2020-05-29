package view.customer;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Item;
import model.Order;
import view.ViewHandler;
import viewmodel.CustomerViewModel;

import java.util.ArrayList;

public class CustomerViewController {
    //Labels
    @FXML private Label statusLabel;
    @FXML private Label totalSum;
    @FXML private Label customerName;

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
    @FXML private TableColumn<OrderTableRowData, Number> sumCollumn;
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

        //set the customer name:
        customerName.setText(viewModel.getCustomerName());

        id.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
        item.setCellValueFactory(CellData -> CellData.getValue().nameProperty());
        quantity.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
        price.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().priceProperty());

        //Update the table to allow for the first and last name fields
        //to be editable
        itemsTable.setEditable(true);

//        roomNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        functionalitiesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        subjectsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        //Orders Table
        customerColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().customerNameProperty());
        orderColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderIdProperty());
        sumCollumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalSumProperty());

        AddressColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().deliveryAddressProperty());
        itemsColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalItemsProperty());
        statusColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderStatusProperty());
        ordersTable.setItems(viewModel.getOrdersList());


    }

    public Region getRoot() { return root; }

    //https://stackoverflow.com/questions/27900344/how-to-make-a-table-column-with-integer-datatype-editable-without-changing-it-to

    public void onNewOrder (ActionEvent actionEvent) {
        itemsTable.setItems(viewModel.getAllWarehouseItems());
    }

    public void onAcceptOrder (ActionEvent actionEvent) {
        ArrayList<ItemTableRowData> itemsSelectedRowData = new ArrayList<ItemTableRowData>();
        ArrayList<Item> itemsSelected = new ArrayList<Item>();
        itemsSelectedRowData.addAll(itemsTable.getItems());

        //Insert all elements from itemsSelectedRowData to itemsSelected(converting each element to Item Type)
        for (ItemTableRowData item : itemsSelectedRowData) {
            itemsSelected.add(item.toItemObject());
        }

        //Remove all not selected items by customer
        ArrayList<Item> valuesToRemove = new ArrayList<>();
        for (Item item : itemsSelected) {
            if (item.getQuantity() == 0){
                //valuesToRemove.remove(item); This Fucking error took me 10 minutes of my life
                valuesToRemove.add(item);
            }
        }

        itemsSelected.removeAll(valuesToRemove);

        //Pass info to viewModel
        viewModel.createCustomerNewOrder(itemsSelected);

        //refresh the list of orders
        ordersTable.setItems(viewModel.getOrdersList());
    }

    public void onLogOut (ActionEvent actionEvent) {
        viewModel.logOut();
        viewHandler.openView("Login");
    }

    public void onRefreshButton(ActionEvent actionEvent) {
        ordersTable.setItems(viewModel.getOrdersList());
//        ArrayList<Item> toTable2 = FXCollections.observableArrayList();
//
//        for(Order order : viewModel.refresh()){
//        }
//        ordersTable.setItems(toTable2);
//
//        //TODO: Upload table with items selected to the second FXML table.
//        orderId.setCellValueFactory(new PropertyValueFactory("MAMA"));
//        numberOfItems.setCellValueFactory(new PropertyValueFactory("Rains items"));
//        totalPrice.setCellValueFactory(new PropertyValueFactory("PRICE$$$"));
//        tableStatus.setCellValueFactory(new PropertyValueFactory("High status person"));

        /* HOW IT SHOULD BE DONE(Arraylist to TableView)
            columnOne.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
            columnTwo.setCellValueFactory(c -> new SimpleStringProperty(new String("456")));
        */
    }
}