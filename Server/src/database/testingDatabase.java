package database;

import model.*;

import java.sql.Connection;
import java.sql.Statement;

public class testingDatabase {
    public static void main (String[] args) {
        Database database = new Database_Implementation ();
        User jano = new User ("jano", UserType.CUSTOMER);
        //database.getAllWarehouseItems ();
        //database.getAllOrders();
        //database.getUserOrders (new User ("juan","Juan Trebolle", UserType.CUSTOMER));
       // database.addJob (new Job ("2","2",null));
        try {
            database.addOrder (new Order (jano, OrderStatus.JOBS_DIVIDED,"3","BC3","Slovenia"));
        } catch (InvalidDatabaseRequestException e) {
            e.printStackTrace ();
        }
    }
}
