package dao;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Immobile;

public class ImmobileDAO extends DatabaseDAO {
    // CRUD API's
    private static final String INSERT_IMMOBILE = "INSERT INTO immobili" +
            " (comune, foglio, particella, subalterno, categoria, classe, superficie, rendita, cf_proprietario, indirizzo, nCivico , affittato) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String CREATE_IMMOBILE = "CREATE TABLE immobili (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "comune VARCHAR(255) NOT NULL," +
            "foglio VARCHAR(255) NOT NULL," +
            "particella VARCHAR(255) NOT NULL," +
            "subalterno VARCHAR(255) NOT NULL," +
            "categoria VARCHAR(255) NOT NULL," +
            "classe VARCHAR(255) NOT NULL," +
            "superficie VARCHAR(255) NOT NULL," +
            "rendita FLOAT NOT NULL," +
            "cf_proprietario VARCHAR(255) NOT NULL," +
            "indirizzo VARCHAR(255) NOT NULL," +
            "nCivico VARCHAR(255) NOT NULL," +
            "affittato BOOLEAN NOT NULL," +
            "FOREIGN KEY (cf_proprietario) REFERENCES utenti(cf)" +
            ")";

    private static final String SELECT_ALL_IMMOBILI = "SELECT comune, indirizzo, nCivico, affittato FROM immobili";

    public ImmobileDAO() {
        connect();
        creaTabella();
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
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO: implementare con il builder pattern la possibilità di visualizzare caratteristiche immobili diverse


    public void visualizzaImmobili() {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IMMOBILI);
            ResultSet rs = statement.executeQuery();
            mostraTabella(rs);
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
