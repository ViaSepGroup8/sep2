package view.administrator;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.AdministratorViewModel;

public class AdministratorViewController {
    private ViewHandler viewHandler;
    private AdministratorViewModel viewModel;
    private Region root;

    public AdministratorViewController() {}

    public void init(ViewHandler viewHandler, AdministratorViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }

    @FXML public void addAccountButtonPressed() {}

    @FXML public void deleteAccountButtonPressed() {}

    @FXML public void deleteOrderButtonPressed() {}

    @FXML public void logOutButtonPressed() {}
}
