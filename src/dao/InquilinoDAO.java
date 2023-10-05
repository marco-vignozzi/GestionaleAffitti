package dao;

import model.Inquilino;
import model.Proprietario;
import view.TabellaGUI;

import java.sql.*;


public class InquilinoDAO extends DatabaseDAO{
    // CRUD APIs
    private static final String INSERT_INQUILINO = "INSERT INTO inquilini" +
            " (cf, nome, cognome, data_di_nascita, città_di_nascita, residenza, telefono, email, pagato) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ALL_INQUILINI = "SELECT inquilini.cf, inquilini.nome, inquilini.cognome, data_di_nascita, " +
            "residenza, telefono, inquilini.email, pagato FROM inquilini JOIN contratti JOIN utenti ON " +
            "cf_proprietario=utenti.cf WHERE utenti.cf = ?;";
    private static final String CREATE_INQUILINI = "CREATE TABLE inquilini (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "cf VARCHAR(255) NOT NULL UNIQUE," +
            "nome VARCHAR(255) NOT NULL," +
            "cognome VARCHAR(255) NOT NULL," +
            "data_di_nascita VARCHAR(255) NOT NULL," +
            "città_di_nascita VARCHAR(255) NOT NULL," +
            "residenza VARCHAR(255) NOT NULL," +
            "telefono VARCHAR(255) NOT NULL," +
            "email VARCHAR(255) NOT NULL UNIQUE," + // UNIQUE per non avere due utenti con la stessa mail (serve il metodo?)
            "pagato BOOLEAN NOT NULL" +
            ")";

    private static final String DELETE_INQUILINO = "DELETE FROM inquilini WHERE id = ?";

    // attributo che tiene un riferimento alla vista della tabella
    private TabellaGUI tabella = null;


    public InquilinoDAO() {
        super.connect();
        creaTabella();
        tabella = new TabellaGUI("Inquilini");
    }

    public void creaTabella() {
        try{
            DatabaseMetaData metadata= connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "inquilini", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_INQUILINI);
                statementCreazione.executeUpdate();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void aggiungiInquilino(Inquilino i, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_INQUILINO);
            statement.setString(1, i.getCf());
            statement.setString(2, i.getNome());
            statement.setString(3, i.getCognome());
            statement.setString(4, i.getDataNascita());
            statement.setString(5, i.getCittàNascita());
            statement.setString(6, i.getResidenza());
            statement.setString(7, i.getTelefono());
            statement.setString(8, i.getEmail());
            statement.setBoolean(9, i.isPagato());
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllInquilini(cfProprietario));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllInquilini(String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_INQUILINI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            return rs;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void visualizzaInquilini(String cfProprietario) {
        try {
            tabella.mostraTabella(getAllInquilini(cfProprietario));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rimuoviInquilino(int id, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_INQUILINO);
            statement.setInt(1, id);
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllInquilini(cfProprietario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void MdoificaInquilino(String id) {

    }

}
