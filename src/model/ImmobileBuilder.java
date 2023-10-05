package model;
import model.Immobile;

public class ImmobileBuilder {
    protected String comune;
    protected int foglio;
    protected int particella;
    protected int subalterno;
    protected String categoria;
    protected String classe;
    protected float superficie;
    protected float rendita;
    protected boolean affittato;
    protected String idProprietario;
    protected String indirizzo;
    protected String nCivico;
    protected int id;

    public ImmobileBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ImmobileBuilder comune(String comune) {
        this.comune = comune;
        return this;
    }

    public ImmobileBuilder foglio(int foglio) {
        this.foglio = foglio;
        return this;
    }

    public ImmobileBuilder particella(int particella) {
        this.particella = particella;
        return this;
    }

    public ImmobileBuilder subalterno(int subalterno) {
        this.subalterno = subalterno;
        return this;
    }

    public ImmobileBuilder categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public ImmobileBuilder classe(String classe) {
        this.classe = classe;
        return this;
    }

    public ImmobileBuilder superficie(float superficie) {
        this.superficie = superficie;
        return this;
    }

    public ImmobileBuilder rendita(float rendita) {
        this.rendita = rendita;
        return this;
    }

    public ImmobileBuilder idProprietario(String idProprietario) {
        this.idProprietario = idProprietario;
        return this;
    }

    public ImmobileBuilder indirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
        return this;
    }

    public ImmobileBuilder nCivico(String nCivico) {
        this.nCivico = nCivico;
        return this;
    }
    public ImmobileBuilder affittato(boolean affittato) {
        this.affittato = affittato;
        return this;
    }


    public Immobile build() {
        return new Immobile(this);
    }
}
