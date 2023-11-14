package dao;

import model.Contratto;
import model.ContrattoBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ContrattoDAO extends BaseDAO<Contratto> {
    // CREATE CRUD APIs
    private static final String CREATE_CONTRATTO = "CREATE TABLE contratti (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "cf_proprietario VARCHAR(255) NOT NULL," +
            "cf_inquilino VARCHAR(255) NOT NULL," +
            "id_immobile INT NOT NULL," +
            "data_inizio VARCHAR(255) NOT NULL," +
            "data_fine VARCHAR(255) NOT NULL," +
            "prossimo_pagamento VARCHAR(255)," +
            "canone FLOAT NOT NULL," +
            "sfratto BOOLEAN NOT NULL, " +
            "proroga BOOLEAN NOT NULL," +
            "FOREIGN KEY (cf_proprietario) REFERENCES utenti(cf) ON DELETE CASCADE," +
            "FOREIGN KEY (cf_inquilino) REFERENCES inquilini(cf) ON DELETE CASCADE ON UPDATE CASCADE," +
            "FOREIGN KEY (id_immobile) REFERENCES immobili(id) ON DELETE CASCADE" +
            ")";
    private static final String CREATE_TRIGGER = "CREATE TRIGGER elimina_inquilino_trigger " +
            "BEFORE DELETE ON contratti " +
            "FOR EACH ROW " +
            "BEGIN " +
            "    DELETE FROM inquilini WHERE inquilini.cf = OLD.cf_inquilino; " +
            "END;";
    // INSERT CRUD API
    private static final String INSERT_CONTRATTO = "INSERT INTO contratti (cf_proprietario, cf_inquilino, id_immobile, " +
            "data_inizio, data_fine, prossimo_pagamento, canone, sfratto, proroga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    // DELETE CRUD API
    private static final String DELETE_CONTRATTO = "DELETE FROM contratti WHERE cf_proprietario = ? AND id = ?";
    // UPDATE CRUD APIs
    private static final String UPDATE_DATA_INIZIO = "UPDATE contratti SET data_inizio = ? WHERE id = ?";
    private static final String UPDATE_DATA_FINE = "UPDATE contratti SET data_fine = ? WHERE id = ?";
    private static final String UPDATE_PROSSIMO_PAGAMENTO = "UPDATE contratti SET prossimo_pagamento = ? WHERE id = ?";
    private static final String UPDATE_CANONE = "UPDATE contratti SET canone = ? WHERE id = ?";
    private static final String UPDATE_SFRATTO = "UPDATE contratti SET sfratto = ? WHERE id = ?";
    private static final String UPDATE_PROROGA = "UPDATE contratti SET proroga = ? WHERE id = ?";
    // SELECT CRUD APIs
    private static final String SELECT_ALL_CONTRATTI = "SELECT * FROM contratti WHERE cf_proprietario = ?";
    private static final String SELECT_CONTRATTO_BY_ID = "SELECT * FROM contratti WHERE cf_proprietario = ? AND id = ?";
    private static final String SELECT_CONTRATTO_BY_INQUILINO = "SELECT data_inizio, data_fine, prossimo_pagamento FROM " +
            "contratti JOIN inquilini ON cf_proprietario = ? WHERE inquilini.id = ?";

    public ContrattoDAO() {
        createTabella();
    }

    @Override
    public void createTabella() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "contratti", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_CONTRATTO);
                statementCreazione.executeUpdate();
                // aggiungo trigger di eliminazione
                PreparedStatement statement = connection.prepareStatement(CREATE_TRIGGER);
                statement.executeUpdate();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Contratto c) {
        boolean result = false;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(INSERT_CONTRATTO);
            statement.setString(1, c.getCfProprietario());
            statement.setString(2, c.getCfInquilino());
            statement.setInt(3, c.getIdImmobile());
            statement.setString(4, c.getDataInizio());
            statement.setString(5, c.getDataFine());
            statement.setString(6, c.getProssimoPagamento());
            statement.setFloat(7, c.getCanone());
            statement.setBoolean(8, c.isSfratto());
            statement.setBoolean(9, c.isProroga());
            statement.executeUpdate();
            result = true;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Contratto select(int idContratto, String cfProprietario) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_CONTRATTO_BY_ID);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idContratto);
            ResultSet rs = statement.executeQuery();
            ContrattoBuilder builder = new ContrattoBuilder();
            Contratto contratto = null;
            if(rs.next()){
                contratto = builder.cfProprietario(rs.getString("cf_proprietario"))
                        .cfInquilino(rs.getString("cf_inquilino"))
                        .idImmobile(rs.getInt("id_immobile"))
                        .dataInizio(rs.getString("data_inizio"))
                        .dataFine(rs.getString("data_fine"))
                        .prossimoPagamento(rs.getString("prossimo_pagamento"))
                        .canone(rs.getFloat("canone"))
                        .sfratto(rs.getBoolean("sfratto"))
                        .proroga(rs.getBoolean("proroga"))
                        .id(rs.getInt("id"))
                        .build();
            }
          // connection.close();
            return contratto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contratto> selectAll(String cfProprietario) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONTRATTI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            List<Contratto> contratti = new ArrayList<>();

            while (rs.next()) {
                contratti.add(select(rs.getInt("id"), cfProprietario));
            }
            connection.close();
            return contratti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int idContratto, String cfProprietario) {
        boolean result=false;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(DELETE_CONTRATTO);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idContratto);
            result=statement.executeUpdate()>0;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean update(int idContratto, Contratto contratto, String cfProprietario) {
        boolean result = false;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
                if(contratto.getDataInizio() != null) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_DATA_INIZIO);
                    stmt.setString(1, contratto.getDataInizio());
                    stmt.setInt(2, idContratto);
                    result= stmt.executeUpdate()>0;
                }
                if(contratto.getDataFine() != null) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_DATA_FINE);
                    stmt.setString(1, contratto.getDataFine());
                    stmt.setInt(2, idContratto);
                    result= stmt.executeUpdate()>0;
                }
                if(contratto.getProssimoPagamento() != null) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_PROSSIMO_PAGAMENTO);
                    stmt.setString(1, contratto.getProssimoPagamento());
                    stmt.setInt(2, idContratto);
                    result=stmt.executeUpdate()>0;
                }
                if(contratto.getCanone() != 0) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_CANONE);
                    stmt.setFloat(1, contratto.getCanone());
                    stmt.setInt(2, idContratto);
                    result= stmt.executeUpdate()>0;
                }
                if(contratto.isSfratto() != select(idContratto, cfProprietario).isSfratto()) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_SFRATTO);
                    stmt.setBoolean(1, contratto.isSfratto());
                    stmt.setInt(2, idContratto);
                    result=stmt.executeUpdate()>0;
                }
                if(contratto.isProroga() != select(idContratto, cfProprietario).isProroga()) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_PROROGA);
                    stmt.setBoolean(1, contratto.isProroga());
                    stmt.setInt(2, idContratto);
                    result=stmt.executeUpdate()>0;
                }
                connection.close();
        } catch(SQLException e) {
                throw new RuntimeException(e);
        }
        return result;
    }
}
