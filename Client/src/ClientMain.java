import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

public class ClientMain extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.openView("Login");
    }
}
