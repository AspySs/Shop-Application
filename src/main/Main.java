package main;
import bdCreator.Conn;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn.conn();
        Conn.createDB();
        Conn.writeDB();
        Conn.readDB();
        Conn.closeDB();
    }
}
