package dao;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public abstract class DatabaseDAO {
    protected Connection connection = null;
    // protected static JFrame tabella = null;

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
