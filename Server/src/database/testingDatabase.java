package database;

import model.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class testingDatabase {
    public static void main (String[] args) {
        Database database = new Database_Implementation ();
        User jano = new User ("jano", UserType.CUSTOMER);
        User javi = new User ("javi", "Javier Candeira", UserType.PICKER);
        User juan = new User ("juan", "Juan Trebolle", UserType.CUSTOMER);
        User joao = new User ("joao", "Joao Bernardo Baptista Vieira Dias", UserType.DRIVER);
        ArrayList<Item> items = new ArrayList<> ();
        items.add (new Item (1, "apple", 2, Location.fromString ("D40"), 7));
        items.add (new Item (2, "bananas", 60, Location.fromString ("D38"), 4));
        items.add (new Item (3, "chocolate", 52, Location.fromString ("D40"), (int) 7.80));
        //database.getAllWarehouseItems ();
        //database.getAllOrders();
        //database.getUserOrders (new User ("juan","Juan Trebolle", UserType.CUSTOMER));
        // database.addJob (new Job ("2","2",null));
       /* try {
            database.addOrder (new Order (jano, OrderStatus.JOBS_DIVIDED,"3","BC3","Slovenia"));
        } catch (InvalidDatabaseRequestException e) {
            e.printStackTrace ();
        }
    */
        //database.getAllUsers();
        // database.getUser ("juan","contraseñaxd");
        //database.getNewJob ();
        // database.completeJob (javi,new Job ("1","1",items));
       /* try {
            database.getJobByUser (juan);
        } catch (InvalidDatabaseRequestException e) {
            e.printStackTrace ();
        }*/
       /* try {
            database.getNewPickupOrder (joao);
        } catch (InvalidDatabaseRequestException e) {
            e.printStackTrace ();
        }
    */
    }
}