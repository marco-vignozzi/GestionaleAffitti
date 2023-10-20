package view;

import controller.Controller;
import model.*;

public class MenuInquilini extends Menu {

    private static String listaOpzioniModifica = " 1 - Codice fiscale\n" +
            " 2 - Nome\n" +
            " 3 - Cognome\n" +
            " 4 - Data di nascita\n" +
            " 5 - Città di nascita\n" +
            " 6 - Residenza\n" +
            " 7 - Telefono\n" +
            " 8 - Email\n" +
            " 9 - Totale dovuto\n" +
            " 10 - Totale pagato\n" +
            " x - Applica modifiche";

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
                    displayModificaInquilino();
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
        controller.visualizzaInquilini();
        String idInquilino;
        String confermaInput;

        System.out.println("Inserire l'ID dell'inquilino che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        idInquilino = scanner.next();

        if(controller.isInquilino(idInquilino)) {
            boolean termina=false;
            String input;
            InquilinoBuilder builder = new InquilinoBuilder();

            while(!termina){
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                switch (input){
                    case "1":
                        System.out.print("Inserire il nuovo codice fiscale: ");
                        input = scanner.next();
                        builder.cf(input);
                        continue;
                    case "2":
                        System.out.print("Inserire il nuovo nome: ");
                        input = scanner.next();
                        builder.nome(input);
                        continue;
                    case "3":
                        System.out.print("Inserire il nuovo cognome: ");
                        input = scanner.next();
                        builder.cognome(input);
                        continue;
                    case "4":
                        System.out.print("Inserire la nuova data di nascita: ");
                        input = scanner.next();
                        builder.dataNascita(input);
                        continue;
                    case "5":
                        System.out.print("Inserire la nuova città di nascita: ");
                        input = scanner.next();
                        builder.cittàNascita(input);
                        continue;
                    case "6":
                        System.out.print("Inserire la nuova residenza: ");
                        input = scanner.next();
                        builder.residenza(input);
                        continue;
                    case "7":
                        System.out.print("Inserire il nuovo telefono: ");
                        input = scanner.next();
                        builder.telefono(input);
                        continue;
                    case "8":
                        System.out.print("Inserire la nuova email: ");
                        input = scanner.next();
                        builder.email(input);
                        continue;
                    case "9":
                        System.out.print("Inserire il nuovo totale dovuto: ");
                        input= scanner.next();
                        builder.totaleDovuto(Float.parseFloat(input));
                        continue;
                    case "10":
                        System.out.print("Inserire il nuovo totale pagato: ");
                        input = scanner.next();
                        builder.totalePagato(Float.parseFloat(input));
                        continue;
                    case "x":
                        termina = true;
                        continue;
                    default:
                        System.out.println("Valore non valido");
                }
                System.out.println("Applicare le modifiche all'inquilino con ID " + idInquilino + "?");
                confermaInput=scanner.next();
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    Inquilino inquilino = builder.build();
                    controller.modificaInquilino(idInquilino, inquilino);
                    System.out.println("Inquilino modificato");
                }
            }
        }
        else {
            System.out.println("Non esiste nessun inquilino con l'ID selezionato.");
        }
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
