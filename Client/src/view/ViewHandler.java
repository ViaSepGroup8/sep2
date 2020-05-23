package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.administrator.AdministratorViewController;
import view.customer.CustomerViewController;
import view.driver.DriverViewController;
import view.login.LoginViewController;
import view.picker.PickerViewController;
import viewmodel.ViewModelFactory;

import java.io.IOException;

public class ViewHandler {
    private ViewModelFactory viewModelFactory;
    private LoginViewController loginViewController;
    private CustomerViewController customerViewController;
    private AdministratorViewController administratorViewController;
    private DriverViewController driverViewController;
    private PickerViewController pickerViewController;
    private Scene currentScene;
    private Stage primaryStage;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("Login");
    }

    public void openView(String id) {
        Region root = null;
        double width = 0;
        double height = 0;

        switch (id) {
            case "Login":
                loginViewController = loadLoginView("/view/login/LoginView.fxml", loginViewController);
                root = loginViewController.getRoot();
                break;
            case "Customer":
                customerViewController = loadCustomerView("/view/customer/CustomerView.fxml", customerViewController);
                root = customerViewController.getRoot();
                break;
            case "Administrator":
                administratorViewController = loadAdministratorView("/view/administrator/AdministratorView.fxml", administratorViewController);
                root = administratorViewController.getRoot();
                break;
            case "Driver":
                driverViewController = loadDriverView("/view/driver/DriverView.fxml", driverViewController);
                root = driverViewController.getRoot();
                break;
            case "Picker":
                pickerViewController = loadPickerView("/view/picker/PickerView.fxml", pickerViewController);
                root = pickerViewController.getRoot();
                break;
            default:
                System.out.println("ERROR NO CHOSEN WINDOW");
        }

        width = root.prefWidth(0);
        height = root.prefHeight(0);

        currentScene.setRoot(root);
        primaryStage.setTitle("Bernardo's warehouse");
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.show();
    }

    public void closeView() { primaryStage.close(); }

    private LoginViewController loadLoginView(String fxmlFile, LoginViewController controller) {
        controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Region root = loader.load();
            controller = loader.getController();
            controller.init(this, viewModelFactory.getLoginViewModel(), root);
        }
        catch (IOException e) { e.printStackTrace(); }
        return controller;
    }

    private CustomerViewController loadCustomerView(String fxmlFile, CustomerViewController controller) {
        controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Region root = loader.load();
            controller = loader.getController();
            controller.init(this, viewModelFactory.getCustomerViewModel(), root);
        }
        catch (IOException e) { e.printStackTrace(); }
        return controller;
    }

    private AdministratorViewController loadAdministratorView(String fxmlFile, AdministratorViewController controller) {
        controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Region root = loader.load();
            controller = loader.getController();
            controller.init(this, viewModelFactory.getAdministratorViewModel(), root);
        }
        catch (IOException e) { e.printStackTrace(); }
        return controller;
    }

    private DriverViewController loadDriverView(String fxmlFile, DriverViewController controller) {
        controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Region root = loader.load();
            controller = loader.getController();
            controller.init(this, viewModelFactory.getDriverViewModel(), root);
        }
        catch (IOException e) { e.printStackTrace(); }
        return controller;
    }

    private PickerViewController loadPickerView(String fxmlFile, PickerViewController controller) {
        controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Region root = loader.load();
            controller = loader.getController();
            controller.init(this, viewModelFactory.getPickerViewModel(), root);
        }
        catch (IOException e) { e.printStackTrace(); }
        return controller;
    }
}
