package view;

import controller.Controller;

import model.Contratto;
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
            System.out.println(" 4 - Visualizza/modifica inquilini");
            System.out.println(" 5 - Visualizza/modifica immobili");
            System.out.println(" 6 - Visualizza/modifica contratti");
            //System.out.println(" 7 - Visualizza resoconto pagamenti");
            System.out.println(" x - Torna al login");

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
        controller.visualizzaContratti();
    }

    private void displayVisualizzaImmobili() {
    }

    private void displayVisualizzaInquilini() {
        controller.visualizzaInquilini();
        boolean termina = false;

        while(!termina) {
            System.out.println("Vuoi modificare o rimuovere un inquilino?");
            System.out.println(" 1 - Modifica");
            System.out.println(" 2 - Rimuovi");
            System.out.println(" x - Indietro");

            String idInquilino;
            String input = scanner.next();
            String conferma;

            switch (input) {
                case "1":
                    System.out.println("Inserire l'ID dell'inquilino che desideri modificare");
                    System.out.print("ID: ");
                    idInquilino = scanner.next();
                    displayModificaInquilino();
                    break;
                case "2":
                    System.out.println("Inserire l'ID dell'inquilino che desideri rimuovere");
                    System.out.print("ID: ");
                    idInquilino = scanner.next();

                    System.out.print("Desideri rimuovere definitivamente l'inquilino con ID " + idInquilino +
                            " e il relativo contratto? (S/n)");
                    conferma = scanner.next();
                    if(conferma == "s" || conferma == "S") {
                        controller.rimuoviInquilino(idInquilino);
                        System.out.println("Rimosso inquilino con iD " + idInquilino);
                    }
                    break;
                case "x":
                    termina = true;
                    break;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
        // TODO: aggiungi operazioni di modifica
    }

    private void displayModificaInquilino() {
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
        boolean conferma = false;
        while (!conferma) {
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
            
            System.out.println("Confermi i dati inseriti? (s/n)");
            String confermaInput = scanner.next();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Inquilino i = new Inquilino(cf, nome, cognome, dataNascita, cittàNascita, residenza, telefono, email);
                controller.aggiungiInquilino(i);
                System.out.println("Inquilino aggiunto con successo.");
                conferma = true;
            }
        }
    }

}
