package models;

import java.sql.*;

public class Database implements AutoCloseable {
    private Connection connection;
    private String dbFile;
    private String dbUrl;
    private String tableName;

    public Database(Model model) throws SQLException {
        this.dbFile = model.getScoreDatabase();
        this.dbUrl = "jdbc:sqlite:" + dbFile; //Andmebaasiga ühendumiseks
        this.tableName = model.getScoreTable();

        //Alustame kohe ühendusega
        connect();
        ensureTableExists();
    }

    private void ensureTableExists() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "time INTEGER NOT NULL," +
                "clicks INTEGER," +
                "board_size INTEGER," +
                "game_time TEXT);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql); //Loob tabeli kui pole
            System.out.println("Tabel kontrollitud/loodud: " + tableName);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.err.println("Viga tabeli loomisel: " + e.getMessage());
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
        System.out.println("Ühendus loodud: " + dbUrl); //TEST
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Ühendus suletud."); //TEST
            } catch (SQLException e) {
                System.err.println("Viga ühenduse sulgemisel: " + e.getMessage());
            }
        }
    }

    /**
     * Mängija andmete lisamine andembaasi tabelisse
     *
     * @param name      nimi
     * @param time      aeg sekundites
     * @param clicks    klikkide arv
     * @param boardSize mängulaua suurus
     * @param played    mängu kellaaeg
     */
    public void insert(String name, int time, int clicks, int boardSize, String played) {
        String sql = "INSERT INTO " + tableName + "(name, time, clicks, board_size, game_time) VALUES (?, ?, ?, ?, ?);";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setInt(2, time);
            stmt.setInt(3, clicks);
            stmt.setInt(4, boardSize);
            stmt.setString(5, played);
            stmt.executeUpdate(); //Käivitab lisamise
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.err.println("Viga andmete lisamisel: " + e.getMessage());
        }
    }
}
