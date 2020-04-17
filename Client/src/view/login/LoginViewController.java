package view.login;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.LoginViewModel;

public class LoginViewController {
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;
    @FXML private TextField username;
    @FXML private TextField password;

    public LoginViewController() {}

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }

    @FXML public void loginButtonClicked() { viewModel.login(username.getText(), password.getText()); }

    @FXML public void exitButtonClicked() { System.exit(0); }
}
