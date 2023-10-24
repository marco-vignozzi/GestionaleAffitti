package dao;

import java.sql.*;


public abstract class DatabaseDAO {
    protected static final String SELECT_INQUILINI_AND_CONTRATTI = "SELECT * FROM inquilini JOIN contratti ON cf = cf_inquilino " +
            "WHERE cf_proprietario = ?";
    protected static final String SELECT_RESOCONTO = "SELECT immobili.id AS id_immobile, immobili.comune, immobili.indirizzo, " +
            "immobili.n_civico, immobili.subalterno, immobili.affittato, inquilini.nome, inquilini.cognome, inquilini.email, " +
            "(inquilini.totale_dovuto-inquilini.totale_pagato) AS debito, contratti.data_fine, contratti.prossimo_pagamento," +
            "contratti.canone, contratti.sfratto, contratti.proroga FROM contratti JOIN inquilini ON cf_inquilino = inquilini.cf " +
            "RIGHT JOIN immobili ON immobili.id = id_immobile WHERE immobili.cf_proprietario = ? " +
            "ORDER BY immobili.comune ASC, immobili.indirizzo ASC, immobili.n_civico ASC, immobili.subalterno ASC;";
    protected static final String UPDATE_DEVE_PAGARE = "UPDATE inquilini SET deve_pagare = true WHERE id = ?";
    protected Connection connection = null;

    public void connect() {
        System.out.println("Connessione al database...");
        try{
            //Verifico che i Driver ci siano nelle External Libraries
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Creo la connessione
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gestionale_affitti", "root","");
            System.out.println("Database connesso!");

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Errore di connessione al database: " + e.getMessage()); //e.printStackTrace();
        }
    }

    protected abstract void createTabella();

}
