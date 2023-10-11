package dao;

import model.Inquilino;
import view.TabellaGUI;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class InquilinoDAO extends DatabaseDAO {
    // CRUD APIs
    private static final String INSERT_INQUILINO = "INSERT INTO inquilini" +
            " (cf, nome, cognome, data_di_nascita, città_di_nascita, residenza, telefono, email, totale_dovuto, " +
            "totale_pagato, deve_pagare) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//    private static final String SELECT_ALL_INQUILINI = "SELECT inquilini.id, inquilini.cf, inquilini.nome, " +
//            "inquilini.cognome, data_di_nascita, residenza, telefono, inquilini.email, (totale_dovuto-totale_pagato) " +
//            "as debito, data_inizio, data_fine, prossimo_pagamento, canone FROM inquilini JOIN contratti ON " +
//            "cf_inquilino = inquilini.cf JOIN utenti ON cf_proprietario=utenti.cf  WHERE utenti.cf = ?;";
    private static final String SELECT_ALL_INQUILINI = "SELECT inquilini.* FROM inquilini JOIN contratti ON cf = cf_inquilino " +
            "WHERE cf_proprietario = ?";
    private static final String SELECT_INQUILINO_BY_ID = "SELECT * FROM inquilini JOIN contratti ON cf_proprietario = ? " +
            "WHERE inquilini.id = ?";
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
            "totale_pagato FLOAT NOT NULL," +
            "deve_pagare BOOLEAN NOT NULL" +
            ")";
    private static final String DELETE_INQUILINO = "DELETE FROM inquilini WHERE id = ?";
    private static final String UPDATE_PAGATO = "UPDATE inquilini SET totale_pagato = ? WHERE id = ?";
    private static final String UPDATE_DOVUTO = "UPDATE inquilini SET totale_dovuto = ?, deve_pagare = false WHERE id = ?";
    // attributo che tiene un riferimento alla vista della tabella
    public TabellaGUI tabella = null;

    public InquilinoDAO() {
        super.connect();
        creaTabella();
        tabella = new TabellaGUI("Inquilini");
    }

    public void creaTabella() {
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

    public void aggiungiInquilino(Inquilino i, String cfProprietario) {
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
            statement.setBoolean(11, i.isDevePagare());
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllInquilini(cfProprietario));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllInquilini(String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_INQUILINI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            return rs;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void visualizzaInquilini(String cfProprietario) {
        try {
            tabella.mostraTabella(getAllInquilini(cfProprietario));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rimuoviInquilino(int idInquilino, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_INQUILINO);
            statement.setInt(1, idInquilino);
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllInquilini(cfProprietario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificaInquilino(int idInquilino, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_INQUILINO_BY_ID);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idInquilino);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Inquilino getInquilino(int idInquilino, String cfProprietario) {
        if (connection == null) {
            connect();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_INQUILINO_BY_ID);
            statement.setString(1, cfProprietario);
            statement.setInt(2, idInquilino);
            ResultSet rs = statement.executeQuery();
            Inquilino inquilino = null;
            if(rs.next()){
               inquilino = new Inquilino(rs.getString("cf"), rs.getString("nome"),
                       rs.getString("cognome"), rs.getString("data_di_nascita"), rs.getString("città_di_nascita"),
                       rs.getString("residenza"), rs.getString("telefono"), rs.getString("email"));
               inquilino.setTotaleDovuto(rs.getFloat("totale_dovuto"));
               inquilino.setTotalePagato(rs.getFloat("totale_pagato"));
               inquilino.setDevePagare(rs.getBoolean("deve_pagare"));
            }
            return inquilino;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // questo metodo ritorna true se l'inquilino deve pagare il mese
    // allora il controller provvederà ad aggiornare il totale_dovuto
    public void aggiungiPagamento(int idInquilino, float pagamento, String cfProprietario) {
        Inquilino inquilino = getInquilino(idInquilino, cfProprietario);
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PAGATO);
            statement.setFloat(1, inquilino.getTotalePagato() + pagamento);
            statement.setInt(2, idInquilino);
            if(statement.executeUpdate()>0) {
                tabella.aggiornaTabella(getAllInquilini(cfProprietario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiornaFinanze(String cfProprietario) {
        try{
            PreparedStatement statement = connection.prepareStatement(SELECT_INQUILINI_AND_CONTRATTI);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if(rs.getBoolean("deve_pagare")) {
                    statement = connection.prepareStatement(UPDATE_DOVUTO);
                    statement.setFloat(1, rs.getFloat("totale_dovuto") + rs.getFloat("canone"));
                    statement.setInt(2, rs.getInt("inquilini.id"));
                    statement.executeUpdate();
                }
            }
            tabella.aggiornaTabella(getAllInquilini(cfProprietario));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
