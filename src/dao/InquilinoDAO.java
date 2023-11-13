package dao;

import model.Inquilino;
import model.InquilinoBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class InquilinoDAO extends BaseDAO<Inquilino> {
    // CREATE CRUD API
    private static final String CREATE_INQUILINI = "CREATE TABLE inquilini (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "cf VARCHAR(255) NOT NULL UNIQUE," +
            "nome VARCHAR(255) NOT NULL," +
            "cognome VARCHAR(255) NOT NULL," +
            "data_di_nascita VARCHAR(255) NOT NULL," +
            "città_di_nascita VARCHAR(255) NOT NULL," +
            "residenza VARCHAR(255) NOT NULL," +
            "telefono VARCHAR(255) NOT NULL," +
            "email VARCHAR(255) NOT NULL UNIQUE," +
            "totale_dovuto FLOAT NOT NULL," +
            "totale_pagato FLOAT NOT NULL" +
            ")";
    // INSERT CRUD API
    private static final String INSERT_INQUILINO = "INSERT INTO inquilini" +
            " (cf, nome, cognome, data_di_nascita, città_di_nascita, residenza, telefono, email, totale_dovuto, " +
            "totale_pagato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    // DELETE CRUD API
    private static final String DELETE_INQUILINO = "DELETE FROM inquilini WHERE id = ?";

    // UPDATE CRUD APIs
    private static final String UPDATE_NOME = "UPDATE inquilini SET nome = ? WHERE id = ?";
    private static final String UPDATE_COGNOME = "UPDATE inquilini SET cognome = ? WHERE id = ?";
    private static final String UPDATE_CF = "UPDATE inquilini SET cf = ? WHERE id = ?";
    private static final String UPDATE_DATA_NASCITA = "UPDATE inquilini SET data_di_nascita = ? WHERE id = ?";
    private static final String UPDATE_CITTA_NASCITA = "UPDATE inquilini SET città_di_nascita = ? WHERE id = ?";
    private static final String UPDATE_RESIDENZA = "UPDATE inquilini SET residenza = ? WHERE id = ?";
    private static final String UPDATE_TELEFONO = "UPDATE inquilini SET telefono = ? WHERE id = ?";
    private static final String UPDATE_EMAIL = "UPDATE inquilini SET email = ? WHERE id = ?";
    private static final String UPDATE_TOTALE_DOVUTO = "UPDATE inquilini SET totale_dovuto = ? WHERE id = ?";
    private static final String UPDATE_TOTALE_PAGATO = "UPDATE inquilini SET totale_pagato = ? WHERE id = ?";

    // SELECT CRUD APIs
    private static final String SELECT_ALL_INQUILINI = "SELECT inquilini.id, inquilini.cf, inquilini.nome, inquilini.cognome, " +
            "inquilini.data_di_nascita, inquilini.città_di_nascita, inquilini.residenza, inquilini.telefono, inquilini.email, " +
            "inquilini.totale_dovuto, inquilini.totale_pagato, (totale_dovuto-totale_pagato) as debito FROM inquilini JOIN " +
            "contratti ON cf = cf_inquilino WHERE cf_proprietario = ?";
    private static final String SELECT_INQUILINO_BY_ID = "SELECT * FROM inquilini JOIN contratti ON cf_proprietario = ? " +
            "WHERE inquilini.id = ?";

    public InquilinoDAO() {
        super.connect();
        createTabella();
    }

    public void createTabella() {
        try{
            DatabaseMetaData metadata= connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "inquilini", null);
            if (!resultSet.next()) {
                PreparedStatement statementCreazione = connection.prepareStatement(CREATE_INQUILINI);
                statementCreazione.executeUpdate();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Inquilino i) {
        boolean result = false;
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_INQUILINO);
            statement.setString(1, i.getCf());
            statement.setString(2, i.getNome());
            statement.setString(3, i.getCognome());
            statement.setString(4, i.getDataNascita());
            statement.setString(5, i.getCittàNascita());
            statement.setString(6, i.getResidenza());
            statement.setString(7, i.getTelefono());
            statement.setString(8, i.getEmail());
            statement.setFloat(9, i.getTotaleDovuto());
            statement.setFloat(10, i.getTotalePagato());
            result = statement.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Inquilino> selectAll(String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_INQUILINI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            List<Inquilino> inquilini = new ArrayList<>();
            while (rs.next()) {
                inquilini.add(select(rs.getInt("id"), cfProprietario));
            }
            return inquilini;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int idInquilino, String cfProprietario) {
        boolean result = false;
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_INQUILINO);
            statement.setInt(1, idInquilino);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Inquilino select(int idInquilino, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_INQUILINO_BY_ID);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idInquilino);
            ResultSet rs = statement.executeQuery();
            InquilinoBuilder builder = new InquilinoBuilder();
            Inquilino inquilino = null;
            if(rs.next()){
                inquilino = builder.cf(rs.getString("cf")).nome(rs.getString("nome"))
                        .cognome(rs.getString("cognome")).dataNascita(rs.getString("data_di_nascita"))
                        .cittàNascita(rs.getString("città_di_nascita")).residenza(rs.getString("residenza"))
                        .telefono(rs.getString("telefono")).email(rs.getString("email"))
                        .totaleDovuto(rs.getFloat("totale_dovuto")).totalePagato(rs.getFloat("totale_pagato"))
                        .id(rs.getInt("id"))
                        .build();
            }
            return inquilino;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(int idInquilino, Inquilino inquilino, String cf) {
        boolean result = false;
        try {
            if (inquilino.getNome() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_NOME);
                stmt.setString(1, inquilino.getNome());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getCognome() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_COGNOME);
                stmt.setString(1, inquilino.getCognome());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getCf() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_CF);
                stmt.setString(1, inquilino.getCf());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getDataNascita() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_DATA_NASCITA);
                stmt.setString(1, inquilino.getDataNascita());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0 ;
            }
            if(inquilino.getCittàNascita() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_CITTA_NASCITA);
                stmt.setString(1, inquilino.getCittàNascita());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getResidenza() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_RESIDENZA);
                stmt.setString(1, inquilino.getResidenza());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getTelefono() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_TELEFONO);
                stmt.setString(1, inquilino.getTelefono());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getEmail() != null) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_EMAIL);
                stmt.setString(1, inquilino.getEmail());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getTotaleDovuto() != -1) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_TOTALE_DOVUTO);
                stmt.setFloat(1, inquilino.getTotaleDovuto());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
            if(inquilino.getTotalePagato() != -1) {
                PreparedStatement stmt = connection.prepareStatement(UPDATE_TOTALE_PAGATO);
                stmt.setFloat(1, inquilino.getTotalePagato());
                stmt.setInt(2, idInquilino);
                result = stmt.executeUpdate() > 0;
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return  result;
    }

}
