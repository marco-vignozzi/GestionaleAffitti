<<<<<<< HEAD
//package test;
//import controller.Controller;
//
//import dao.ContrattoDAO;
//import dao.ImmobileDAO;
//import dao.InquilinoDAO;
//import dao.ProprietarioDAO;
//import model.*;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.parallel.Execution;
//import org.junit.jupiter.api.parallel.ExecutionMode;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Objects;
//
//import static org.junit.Assert.*;
//
//
//@Execution(ExecutionMode.SAME_THREAD)
//public class ControllerTest {
//    Proprietario proprietario;
//    Controller controller;
//    Inquilino inquilino;
//    Immobile immobile;
//
//    public ControllerTest() {
//        this.proprietario = new Proprietario("a","a","a","a","A");
//        this.controller = new Controller();
//        this.controller.setProprietario(this.proprietario);
////        ProprietarioDAO dao = new ProprietarioDAO();
////        dao.insert(this.proprietario);
//    }
//
//    @BeforeEach
//    void setUp() {
//        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
//        List<Proprietario> utenti = proprietarioDAO.selectAll("");
//        for(Proprietario utente : utenti) {
//            if(utente.getEmail().equals("example@gmail.com")) {
//                proprietarioDAO.delete(1, utente.getCf());
//            }
//        }
//        InquilinoDAO inquilinoDAO = new InquilinoDAO();
//        List<Inquilino> inquilini = inquilinoDAO.selectAll("A");
//        for(Inquilino i: inquilini) {
//            inquilinoDAO.delete(i.getID(), "A");
//        }
//        ContrattoDAO contrattoDAO = new ContrattoDAO();
//        List<Contratto> contratti = contrattoDAO.selectAll("A");
//        for(Contratto c: contratti) {
//            contrattoDAO.delete(c.getID(), "A");
//        }
//        ImmobileDAO immobileDAO = new ImmobileDAO();
//        List<Immobile> immobili = immobileDAO.selectAll("A");
//        for(Immobile immobile : immobili) {
//            int id = immobile.getID();
//            immobileDAO.delete(id, "");
//        }
//
//        ImmobileBuilder immobileBuilder = new ImmobileBuilder();
//        immobileBuilder.comune("example")
//                .foglio(1)
//                .particella(1)
//                .subalterno(1)
//                .categoria("example")
//                .classe("example")
//                .superficie(1)
//                .rendita(1)
//                .indirizzo("example")
//                .nCivico("example")
//                .affittato(false);
//        this.immobile = immobileBuilder.build();
//
//        InquilinoBuilder ibuilder = new InquilinoBuilder();
//        ibuilder.cf("EXAMPLE")
//                .nome("example")
//                .cognome("example")
//                .cittàNascita("example")
//                .dataNascita("example")
//                .email("example@gmail.com")
//                .residenza("example")
//                .totaleDovuto(100)
//                .totalePagato(0)
//                .telefono("example");
//        this.inquilino = ibuilder.build();
//
//    }
//
//    @Test
//    void testAggiungiUtente() {         // va fatto per forza sul DAO visto che non c'è metodo getUtente() in Controller
//        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
//        Proprietario p = new Proprietario("example@gmail.com","example","example","example","EXAMPLE");
//        assertTrue(proprietarioDAO.insert(p));
//        Proprietario nuovoProprietario = proprietarioDAO.select("example@gmail.com", "example");
//        assertEquals(nuovoProprietario.getCf(), "EXAMPLE");
//        assertEquals(nuovoProprietario.getNome(), "example");
//        assertEquals(nuovoProprietario.getCognome(), "example");
//        assertEquals(nuovoProprietario.getPassword(), "example");
//        assertEquals(nuovoProprietario.getEmail(), "example@gmail.com");
//    }
//
//    @Test
//    void testAggiungi() {
//        ImmobileDAO immobileDAO = new ImmobileDAO();
//
//        //verifichiamo che l'immobile venga aggiunto
//        assertTrue(this.controller.aggiungiImmobile(this.immobile));
//
//        // verifichiamo che l'ID sia stato aggiunto correttamente all'oggetto immobile appena inserito
//        List<Immobile> immobili = this.controller.getAllImmobili();
//        for(Immobile i: immobili) {
//            if(Objects.equals(i.getComune(), this.immobile.getComune()) && Objects.equals(i.getIndirizzo(), this.immobile.getIndirizzo()) &&
//                    Objects.equals(i.getnCivico(), this.immobile.getnCivico()) && i.getSubalterno() == this.immobile.getSubalterno()) {
//                assertEquals(this.immobile.getID(), i.getID());
//                assertEquals(this.immobile.getIdProprietario(), this.proprietario.getCf());
//            }
//        }
//
//        // verifichiamo che affittato sia false automaticamente
//        this.immobile = immobileDAO.select(immobile.getID(), this.proprietario.getCf());
//        assertEquals(this.immobile.isAffittato(), false);
//
//        // verifichiamo che l'inquilino sia aggiunto correttamente
//        assertTrue(this.controller.aggiungiInquilino(inquilino));
//
//        ContrattoBuilder cbuilder = new ContrattoBuilder();
//        cbuilder.proroga(true)
//                .sfratto(true)
//                .canone(1000)
//                .prossimoPagamento("10")
//                .dataFine("2025-01-01")
//                .dataInizio("2023-01-01")
//                .cfInquilino("EXAMPLE")
//                .idImmobile(this.immobile.getID());
//        Contratto contratto = cbuilder.build();
//
//        // verifichiamo che il contratto sia aggiunto correttamente
//        assertTrue(this.controller.aggiungiContratto(contratto));
//        List<Contratto> contratti = this.controller.getAllContratti();
//
//        // verifichiamo che la data del prossimo pagamento sia corretto in base alla data di oggi.
//        LocalDate oggi = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        for(Contratto c: contratti) {
//            LocalDate dataFine = LocalDate.parse(c.getDataFine(), formatter);
//            LocalDate prossimoPagamento = LocalDate.parse(c.getProssimoPagamento());
//            if (!oggi.isAfter(dataFine) && !(oggi.getDayOfMonth() > prossimoPagamento.getDayOfMonth())) {
//                assertEquals(prossimoPagamento.toString(),
//                        oggi.getYear() + "-" + oggi.getMonthValue() + "-" + LocalDate.parse(contratto.getProssimoPagamento(), formatter).getDayOfMonth());
//            } else if(oggi.getDayOfMonth() > prossimoPagamento.getDayOfMonth() && !oggi.isAfter(dataFine)) {
//                assertEquals(prossimoPagamento.toString(),
//                        oggi.getYear() + "-" + oggi.plusMonths(1).getMonthValue() + "-" + LocalDate.parse(contratto.getProssimoPagamento(), formatter).getDayOfMonth());
//            }
//            else {
//                assertEquals(c.getProssimoPagamento(), "");
//            }
//        }
//
//        // verifichiamo che dopo l'aggiunta del contratto l'immobile sia affittato
//        immobili = this.controller.getAllImmobili();
//        for(Immobile i: immobili) {
//            if(Objects.equals(i.getComune(), this.immobile.getComune()) && Objects.equals(i.getIndirizzo(), this.immobile.getIndirizzo()) &&
//                    Objects.equals(i.getnCivico(), this.immobile.getnCivico()) && i.getSubalterno() == this.immobile.getSubalterno()) {
//                assertTrue(i.isAffittato());
//            }
//        }
//
//    }
//
//    @Test
//    void testModifica(){
//        //test modifica immobile
//        ImmobileDAO immobileDAO = new ImmobileDAO();
//        ImmobileBuilder immBuilder = new ImmobileBuilder();
//        immBuilder.indirizzo("modificato");
//
//        Immobile immod=immBuilder.build();
//        assertTrue(controller.aggiungiImmobile(this.immobile));
//        assertTrue(controller.modificaImmobile(Integer.toString(this.immobile.getID()), immod));
//        this.immobile = immobileDAO.select(this.immobile.getID(), this.proprietario.getCf());
//        assertEquals(this.immobile.getIndirizzo(), "modificato");
//    }
//
//    // Qui si verifica se eliminando l'inquilino si elimina anche il contratto
//    @Test
//    void testRimuoviInquilino() {
//        assertTrue(this.controller.aggiungiImmobile(this.immobile));
//
//        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
//
//        ContrattoBuilder cbuilder = new ContrattoBuilder();
//        cbuilder.proroga(true)
//                .sfratto(true)
//                .canone(1000)
//                .prossimoPagamento("10")
//                .dataFine("2025-01-01")
//                .dataInizio("2023-01-01")
//                .cfInquilino("EXAMPLE")
//                .idImmobile(this.immobile.getID());
//        Contratto contratto = cbuilder.build();
//
//        assertTrue(this.controller.aggiungiContratto(contratto));
//
//       // seleziono gli id di inquilino e contratto appena inseriti e verifico che rimuovendo inquilino venga
//       // rimosso anche contratto
//        List<Inquilino> inquilini = this.controller.getAllInquilini();
//        for (Inquilino i: inquilini) {
//            if(i.getCf().equals("EXAMPLE")) {
//                this.inquilino.setID(i.getID());
//            }
//        }
//        List<Contratto> contratti = this.controller.getAllContratti();
//        for (Contratto c: contratti) {
//            if(c.getCfProprietario().equals("EXAMPLE")) {
//                contratto.setID(c.getID());
//            }
//        }
//        assertTrue(this.controller.rimuoviInquilino(Integer.toString(this.inquilino.getID())));
//        // verifico che non ci sia già più il contratto
//        assertFalse(this.controller.rimuoviContratto(Integer.toString(contratto.getID())));
//    }
//
//    // Qui si verifica se eliminando l'inquilino si elimina anche il contratto
//    @Test
//    void testRimuoviImmobile() {
//        assertTrue(this.controller.aggiungiImmobile(this.immobile));
//
//        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
//
//        ContrattoBuilder cbuilder = new ContrattoBuilder();
//        cbuilder.proroga(true)
//                .sfratto(true)
//                .canone(1000)
//                .prossimoPagamento("10")
//                .dataFine("2025-01-01")
//                .dataInizio("2023-01-01")
//                .cfInquilino("EXAMPLE")
//                .idImmobile(this.immobile.getID());
//        Contratto contratto = cbuilder.build();
//
//        assertTrue(this.controller.aggiungiContratto(contratto));
//
//        // seleziono gli id di inquilino e contratto appena inseriti e verifico che rimuovendo inquilino venga
//        // rimosso anche contratto
//        List<Immobile> immobili = this.controller.getAllImmobili();
//        for (Immobile i: immobili) {
//            if(i.getIdProprietario().equals("A")) {
//                this.immobile.setID(i.getID());
//            }
//        }
//        List<Contratto> contratti = this.controller.getAllContratti();
//        for (Contratto c: contratti) {
//            if(c.getCfProprietario().equals("EXAMPLE")) {
//                contratto.setID(c.getID());
//            }
//        }
//        assertTrue(this.controller.rimuoviImmobile(Integer.toString(this.immobile.getID())));
//        assertFalse(this.controller.rimuoviContratto(Integer.toString(contratto.getID())));
//    }
//
//    void testRimuoviContratto() {
//        assertTrue(this.controller.aggiungiImmobile(this.immobile));
//
//        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
//
//        ContrattoBuilder cbuilder = new ContrattoBuilder();
//        cbuilder.proroga(true)
//                .sfratto(true)
//                .canone(1000)
//                .prossimoPagamento("10")
//                .dataFine("2025-01-01")
//                .dataInizio("2023-01-01")
//                .cfInquilino("EXAMPLE")
//                .idImmobile(this.immobile.getID());
//        Contratto contratto = cbuilder.build();
//
//        assertTrue(this.controller.aggiungiContratto(contratto));
//
//        // seleziono gli id di inquilino e contratto appena inseriti e verifico che rimuovendo inquilino venga
//        // rimosso anche contratto
//        List<Immobile> immobili = this.controller.getAllImmobili();
//        for (Immobile i: immobili) {
//            if(i.getIdProprietario().equals("A")) {
//                this.immobile.setID(i.getID());
//            }
//        }
//        List<Inquilino> inquilini = this.controller.getAllInquilini();
//        for (Inquilino i: inquilini) {
//            if(i.getCf().equals("EXAMPLE")) {
//                this.inquilino.setID(i.getID());
//            }
//        }
//        List<Contratto> contratti = this.controller.getAllContratti();
//        for (Contratto c: contratti) {
//            if(c.getCfProprietario().equals("EXAMPLE")) {
//                contratto.setID(c.getID());
//            }
//        }
//        assertTrue(this.controller.rimuoviContratto(Integer.toString(contratto.getID())));
//        assertFalse(this.controller.rimuoviInquilino(Integer.toString(this.inquilino.getID())));
//        assertTrue(this.controller.rimuoviImmobile(Integer.toString(this.immobile.getID())));
//    }
//
//    //testo l'aggiunta di una spesa al totale dovuto
//    @Test
//    void testAggiungiSpesa(){
//        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
//        // senza contratto l'inquilino non viene trovato con la select
//        // e senza immobile non puoi creare un contratto
//        assertTrue(this.controller.aggiungiImmobile(this.immobile));
//        ContrattoBuilder cbuilder = new ContrattoBuilder();
//        cbuilder.proroga(true)
//                .sfratto(true)
//                .canone(1000)
//                .prossimoPagamento("10")
//                .dataFine("2025-01-01")
//                .dataInizio("2023-01-01")
//                .cfInquilino("EXAMPLE")
//                .idImmobile(this.immobile.getID());
//        Contratto contratto = cbuilder.build();
//        assertTrue(this.controller.aggiungiContratto(contratto));
//
//        List<Inquilino> inquilini = this.controller.getAllInquilini();
//        for (Inquilino i: inquilini) {
//            if(i.getCf().equals("EXAMPLE")) {
//                assertEquals(this.inquilino.getTotaleDovuto(), i.getTotaleDovuto(), 0.0);
//                this.inquilino.setID(i.getID());
//            }
//        }
//        // verifichiamo che la spesa sia stata aggiunta al totale dovuto dall'inquilino
//        controller.aggiungiSpesa(Integer.toString(inquilino.getID()),"600");
//        inquilini = this.controller.getAllInquilini();
//        for (Inquilino i: inquilini) {
//            if(i.getID() == inquilino.getID()) {
//                assertEquals(this.inquilino.getTotaleDovuto() + 600, i.getTotaleDovuto(), 0.0);
//            }
//        }
//    }
//
//    @Test
//    void testAggiungiPagamento() {
//        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
//        // senza contratto l'inquilino non viene trovato con la select
//        // e senza immobile non puoi creare un contratto
//        assertTrue(this.controller.aggiungiImmobile(this.immobile));
//        ContrattoBuilder cbuilder = new ContrattoBuilder();
//        cbuilder.proroga(true)
//                .sfratto(true)
//                .canone(1000)
//                .prossimoPagamento("10")
//                .dataFine("2025-01-01")
//                .dataInizio("2023-01-01")
//                .cfInquilino("EXAMPLE")
//                .idImmobile(this.immobile.getID());
//        Contratto contratto = cbuilder.build();
//        assertTrue(this.controller.aggiungiContratto(contratto));
//
//        List<Inquilino> inquilini = this.controller.getAllInquilini();
//        for (Inquilino i : inquilini) {
//            if (i.getCf().equals("EXAMPLE")) {
//                assertEquals(this.inquilino.getTotalePagato(), i.getTotalePagato(), 0.0);
//                this.inquilino.setID(i.getID());
//            }
//        }
//        // verifichiamo che la spesa sia stata aggiunta al totale dovuto dall'inquilino
//        controller.aggiungiPagamento(Integer.toString(inquilino.getID()), "600");
//        inquilini = this.controller.getAllInquilini();
//        for (Inquilino i : inquilini) {
//            if (i.getID() == inquilino.getID()) {
//                assertEquals(this.inquilino.getTotalePagato() + 600, i.getTotalePagato(), 0.0);
//            }
//        }
//    }
//
//}
//
=======
package test;
import controller.Controller;

