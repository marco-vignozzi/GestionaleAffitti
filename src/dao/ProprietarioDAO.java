package dao;

import model.Proprietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProprietarioDAO extends DatabaseDAO {
    private static final String INSERT_USER = "INSERT INTO utenti" +
            " (cf,nome, cognome,mail,password) VALUES " + " (?, ?, ?, ?, ?);";

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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM utenti WHERE mail = '" + email + "'");  // join ON email?

            return !resultSet.next();  // ritorna true se non ci sono elementi nella tabella

        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return false;
    }
}
