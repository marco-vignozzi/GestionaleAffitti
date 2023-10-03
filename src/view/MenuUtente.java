package view;

import controller.Controller;
import model.Contratto;

import java.util.Scanner;

public class MenuUtente {

    // nome e cognome servono per ora solo a mostrare i nomi nel benvenuto
    // TODO: più avanti probabilmente servirà tutta la struttura dati Proprietario, come facciamo?
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
            System.out.println(" 0 - Chiudi programma");


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
        boolean conferma=false;

        while (!conferma) {
            System.out.println("Inserisci i dati del contratto");
            String cfProprietario = controller.getCfProprietario();
            System.out.println("Il tuo codice fiscale è: " + cfProprietario);
            System.out.print("Inserisci il codice fiscale dell'inquilino: ");
            String cfInquilino = scanner.next();
            System.out.print("Inserisci l'id dell'immobile: ");
            String idImmobile = scanner.next();
            System.out.print("Inserisci la data di inizio del contratto (formato: YYYY-MM-DD): ");
            String dataInizio = scanner.next();
            System.out.print("Inserisci la data di fine del contratto (formato: YYYY-MM-DD): ");
            String dataFine = scanner.next();
            System.out.print("Inserisci il canone mensile: ");
            float canone = scanner.nextFloat();
            System.out.println("Confermi i dati inseriti? (s/n)");
            String confermaInput = scanner.next();
            if (confermaInput.equals("s")) {
                Contratto contratto= new Contratto(idImmobile, cfInquilino, cfProprietario, dataInizio, dataFine, canone);
                controller.aggiungiContratto(contratto);
                conferma = true;
            }

    }
    }

    private void displayAggiungiInquilino() {
    }

}
