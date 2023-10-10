package model;


public class Contratto {

    public Contratto(int idImmobile, String cfInquilino, String cfProprietario, String dataInizio, String dataFine,
                     String dataPagamento, float canone)
    {
        this.idImmobile = idImmobile;
        this.cfInquilino = cfInquilino;
        this.cfProprietario = cfProprietario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.dataPagamento = dataPagamento;
        this.canone = canone;
    }

    protected Contratto(ContrattoBuilder contrattoBuilder) {
        this.idImmobile = contrattoBuilder.idImmobile;
        this.cfInquilino = contrattoBuilder.cfInquilino;
        this.cfProprietario = contrattoBuilder.cfProprietario;
        this.dataInizio = contrattoBuilder.dataInizio;
        this.dataFine = contrattoBuilder.dataFine;
        this.dataPagamento = contrattoBuilder.dataPagamento;
        this.canone = contrattoBuilder.canone;
        this.sfratto = contrattoBuilder.sfratto;
    }


    public int getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(int idImmobile) {
        this.idImmobile = idImmobile;
    }

    public String getCfInquilino() {
        return cfInquilino;
    }

    public void setCfInquilino(String cfInquilino) {
        this.cfInquilino = cfInquilino;
    }

    public String getCfProprietario() {
        return cfProprietario;
    }

    public void setCfProprietario(String cfProprietario) {
        this.cfProprietario = cfProprietario;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public float getCanone() {
        return canone;
    }

    public void setCanone(float canone) {
        this.canone = canone;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public boolean isSfratto() {
        return sfratto;
    }

    public void setSfratto(boolean sfratto) {
        this.sfratto = sfratto;
    }

    private int idImmobile;
    private String cfInquilino;
    private String cfProprietario;
    private String dataInizio;
    private String dataFine;
    private String dataPagamento;
    private float canone;   // prezzo affitto
    private boolean sfratto = false;

}
