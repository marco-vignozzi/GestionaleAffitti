package model;


public class Inquilino {
    private int ID;
    private String nome;
    private String cognome;
    private String cf;
    private String dataNascita;
    private String cittàNascita;
    private String residenza;
    private String telefono;
    private String email;
    private float totaleDovuto = 0;
    private float totalePagato = 0;

    public Inquilino(String cf, String nome, String cognome, String dataNascita,
                     String cittàNascita, String residenza, String tel, String email)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.dataNascita = dataNascita;
        this.cittàNascita = cittàNascita;
        this.residenza = residenza;
        this.telefono = tel;
        this.email = email;
    }

    protected Inquilino(InquilinoBuilder inquilinoBuilder) {
        this.ID = inquilinoBuilder.id;
        this.nome = inquilinoBuilder.nome;
        this.cognome = inquilinoBuilder.cognome;
        this.cf = inquilinoBuilder.cf;
        this.dataNascita = inquilinoBuilder.dataNascita;
        this.cittàNascita = inquilinoBuilder.cittàNascita;
        this.residenza = inquilinoBuilder.residenza;
        this.telefono = inquilinoBuilder.telefono;
        this.email = inquilinoBuilder.email;
        this.totaleDovuto = inquilinoBuilder.totaleDovuto;
        this.totalePagato = inquilinoBuilder.totalePagato;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCittàNascita() {
        return cittàNascita;
    }

    public void setCittàNascita(String cittàNascita) {
        this.cittàNascita = cittàNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotaleDovuto() {
        return totaleDovuto;
    }

    public void setTotaleDovuto(float totaleDovuto) {
        this.totaleDovuto = totaleDovuto;
    }

    public float getTotalePagato() {
        return totalePagato;
    }

    public void setTotalePagato(float totalePagato) {
        this.totalePagato = totalePagato;
    }

}
