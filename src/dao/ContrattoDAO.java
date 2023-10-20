package dao;

import model.Contratto;
import model.ContrattoBuilder;
import model.Inquilino;
import model.InquilinoBuilder;
import view.TabellaGUI;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ContrattoDAO extends DatabaseDAO {
    public TabellaGUI tabella= null;

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
                // aggiungo trigger di eliminazione
                PreparedStatement statement = connection.prepareStatement(CREATE_TRIGGER);
                statement.executeUpdate();
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
            statement.setString(6, c.getProssimoPagamento());
            statement.setFloat(7, c.getCanone());
            statement.setBoolean(8, c.isSfratto());
            statement.setBoolean(9, c.isProroga());
            if( statement.executeUpdate()>0){
                tabella.aggiornaTabella(getAllContratti(c.getCfProprietario()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Contratto getContratto(int idContratto, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
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
                        .build();
            }
            return contratto;
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

    public void rimuoviContratto(int idContratto, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CONTRATTO);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idContratto);
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllContratti(cfProprietario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificaContratto(int idContratto, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CONTRATTO_BY_ID);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idContratto);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiornaDatePagamento(String cfProprietario) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_INQUILINI_AND_CONTRATTI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate oggi = LocalDate.now();

            while(rs.next()) {
                if (!rs.getString("prossimo_pagamento").isEmpty()) {
                    LocalDate prossimoPagamento = LocalDate.parse(rs.getString("prossimo_pagamento"), formatter);
                    if (!oggi.isAfter(LocalDate.parse(rs.getString("data_fine"), formatter)) && oggi.isAfter(prossimoPagamento)) {
                        prossimoPagamento = prossimoPagamento.plusMonths(1);
                        statement = connection.prepareStatement(UPDATE_PROSSIMO_PAGAMENTO);
                        statement.setString(1, prossimoPagamento.toString());
                        statement.setInt(2, rs.getInt("contratti.id"));
                        statement.executeUpdate();
                        statement = connection.prepareStatement(UPDATE_DEVE_PAGARE);
                        statement.setInt(1, rs.getInt("inquilini.id"));
                        statement.executeUpdate();
                    }
                }
            }
            tabella.aggiornaTabella(getAllContratti(cfProprietario));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificaContratto(int idContratto, Contratto contratto, String cfProprietario) {
        try {
                if(contratto.getDataInizio() != null) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_DATA_INIZIO);
                    stmt.setString(1, contratto.getDataInizio());
                    stmt.setInt(2, idContratto);
                    stmt.executeUpdate();
                }
                if(contratto.getDataFine() != null) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_DATA_FINE);
                    stmt.setString(1, contratto.getDataFine());
                    stmt.setInt(2, idContratto);
                    stmt.executeUpdate();
                }
                if(contratto.getProssimoPagamento() != null) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_PROSSIMO_PAGAMENTO);
                    stmt.setString(1, contratto.getProssimoPagamento());
                    stmt.setInt(2, idContratto);
                    stmt.executeUpdate();
                }
                if(contratto.getCanone() != 0) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_CANONE);
                    stmt.setFloat(1, contratto.getCanone());
                    stmt.setInt(2, idContratto);
                    stmt.executeUpdate();
                }
                if(contratto.isSfratto() != getContratto(idContratto, cfProprietario).isSfratto()) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_SFRATTO);
                    stmt.setBoolean(1, contratto.isSfratto());
                    stmt.setInt(2, idContratto);
                    stmt.executeUpdate();
                }
                if(contratto.isProroga() != getContratto(idContratto, cfProprietario).isProroga()) {
                    PreparedStatement stmt = connection.prepareStatement(UPDATE_PROROGA);
                    stmt.setBoolean(1, contratto.isProroga());
                    stmt.setInt(2, idContratto);
                    stmt.executeUpdate();
                }
        } catch(SQLException e) {
                throw new RuntimeException(e);
        }
    }
}
