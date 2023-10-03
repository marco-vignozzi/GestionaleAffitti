package model;


public class Contratto {

    public Contratto(String idImmobile, String cfInquilino, String cfProprietario, String dataInizio, String dataFine,
                     float canone)
    {
        this.idImmobile = idImmobile;
        this.cfInquilino = cfInquilino;
        this.cfProprietario = cfProprietario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.canone = canone;
    }

    public String getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(String idImmobile) {
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

    private String idImmobile;
    private String cfInquilino;
    private String cfProprietario;
    private String dataInizio;
    private String dataFine;
    private float canone;   // prezzo affitto

}
