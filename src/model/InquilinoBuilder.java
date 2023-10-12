package model;

public class InquilinoBuilder {
    protected String nome;
    protected String cognome;
    protected String cf;
    protected String dataNascita;
    protected String cittàNascita;
    protected String residenza;
    protected String telefono;
    protected String email;
    protected float totaleDovuto = 0;
    protected float totalePagato = 0;
    protected boolean devePagare = false;

    public InquilinoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public InquilinoBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public InquilinoBuilder cf(String cf) {
        this.cf = cf;
        return this;
    }

    public InquilinoBuilder dataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public InquilinoBuilder cittàNascita(String cittàNascita) {
        this.cittàNascita = cittàNascita;
        return this;
    }

    public InquilinoBuilder residenza(String residenza) {
        this.residenza = residenza;
        return this;
    }

    public InquilinoBuilder telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public InquilinoBuilder email(String email) {
        this.email = email;
        return this;
    }

    public InquilinoBuilder totaleDovuto(float totaleDovuto) {
        this.totaleDovuto = totaleDovuto;
        return this;
    }

    public InquilinoBuilder totalePagato(float totalePagato) {
        this.totalePagato = totalePagato;
        return this;
    }

    public InquilinoBuilder devePagare(boolean devePagare) {
        this.devePagare = devePagare;
        return this;
    }

    public Inquilino build(){
        return new Inquilino(this);
    }
}
