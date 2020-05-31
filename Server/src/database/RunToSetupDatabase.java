package database;

public class RunToSetupDatabase {
    public static void main(String[] args) {
        Database database = new Database_Implementation();
        database.deleteAllData();
        database.setupStructure();
        database.loadSampleData();
        database.close();
    }
}
