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

    private String idImmobile;
    private String cfInquilino;
    private String cfProprietario;
    private String dataInizio;
    private String dataFine;
    private float canone;   // prezzo affitto

}
