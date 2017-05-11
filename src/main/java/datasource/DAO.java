package datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private PropertiesLoader propertiesLoader;

    private Connection con;

    public DAO() throws IOException, ClassNotFoundException {
        propertiesLoader = new PropertiesLoader("database.properties");
        Class.forName(propertiesLoader.getProperty("driver"));
    }

    public boolean canConnect() throws SQLException {
        checkDbConnection();
        if(con != null){
            return true;
        }
        return false;
    }


    private void checkDbConnection() throws SQLException {
        if(con != null) {
            if (con.isClosed()) {
                con = DriverManager.getConnection(makeConnectionString());
            }
        } else {
            con = DriverManager.getConnection(makeConnectionString());
        }
    }

    private String makeConnectionString(){
        return "jdbc:sqlserver://"
                + propertiesLoader.getProperty("address")
                + ";databaseName="
                + propertiesLoader.getProperty("databaseName")
                + ";user="
                + propertiesLoader.getProperty("username")
                + ";password="
                + propertiesLoader.getProperty("password")
                +";";
    }
}
