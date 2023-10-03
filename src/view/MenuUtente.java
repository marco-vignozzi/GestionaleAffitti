package view;

import controller.Controller;
import dao.InquilinoDAO;
import model.Inquilino;

import java.util.Scanner;

public class MenuUtente {

    Controller controller;
    Scanner scanner = new Scanner(System.in);

    public MenuUtente(Controller controller) {
        this.controller = controller;
    }

    public void display() {
        // TODO: finire di implementare
        boolean termina = false;

        while (!termina) {
            System.out.println("Bentornato " + controller.getNomeProprietario() + " " + controller.getCognomeProprietario() + "!");
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi inquilino");
            System.out.println(" 2 - Aggiungi contratto");
            System.out.println(" 3 - Aggiungi immobile");
            System.out.println(" 4 - Visualizza inquilini");
            System.out.println(" 5 - Visualizza/modifica immobili");
            System.out.println(" 6 - Visualizza/modifica contratti");
            //System.out.println(" 7 - Visualizza resoconto pagamenti");
            System.out.println(" x - Chiudi programma");


            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiInquilino();
                    continue;
                case "2":
                    displayAggiungiContratto();
                    continue;
                case "3":
                    displayAggiungiImmobile();
                    continue;
                case "4":
                    displayVisualizzaInquilini();
                    continue;
                case "5":
                    displayVisualizzaImmobili();
                    continue;
                case "6":
                    displayVisualizzaContratti();
                    continue;
                case "0":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    private void displayVisualizzaContratti() {
    }

    private void displayVisualizzaImmobili() {
    }

    private void displayVisualizzaInquilini() {
    }

    private void displayAggiungiImmobile() {
    }

    private void displayAggiungiContratto() {
    }

    private void displayAggiungiInquilino() {
        System.out.println("Inserire i seguenti dati");
        System.out.print("Nome: ");
        String nome = scanner.next();
        scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Codice fiscale: ");
        String cf = scanner.nextLine();
        System.out.print("Data di nascita (formato: YYYY-MM-DD): ");
        String dataNascita = scanner.nextLine();
        System.out.print("Città di nascita: ");
        String cittàNascita = scanner.nextLine();
        System.out.print("Residenza: ");
        String residenza = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Inquilino i = new Inquilino(cf, nome, cognome, dataNascita, cittàNascita, residenza, telefono, email);
        controller.aggiungiInquilino(i);
        System.out.println("Inquilino aggiunto con successo.");
    }

}
