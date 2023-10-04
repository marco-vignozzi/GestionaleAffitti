package dao;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Immobile;
import view.TabellaGUI;

public class ImmobileDAO extends DatabaseDAO {
    // CRUD API's
    private static final String INSERT_IMMOBILE = "INSERT INTO immobili" +
            " (comune, foglio, particella, subalterno, categoria, classe, superficie, rendita, cf_proprietario, indirizzo, nCivico , affittato) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String CREATE_IMMOBILE = "CREATE TABLE immobili (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "comune VARCHAR(255) NOT NULL," +
            "foglio INT NOT NULL," +
            "particella INT NOT NULL," +
            "subalterno INT NOT NULL," +
            "categoria VARCHAR(255) NOT NULL," +
            "classe VARCHAR(255) NOT NULL," +
            "superficie_mq FLOAT NOT NULL," +
            "rendita FLOAT NOT NULL," +
            "cf_proprietario VARCHAR(255) NOT NULL," +
            "indirizzo VARCHAR(255) NOT NULL," +
            "n_civico VARCHAR(255) NOT NULL," +
            "affittato BOOLEAN NOT NULL," +
            "FOREIGN KEY (cf_proprietario) REFERENCES utenti(cf)" +
            ")";

    private static final String SELECT_ALL_IMMOBILI = "SELECT immobili.id, comune, indirizzo, n_civico, subalterno, affittato, " +
            "cf_inquilino, canone FROM immobili LEFT JOIN contratti ON id_immobile=immobili.id";

    private TabellaGUI tabella;

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
                tabella.aggiornaTabella(getAllImmobili());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO: implementare con il builder pattern la possibilità di visualizzare caratteristiche immobili diverse


    public ResultSet getAllImmobili() {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IMMOBILI);
            ResultSet rs = statement.executeQuery();
            return rs;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void visualizzaImmobili() {
        try {
            tabella.mostraTabella(getAllImmobili());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
