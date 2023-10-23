package view.tabella;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public abstract class TabellaGUI<T> extends JFrame {
    protected JTable tabella;
    protected DefaultTableModel modelloTabella;

    public TabellaGUI(String nomeTabella) {
        super(nomeTabella);
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

    public abstract void aggiornaTabella(List<T> lista);

    public void mostraTabella(List<T> lista) {
        aggiornaTabella(lista);
        setVisible(true);
    }

}
