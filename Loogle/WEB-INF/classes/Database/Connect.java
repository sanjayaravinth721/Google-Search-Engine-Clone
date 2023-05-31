package Database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Connect {
    String driver = "org.postgresql.Driver";
    String connectionString = "jdbc:postgresql://localhost:5432/loogle";
    String username = "postgres";
    String password = "Sanjay@1206";
    private Connection connection;

    private static Connect connect = null;

    private Connect() {
        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(connectionString, username, password);
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(connectionString, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    public static Connect getInstance() {
        if (connect == null) {
            connect = new Connect();
        }
        return connect;
    }

}
