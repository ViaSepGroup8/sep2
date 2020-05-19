package database;

import java.sql.*;

public class Insert_Into {
    public static void main (String[] args) {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/Sep_Warehouse";
        String user = "postgres";
       // String password = PasswordContainer.getPassword();

        Connection connection = null;
        try {
            Class.forName (driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace ();
        }
        try {
            //1.Get a connection to database
            connection = DriverManager.getConnection (url,user, "password1234?");
            //2. Create a statement
            Statement statement = connection.createStatement ();

            //3. Execute SQL Query
            ResultSet resultSet = statement.executeQuery ("INSERT INTO \"Warehouse\".Users VALUES('juan','Juan Trebolle', 'contraseñaxd', '1')");
            //4. Process the result test

        }catch (SQLException e){
            e.printStackTrace ();
        }
        /*String sql = "CREATE SCHEMA IF NOT EXISTS \"Users\";";
        try {
            Statement statement = connection.createStatement ();
            statement.execute (sql);
        }catch (SQLException e){
            e.printStackTrace ();
        }*/

    }

}
