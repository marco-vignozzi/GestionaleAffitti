package test;
import controller.Controller;

import dao.ContrattoDAO;
import dao.ImmobileDAO;
import dao.InquilinoDAO;
import dao.ProprietarioDAO;
import model.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;



public class ControllerTest {
    Proprietario proprietario;
    Controller controller;
    Immobile immobile;
    Inquilino inquilino;
    Contratto contratto;

    public ControllerTest() {
        this.proprietario = new Proprietario("a","a","a","a","A");
        this.controller = new Controller();
        this.controller.setProprietario(this.proprietario);
        ProprietarioDAO dao = new ProprietarioDAO();
//        dao.insert(this.proprietario);

        ImmobileBuilder immobileBuilder = new ImmobileBuilder();
        immobileBuilder.comune("example")
                .foglio(1)
                .particella(1)
                .subalterno(1)
                .categoria("example")
                .classe("example")
                .superficie(1)
                .rendita(1)
                .indirizzo("example")
                .nCivico("example")
                .affittato(false);

        this.immobile = immobileBuilder.build();
        InquilinoBuilder ibuilder = new InquilinoBuilder();
        ibuilder.cf("EXAMPLE")
                .nome("example")
                .cognome("example")
                .cittàNascita("example")
                .dataNascita("example")
                .email("example@gmail.com")
                .residenza("example")
                .totaleDovuto(100)
                .totalePagato(100)
                .telefono("example");
        this.inquilino = ibuilder.build();

    }

    @BeforeAll
    static void setUp() {
        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
        List<Proprietario> utenti = proprietarioDAO.selectAll("");
        for(Proprietario utente : utenti) {
            if(utente.getEmail().equals("example@gmail.com")) {
                proprietarioDAO.delete(1, utente.getCf());
            }
        }
        ImmobileDAO immobileDAO = new ImmobileDAO();
        List<Immobile> immobili = new ImmobileDAO().selectAll("A");
        for(Immobile immobile : immobili) {
            if(immobile.getIdProprietario().equals("A")) {
                int id = immobile.getID();
                immobileDAO.delete(id, "");
            }
        }
        InquilinoDAO inquilinoDAO = new InquilinoDAO();
        List<Inquilino> inquilini = inquilinoDAO.selectAll("A");
        for(Inquilino i: inquilini) {
            if(i.getCf().equals("EXAMPLE")) {
                inquilinoDAO.delete(i.getID(), "A");
            }
        }

        ContrattoDAO contrattoDAO = new ContrattoDAO();
        List<Contratto> contratti = contrattoDAO.selectAll("A");
        for(Contratto c: contratti) {
            if(c.getCfProprietario().equals("A")) {
                contrattoDAO.delete(c.getID(), "A");
            }
        }
    }

    @Test
    void testAggiungiUtente() {         // va fatto per forza sul DAO visto che non c'è metodo getUtente() in Controller
        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
        Proprietario p = new Proprietario("example@gmail.com","example","example","example","EXAMPLE");
        assertTrue(proprietarioDAO.insert(p));
        Proprietario nuovoProprietario = proprietarioDAO.select("example@gmail.com", "example");
        assertEquals(nuovoProprietario.getCf(), "EXAMPLE");
        assertEquals(nuovoProprietario.getNome(), "example");
        assertEquals(nuovoProprietario.getCognome(), "example");
        assertEquals(nuovoProprietario.getPassword(), "example");
        assertEquals(nuovoProprietario.getEmail(), "example@gmail.com");
    }

    @Test
    void testAggiungi() {
        ImmobileDAO immobileDAO = new ImmobileDAO();

        //verifichiamo che l'immobile venga aggiunto
        assertTrue(this.controller.aggiungiImmobile(this.immobile));

        // verifichiamo che l'ID sia stato aggiunto correttamente all'oggetto immobile appena inserito
        List<Immobile> immobili = this.controller.getAllImmobili();
        for(Immobile i: immobili) {
            if(Objects.equals(i.getComune(), immobile.getComune()) && Objects.equals(i.getIndirizzo(), immobile.getIndirizzo()) &&
                    Objects.equals(i.getnCivico(), immobile.getnCivico()) && i.getSubalterno() == immobile.getSubalterno()) {
                assertEquals(this.immobile.getID(), i.getID());
                assertEquals(this.immobile.getIdProprietario(), this.proprietario.getCf());
            }
        }

        // verifichiamo che affittato sia false automaticamente
        Immobile immobile = immobileDAO.select(this.immobile.getID(), this.proprietario.getCf());
        assertEquals(immobile.isAffittato(), false);

        // verifichiamo che l'inquilino sia aggiunto correttamente
        InquilinoDAO inquilinoDAO = new InquilinoDAO();

        assertTrue(this.controller.aggiungiInquilino(this.inquilino));

        // verifichiamo che il contratto sia aggiunto correttamente
        ContrattoDAO contrattoDAO = new ContrattoDAO();

        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(immobile.getID());
        this.contratto = cbuilder.build();

        assertTrue(this.controller.aggiungiContratto(this.contratto));
        List<Contratto> contratti = this.controller.getAllContratti();

        // verifichiamo che la data del prossimo pagamento sia corretto in base alla data di oggi.
        LocalDate oggi = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for(Contratto c: contratti) {
            LocalDate dataFine = LocalDate.parse(c.getDataFine(), formatter);
            LocalDate prossimoPagamento = LocalDate.parse(c.getProssimoPagamento());
            if (!oggi.isAfter(dataFine) && !(oggi.getDayOfMonth() > prossimoPagamento.getDayOfMonth())) {
                assertEquals(prossimoPagamento.toString(),
                        oggi.getYear() + "-" + oggi.getMonthValue() + "-" + LocalDate.parse(this.contratto.getProssimoPagamento(), formatter).getDayOfMonth());
            } else if(oggi.getDayOfMonth() > prossimoPagamento.getDayOfMonth() && !oggi.isAfter(dataFine)) {
                assertEquals(prossimoPagamento.toString(),
                        oggi.getYear() + "-" + oggi.plusMonths(1).getMonthValue() + "-" + LocalDate.parse(this.contratto.getProssimoPagamento(), formatter).getDayOfMonth());
            }
            else {
                assertEquals(c.getProssimoPagamento(), "");
            }
        }

        // verifichiamo che dopo l'aggiunta del contratto l'immobile sia affittato
        immobili = this.controller.getAllImmobili();
        for(Immobile i: immobili) {
            if(Objects.equals(i.getComune(), immobile.getComune()) && Objects.equals(i.getIndirizzo(), immobile.getIndirizzo()) &&
                    Objects.equals(i.getnCivico(), immobile.getnCivico()) && i.getSubalterno() == immobile.getSubalterno()) {
                assertTrue(i.isAffittato());
            }
        }

    }

    //TODO test modifica
    //TODO test elimina
    //TODO test aggiungi pagamento



}
