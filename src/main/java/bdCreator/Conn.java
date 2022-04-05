package bdCreator;
import java.sql.*;

public class Conn {
    public static Statement statmt = null;
    public static ResultSet resSet = null;
    private static Conn instance = null;

    private Conn(Connection con){
        try {
            createDB(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static Conn getInstance(Connection con){
        if(instance == null){
            instance = new Conn(con);
        }
        return instance;
    }
    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static Connection conn() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:shop.sqlite");
    }

    // --------Создание таблицы--------
    private static void createDB(Connection conn) throws SQLException
    {
        if(conn == null){
            System.out.println("Не найдено соединение с БД");
            return;
        }
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
    public static void writeDB(Connection conn) throws SQLException
    {
        if(conn == null){
            System.out.println("Не найдено соединение с БД");
            return;
        }
        if(statmt == null){
            statmt = conn.createStatement();
        }
        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        statmt.execute("INSERT INTO 'warehouses' ('name', 'amount', 'quantity') VALUES ('mouse1', 1000, 5); ");
        statmt.execute("INSERT INTO 'warehouses' ('name', 'amount', 'quantity') VALUES ('mic1', 250, 5); ");
        statmt.execute("INSERT INTO 'warehouses' ('name', 'amount', 'quantity') VALUES ('button', 300, 5); ");
        statmt.execute("INSERT INTO 'sales' ('amount', 'quantity', 'sale_date', 'warehouse_id') VALUES (1000, 2, "+timestamp+", 1); ");
       // statmt.execute("INSERT INTO 'sales' ('amount', 'quantity', 'sale_date', 'warehouse_id') VALUES (1000, 2, "+timestamp+", 6); ");


        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void readDB(Connection conn) throws SQLException
    {
        if(conn == null){
            System.out.println("Не найдено соединение с БД");
            return;
        }
        if(statmt == null){
            statmt = conn.createStatement();
        }
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

    public static void printTable(Connection conn, String tableName){
        String sql = "SELECT * FROM "+tableName;
        try (PreparedStatement statement = conn.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();
            System.out.println("------------------"+tableName+"----------------------");
            while (result.next()){
                switch (tableName){
                    case "sales":
                        System.out.println( "ID = " + result.getInt("id") );
                        System.out.println( "amount = " + result.getInt("amount") );
                        System.out.println( "quantity = " + result.getInt("quantity") );
                        System.out.println( "sale_date = " + result.getTimestamp("sale_date") );
                        System.out.println( "warehouse_id = " + result.getInt("warehouse_id") );
                        System.out.println("----------------------------------------------------");
                        break;
                    case "warehouses":
                        System.out.println( "ID = " + result.getInt("id") );
                        System.out.println( "name = " + result.getString("name") );
                        System.out.println( "quantity = " + result.getInt("quantity") );
                        System.out.println( "amount = " + result.getInt("amount") );
                        System.out.println("----------------------------------------------------");
                        break;
                    case "charges":
                        System.out.println( "ID = " + result.getInt("id") );
                        System.out.println( "amount = " + result.getInt("amount") );
                        System.out.println( "charge_date = " + result.getInt("charge_date") );
                        System.out.println( "expense_item_id = " + result.getInt("warehouse_id") );
                        System.out.println("----------------------------------------------------");
                        break;
                    case "expense_items":
                        System.out.println( "ID = " + result.getInt("id") );
                        System.out.println( "name = " + result.getString("name") );
                        System.out.println("----------------------------------------------------");
                        break;
                    default:
                        System.out.println("Error invalid table_name ?");
                }
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("Invalid table name");
        }
    }

    public static boolean addToTable(Connection conn, String tableName){
        return true;
    }
    // --------Закрытие--------
/*
    public static void closeDB() throws SQLException
    {
        if((conn == null)&&(statmt == null)&&(resSet == null)) {
            System.out.println("Не найдено открытых соединений!");
        } else {
            if (conn != null) {
                conn.close();
            }
            if (statmt != null) {
                statmt.close();
            }
            if (resSet != null) {
                resSet.close();
            }
            System.out.println("Все соединения закрыты!");
        }
    }
*/

}
