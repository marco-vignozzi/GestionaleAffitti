package view.tabella;


import model.Resoconto;
import java.util.List;

public class TabellaResoconto extends TabellaGUI<Resoconto> {
    private Object[] nomiColonne = {"ID_immobile",
            "comune",
            "indirizzo",
            "n_civico",
            "subalterno",
            "affittato",
            "nome",
            "cognome",
            "email",
            "debito",
            "data_fine",
            "prossimo_pagamento",
            "canone",
            "sfratto",
            "proroga"};

    public TabellaResoconto() {
        super("Tabella Resoconto");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Resoconto> lista) {
        modelloTabella.setRowCount(0);

        for(Resoconto r: lista) {
            Object[] riga = {r.getID(), r.getComune(), r.getIndirizzo(), r.getnCivico(), r.getSubalterno(),
                    r.isAffittato()?"sì":"no", r.getNome(), r.getCognome(), r.getEmail(), r.getDebito(), r.getDataFine(),
                    r.getProssimoPagamento(), r.getCanone(), r.isSfratto()?"sì":"no", r.isProroga()?"sì":"no"};
            modelloTabella.addRow(riga);
        }
    }

}
