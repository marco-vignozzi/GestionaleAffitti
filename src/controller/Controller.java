package controller;

import dao.DatabaseDAO;
import model.Inquilino;
import model.Proprietario;

import java.util.Objects;
import java.util.Scanner;

public class Controller {

    public Controller() {
        dao = new DatabaseDAO();
        dao.connect();
    }

    // qui passo tipo utente come parametro per decidere se è un proprietario o un inquilino
    public boolean aggiungiUtente(String tipoUtente) {

        System.out.print("Email: ");
        String email = scanner.next();
        if(!emailDisponibile(email)) {
            System.out.println("Email già associata a un account.");
            return false;
        }
        System.out.print("Password: ");
        String pwd = scanner.next();
        System.out.print("Nome: ");
        String nome = scanner.next();
        System.out.print("Cognome: ");
        String cognome = scanner.next();
        System.out.print("Codice fiscale: ");
        String cf = scanner.next();
        System.out.print("Data di nascita: ");
        String dataNascita = scanner.next();
        System.out.print("Città di nascita: ");
        String cittàNascita = scanner.next();
        System.out.print("Residenza: ");
        String residenza = scanner.next();
        System.out.print("Telefono: ");
        String tel = scanner.next();

        if(Objects.equals(tipoUtente, "proprietario")) {
            Proprietario p = new Proprietario(email, pwd, nome, cognome, cf, dataNascita, cittàNascita, residenza, tel);
            // TODO: implementare dao.aggiungiProprietario(p);
        }
        else if (Objects.equals(tipoUtente, "inquilino")){
            Inquilino i = new Inquilino(email, pwd, nome, cognome, cf, dataNascita, cittàNascita, residenza, tel);
            // TODO: implementare dao.aggiungiInquilino(p);
        }
        return true;
    }

    public boolean emailDisponibile(String email) {
        return dao.emailDisponibile(email);
    }

    public void aggiungiinquilino(Inquilino inquilino) {
        // TODO: implementare
    }

    public boolean isInquilino(String email, String password) {
        return false;       // TODO: implementare
    }

    public boolean isProprietario(String email, String password) {
        return false;       // TODO: implementare
    }

    public String getCognome(String email, String password) {
        return null;        // TODO: implementare
    }

    public String getNome(String email, String password) {
        return null;        // TODO: implementare
    }

    // questo metodo permette di pagare l'affitto dall'app
    public void pagaAffitto() {
        // TODO: implementare
    }

    // questo metodo permette di visualizzare il contratto di affitto
    public void visualizzaContratto() {
        // TODO: implementare
    }

    // questo metodo permette di visualizzare le notifiche
    public void visualizzaNotifiche() {
        // TODO: implementare
    }

    // questo metodo permette di visualizzare tutti gli immobili disponibili per l'affitto
    public void visualizzaImmobili() {
        // TODO: implementare
    }

    private Scanner scanner;
    private final DatabaseDAO dao;
    private Inquilino inquilino;
    private Proprietario proprietario;
}
