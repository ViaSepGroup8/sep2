package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SELECT_FROM {
    public static void main (String[] args) {
        Connection connection = null;
        Statement statement = null;
        try{
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/Sep_Warehouse","postgres","password1234?");
            System.out.println ("Opened Database successfully");
            statement = connection.createStatement ();
            ResultSet resultSet = statement.executeQuery ("SELECT * FROM \"Warehouse\".Users");
            while (resultSet.next ()) {
                String id = resultSet.getString ("username") + ", " + resultSet.getString ("password")+ ", " + resultSet.getString ("role");
                System.out.println (id);
            }
            resultSet.close ();
            statement.close ();
            connection.close ();
        }catch (Exception e){
            System.err.println (e.getClass ().getName () + ":" + e.getMessage ());
            System.exit (0);
        }
        System.out.println ("Database Query OK!");
    }
}
