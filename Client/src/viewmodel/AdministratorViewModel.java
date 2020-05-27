package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientModel;
import model.Order;
import model.User;
import view.administrator.OrderTableRowData;
import view.administrator.UserTableRowData;

public class AdministratorViewModel {
    private ClientModel model;

    public AdministratorViewModel(ClientModel model) {
        this.model = model;
    }

    public ObservableList<UserTableRowData> getAccountsList() {
        ObservableList<UserTableRowData> list = FXCollections.observableArrayList();
        for (User user: model.getAccountList()) {
            list.add(new UserTableRowData(user));
        }
        return list;
    }

    public ObservableList<OrderTableRowData> getOrdersList() {
        ObservableList<OrderTableRowData> list = FXCollections.observableArrayList();
        for (Order order: model.getOrderList()) {
            list.add(new OrderTableRowData(order));
        }
        return list;
    }

    public void logOut() { model.logOut(); }

    public void addAccount() { model.addAccount(); }

    public void deleteAccount() { model.deleteAccount(); }

    public void deleteOrder() { model.deleteOrder(); }
}
