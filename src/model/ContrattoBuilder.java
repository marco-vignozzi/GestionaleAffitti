package model;

public class ContrattoBuilder {
    protected int idImmobile;
    protected String cfInquilino;
    protected String cfProprietario;
    protected String dataInizio;
    protected String dataFine;
    protected String dataPagamento;
    protected float canone;   // prezzo affitto
    protected boolean sfratto = false;

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

    public ContrattoBuilder dataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
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

    public Contratto build() {
        return new Contratto(this);
    }

}
