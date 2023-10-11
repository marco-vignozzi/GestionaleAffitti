package view;

import controller.Controller;
import model.Contratto;
import model.Inquilino;

public class MenuInquilini extends Menu {

    public MenuInquilini(Controller controller) {
        super(controller);
    }

    public void display() {

        boolean termina = false;

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi inquilino");
            System.out.println(" 2 - Mostra tabella inquilini");
            System.out.println(" 3 - Modifica inquilino");
            System.out.println(" 4 - Rimuovi inquilino");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiInquilino();
                    continue;
                case "2":
                    controller.visualizzaInquilini();
                    continue;
                case "3":
                    displayModificaInquilino();         // TODO: implementare
                    continue;
                case "4":
                    displayRimuoviInquilino();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public Inquilino displayAggiungiInquilino() {
        boolean conferma = false;
        while (!conferma) {
            System.out.println("Dopo aver aggiunto i dati dell'inquilino verrà richiesto " +
                    "di inserire i dati del relativo contratto");
            System.out.println("Inserire i dati dell'inquilino");
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

            System.out.println("Confermi i dati inseriti? (S/n)");
            String confermaInput = scanner.next();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Inquilino inquilino = new Inquilino(cf, nome, cognome, dataNascita, cittàNascita, residenza, telefono, email);
                MenuContratti menuContratti = new MenuContratti(controller);
                Contratto contratto = menuContratti.displayAggiungiContratto(inquilino);
                if(contratto != null) {
                    controller.aggiungiInquilino(inquilino);
                    controller.aggiungiContratto(contratto);
                    System.out.println("Inquilino e relativo contratto aggiunti con successo.");
                    return inquilino;
                }
                return null;
            }

            System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                conferma = true;
            }
        }
        return null;
    }

    public void displayModificaInquilino() {

    }

    public void displayRimuoviInquilino() {
        String idInquilino;
        String confermaInput;

        System.out.println("Inserire l'ID dell'inquilino che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idInquilino = scanner.next();

        if(controller.isInquilino(idInquilino)) {
            System.out.println("Rimuovere definitivamente l'inquilino con ID " + idInquilino +
                    " e il relativo contratto? (S/n)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.rimuoviInquilino(idInquilino);
                System.out.println("Rimosso inquilino con ID " + idInquilino);
            }
        }
        else {
            System.out.println("Non esiste nessun inquilino con l'ID selezionato.");
        }
    }

}
