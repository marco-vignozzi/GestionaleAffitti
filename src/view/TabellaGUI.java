package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class TabellaGUI extends JFrame {
    private JTable tabella;
    private DefaultTableModel modelloTabella;

    public TabellaGUI(String nomeTabella) {
        super("Tabella " + nomeTabella);
        super.setLayout(new BorderLayout());

        // Imposta il modello per la tabella
        modelloTabella = new DefaultTableModel();

        // Crea una tabella con il modello
        tabella = new JTable(modelloTabella);

        // Aggiungi la tabella a uno JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabella);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

        // Aggiungi il JScrollPane al frame
        add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(width, height);
        setVisible(false);
    }

    public void aggiornaTabella(ResultSet rs) throws SQLException {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numColonne = metaData.getColumnCount();
            Object[] nomiColonne = new Object[numColonne];

            for (int i = 0; i < numColonne; i++) {
                nomiColonne[i] = metaData.getColumnLabel(i + 1);
            }

            modelloTabella.setColumnIdentifiers(nomiColonne);
            modelloTabella.setRowCount(0);

            while (rs.next()) {
                Object[] riga = new Object[numColonne];
                for (int i = 0; i < numColonne; i++) {
                    riga[i] = rs.getString(metaData.getColumnLabel(i + 1));
                }
                modelloTabella.addRow(riga);
            }
        }catch (NoSuchElementException | ArrayIndexOutOfBoundsException e) {}
    }

    public void mostraTabella(ResultSet rs) throws SQLException {
        aggiornaTabella(rs);
        setVisible(true);
    }

}
