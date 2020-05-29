package view.customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.converter.NumberStringConverter;
import model.Item;
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

        customerName.textProperty().bind(viewModel.getUsername()); //bind the customer name
        ordersTable.setItems(viewModel.getOrdersList()); //bind order list
        viewModel.refresh();

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
        sumCollumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalSumProperty());
        AddressColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().deliveryAddressProperty());
        itemsColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalItemsProperty());
        statusColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderStatusProperty());
        ordersTable.setItems(viewModel.getOrdersList());
    }

    public Region getRoot() { return root; }

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

        //Remove all not selected items by customer also remove invalid values
        ArrayList<Item> valuesToRemove = new ArrayList<>();
        for (Item item : itemsSelected) {
            if (item.getQuantity() < 1) valuesToRemove.add(item);
        }
        itemsSelected.removeAll(valuesToRemove);
        if(itemsSelected.size()  == 0){
            System.out.println("zero items in order");return;
        }

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
        viewModel.refresh();
    }
}