package controller;


import dao.*;
import mail_service.JavaMailUtil;
import model.*;
import view.table.TabellaContratti;
import view.table.TabellaImmobili;
import view.table.TabellaInquilini;
import view.table.TabellaResoconto;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Controller {
    private TabellaInquilini tabellaInquilini = new TabellaInquilini();
    private TabellaResoconto tabellaResoconto = new TabellaResoconto();
    private TabellaImmobili tabellaImmobili = new TabellaImmobili();
    private TabellaContratti tabellaContratti = new TabellaContratti();
    private Proprietario proprietario = null;
    private String testoSollecito = "Salve {0} {1},\nla presente lettera è per ricordarle gentilmente che il pagamento " +
            "del suo debito è ancora in sospeso. L''importo totale del debito, che la prego di estinguere al più presto, " +
            "è di {2, number, currency}.\nCordiali saluti,\n{3} {4}";

    public boolean aggiungiProprietario(Proprietario proprietario) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        return proprietarioDao.insert(proprietario);
    }

    public boolean aggiungiContratto(Contratto contratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        contratto.setCfProprietario(proprietario.getCf());
        try {
            LocalDate oggi = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String prossimoPagamento;

            if (!oggi.isAfter(LocalDate.parse(contratto.getDataFine(), formatter))) {
                prossimoPagamento = oggi.getYear() + "-" + oggi.getMonthValue() + "-" + contratto.getProssimoPagamento();
                contratto.setProssimoPagamento(prossimoPagamento);
            } else {
                contratto.setProssimoPagamento("");
            }
        }catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("ATTENZIONE! sono stati inseriti valori non validi per le date");
            System.out.println("Per poter utilizzare tutte le funzionalità del programma " +
                    "è necessario modificarle dal menu gestione contratti");
        }
        boolean esito = contrattoDao.insert(contratto);
        aggiorna();
        return esito;
    }

    public boolean aggiungiInquilino(Inquilino inquilino) {
        InquilinoDAO inquilinoDao = new InquilinoDAO();
        boolean esito = inquilinoDao.insert(inquilino);
        aggiorna();
        return esito;
    }

    public boolean aggiungiImmobile(Immobile immobile) {
        ImmobileDAO immobileDao = new ImmobileDAO();
        immobile.setIdProprietario(proprietario.getCf());
        boolean esito = immobileDao.insert(immobile);
        List<Immobile> immobili = immobileDao.selectAll(proprietario.getCf());
        for(Immobile i: immobili) {
            if(Objects.equals(i.getComune(), immobile.getComune()) && Objects.equals(i.getIndirizzo(), immobile.getIndirizzo()) &&
                    Objects.equals(i.getnCivico(), immobile.getnCivico()) && i.getSubalterno() == immobile.getSubalterno()) {
                immobile.setID(i.getID());
            }
        }
        aggiorna();
        return esito;
    }

    public List<Immobile> getAllImmobili() {
        ImmobileDAO immobileDao = new ImmobileDAO();
        return immobileDao.selectAll(proprietario.getCf());
    }

    public List<Inquilino> getAllInquilini() {
        InquilinoDAO inquilinoDao = new InquilinoDAO();
        return inquilinoDao.selectAll(proprietario.getCf());
    }

    public List<Inquilino> getInquiliniSollecito() {
        List<Inquilino> inquilini = getAllInquilini();
        List<Inquilino> inquiliniSollecito = new ArrayList<>();

        for (Inquilino i :inquilini) {
            if (i.getTotaleDovuto() > i.getTotalePagato()) {
                inquiliniSollecito.add(i);
            }
        }
        return inquiliniSollecito;
    }

    public Inquilino getInquilino(String idInquilino) {
        try {
            InquilinoDAO inquilinoDao = new InquilinoDAO();
            return inquilinoDao.select(Integer.parseInt(idInquilino), proprietario.getCf());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return null;
    }

    public List<Contratto> getAllContratti() {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        return contrattoDao.selectAll(proprietario.getCf());
    }

    public List<Resoconto> getResoconti() {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        return proprietarioDao.selectResoconti(proprietario.getCf());
    }

    public boolean isProprietario(String email, String password) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        List<Proprietario> proprietari = proprietarioDao.selectAll("");
        for(Proprietario p: proprietari) {
            if(Objects.equals(p.getEmail(), email) && Objects.equals(p.getPassword(), password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInquilino(String idInquilino) {
        try {
            InquilinoDAO inquilinoDao = new InquilinoDAO();
            return inquilinoDao.select(Integer.parseInt(idInquilino), proprietario.getCf()) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean isImmobile(String idImmobile) {
        try {
            ImmobileDAO immobileDao = new ImmobileDAO();
            return immobileDao.select(Integer.parseInt(idImmobile), proprietario.getCf()) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean isAffittato(String idImmobile) {
        try {
            ImmobileDAO immobileDao = new ImmobileDAO();
            return immobileDao.select(Integer.parseInt(idImmobile), proprietario.getCf()).isAffittato();
        } catch (NumberFormatException e) {
            System.out.println("L'ID immobile non è valido");
        }
        return false;
    }

    public boolean isContratto(String idContratto) {
        try {
            ContrattoDAO contrattoDao = new ContrattoDAO();
            return contrattoDao.select(Integer.parseInt(idContratto), proprietario.getCf()) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean rimuoviInquilino(String idInquilino) {
        boolean esito = false;
        try {
            InquilinoDAO inquilinoDao = new InquilinoDAO();
            esito = inquilinoDao.delete(Integer.parseInt(idInquilino), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return esito;
    }

    public boolean rimuoviImmobile(String idImmobile) {
        boolean esito = false;
        try {
            ImmobileDAO immobileDao = new ImmobileDAO();
            esito = immobileDao.delete(Integer.parseInt(idImmobile), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return esito;
    }

    public boolean rimuoviContratto(String idContratto) {
        boolean esito = false;
        try {
            ContrattoDAO contrattoDao = new ContrattoDAO();
            esito = contrattoDao.delete(Integer.parseInt(idContratto), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return esito;
    }

    public boolean rimuoviProprietario() {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        boolean esito = proprietarioDao.delete(0, proprietario.getCf());
        aggiorna();
        return esito;
    }

    public boolean modificaContratto(String idContratto, Contratto contratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        boolean esito = contrattoDao.update(Integer.parseInt(idContratto), contratto, proprietario.getCf());
        aggiorna();
        return esito;
    }

    public boolean modificaImmobile(String idImmobile, Immobile immobile) {
        ImmobileDAO immobileDao = new ImmobileDAO();
        boolean esito = immobileDao.update(Integer.parseInt(idImmobile), immobile,"");
        aggiorna();
        return esito;
    }

    public boolean modificaInquilino(String idInquilino, Inquilino inquilino) {
        InquilinoDAO inquilinoDao = new InquilinoDAO();
        boolean esito = inquilinoDao.update(Integer.parseInt(idInquilino), inquilino, proprietario.getCf());
        aggiorna();
        return esito;
    }

    public boolean modificaProprietario(Proprietario nuovoP) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        boolean esito = proprietarioDao.update(0, nuovoP, proprietario.getCf());
        proprietario = nuovoP;
        aggiorna();
        return esito;
    }

    public void aggiungiPagamento(String idInquilino, String pagamento) {
        try {
            InquilinoDAO inquilinoDao = new InquilinoDAO();
            Inquilino inquilino = inquilinoDao.select(Integer.parseInt(idInquilino), proprietario.getCf());
            inquilino.setTotalePagato(inquilino.getTotalePagato() + Float.parseFloat(pagamento));
            inquilinoDao.update(Integer.parseInt(idInquilino), inquilino, proprietario.getCf());

            System.out.println("Pagamento salvato");
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("Valore in denaro non valido");
        }
    }

    public void aggiungiSpesa(String idInquilino, String spesa) {
        try {
            InquilinoDAO inquilinoDao = new InquilinoDAO();
            Inquilino inquilino = inquilinoDao.select(Integer.parseInt(idInquilino), proprietario.getCf());
            inquilino.setTotaleDovuto(inquilino.getTotaleDovuto() + Float.parseFloat(spesa));
            inquilinoDao.update(Integer.parseInt(idInquilino), inquilino, proprietario.getCf());

            System.out.println("Spesa salvata");
            aggiorna();
        }catch (NumberFormatException e) {
            System.out.println("Valore in denaro non valido");
        }
    }

    public void inviaSollecito(List<Inquilino> inquilini) {
        JavaMailUtil mailService = new JavaMailUtil(proprietario.getEmail());
        for (Inquilino i: inquilini) {
            Object[] argomenti = {i.getNome(), i.getCognome(),
                    i.getTotaleDovuto()-i.getTotalePagato(),
                    proprietario.getNome(), proprietario.getCognome()};

            System.out.println("Invio email di sollecito all'inquilino con ID " + i.getID() + " in corso...");
            mailService.send(i.getEmail(), "Sollecito di pagamento", MessageFormat.format(testoSollecito, argomenti));
        }
    }

    public boolean emailDisponibile(String email) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        List<Proprietario> proprietari = proprietarioDao.selectAll("");
        for(Proprietario p: proprietari) {
            if(Objects.equals(p.getEmail(), email)) {
                return false;
            }
        }
        return true;
    }

    public void setProprietario(String email, String password) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        proprietario = proprietarioDao.select(email, password);
    }

    public void setProprietario(Proprietario p) {
        this.proprietario = p;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    // serve ad aggiornare la logica dei pagamenti
    public void aggiorna() {
        try {
            ImmobileDAO immobileDao = new ImmobileDAO();
            InquilinoDAO inquilinoDao = new InquilinoDAO();
            ContrattoDAO contrattoDao = new ContrattoDAO();

            List<Immobile> immobili = getAllImmobili();
            List<Contratto> contratti = getAllContratti();
            List<Inquilino> inquilini = getAllInquilini();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate adesso = LocalDate.now();

            // aggiorna le date di pagamento e il totale dovuto degli inquilini
            for(Contratto c: contratti) {
                if(!c.getProssimoPagamento().isEmpty()) {
                    LocalDate prossimoPagamento = LocalDate.parse(c.getProssimoPagamento(), formatter);
                    if (!adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter)) && adesso.isAfter(prossimoPagamento)) {
                        prossimoPagamento = adesso.plusMonths(1).withDayOfMonth(prossimoPagamento.getDayOfMonth());
                        c.setProssimoPagamento(prossimoPagamento.toString());
                        // se aggiorno il prossimo pagamento aggiungo il canone al totale dovuto dall'inquilino
                        for (Inquilino i : inquilini) {
                            if (c.getCfInquilino() == i.getCf()) {
                                i.setTotaleDovuto(i.getTotaleDovuto() + c.getCanone());
                                inquilinoDao.update(i.getID(), i, proprietario.getCf());
                            }
                        }
                    } else if (adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter))) {
                        c.setProssimoPagamento("");
                    }
                    // l'update lo fo solo se ho modificato qualcosa e quindi se prossimo pagamento non è null
                    contrattoDao.update(c.getID(), c, proprietario.getCf());
                }
            }

            // aggiorna il campo affittato degli immobili
            for(Immobile i : immobili) {
                boolean affittato = false;
                for(Contratto c : contratti) {
                    if(i.getID() == c.getIdImmobile() && !adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter))) {
                        affittato = true;
                    }
                }
                i.setAffittato(affittato);
                immobileDao.update(i.getID(), i,"");
            }


            tabellaContratti.aggiornaTabella(contratti);
            tabellaImmobili.aggiornaTabella(immobili);
            tabellaInquilini.aggiornaTabella(inquilini);
            tabellaResoconto.aggiornaTabella(getResoconti());

        }catch (DateTimeParseException | NumberFormatException e) {
            System.out.println(e);
            System.out.println("ATTENZIONE! nel database sono presenti date in un formato non riconosciuto (formato valido: YYYY-MM-DD)");
            System.out.println("Se non si effettuano le dovute modifiche alla tabella contratti il programma " +
                    "potrebbe non funzionare correttamente");
            System.out.println("(è possibile modificare le date dei contratti dal menu gestione contratti)");
        }
    }

    public void mostraResoconto() {
        aggiorna();
        tabellaResoconto.mostraTabella(getResoconti());
    }

    public void mostraContratti() {
        aggiorna();
        tabellaContratti.mostraTabella(getAllContratti());
    }

    public void mostraImmobili() {
        aggiorna();
        tabellaImmobili.mostraTabella(getAllImmobili());
    }

    public void mostraInquilini() {
        aggiorna();
        tabellaInquilini.mostraTabella(getAllInquilini());
    }

    public void reset() {
        tabellaInquilini.dispose();
        tabellaContratti.dispose();
        tabellaImmobili.dispose();
        tabellaResoconto.dispose();
        proprietario = null;
    }
}
