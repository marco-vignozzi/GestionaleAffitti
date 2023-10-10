package dao;

import java.sql.*;

import model.Immobile;
import model.ImmobileBuilder;
import view.TabellaGUI;


public class ImmobileDAO extends DatabaseDAO {
    // CRUD API's
    private static final String INSERT_IMMOBILE = "INSERT INTO immobili" +
            " (comune, foglio, particella, subalterno, categoria, classe, superficie_mq, rendita, cf_proprietario, " +
            "indirizzo, n_civico , affittato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_IMMOBILE_BY_ID = "SELECT * FROM immobili WHERE id = ? AND cf_proprietario = ?";
    private static final String SELECT_IMMOBILE = "SELECT id FROM immobili WHERE comune = ? AND indirizzo = ? AND n_civico = ? " +
            "AND subalterno = ? ";
    // TODO: vedere se usare una query che ti seleziona tutti i dati degli immobili e mettere questa come query di "resoconto"
    private static final String SELECT_ALL_IMMOBILI = "SELECT immobili.id, comune, indirizzo, n_civico, subalterno, affittato, " +
            "cf_inquilino, inquilini.nome, inquilini.cognome, canone, debito FROM contratti JOIN inquilini ON " +
            "inquilini.cf = contratti.cf_inquilino RIGHT JOIN immobili ON id_immobile = immobili.id WHERE immobili.cf_proprietario = ?";
    private static final String CREATE_IMMOBILE = "CREATE TABLE immobili (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "comune VARCHAR(255) NOT NULL," +
            "foglio INT," +
            "particella INT," +
            "subalterno INT NOT NULL," +
            "categoria VARCHAR(255)," +
            "classe VARCHAR(255)," +
            "superficie_mq FLOAT," +
            "rendita FLOAT," +
            "cf_proprietario VARCHAR(255) NOT NULL," +
            "indirizzo VARCHAR(255) NOT NULL," +
            "n_civico VARCHAR(255) NOT NULL," +
            "affittato BOOLEAN NOT NULL," +
            "FOREIGN KEY (cf_proprietario) REFERENCES utenti(cf) ON DELETE CASCADE" +
            ")";
    private static final String DELETE_IMMOBILE = "DELETE FROM immobili WHERE id = ?";

    public TabellaGUI tabella;

    public ImmobileDAO() {
        connect();
        creaTabella();
        tabella = new TabellaGUI("Immobili");
    }

    public void creaTabella() {
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "immobili", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_IMMOBILE);
                statementCreazione.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiungiImmobile(Immobile i) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_IMMOBILE);
            statement.setString(1, i.getComune());
            statement.setInt(2, i.getFoglio());
            statement.setInt(3, i.getParticella());
            statement.setInt(4, i.getSubalterno());
            statement.setString(5, i.getCategoria());
            statement.setString(6, i.getClasse());
            statement.setFloat(7, i.getSuperficie());
            statement.setFloat(8, i.getRendita());
            statement.setString(9, i.getIdProprietario());
            statement.setString(10, i.getIndirizzo());
            statement.setString(11, i.getnCivico());
            statement.setBoolean(12, i.isAffittato());
            if(statement.executeUpdate()>0){
                tabella.aggiornaTabella(getAllImmobili(i.getIdProprietario()));
                i.setId(getImmobileID(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllImmobili(String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IMMOBILI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            return rs;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void visualizzaImmobili(String cfProprietario) {
        try {
            tabella.mostraTabella(getAllImmobili(cfProprietario));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getImmobileID(Immobile i) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_IMMOBILE);
            statement.setString(1, i.getComune());
            statement.setString(2, i.getIndirizzo());
            statement.setString(3, i.getnCivico());
            statement.setInt(4, i.getSubalterno());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("id");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificaImmobile(int idImmobile, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_IMMOBILE_BY_ID);
            statement.setInt(1, idImmobile);
            statement.setString(2, cfProprietario);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void rimuoviImmobile(int idImmobile, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_IMMOBILE);
            statement.setInt(1, idImmobile);
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllImmobili(cfProprietario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
