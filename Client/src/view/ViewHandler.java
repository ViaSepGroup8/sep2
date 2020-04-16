package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
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
        loginViewController = new LoginViewController();
        customerViewController = new CustomerViewController();
        administratorViewController = new AdministratorViewController();
        driverViewController = new DriverViewController();
        pickerViewController = new PickerViewController();

        this.currentScene = new Scene(new Region());

        loginViewController.init(this, viewModelFactory.getLoginViewModel(), new Region());
        customerViewController.init(this, viewModelFactory.getLoginViewModel(), new Region());
        administratorViewController.init(this, viewModelFactory.getLoginViewModel(), new Region());
        driverViewController.init(this, viewModelFactory.getLoginViewModel(), new Region());
        pickerViewController.init(this, viewModelFactory.getLoginViewModel(), new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void openView(String id) {
        Region root = null;
        String title = "";
        primaryStage = new Stage();
        int width = 0;
        int height = 0;

        switch (id) {
            case "Login":
                loginViewController = loadLoginView("/view/LoginView.fxml", loginViewController);
                root = loginViewController.getRoot();
                width = 225;
                height = 170;
                break;
            case "Customer":
                /*mainViewController = loadMainView("/view/MainView.fxml", mainViewController);
                root = mainViewController.getRoot();
                width = 620;
                height = 435;
                break;*/
            case "Administrator":
                /*mainViewController = loadMainView("/view/MainView.fxml", mainViewController);
                root = mainViewController.getRoot();
                width = 620;
                height = 435;
                break;*/
            case "Driver":
                /*mainViewController = loadMainView("/view/MainView.fxml", mainViewController);
                root = mainViewController.getRoot();
                width = 620;
                height = 435;
                break;*/
            case "Picker":
                /*mainViewController = loadMainView("/view/MainView.fxml", mainViewController);
                root = mainViewController.getRoot();
                width = 620;
                height = 435;
                break;*/
            default:
                System.out.println("ERROR NO CHOSEN WINDOW");
        }

        currentScene.setRoot(root);
        primaryStage.setTitle("Chat room");
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
}
