package view.table;


import model.Contratto;
import java.util.List;


public class TabellaContratti extends TabellaGUI<Contratto> {
    private Object[] nomiColonne = {"ID",
            "cf_inquilino",
            "ID_immobile",
            "data_inizio",
            "data_fine",
            "prossimo_pagamento",
            "canone",
            "sfratto",
            "proroga"};

    public TabellaContratti() {
        super("Tabella Contratti");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Contratto> lista) {
        modelloTabella.setRowCount(0);

        for(Contratto c: lista) {
            Object[] riga = {c.getID(), c.getCfInquilino(), c.getIdImmobile(), c.getDataInizio(), c.getDataFine(),
            c.getProssimoPagamento(), c.getCanone(), c.isSfratto()?"sì":"no", c.isProroga()?"sì":"no"};
            modelloTabella.addRow(riga);
        }
    }

}
