package lk.ijse.dep7.pos.db;

import java.sql.Connection;

public class DBConnection {

    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public static void setConnection(Connection connection){
        connectionThreadLocal.set(connection);
    }

    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }

}
