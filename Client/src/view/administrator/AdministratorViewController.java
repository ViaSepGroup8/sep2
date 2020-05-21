package view.administrator;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Item;
import model.Job;
import view.ViewHandler;
import view.customer.ItemTableRowData;
import view.customer.OrderTableRowData;
import view.picker.PickerTableRowData;
import viewmodel.AdministratorViewModel;
import viewmodel.CustomerViewModel;
import viewmodel.DriverViewModel;
import viewmodel.PickerViewModel;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AdministratorViewController {
    private ViewHandler viewHandler;
    private AdministratorViewModel viewModel;
    private CustomerViewModel cViewModel;
    private DriverViewModel dViewModel;
    private PickerViewModel pViewModel;
    private Region root;

    public AdministratorViewController() {}

    public void init(ViewHandler viewHandler, AdministratorViewModel viewModel, CustomerViewModel cViewModel, DriverViewModel dViewModel, PickerViewModel pViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.cViewModel = cViewModel;
        this.dViewModel = dViewModel;
        this.pViewModel = pViewModel;
        this.root = root;

        //Customer

        customerName.setText(cViewModel.getCustomerName());

        id.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
        item.setCellValueFactory(CellData -> CellData.getValue().nameProperty());
        quantity.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
        price.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().priceProperty());

        itemsTable.setEditable(true);

        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        //Orders Table
        customerColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>()
        {
            @Override public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures)
            {
                return orderTableRowDataStringCellDataFeatures.getValue().customerNameProperty();
            }
        });

        orderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>()
        {
            @Override public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures)
            {
                return orderTableRowDataStringCellDataFeatures.getValue().orderIdProperty();
            }
        });

        sumCollumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, Number>, ObservableValue<Number>>()
        {
            @Override public ObservableValue<Number> call(
                    TableColumn.CellDataFeatures<OrderTableRowData, Number> orderTableRowDataNumberCellDataFeatures)
            {
                return orderTableRowDataNumberCellDataFeatures.getValue().totalSumProperty();
            }
        });


        AddressColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>()
        {
            @Override public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures)
            {
                return orderTableRowDataStringCellDataFeatures.getValue().deliveryAddressProperty();
            }
        });

        itemsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, Number>, ObservableValue<Number>>()
        {
            @Override public ObservableValue<Number> call(
                    TableColumn.CellDataFeatures<OrderTableRowData, Number> orderTableRowDataNumberCellDataFeatures)
            {
                return orderTableRowDataNumberCellDataFeatures.getValue().totalItemsProperty();
            }
        });

        statusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>()
        {
            @Override public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures)
            {
                return orderTableRowDataStringCellDataFeatures.getValue().orderStatusProperty();
            }
        });
        ordersTable.setItems(cViewModel.getOrdersList());

        //Driver

        orderId.textProperty().bind(dViewModel.orderIdProperty());
        orderStatus.textProperty().bind(dViewModel.orderStatusProperty());
        orderGate.textProperty().bind(dViewModel.gateProperty());
        customerId.textProperty().bind(dViewModel.customerNameProperty());
        orderDeliveryAddress.textProperty().bind(dViewModel.deliverAddressProperty());

        deliveryButton.setDisable(true);

        //Picker

        idColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
        nameColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().nameProperty());
        quantityColumn.setCellValueFactory(itemTableRowDataIntegerCellDataFeatures -> itemTableRowDataIntegerCellDataFeatures.getValue().quantityProperty());
        locationColumn.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().locationProperty());

        pickerTable.setEditable(true);
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        idColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, Number>, ObservableValue<Number>>() {
            @Override public ObservableValue<Number> call(
                    TableColumn.CellDataFeatures<PickerTableRowData, Number> orderTableRowDataNumberCellDataFeatures) {
                return orderTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty();
            }
        });

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<PickerTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().nameProperty();
            }
        });

        quantityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, Number>, ObservableValue<Number>>() {
            @Override public ObservableValue<Number> call(
                    TableColumn.CellDataFeatures<PickerTableRowData, Number> orderTableRowDataNumberCellDataFeatures) {
                return orderTableRowDataNumberCellDataFeatures.getValue().quantityProperty();
            }
        });


        locationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PickerTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call (
                    TableColumn.CellDataFeatures<PickerTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().locationProperty();
            }
        });

        pickerTable.setItems(pViewModel.getPickerList());

    }

    public Region getRoot() { return root; }

    @FXML
    public void logOutButtonClicked () { viewModel.logOut(); }

    // Customer

    @FXML public Label totalSum;
    @FXML public Label customerName;
    @FXML public TableView<ItemTableRowData> itemsTable;
    @FXML public TableColumn<ItemTableRowData, Number> id;
    @FXML public TableColumn<ItemTableRowData, String> item;
    @FXML public TableColumn<ItemTableRowData, Number> quantity;
    @FXML public TableColumn<ItemTableRowData, Number> price;
    @FXML public TableView<view.customer.OrderTableRowData> ordersTable;
    @FXML public TableColumn<view.customer.OrderTableRowData, String> customerColumn;
    @FXML public TableColumn<view.customer.OrderTableRowData, String> orderColumn;
    @FXML public TableColumn<view.customer.OrderTableRowData, String> AddressColumn;
    @FXML public TableColumn<view.customer.OrderTableRowData, Number> sumCollumn;
    @FXML public TableColumn<view.customer.OrderTableRowData, Number> itemsColumn;
    @FXML public TableColumn<OrderTableRowData, String> statusColumn;

    @FXML
    public void newOrderButtonClicked () {
        itemsTable.setItems(cViewModel.getAllWarehouseItems());
    }

    @FXML
    public void acceptOrderButtonClicked () {
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
        cViewModel.createCustomerNewOrder(itemsSelected);

        //refresh the list of orders
        ordersTable.setItems(cViewModel.getOrdersList());
    }

    @FXML
    public void refreshButtonClicked() {
        ordersTable.setItems(cViewModel.getOrdersList());
    }

    // Driver

    @FXML public Label orderId;
    @FXML public Label orderStatus;
    @FXML public Label orderGate;
    @FXML public Label customerId;
    @FXML public Label orderDeliveryAddress;
    @FXML public Button askButton;
    @FXML public Button deliveryButton;

    @FXML
    public void askButtonClicked() {
        dViewModel.ask();
        if(!orderId.textProperty().get().equals("-")) {
            deliveryButton.setDisable(false);
        }
    }

    @FXML
    public void deliveredButtonClicked() {
        dViewModel.deliver();
        deliveryButton.setDisable(true);
    }

    // Picker

    private Job job;
    @FXML public TableView<view.picker.PickerTableRowData> pickerTable;
    @FXML public TableColumn<view.picker.PickerTableRowData, Number> idColumn;
    @FXML public TableColumn<view.picker.PickerTableRowData, String> nameColumn;
    @FXML public TableColumn<view.picker.PickerTableRowData, Number> quantityColumn;
    @FXML public TableColumn<PickerTableRowData, String> locationColumn;

    @FXML
    public void orderCompletedButtonClicked() throws RemoteException {
        if (job != null) {
            String jobId = this.job.getJobId();
            pViewModel.completeJob(jobId);
        }
        else { System.out.println("Job is null"); }
    }
}
