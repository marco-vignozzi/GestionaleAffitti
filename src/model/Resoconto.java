package model;

public class Resoconto {
    private int ID;
    private String comune;
    private  String indirizzo;
    private  String nCivico;
    private  int subalterno;
    private  boolean affittato;
    private  String nome;
    private  String cognome;
    private  String email;
    private  float debito;
    private  String data_fine;
    private  String prossimo_pagamento;
    private  float canone;
    private  boolean proroga;
    private  boolean sfratto;

    public Resoconto(int id, String comune, String indirizzo, String nCivico, int subalterno, boolean affittato,
                     String nome, String cognome, String email, float debito, String data_fine, String prossimo_pagamento,
                     float canone, boolean sfratto, boolean proroga) {
        this.ID = id;
        this.comune = comune;
        this.indirizzo = indirizzo;
        this.nCivico = nCivico;
        this.subalterno = subalterno;
        this.affittato = affittato;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.debito = debito;
        this.data_fine = data_fine;
        this.prossimo_pagamento = prossimo_pagamento;
        this.canone = canone;
        this.sfratto = sfratto;
        this.proroga = proroga;
    }

    public int getID() {
        return ID;
    }

    public String getComune() {
        return comune;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getnCivico() {
        return nCivico;
    }

    public int getSubalterno() {
        return subalterno;
    }

    public boolean isAffittato() {
        return affittato;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public float getDebito() {
        return debito;
    }

    public String getDataFine() {
        return data_fine;
    }

    public String getProssimoPagamento() {
        return prossimo_pagamento;
    }

    public float getCanone() {
        return canone;
    }

    public boolean isProroga() {
        return proroga;
    }

    public boolean isSfratto() {
        return sfratto;
    }
}
