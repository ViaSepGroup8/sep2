package view.driver;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.DriverViewModel;

public class DriverViewController {
    public Label orderId;
    public Label orderStatus;
    public Label orderGate;
    public Label customerId;
    public Label OrderDeliveryAddress;
    public Button askButton;
    public Button deliveryButton;

    private ViewHandler viewHandler;
    private DriverViewModel viewModel;
    private Region root;

    public DriverViewController() {}

    public void init(ViewHandler viewHandler, DriverViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        orderId.textProperty().bind(viewModel.orderIdProperty());
        orderStatus.textProperty().bind(viewModel.orderStatusProperty());
        orderGate.textProperty().bind(viewModel.gateProperty());
        customerId.textProperty().bind(viewModel.customerNameProperty());
        OrderDeliveryAddress.textProperty().bind(viewModel.deliverAddressProperty());

        deliveryButton.setDisable(true);
    }

    public Region getRoot() { return root; }

    public void ask(ActionEvent actionEvent)
    {
        viewModel.ask();
        if(!orderId.textProperty().get().equals("-")){
            deliveryButton.setDisable(false);
        }
    }

    public void delivered(ActionEvent actionEvent)
    {
        viewModel.deliver();
        deliveryButton.setDisable(true);
    }

    public void logout(ActionEvent actionEvent)
    {
        viewModel.logOut();
    }
}
