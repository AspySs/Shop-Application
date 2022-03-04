package bdCreator;
import org.sqlite.JDBC;
import java.sql.*;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:shop.sqlite");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws SQLException
    {
        statmt = conn.createStatement();
        if(statmt.executeQuery("PRAGMA foreign_keys;").getInt(1) == 0){
            statmt.execute("PRAGMA foreign_keys = ON;");
        }
        statmt.execute("CREATE TABLE if not exists 'sales' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'amount' DOUBLE, 'quantity' INTEGER, 'sale_date' TIMESTAMP, 'warehouse_id' INTEGER, FOREIGN KEY('warehouse_id') REFERENCES warehouses('id'));");
        statmt.execute("CREATE TABLE if not exists 'warehouses' ('id' INTEGER PRIMARY KEY, 'name' TEXT , 'quantity' INTEGER, 'amount' DOUBLE);");
        statmt.execute("CREATE TABLE if not exists 'charges' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'amount' DOUBLE, 'charge_date' TIMESTAMP, 'expense_item_id' INTEGER, FOREIGN KEY('expense_item_id') REFERENCES expense_items('id'));");
        statmt.execute("CREATE TABLE if not exists 'expense_items' ('id' INTEGER PRIMARY KEY, 'name' text);");
        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        statmt.execute("INSERT INTO 'warehouses' ('name', 'amount', 'quantity') VALUES ('mouse1', 1000, 5); ");
        statmt.execute("INSERT INTO 'warehouses' ('name', 'amount', 'quantity') VALUES ('mic1', 250, 5); ");
        statmt.execute("INSERT INTO 'warehouses' ('name', 'amount', 'quantity') VALUES ('button', 300, 5); ");
        statmt.execute("INSERT INTO 'sales' ('amount', 'quantity', 'sale_date', 'warehouse_id') VALUES (1000, 2, "+timestamp+", 1); ");
       // statmt.execute("INSERT INTO 'sales' ('amount', 'quantity', 'sale_date', 'warehouse_id') VALUES (1000, 2, "+timestamp+", 6); ");


        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM warehouses");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  name = resSet.getString("name");
            int  amount = resSet.getInt("amount");
            int  quantity = resSet.getInt("quantity");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "amount = " + amount );
            System.out.println( "quantity = " + quantity );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}
