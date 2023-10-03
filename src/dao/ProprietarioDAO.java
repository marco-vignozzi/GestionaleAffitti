package dao;

import model.Proprietario;

import java.sql.*;

public class ProprietarioDAO extends DatabaseDAO {

    // DDL CRUD APIs
    private static final String INSERT_USER = "INSERT INTO utenti" +
            " (cf, nome, cognome, email, password) VALUES " + " (?, ?, ?, ?, ?);";
    private static final String SELECT_ALL_USERS_WITH_MAIL = "SELECT * FROM utenti WHERE email = ?";
    private static final String SELECT_USER = "SELECT * FROM utenti WHERE email = ? and password = ?";

    // DML CRUD APIs
    private static final String CREATE_UTENTI = "CREATE TABLE utenti (" +
            "cf VARCHAR(255) NOT NULL PRIMARY KEY ," +
            "nome VARCHAR(255) NOT NULL," +
            "cognome VARCHAR(255) NOT NULL," +
            "email VARCHAR(255) NOT NULL UNIQUE," + // UNIQUE per non avere due utenti con la stessa mail (serve il metodo?)
            "password VARCHAR(255) NOT NULL" +
            ")";


    public ProprietarioDAO() {
        super.connect();
        creaTabella();
    }


    public void creaTabella(){
        try{
            DatabaseMetaData metadata= connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "utenti", null);
            if (!resultSet.next()) {
                PreparedStatement statementcreazione = connection.prepareStatement(CREATE_UTENTI);
                statementcreazione.executeUpdate();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void aggiungiUtente(Proprietario p) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, p.getCf());
            statement.setString(2, p.getNome());
            statement.setString(3, p.getCognome());
            statement.setString(4, p.getEmail());
            statement.setString(5, p.getPassword());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean emailDisponibile(String email){
        if(connection == null){
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_WITH_MAIL);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            return !rs.next();  // ritorna true se non ci sono elementi nella tabella

        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return false;
    }


    public boolean verificaUtente(String email, String password) {
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            return rs.next();  // ritorna true se ci sono elementi nella tabella

        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return false;
    }

    public Proprietario getUtente(String email, String password) {          // FIXME: non funziona perchè boh
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            rs.next();

            // creo il proprietario con i campi del result set
            Proprietario p = new Proprietario(rs.getString("email"), rs.getString("password"), rs.getString("nome"),
                    rs.getString("cognome"), rs.getString("cf"));

            return p;

        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return null; // se avviene qualche problema ritorno null

    }
}

