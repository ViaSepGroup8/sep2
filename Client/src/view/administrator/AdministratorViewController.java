package view.administrator;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import view.ViewHandler;
import view.customer.ItemTableRowData;
import view.customer.OrderTableRowData;
import viewmodel.AdministratorViewModel;

public class AdministratorViewController {

    public TextField descriptionTextField;
    public TextField priceTextField;
    @FXML private TableView<UserTableRowData> accountsTable;
    @FXML private TableColumn<UserTableRowData, String> usernameColumn;
    @FXML private TableColumn<UserTableRowData, String> typeOfAccountColumn;
    @FXML private TableColumn<UserTableRowData, String> roleColumn;

    @FXML private TextField usernameTextField;
    @FXML private TextField FullNameTextField;
    @FXML private ChoiceBox<RoleChoiceBox> roleChoiceBox;
    @FXML private PasswordField passwordField;

    //Labels
    @FXML private Label userError;

    //Table 1
    @FXML private TableView<ItemTableRowData> itemsTable;
    @FXML private TableColumn<ItemTableRowData, Number> id;
    @FXML private TableColumn<ItemTableRowData, String> item;
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
    private AdministratorViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, AdministratorViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        ordersTable.setItems(viewModel.getOrderList());             //bind orders list
        itemsTable.setItems(viewModel.getProductList());            //bind products list
        accountsTable.setItems(viewModel.getUserList());            //bind user list
        userError.textProperty().bind(viewModel.getUserError());    //bind the error label property
        
        //Products table
        id.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().uniqueIdProperty());
        item.setCellValueFactory(CellData -> CellData.getValue().nameProperty());
        price.setCellValueFactory(itemTableRowDataNumberCellDataFeatures -> itemTableRowDataNumberCellDataFeatures.getValue().priceProperty());
        itemsTable.setEditable(true); //to be editable

        //Orders Table
        customerColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().customerNameProperty());
        orderColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderIdProperty());
        sumColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalSumProperty());
        AddressColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().deliveryAddressProperty());
        itemsColumn.setCellValueFactory(orderTableRowDataNumberCellDataFeatures -> orderTableRowDataNumberCellDataFeatures.getValue().totalItemsProperty());
        statusColumn.setCellValueFactory(orderTableRowDataStringCellDataFeatures -> orderTableRowDataStringCellDataFeatures.getValue().orderStatusProperty());

        //User Table
        usernameColumn.setCellValueFactory(userTableRowDataStringCellDataFeatures -> userTableRowDataStringCellDataFeatures.getValue().usernameProperty());
        typeOfAccountColumn.setCellValueFactory(userTableRowDataStringCellDataFeatures -> userTableRowDataStringCellDataFeatures.getValue().fullNameProperty());
        roleColumn.setCellValueFactory(userTableRowDataStringCellDataFeatures -> userTableRowDataStringCellDataFeatures.getValue().userTypeProperty());

        // Role choice box
        roleChoiceBox.setConverter(RoleChoiceBox.getConverter());
        roleChoiceBox.setItems(RoleChoiceBox.getRoleChoiceObservableList());

        usernameTextField.textProperty().bindBidirectional(viewModel.getUserNameProperty());
        FullNameTextField.textProperty().bindBidirectional(viewModel.getFullNameProperty());
        passwordField.textProperty().bindBidirectional(viewModel.getPasswordFieldProperty());

        // Products
        descriptionTextField.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        Bindings.bindBidirectional(priceTextField.textProperty(), viewModel.getPriceProperty(), new NumberStringConverter());

        viewModel.refreshOrderList();
        viewModel.refreshProductList();
        viewModel.refreshUserList();
    }

    public Region getRoot() { return root; }

    public void onLogOut (ActionEvent actionEvent) {
        viewModel.logOut();
        viewHandler.openView("Login");
    }


    public void addAccountButtonPressed(ActionEvent actionEvent)
    {
        viewModel.addAccount(roleChoiceBox.getValue().getUserType());
    }

    public void deleteAccountButtonPressed(ActionEvent actionEvent)
    {
        viewModel.deleteAccount(accountsTable.getSelectionModel().getSelectedItem());
    }

    public void addItemButtonPressed(ActionEvent actionEvent)
    {
        viewModel.addProduct();
    }

    public void deleteItemButtonPressed(ActionEvent actionEvent)
    {
        viewModel.removeProduct(itemsTable.getSelectionModel().getSelectedItem());
    }

    public void deleteOrderButtonPressed(ActionEvent actionEvent)
    {
        viewModel.deleteOrder();
    }
}