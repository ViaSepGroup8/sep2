package view.login;

import javafx.scene.layout.Region;
import view.ViewHandler;
import viewmodel.LoginViewModel;

public class LoginViewController {
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;

    public LoginViewController() {}

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public Region getRoot() { return root; }
}
