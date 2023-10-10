package dao;

import model.Contratto;
import view.TabellaGUI;

import java.sql.*;


public class ContrattoDAO extends DatabaseDAO {
    public TabellaGUI tabella= null;

    // CRUD API's
    private static final String INSERT_CONTRATTO = "INSERT INTO contratti (cf_proprietario, cf_inquilino, id_immobile, data_inizio, data_fine,canone) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_CONTRATTO = "CREATE TABLE contratti (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "cf_proprietario VARCHAR(255) NOT NULL," +
            "cf_inquilino VARCHAR(255) NOT NULL," +
            "id_immobile INT NOT NULL," +
            "data_inizio VARCHAR(255) NOT NULL," +
            "data_fine VARCHAR(255) NOT NULL," +
            "canone FLOAT NOT NULL," +
            "FOREIGN KEY (cf_proprietario) REFERENCES utenti(cf) ON DELETE CASCADE," +
            "FOREIGN KEY (cf_inquilino) REFERENCES inquilini(cf) ON DELETE CASCADE," +
            "FOREIGN KEY (id_immobile) REFERENCES immobili(id) ON DELETE CASCADE" +
            ")";
    private static final String SELECT_ALL_CONTRATTI = "SELECT * FROM contratti WHERE cf_proprietario = ?";

    public ContrattoDAO() {
        connect();
        creaTabella();
        tabella = new TabellaGUI("Contratti");
    }

    public void creaTabella() {
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "contratti", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_CONTRATTO);
                statementCreazione.executeUpdate();
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
            statement.setInt(3, c.getIdImmobile());
            statement.setString(4, c.getDataInizio());
            statement.setString(5, c.getDataFine());
            statement.setFloat(6, c.getCanone());
            if( statement.executeUpdate()>0){
                tabella.aggiornaTabella(getAllContratti(c.getCfProprietario()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllContratti(String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONTRATTI);
            statement.setString(1, cfProprietario);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void visualizzaContratti(String cfProprietario) {
       try{
           tabella.mostraTabella(getAllContratti(cfProprietario));
       }catch (Exception e){
           throw new RuntimeException(e);
       }
    }

}
