package model;

public class Immobile {

    private String comune;
    private int foglio;     // chiave primaria
    private int particella;     // chiave primaria
    private int subalterno;  // chiave primaria
    private String categoria;
    private String classe;
    private float superficie;  // in metri quadri
    private float rendita;      // in generale diversa dal canone
    private boolean affittato;
    private String idProprietario;
    private String indirizzo;
    private String nCivico;
    private int id;


    protected Immobile(ImmobileBuilder builder) {
        this.comune = builder.comune;
        this.foglio = builder.foglio;
        this.particella = builder.particella;
        this.subalterno = builder.subalterno;
        this.categoria = builder.categoria;
        this.classe = builder.classe;
        this.superficie = builder.superficie;
        this.rendita = builder.rendita;
        this.idProprietario = builder.idProprietario;
        this.indirizzo = builder.indirizzo;
        this.nCivico = builder.nCivico;
        this.affittato = builder.affittato;
        this.id = builder.id;
    }

    public boolean isAffittato() {
        return affittato;
    }

    public void setAffittato(boolean affittato) {
        this.affittato = affittato;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public int getFoglio() {
        return foglio;
    }

    public void setFoglio(int foglio) {
        this.foglio = foglio;
    }

    public int getParticella() {
        return particella;
    }

    public void setParticella(int particella) {
        this.particella = particella;
    }

    public int getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(int subalterno) {
        this.subalterno = subalterno;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public float getRendita() {
        return rendita;
    }

    public void setRendita(float rendita) {
        this.rendita = rendita;
    }

    public String getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(String idProprietario) {
        this.idProprietario = idProprietario;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getnCivico() {
        return nCivico;
    }

    public void setnCivico(String nCivico) {
        this.nCivico = nCivico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
