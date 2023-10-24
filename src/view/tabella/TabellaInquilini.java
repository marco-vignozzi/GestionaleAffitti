package view.tabella;


import model.Inquilino;
import java.util.List;


public class TabellaInquilini extends TabellaGUI<Inquilino> {
    private Object[] nomiColonne = {"ID",
             "codice_fiscale",
             "nome",
             "cognome",
             "data_di_nascita",
             "città_di_nascita",
             "residenza",
             "telefono",
             "email",
             "totale_dovuto",
             "totale_pagato",
             "debito"};

    public TabellaInquilini() {
        super("Tabella Inquilini");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Inquilino> lista) {
        modelloTabella.setRowCount(0);

        for(Inquilino i: lista) {
            Object[] riga = {i.getID(), i.getCf(), i.getNome(), i.getCognome(), i.getDataNascita(), i.getCittàNascita(),
                    i.getResidenza(), i.getTelefono(), i.getEmail(), i.getTotaleDovuto(), i.getTotalePagato(),
                    i.getTotaleDovuto()-i.getTotalePagato()};
            modelloTabella.addRow(riga);
        }
    }

}
