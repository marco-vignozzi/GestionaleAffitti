package dao;

import model.Contratto;

import java.sql.*;


public class ContrattoDAO extends DatabaseDAO {
    // CRUD API's
    private static final String INSERT_CONTRATTO = "INSERT INTO contratti (cf_proprietario, cf_inquilino, id_immobile, data_inizio, data_fine,canone) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_CONTRATTO = "CREATE TABLE contratti (" +
            "cf_proprietario VARCHAR(255) NOT NULL," +
            "cf_inquilino VARCHAR(255) NOT NULL," +
            "id_immobile VARCHAR(255) NOT NULL," +
            "data_inizio VARCHAR(255) NOT NULL," +
            "data_fine VARCHAR(255) NOT NULL," +
            "canone FLOAT NOT NULL," +
            "FOREIGN KEY (cf_proprietario) REFERENCES utenti(cf)," +
            "FOREIGN KEY (cf_inquilino) REFERENCES inquilini(cf)," +
            "FOREIGN KEY (id_immobile) REFERENCES immobili(id)" +
            ")";
    private static final String SELECT_ALL_CONTRATTI = "SELECT * FROM contratti";

    public ContrattoDAO() {
        connect();
        creaTabella();
    }

    public void creaTabella() {
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "contratti", null);
            if (!resultSet.next()) {
                PreparedStatement statementcreazione = connection.prepareStatement(CREATE_CONTRATTO);
                statementcreazione.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiungiContratto(Contratto c) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_CONTRATTO);
            statement.setString(1, c.getCfProprietario());
            statement.setString(2, c.getCfInquilino());
            statement.setString(3, c.getIdImmobile());
            statement.setString(4, c.getDataInizio());
            statement.setString(5, c.getDataFine());
            statement.setFloat(6, c.getCanone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void visualizzaContratti() {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONTRATTI);
            ResultSet rs = statement.executeQuery();
            mostraTabella(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
