package viewmodel;

import model.ClientModel;

public class LoginViewModel {
    private ClientModel model;

    public LoginViewModel(ClientModel model) {
        this.model = model;
    }

    public void login(String username, String password) { model.login(username, password); }
}
