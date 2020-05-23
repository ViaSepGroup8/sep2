package viewmodel;

import model.ClientModel;

public class AdministratorViewModel {
    private ClientModel model;

    public AdministratorViewModel(ClientModel model) {
        this.model = model;
    }

    public void logOut() { model.logOut(); }

    public void addAccount() { model.addAccount(); }

    public void deleteAccount() { model.deleteAccount(); }

    public void deleteOrder() { model.deleteOrder(); }
}
