package database;

import java.sql.*;

public class CreateDatabase {
    public static void main (String[] args) {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/SEP2";
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
            connection = DriverManager.getConnection (url,user, "");
            //2. Create a statement
            PreparedStatement statement = connection.prepareStatement ("SELECT * FROM Users");
            //3. Execute SQL Query
            ResultSet resultSet = statement.executeQuery ();
            //4. Process the result test
            while (resultSet.next ()){
                System.out.println (resultSet.getString ("username") + ", " + resultSet.getString ("fullName"));
            }
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
