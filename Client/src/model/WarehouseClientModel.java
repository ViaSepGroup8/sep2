package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WarehouseClientModel extends Remote {
    void answer(int profession);
}
