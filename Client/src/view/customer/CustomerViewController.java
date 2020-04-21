package view.customer;

import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.CustomerViewModel;

public class CustomerViewController {
    private ViewHandler viewHandler;
    private CustomerViewModel viewModel;
    private Region root;

    public CustomerViewController() {}

    public void init(ViewHandler viewHandler, CustomerViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }



    public void onNewOrder (ActionEvent actionEvent) {


    }

    public void onAcceptOrder (ActionEvent actionEvent) {

    }

    public void onLogOut (ActionEvent actionEvent) {
    viewModel.logOut();

    }
}
