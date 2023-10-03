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


}
