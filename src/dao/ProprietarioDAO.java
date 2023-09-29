package dao;

import model.Proprietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProprietarioDAO extends DatabaseDAO {

    // CRUD APIs
    private static final String INSERT_USER = "INSERT INTO utenti" +
            " (cf, nome, cognome, email, password) VALUES " + " (?, ?, ?, ?, ?);";             // FIXME: cambia mail in email, dhaaai
    private static final String SELECT_ALL_USERS_WITH_MAIL = "SELECT * FROM utenti WHERE email = ?";        // CAMBIALA DHAAAAAI!
    private static final String SELECT_USER = "SELECT * FROM utenti WHERE email = ? and password = ?";

    public ProprietarioDAO() {
        super.connect();
    }

    public void aggiungiUtente(Proprietario p) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            System.out.println(p.getCf());
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

    public Proprietario getUtente(String email, String password) {
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            // creo il proprietario con i campi del result set
            Proprietario p = new Proprietario(rs.getString("cf"), rs.getString("nome"), rs.getString("cognome"),
                    rs.getString("email"), rs.getString("password"));

            return p;

        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return null; // se avviene qualche problema ritorno null
    }
}
