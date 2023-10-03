package dao;

import model.Contratto;

import java.sql.*;


public class ContrattoDAO extends DatabaseDAO{
    public ContrattoDAO(){
        connect();
        creaTabella();
    }
    private static final String INSERT_CONTRATTO = "INSERT INTO contratti (id_proprietario, id_inquilino, id_immobile, data_inizio, data_fine,canone) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_CONTRATTO = "CREATE TABLE contratti (" +
            "id_proprietario VARCHAR(255) NOT NULL," +
            "id_inquilino VARCHAR(255) NOT NULL," +
            "id_immobile VARCHAR(255) NOT NULL," +
            "data_inizio DATE NOT NULL," +
            "data_fine DATE NOT NULL," +
            "canone FLOAT NOT NULL," +
            "FOREIGN KEY (id_proprietario) REFERENCES utenti(cf)" +
            //"FOREIGN KEY (id_inquilino) REFERENCES inquilini(cf)," +
            //"FOREIGN KEY (id_immobile) REFERENCES immobili(id)" +
            ")";

    public void creaTabella(){
        try{
            DatabaseMetaData metadata= connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "contratti", null);
            if (!resultSet.next()) {
                PreparedStatement statementcreazione = connection.prepareStatement(CREATE_CONTRATTO);
                statementcreazione.executeUpdate();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void aggiungiContratto(Contratto c){
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_CONTRATTO);
            statement.setString(1,c.getCfProprietario());
            statement.setString(2, c.getCfInquilino());
            statement.setString(3, c.getIdImmobile());
            statement.setString(4, c.getDataInizio());
            statement.setString(5, c.getDataFine());
            statement.setFloat(6, c.getCanone());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
