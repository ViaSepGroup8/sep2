package viewmodel;

import model.ClientModel;

public class DriverViewModel {
    private ClientModel model;

    public DriverViewModel(ClientModel model) {
        this.model = model;
    }

    public void logOut() { model.logOut(); }
}
