package dao;

import java.sql.*;


public abstract class DatabaseDAO {
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
    protected abstract void creaTabella();

}
