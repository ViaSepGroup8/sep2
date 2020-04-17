package viewmodel;

import model.WarehouseClientModelManager;

public class LoginViewModel {
    private WarehouseClientModelManager model;

    public LoginViewModel(WarehouseClientModelManager model) {
        this.model = model;
    }

    public void login(String username, String password) { model.login(username, password); }
}
