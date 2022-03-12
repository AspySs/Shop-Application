package main;
import bdCreator.Conn;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
        try {
            Conn.getInstance();
            Conn.writeDB();
            Conn.readDB();
            Conn.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
