package model;

public class Immobile {
    public Immobile(String comune, int foglio, int particella, int subalterno, String categoria, String classe,
                    float superficie, float rendita, String idProprietario, String indirizzo, String nCivico) {
        this.comune = comune;
        this.foglio = foglio;
        this.particella = particella;
        this.subalterno = subalterno;
        this.categoria = categoria;
        this.classe = classe;
        this.superficie = superficie;
        this.rendita = rendita;
        this.idProprietario = idProprietario;
        this.indirizzo = indirizzo;
        this.nCivico = nCivico;
    }

    private String comune;
    private int foglio;     // chiave primaria
    private int particella;     // chiave primaria
    private int subalterno;  // chiave primaria
    private String categoria;
    private String classe;
    private float superficie;  // in metri quadri
    private float rendita;      // in generale diversa dal canone

    //TODO: vedere se servono
    private String idProprietario;
    private String indirizzo;
    private String nCivico;
}
