package dao;

import model.Resoconto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseDAO<T> {
    protected static final String SELECT_RESOCONTO = "SELECT immobili.id AS id_immobile, immobili.comune, immobili.indirizzo, " +
            "immobili.n_civico, immobili.subalterno, immobili.affittato, inquilini.nome, inquilini.cognome, inquilini.email, " +
            "(inquilini.totale_dovuto-inquilini.totale_pagato) AS debito, contratti.data_fine, contratti.prossimo_pagamento," +
            "contratti.canone, contratti.sfratto, contratti.proroga FROM contratti JOIN inquilini ON cf_inquilino = inquilini.cf " +
            "RIGHT JOIN immobili ON immobili.id = id_immobile WHERE immobili.cf_proprietario = ? " +
            "ORDER BY immobili.comune ASC, immobili.indirizzo ASC, immobili.n_civico ASC, immobili.subalterno ASC;";
    protected Connection connection = null;

    public void connect() {
        try{
            // Verifico che i Driver siano nelle External Libraries
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Creo la connessione
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gestionale_affitti", "root","");

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Errore di connessione al database --> " + e.getMessage()); //e.printStackTrace();
        }
    }


    protected abstract void createTabella();

    public abstract boolean insert(T entità);

    public abstract T select(int id, String cf);
    public abstract List<T> selectAll(String cf);

    public abstract boolean update(int id, T entità, String cf);

    public abstract boolean delete(int id, String cf);

    public List<Resoconto> selectResoconti(String cfProprietario) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            PreparedStatement statement = connection.prepareStatement(SELECT_RESOCONTO);
            statement.setString(1, cfProprietario);
            ResultSet rs = statement.executeQuery();
            List<Resoconto> resoconti = new ArrayList<>();
            while(rs.next()) {
                Resoconto resoconto = new Resoconto(rs.getInt("id_immobile"), rs.getString("comune"),
                        rs.getString("indirizzo"), rs.getString("n_civico"), rs.getInt("subalterno"),
                        rs.getBoolean("affittato"), rs.getString("nome"), rs.getString("cognome"),
                        rs.getString("email"), rs.getFloat("debito"), rs.getString("data_fine"),
                        rs.getString("prossimo_pagamento"), rs.getFloat("canone"), rs.getBoolean("sfratto"),
                        rs.getBoolean("proroga"));
                resoconti.add(resoconto);
            }
            connection.close();
            return resoconti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
