package dao;

import java.sql.*;

import model.Immobile;
import view.TabellaGUI;


public class ImmobileDAO extends DatabaseDAO {
    // CREATE CRUD API's
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
    private static final String CREATE_TRIGGER = "CREATE TRIGGER elimina_contratto_trigger " +
            "BEFORE DELETE ON immobili " +
            "FOR EACH ROW " +
            "BEGIN " +
            "    DELETE FROM contratti WHERE OLD.id = id_immobile; " +
            "END;";
    // INSERT CRUD API
    private static final String INSERT_IMMOBILE = "INSERT INTO immobili" +
            " (comune, foglio, particella, subalterno, categoria, classe, superficie_mq, rendita, cf_proprietario, " +
            "indirizzo, n_civico , affittato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    // DELETE CRUD API
    private static final String DELETE_IMMOBILE = "DELETE FROM immobili WHERE id = ?";
    // UPDATE CRUD API's
    private static final String UPDATE_AFFITTATO = "UPDATE immobili SET affittato = ? WHERE id = ?";
    private static final String UPDATE_COMUNE = "UPDATE immobili SET comune = ? WHERE id = ?";
    private static final String UPDATE_FOGLIO = "UPDATE immobili SET foglio = ? WHERE id = ?";
    private static final String UPDATE_PARTICELLA = "UPDATE immobili SET particella = ? WHERE id = ?";
    private static final String UPDATE_SUBALTERNO = "UPDATE immobili SET subalterno = ? WHERE id = ?";
    private static final String UPDATE_CATEGORIA = "UPDATE immobili SET categoria = ? WHERE id = ?";
    private static final String UPDATE_CLASSE = "UPDATE immobili SET classe = ? WHERE id = ?";
    private static final String UPDATE_SUPERFICIE = "UPDATE immobili SET superficie_mq = ? WHERE id = ?";
    private static final String UPDATE_RENDITA = "UPDATE immobili SET rendita = ? WHERE id = ?";
    private static final String UPDATE_INDIRIZZO = "UPDATE immobili SET indirizzo = ? WHERE id = ?";
    private static final String UPDATE_N_CIVICO = "UPDATE immobili SET n_civico = ? WHERE id = ?";


    // SELECT CRUD API's
    private static final String SELECT_IMMOBILE_AND_CONTRATTO_BY_ID = "SELECT * FROM immobili LEFT JOIN contratti ON immobili.id = id_immobile " +
            "WHERE immobili.id = ? AND immobili.cf_proprietario = ?";
    private static final String SELECT_IMMOBILE = "SELECT id FROM immobili WHERE comune = ? AND indirizzo = ? AND n_civico = ? " +
            "AND subalterno = ? AND cf_proprietario = ?";
    private static final String SELECT_ALL_IMMOBILI = "SELECT id, comune, indirizzo, n_civico, subalterno, affittato, " +
            "foglio, particella, categoria, classe, superficie_mq, rendita FROM immobili WHERE cf_proprietario = ?";

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
                // aggiungo trigger di eliminazione
                PreparedStatement statement = connection.prepareStatement(CREATE_TRIGGER);
                statement.executeUpdate();
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
            statement.setString(5, i.getIdProprietario());
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
            PreparedStatement statement = connection.prepareStatement(SELECT_IMMOBILE_AND_CONTRATTO_BY_ID);
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

    public void aggiornaAffittato(String cfProprietario) {
        try{
            PreparedStatement statement;
            ResultSet allImmobili = getAllImmobili(cfProprietario);
            while(allImmobili.next()) {
                statement = connection.prepareStatement(SELECT_IMMOBILE_AND_CONTRATTO_BY_ID);
                statement.setInt(1, allImmobili.getInt("id"));
                statement.setString(2, cfProprietario);
                ResultSet rs = statement.executeQuery();
                rs.next();

                statement = connection.prepareStatement(UPDATE_AFFITTATO);
                statement.setInt(2, allImmobili.getInt("id"));

                if (allImmobili.getBoolean("affittato") && rs.getInt("contratti.id") == 0) {
                    statement.setBoolean(1, false);
                    statement.executeUpdate();
                } else if (!allImmobili.getBoolean("affittato") && rs.getInt("contratti.id") > 0) {
                    statement.setBoolean(1, true);
                    statement.executeUpdate();
                }
                tabella.aggiornaTabella(getAllImmobili(cfProprietario));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificaImmobile(int i, Immobile immobile) {
        try{
            if(immobile.getComune() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_COMUNE);
                stmt.setString(1, immobile.getComune());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getFoglio() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_FOGLIO);
                stmt.setInt(1, immobile.getFoglio());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getParticella() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_PARTICELLA);
                stmt.setInt(1, immobile.getParticella());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getSubalterno() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_SUBALTERNO);
                stmt.setInt(1, immobile.getSubalterno());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getCategoria() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_CATEGORIA);
                stmt.setString(1, immobile.getCategoria());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getClasse() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_CLASSE);
                stmt.setString(1, immobile.getClasse());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getSuperficie() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_SUPERFICIE);
                stmt.setFloat(1, immobile.getSuperficie());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getRendita() != 0){
                PreparedStatement stmt= connection.prepareStatement(UPDATE_RENDITA);
                stmt.setFloat(1, immobile.getRendita());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getIndirizzo() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_INDIRIZZO);
                stmt.setString(1, immobile.getIndirizzo());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }
            if(immobile.getnCivico() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_N_CIVICO);
                stmt.setString(1, immobile.getnCivico());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean getAffittato(int idImmobile, String cfProprietario) {
        try{
            PreparedStatement stmt = connection.prepareStatement(SELECT_IMMOBILE_AND_CONTRATTO_BY_ID);
            stmt.setInt(1, idImmobile);
            stmt.setString(2, cfProprietario);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            return rs.getBoolean("affittato");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
