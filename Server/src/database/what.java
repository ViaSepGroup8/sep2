package database;

import model.UserType;

public class what
{
    public static void main(String[] args) {
        Database database;
        database = new Database_Implementation();
        database.deleteAllData();
        database.setupStructure();
        database.connect();

        // create 5 user
        database.addUser("jan", "Jan Lishak", UserType.CUSTOMER, "jan123");
        database.addUser("juan", "Juan Trebolle", UserType.CUSTOMER, "juan123");
        database.addUser("joao", "João Bernando Dias", UserType.ADMIN, "joao123");
        database.addUser("lenka", "Lenka Orinčáková", UserType.PICKER, "lenka123");
        database.addUser("javier", "Javier Candeira", UserType.DRIVER, "javier123");

        database.close();
    }
}