import dao.ContrattoDAO;
import dao.ImmobileDAO;
import dao.InquilinoDAO;
import dao.ProprietarioDAO;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;


@Execution(ExecutionMode.SAME_THREAD)
public class ControllerTest {
    Proprietario proprietario;
    Controller controller;
    Inquilino inquilino;
    Immobile immobile;

    public ControllerTest() {
        this.proprietario = new Proprietario("a","a","a","a","A");
        this.controller = new Controller();
        this.controller.setProprietario(this.proprietario);
//        ProprietarioDAO dao = new ProprietarioDAO();
//        dao.insert(this.proprietario);
    }

    @BeforeEach
    void setUp() {
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
                .totalePagato(0)
                .telefono("example");
        this.inquilino = ibuilder.build();
    }

    @AfterEach
    void tearDown(){
        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
        List<Proprietario> utenti = proprietarioDAO.selectAll("");
        for(Proprietario utente : utenti) {
            if(utente.getEmail().equals("example@gmail.com")) {
                proprietarioDAO.delete(1, utente.getCf());
            }
        }
        InquilinoDAO inquilinoDAO = new InquilinoDAO();
        List<Inquilino> inquilini = inquilinoDAO.selectAll("A");
        for(Inquilino i: inquilini) {
            inquilinoDAO.delete(i.getID(), "A");
        }
        ContrattoDAO contrattoDAO = new ContrattoDAO();
        List<Contratto> contratti = contrattoDAO.selectAll("A");
        for(Contratto c: contratti) {
            contrattoDAO.delete(c.getID(), "A");
        }
        ImmobileDAO immobileDAO = new ImmobileDAO();
        List<Immobile> immobili = immobileDAO.selectAll("A");
        for(Immobile immobile : immobili) {
            int id = immobile.getID();
            immobileDAO.delete(id, "");
        }
    }

