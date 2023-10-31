package view.table;


import model.Immobile;
import java.util.List;

public class TabellaImmobili extends TabellaGUI<Immobile> {
    private Object[] nomiColonne = {"ID",
            "comune",
            "indirizzo",
            "n_civico",
            "subalterno",
            "affittato",
            "foglio",
            "particella",
            "categoria",
            "classe",
            "superficie_mq",
            "rendita"};

    public TabellaImmobili() {
        super("Tabella Immobili");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Immobile> lista) {
        modelloTabella.setRowCount(0);

        for(Immobile i: lista) {
            Object[] riga = {i.getID(), i.getComune(), i.getIndirizzo(), i.getnCivico(), i.getSubalterno(),
                    i.isAffittato()?"sì":"no", i.getFoglio(), i.getParticella(), i.getCategoria(), i.getClasse(),
                    i.getSuperficie(), i.getRendita()};
            modelloTabella.addRow(riga);
        }
    }

}
