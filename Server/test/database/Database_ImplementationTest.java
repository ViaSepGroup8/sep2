package database;

import model.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Database_ImplementationTest {
    String driver = "org.postgresql.Driver";
    String url = "jdbc:postgresql://localhost:5432/Sep_Warehouse";
    String user = "postgres";

    @Test
    public ArrayList<Item> getAllWarehouseItems(){
        //Arrange
        ArrayList<Item> items = new ArrayList<> ();
        //Act

        //Assert
        return null;
    }
}