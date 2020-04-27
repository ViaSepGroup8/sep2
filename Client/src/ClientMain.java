import javafx.application.Application;
import javafx.stage.Stage;
import model.ClientModel;
import model.ClientModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

public class ClientMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ClientModel model = new ClientModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(stage);
    }
}
