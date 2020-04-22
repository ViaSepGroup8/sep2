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

    //https://stackoverflow.com/questions/27900344/how-to-make-a-table-column-with-integer-datatype-editable-without-changing-it-to

    public void onNewOrder (ActionEvent actionEvent) {


    }

    public void onAcceptOrder (ActionEvent actionEvent) {

    }

    public void onLogOut (ActionEvent actionEvent) {
    viewModel.logOut();

    }
}
