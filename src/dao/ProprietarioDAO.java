package dao;

import model.Proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO extends BaseDAO<Proprietario> {

    // CREATE CRUD API
    private static final String CREATE_UTENTI = "CREATE TABLE utenti (" +
            "cf VARCHAR(255) NOT NULL PRIMARY KEY," +
            "nome VARCHAR(255) NOT NULL," +
            "cognome VARCHAR(255) NOT NULL," +
            "email VARCHAR(255) NOT NULL UNIQUE," + // UNIQUE per non avere due utenti con la stessa mail (serve il metodo?)
            "password VARCHAR(255) NOT NULL" +
            ")";
    // INSERT CRUD API
    private static final String INSERT_USER = "INSERT INTO utenti" +
            " (cf, nome, cognome, email, password) VALUES " + " (?, ?, ?, ?, ?);";
    // DELETE CRUD API
    private static final String DELETE = "DELETE FROM utenti WHERE cf = ?";
    // UPDATE CRUD API
    private static final String UPDATE_USER = "UPDATE utenti SET  nome = ?, cognome = ?, email = ?, password = ? WHERE cf = ?";
    // SELECT CRUD APIs
    private static final String SELECT_ALL_USERS = "SELECT * FROM utenti";
    private static final String SELECT_USER = "SELECT * FROM utenti WHERE email = ? and password = ?";

    public ProprietarioDAO() {
        createTabella();
    }

    public void createTabella(){
        try{
            if (connection == null || connection.isClosed()) {
                connect();
            }
            DatabaseMetaData metadata= connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "utenti", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_UTENTI);
                statementCreazione.executeUpdate();
            }
            connection.close();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Proprietario p) {
        boolean result = false;
        try{
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, p.getCf());
            statement.setString(2, p.getNome());
            statement.setString(3, p.getCognome());
            statement.setString(4, p.getEmail());
            statement.setString(5, p.getPassword());
            statement.executeUpdate();
            result = true;
            connection.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Proprietario> selectAll(String cf) {
        try{
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = statement.executeQuery();
            List<Proprietario> proprietari = new ArrayList<>();

            while (rs.next()){
                proprietari.add(select(rs.getString("email"), rs.getString("password")));
            }
            connection.close();
            return proprietari;
        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return null;
    }

    public Proprietario select(int id, String cf){
        // serve solo a dare un'implementazione del metodo astratto della superclasse
        return null;
    }

    public Proprietario select(String email, String password) {
        try{
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            rs.next();

            // creo il proprietario con i campi del result set
            Proprietario p = new Proprietario(rs.getString("email"), rs.getString("password"), rs.getString("nome"),
                    rs.getString("cognome"), rs.getString("cf"));
     //       connection.close();
            return p;
        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return null; // se avviene qualche problema ritorno null

    }

    public boolean update(int id, Proprietario p, String cf){
        boolean result=false;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement stmt = connection.prepareStatement(UPDATE_USER);
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCognome());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getPassword());
            stmt.setString(5, cf);
            result = stmt.executeUpdate()>0;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean delete(int id, String cf){
        boolean result=false;
        try{
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setString(1, cf);
            result = statement.executeUpdate()>0;
            connection.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return result;
    }

}

