package viewmodel;

import model.ClientModel;
import model.UserType;
import view.ViewHandler;

public class LoginViewModel {
    private ClientModel model;

    public LoginViewModel(ClientModel model) {
        this.model = model;
    }

    public void login(String username, String password, ViewHandler viewHandler) {
        model.login(username, password);
        UserType userType = model.getUserAccount().getUserType();

        if(userType == null){
            model.fatalError("userType cannot be null");
        }
        switch (userType){
            case ADMIN:
                viewHandler.openView("Administrator");
                return;
            case DRIVER:
                viewHandler.openView("Driver");
                return;
            case PICKER:
                viewHandler.openView("Picker");
                return;
            case CUSTOMER:
                viewHandler.openView("Customer");
                return;
            case UNKNOWN:
                //todo add wrong login message for the view
                model.debugLog("wrong login");
                return;
            default:
                model.debugLog("unrecognized userType: " + userType.name());
        }
    }
}
