package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Immobile;
import model.ImmobileBuilder;


public class ImmobileDAO extends BaseDAO<Immobile> {
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

    public ImmobileDAO() {
        createTabella();
    }

    public void createTabella() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "immobili", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_IMMOBILE);
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

    public boolean insert(Immobile i) {
        boolean result=false;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
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
            result = statement.executeUpdate() > 0;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Immobile> selectAll(String cfProprietario) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IMMOBILI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            List<Immobile> immobili = new ArrayList<>();
            while (rs.next()) {
                immobili.add(select(rs.getInt("id"), cfProprietario));
            }
            connection.close();
            return immobili;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Immobile select(int idImmobile, String cfProprietario) {
        try{
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_IMMOBILE_AND_CONTRATTO_BY_ID);
            statement.setInt(1, idImmobile);
            statement.setString(2, cfProprietario);
            ResultSet rs = statement.executeQuery();
            //connection.close();
            ImmobileBuilder builder = new ImmobileBuilder();
            if(rs.next()) {
                return builder.comune(rs.getString("comune"))
                        .foglio(rs.getInt("foglio"))
                        .particella(rs.getInt("particella"))
                        .subalterno(rs.getInt("subalterno"))
                        .categoria(rs.getString("categoria"))
                        .classe(rs.getString("classe"))
                        .superficie(rs.getFloat("superficie_mq"))
                        .rendita(rs.getFloat("rendita"))
                        .idProprietario(rs.getString("cf_proprietario"))
                        .indirizzo(rs.getString("indirizzo"))
                        .nCivico(rs.getString("n_civico"))
                        .affittato(rs.getBoolean("affittato"))
                        .id(rs.getInt("immobili.id"))
                        .build();
            }
            return null;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int idImmobile, String cfProprietario) {
        boolean result = false;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(DELETE_IMMOBILE);
            statement.setInt(1, idImmobile);
            result = statement.executeUpdate() > 0;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean update(int idImmobile, Immobile immobile,String cf) {
        boolean result = false;
        try{
            if(connection == null || connection.isClosed()) {
                connect();
            }
            if(immobile.getComune() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_COMUNE);
                stmt.setString(1, immobile.getComune());
                stmt.setInt(2, idImmobile);
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getFoglio() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_FOGLIO);
                stmt.setInt(1, immobile.getFoglio());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getParticella() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_PARTICELLA);
                stmt.setInt(1, immobile.getParticella());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getSubalterno() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_SUBALTERNO);
                stmt.setInt(1, immobile.getSubalterno());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getCategoria() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_CATEGORIA);
                stmt.setString(1, immobile.getCategoria());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getClasse() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_CLASSE);
                stmt.setString(1, immobile.getClasse());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getSuperficie() != 0) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_SUPERFICIE);
                stmt.setFloat(1, immobile.getSuperficie());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getRendita() != 0){
                PreparedStatement stmt= connection.prepareStatement(UPDATE_RENDITA);
                stmt.setFloat(1, immobile.getRendita());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getIndirizzo() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_INDIRIZZO);
                stmt.setString(1, immobile.getIndirizzo());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            if(immobile.getnCivico() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_N_CIVICO);
                stmt.setString(1, immobile.getnCivico());
                stmt.setInt(2, idImmobile);
                stmt.executeUpdate();
                result = stmt.executeUpdate() > 0;
            }
            PreparedStatement stmt = connection.prepareStatement(UPDATE_AFFITTATO);
            stmt.setBoolean(1, immobile.isAffittato());
            stmt.setInt(2, idImmobile);
            result = stmt.executeUpdate() > 0;
            connection.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return  result;
    }

}
