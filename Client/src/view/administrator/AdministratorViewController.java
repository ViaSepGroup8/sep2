package view.administrator;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import model.Order;
import model.User;
import view.ViewHandler;
import viewmodel.AdministratorViewModel;



public class AdministratorViewController {
    private ViewHandler viewHandler;
    private AdministratorViewModel viewModel;
    private Region root;

    @FXML private TableView<UserTableRowData> accountsTable;
    @FXML private TableColumn<UserTableRowData, String> usernameColumn;
    @FXML private TableColumn<UserTableRowData, String> typeOfAccountColumn;

    @FXML private TableView<OrderTableRowData> ordersTable;
    @FXML private TableColumn<OrderTableRowData, String> customerColumn;
    @FXML private TableColumn<OrderTableRowData, String> orderColumn;
    @FXML private TableColumn<OrderTableRowData, String> deliveryAddressColumn;
    @FXML private TableColumn<OrderTableRowData, Number> totalSumColumn;
    @FXML private TableColumn<OrderTableRowData, Number> totalItemsColumn;
    @FXML private TableColumn<OrderTableRowData, String> statusColumn;

    public AdministratorViewController() {}

    public void init(ViewHandler viewHandler, AdministratorViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        //User table

        usernameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<UserTableRowData, String> userTableRowDataStringCellDataFeatures) {
                return userTableRowDataStringCellDataFeatures.getValue().usernameProperty();
            }
        });

        typeOfAccountColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<UserTableRowData, String> userTableRowDataStringCellDataFeatures) {
                return userTableRowDataStringCellDataFeatures.getValue().typeOfAccountProperty();
            }
        });

         accountsTable.setItems(viewModel.getAccountsList());

        //Order table

        customerColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().customerNameProperty();
            }
        });

        orderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().orderIdProperty();
            }
        });

        totalSumColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, Number>, ObservableValue<Number>>() {
            @Override public ObservableValue<Number> call(TableColumn.CellDataFeatures<OrderTableRowData, Number> orderTableRowDataNumberCellDataFeatures) {
                return orderTableRowDataNumberCellDataFeatures.getValue().totalSumProperty();
            }
        });


        deliveryAddressColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().deliveryAddressProperty();
            }
        });

        totalItemsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, Number>, ObservableValue<Number>>() {
            @Override public ObservableValue<Number> call(TableColumn.CellDataFeatures<OrderTableRowData, Number> orderTableRowDataNumberCellDataFeatures) {
                return orderTableRowDataNumberCellDataFeatures.getValue().totalItemsProperty();
            }
        });

        statusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OrderTableRowData, String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderTableRowData, String> orderTableRowDataStringCellDataFeatures) {
                return orderTableRowDataStringCellDataFeatures.getValue().orderStatusProperty();
            }
        });

        ordersTable.setItems(viewModel.getOrdersList());

    }

    public Region getRoot() { return root; }

    @FXML public void addAccountButtonPressed() { viewModel.addAccount(); }

    @FXML public void deleteAccountButtonPressed() {
        ObservableList<User> selectedRows;

        selectedRows = convertToUser(accountsTable.getSelectionModel().getSelectedItems());

        for (User user: selectedRows) {
            viewModel.deleteAccount();
        }
    }

    @FXML public void deleteOrderButtonPressed() {
        ObservableList<Order> selectedRows;

        selectedRows = convertToOrder(ordersTable.getSelectionModel().getSelectedItems());

        for (Order order: selectedRows) {
            viewModel.deleteAccount();
        }
    }

    private ObservableList<User> convertToUser(ObservableList<UserTableRowData> userTableRowData) {
        return null;
    }

    private ObservableList<Order> convertToOrder(ObservableList<OrderTableRowData> orderTableRowData) {
        return null;
    }

    @FXML public void logOutButtonPressed() { viewModel.logOut(); }
}
