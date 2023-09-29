package dao;

import java.sql.*;


public class DatabaseDAO {
    protected Connection connection = null;
/*
    // CRUD API per creare le tabelle la prima volta

    public static final String CREATE_UTENTI = "CREATE TABLE IF NOT EXISTS utenti " +
            "    id INT AUTO_INCREMENT PRIMARY KEY, " +
            "    nome VARCHAR(255)," +
            "    cognome VARCHAR(255)," +
            "    eta INT" +
            ");";
    public static final String CREATE_INQUILINI = "";
    public static final String CREATE_IMMOBILI = "";
    public static final String CREATE_CONTRATTI = "";

    public DatabaseDAO() {
        connect();
        /*DatabaseMetaData dbm = null;
        try {
        /*
            dbm = connection.getMetaData();
            // controllo se esiste la tabella "utenti"
            ResultSet tables = dbm.getTables(null, null, "utenti", null);
            if (!tables.next()) {
                // se non esiste la creo
                PreparedStatement stmt = connection.prepareStatement(CREATE_UTENTI);
                stmt.executeQuery();
            }

            PreparedStatement stmt = connection.prepareStatement(CREATE_UTENTI);
            stmt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
*/
    public void connect() {
        System.out.println("Connessione al database...");
        try{
            //Verifico che i Driver ci siano nelle External Libraries
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Creo la connessione
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gestionale_affitti", "root","");
            System.out.println("Database connesso!");

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Errore di connessione al database: " + e.getMessage()); //e.printStackTrace();
        }
    }

    // ritorna true se l'email è presente, false altrimenti




}