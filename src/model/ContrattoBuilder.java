package model;

public class ContrattoBuilder {
    protected int idImmobile;
    protected String cfInquilino;
    protected String cfProprietario;
    protected String dataInizio;
    protected String dataFine;
    protected String prossimoPagamento;
    protected float canone;   // prezzo affitto
    protected boolean sfratto = false;
    protected boolean proroga;

    public ContrattoBuilder idImmobile(int idImmobile) {
        this.idImmobile = idImmobile;
        return this;
    }

    public ContrattoBuilder cfInquilino(String cfInquilino) {
        this.cfInquilino = cfInquilino;
        return this;
    }

    public ContrattoBuilder cfProprietario(String cfProprietario) {
        this.cfProprietario = cfProprietario;
        return this;
    }

    public ContrattoBuilder dataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
        return this;
    }

    public ContrattoBuilder dataFine(String dataFine) {
        this.dataFine = dataFine;
        return this;
    }

    public ContrattoBuilder prossimoPagamento(String dataPagamento) {
        this.prossimoPagamento = dataPagamento;
        return this;
    }

    public ContrattoBuilder canone(float canone) {
        this.canone = canone;
        return this;
    }

    public ContrattoBuilder sfratto(boolean sfratto) {
        this.sfratto = sfratto;
        return this;
    }

    public ContrattoBuilder proroga(boolean proroga) {
        this.proroga = proroga;
        return this;
    }

    public Contratto build() {
        return new Contratto(this);
    }

}
