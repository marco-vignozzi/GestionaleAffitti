package dao;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


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

    public static void mostraTabella(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int numColonne = metaData.getColumnCount();
        Object[] nomiColonne = new Object[numColonne];

        for (int i=0; i < numColonne; i++) {
            nomiColonne[i] = metaData.getColumnName(i+1);
        }

        JFrame frame = new JFrame("Visualizzazione Tabella");

        // Imposta il layout del frame
        frame.setLayout(new BorderLayout());

        // Imposta il modello per la tabella
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(nomiColonne);

        // Crea una tabella con il modello
        JTable table = new JTable(tableModel);

        // Aggiungi la tabella a uno JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Aggiungi il JScrollPane al frame
        frame.add(scrollPane, BorderLayout.CENTER);

        tableModel.setRowCount(0);

        while(rs.next()) {
            Object[] riga = new Object[numColonne];
            for (int i=0; i < numColonne; i++) {
                riga[i] = rs.getString(metaData.getColumnName(i+1));
            }
            tableModel.addRow(riga);
        }
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setVisible(true);
    }
}