    @Test
    void testAggiungiProprietario() {         // va fatto per forza sul DAO visto che non c'è metodo getUtente() in Controller
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
            if(Objects.equals(i.getComune(), this.immobile.getComune()) && Objects.equals(i.getIndirizzo(), this.immobile.getIndirizzo()) &&
                    Objects.equals(i.getnCivico(), this.immobile.getnCivico()) && i.getSubalterno() == this.immobile.getSubalterno()) {
                assertEquals(this.immobile.getID(), i.getID());
                assertEquals(this.immobile.getIdProprietario(), this.proprietario.getCf());
            }
        }
        // verifichiamo che affittato sia false automaticamente
        this.immobile = immobileDAO.select(immobile.getID(), this.proprietario.getCf());
        assertEquals(this.immobile.isAffittato(), false);
        // verifichiamo che l'inquilino sia aggiunto correttamente
        assertTrue(this.controller.aggiungiInquilino(inquilino));
        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(this.immobile.getID());
        Contratto contratto = cbuilder.build();
        // verifichiamo che il contratto sia aggiunto correttamente
        assertTrue(this.controller.aggiungiContratto(contratto));
        List<Contratto> contratti = this.controller.getAllContratti();
        // verifichiamo che la data del prossimo pagamento sia corretto in base alla data di oggi.
        LocalDate oggi = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(Contratto c: contratti) {
            LocalDate dataFine = LocalDate.parse(c.getDataFine(), formatter);
            LocalDate prossimoPagamento = LocalDate.parse(c.getProssimoPagamento());
            if (!oggi.isAfter(dataFine) && !(oggi.getDayOfMonth() > prossimoPagamento.getDayOfMonth())) {
                assertEquals(prossimoPagamento.toString(),
                        oggi.getYear() + "-" + oggi.getMonthValue() + "-" +
                                LocalDate.parse(contratto.getProssimoPagamento(), formatter).getDayOfMonth());
            } else if(oggi.getDayOfMonth() > prossimoPagamento.getDayOfMonth() && !oggi.isAfter(dataFine)) {
                assertEquals(prossimoPagamento.toString(),
                        oggi.getYear() + "-" + oggi.plusMonths(1).getMonthValue() + "-" +
                                LocalDate.parse(contratto.getProssimoPagamento(), formatter).getDayOfMonth());
            }
            else {
                assertEquals(c.getProssimoPagamento(), "");
            }
        }
        // verifichiamo che dopo l'aggiunta del contratto l'immobile sia affittato
        immobili = this.controller.getAllImmobili();
        for(Immobile i: immobili) {
            if(Objects.equals(i.getComune(), this.immobile.getComune()) && Objects.equals(i.getIndirizzo(), this.immobile.getIndirizzo()) &&
                    Objects.equals(i.getnCivico(), this.immobile.getnCivico()) && i.getSubalterno() == this.immobile.getSubalterno()) {
                assertTrue(i.isAffittato());
            }
        }
    }

    @Test
    void testModifica(){
        //test modifica immobile
        ImmobileDAO immobileDAO = new ImmobileDAO();
        ImmobileBuilder immBuilder = new ImmobileBuilder();
        immBuilder.indirizzo("modificato");

        Immobile immod=immBuilder.build();
        assertTrue(controller.aggiungiImmobile(this.immobile));
        assertTrue(controller.modificaImmobile(Integer.toString(this.immobile.getID()), immod));
        this.immobile = immobileDAO.select(this.immobile.getID(), this.proprietario.getCf());
        assertEquals(this.immobile.getIndirizzo(), "modificato");
    }

    // Qui si verifica se eliminando l'inquilino si elimina anche il contratto
    @Test
    void testRimuoviInquilino() {
        assertTrue(this.controller.aggiungiImmobile(this.immobile));

        assertTrue(this.controller.aggiungiInquilino(this.inquilino));

        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(this.immobile.getID());
        Contratto contratto = cbuilder.build();

        assertTrue(this.controller.aggiungiContratto(contratto));

       // seleziono gli id di inquilino e contratto appena inseriti e verifico che rimuovendo inquilino venga
       // rimosso anche contratto
        List<Inquilino> inquilini = this.controller.getAllInquilini();
        for (Inquilino i: inquilini) {
            if(i.getCf().equals("EXAMPLE")) {
                this.inquilino.setID(i.getID());
            }
        }
        List<Contratto> contratti = this.controller.getAllContratti();
        for (Contratto c: contratti) {
            if(c.getCfProprietario().equals("EXAMPLE")) {
                contratto.setID(c.getID());
            }
        }
        assertTrue(this.controller.rimuoviInquilino(Integer.toString(this.inquilino.getID())));
        // verifico che non ci sia già più il contratto
        assertFalse(this.controller.rimuoviContratto(Integer.toString(contratto.getID())));
    }

    // Qui si verifica se eliminando l'inquilino si elimina anche il contratto
    @Test
    void testRimuoviImmobile() {
        assertTrue(this.controller.aggiungiImmobile(this.immobile));

        assertTrue(this.controller.aggiungiInquilino(this.inquilino));

        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(this.immobile.getID());
        Contratto contratto = cbuilder.build();

        assertTrue(this.controller.aggiungiContratto(contratto));

        // seleziono gli id di inquilino e contratto appena inseriti e verifico che rimuovendo inquilino venga
        // rimosso anche contratto
        List<Immobile> immobili = this.controller.getAllImmobili();
        for (Immobile i: immobili) {
            if(i.getIdProprietario().equals("A")) {
                this.immobile.setID(i.getID());
            }
        }
        List<Contratto> contratti = this.controller.getAllContratti();
        for (Contratto c: contratti) {
            if(c.getCfProprietario().equals("EXAMPLE")) {
                contratto.setID(c.getID());
            }
        }
        assertTrue(this.controller.rimuoviImmobile(Integer.toString(this.immobile.getID())));
        assertFalse(this.controller.rimuoviContratto(Integer.toString(contratto.getID())));
    }

    void testRimuoviContratto() {
        assertTrue(this.controller.aggiungiImmobile(this.immobile));

        assertTrue(this.controller.aggiungiInquilino(this.inquilino));

        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(this.immobile.getID());
        Contratto contratto = cbuilder.build();

        assertTrue(this.controller.aggiungiContratto(contratto));

        // seleziono gli id di inquilino e contratto appena inseriti e verifico che rimuovendo inquilino venga
        // rimosso anche contratto
        List<Immobile> immobili = this.controller.getAllImmobili();
        for (Immobile i: immobili) {
            if(i.getIdProprietario().equals("A")) {
                this.immobile.setID(i.getID());
            }
        }
        List<Inquilino> inquilini = this.controller.getAllInquilini();
        for (Inquilino i: inquilini) {
            if(i.getCf().equals("EXAMPLE")) {
                this.inquilino.setID(i.getID());
            }
        }
        List<Contratto> contratti = this.controller.getAllContratti();
        for (Contratto c: contratti) {
            if(c.getCfProprietario().equals("EXAMPLE")) {
                contratto.setID(c.getID());
            }
        }
        assertTrue(this.controller.rimuoviContratto(Integer.toString(contratto.getID())));
        assertFalse(this.controller.rimuoviInquilino(Integer.toString(this.inquilino.getID())));
        assertTrue(this.controller.rimuoviImmobile(Integer.toString(this.immobile.getID())));
    }

    //testo l'aggiunta di una spesa al totale dovuto
    @Test
    void testAggiungiSpesa(){
        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
        // senza contratto l'inquilino non viene trovato con la select
        // e senza immobile non puoi creare un contratto
        assertTrue(this.controller.aggiungiImmobile(this.immobile));
        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(this.immobile.getID());
        Contratto contratto = cbuilder.build();
        assertTrue(this.controller.aggiungiContratto(contratto));

        List<Inquilino> inquilini = this.controller.getAllInquilini();
        for (Inquilino i: inquilini) {
            if(i.getCf().equals("EXAMPLE")) {
                assertEquals(this.inquilino.getTotaleDovuto(), i.getTotaleDovuto(), 0.0);
                this.inquilino.setID(i.getID());
            }
        }
        // verifichiamo che la spesa sia stata aggiunta al totale dovuto dall'inquilino
        controller.aggiungiSpesa(Integer.toString(inquilino.getID()),"600");
        inquilini = this.controller.getAllInquilini();
        for (Inquilino i: inquilini) {
            if(i.getID() == inquilino.getID()) {
                assertEquals(this.inquilino.getTotaleDovuto() + 600, i.getTotaleDovuto(), 0.0);
            }
        }
    }

    @Test
    void testAggiungiPagamento() {
        assertTrue(this.controller.aggiungiInquilino(this.inquilino));
        // senza contratto l'inquilino non viene trovato con la select
        // e senza immobile non puoi creare un contratto
        assertTrue(this.controller.aggiungiImmobile(this.immobile));
        ContrattoBuilder cbuilder = new ContrattoBuilder();
        cbuilder.proroga(true)
                .sfratto(true)
                .canone(1000)
                .prossimoPagamento("10")
                .dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfInquilino("EXAMPLE")
                .idImmobile(this.immobile.getID());
        Contratto contratto = cbuilder.build();
        assertTrue(this.controller.aggiungiContratto(contratto));

        List<Inquilino> inquilini = this.controller.getAllInquilini();
        for (Inquilino i : inquilini) {
            if (i.getCf().equals("EXAMPLE")) {
                assertEquals(this.inquilino.getTotalePagato(), i.getTotalePagato(), 0.0);
                this.inquilino.setID(i.getID());
            }
        }
        // verifichiamo che la spesa sia stata aggiunta al totale dovuto dall'inquilino
        controller.aggiungiPagamento(Integer.toString(inquilino.getID()), "600");
        inquilini = this.controller.getAllInquilini();
        for (Inquilino i : inquilini) {
            if (i.getID() == inquilino.getID()) {
                assertEquals(this.inquilino.getTotalePagato() + 600, i.getTotalePagato(), 0.0);
            }
        }
    }

}

>>>>>>> 2f73612ac39b550fbd0dac0c6d42fae16c3ef92f
