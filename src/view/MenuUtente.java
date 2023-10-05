package view;

import controller.Controller;

import model.Contratto;
import model.Immobile;
import model.ImmobileBuilder;
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
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi inquilino");      // FATTO
            System.out.println(" 2 - Aggiungi immobile");
            System.out.println(" 3 - Visualizza/modifica inquilini");       // IN CORSO...
            System.out.println(" 4 - Visualizza/modifica immobili");
            System.out.println(" 5 - Visualizza/modifica contratti");       // IN CORSO...
            //System.out.println(" 7 - Visualizza resoconto pagamenti");
            System.out.println(" x - Torna al login");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiInquilino();
                    continue;
                case "2":
                    displayAggiungiImmobile();
                    continue;
                case "3":
                    displayVisualizzaInquilini();
                    continue;
                case "4":
                    displayVisualizzaImmobili();
                    continue;
                case "5":
                    displayVisualizzaContratti();
                    continue;
                case "x":
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
        controller.visualizzaImmobili();
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
            String confermaInput;

            switch (input) {
                case "1":
                    System.out.println("Inserire l'ID dell'inquilino che desideri modificare");
                    System.out.print("ID: ");
                    idInquilino = scanner.next();
                    displayModificaInquilino();
                    break;
                case "2":
                    System.out.println("Inserire l'ID dell'inquilino che si desidera rimuovere");
                    System.out.print("ID: ");
                    idInquilino = scanner.next();

                    System.out.println("Rimuovere definitivamente l'inquilino con ID " + idInquilino +
                            " e il relativo contratto? (S/n)");
                    confermaInput = scanner.next();
                    
                    if(confermaInput.equals("s") || confermaInput.equals("S")) {
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

    private Immobile displayAggiungiImmobile() {
        boolean conferma=false;
        ImmobileBuilder builder = new ImmobileBuilder();

        while (!conferma) {
            System.out.println("Inserire i dati dell'immobile");
            System.out.print("Comune: ");
            String comune = scanner.next();
            System.out.print("Indirizzo: ");
            String indirizzo = scanner.next();
            System.out.print("Numero Civico: ");
            String nCivico = scanner.next();
            scanner.nextLine();
            System.out.print("Subalterno: ");
            String subalterno = scanner.next();
            System.out.print("Affittato (S/n): ");
            String confermaInput = scanner.next();
            boolean affittato;

            if(confermaInput.equals("s") || confermaInput.equals("S")) {
                affittato = true;
            }
            else {
                affittato = false;
            }

            builder.comune(comune).indirizzo(indirizzo).nCivico(nCivico).subalterno(Integer.parseInt(subalterno)).affittato(affittato);

            System.out.println("Vuoi aggiungere i dati catastali? (altrimenti sarà possibile aggiungerli più tardi dal menu utente)");
            System.out.print("S/n : ");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")){
                System.out.print("Foglio: ");
                String foglio = scanner.next();
                System.out.print("Particella: ");
                String particella = scanner.next();
                System.out.print("Categoria: ");
                String categoria = scanner.next();
                System.out.print("Classe: ");
                String classe = scanner.next();
                System.out.print("Superficie: ");
                String superficie = scanner.next();
                System.out.print("Rendita: ");
                String rendita = scanner.next();

                builder.foglio(Integer.parseInt(foglio)).particella(Integer.parseInt(particella)).categoria(categoria)
                        .classe(classe).superficie(Float.parseFloat(superficie)).rendita(Float.parseFloat(rendita));

            }

            System.out.println("Confermi i dati inseriti? (s/n)");
            confermaInput = scanner.next();

            Immobile immobile = builder.build();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.aggiungiImmobile(immobile);
                conferma = true;
                System.out.println("Immobile aggiunto con successo.");
                return immobile;
            }
        }
        return null;
    }

    private Contratto displayAggiungiContratto(Inquilino inquilino) {
        boolean conferma=false;
        String cfInquilino = inquilino.getCf();

        while (!conferma) {
            controller.visualizzaImmobili();
            System.out.println("Inserisci i dati del contratto");
            System.out.print("ID dell'immobile come indicato nella tabella: ");
            int idImmobile = scanner.nextInt();
            if(!controller.isImmobile(idImmobile)) {
                System.out.println("L'immobile selezionato non è presente nel database. Si desidera crearlo adesso? (S/n)");
                String confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    Immobile immobile = displayAggiungiImmobile();
                    if(immobile != null) {
                        idImmobile = immobile.getId();
                    }
                    else {
                        return null;
                    }
                }
                else {
                    System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                    confermaInput = scanner.next();

                    if (confermaInput.equals("s")) {
                        conferma = true;
                    }
                }
            }
            System.out.print("Data di inizio contratto (formato: YYYY-MM-DD): ");
            String dataInizio = scanner.next();
            System.out.print("Data di fine contratto (formato: YYYY-MM-DD): ");
            String dataFine = scanner.next();
            System.out.print("Canone mensile: ");
            String canone = scanner.next();

            System.out.println("Confermi inquilino dati inseriti? (s/n)");
            String confermaInput = scanner.next();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Contratto contratto = new Contratto(idImmobile, cfInquilino, controller.getCfProprietario(), dataInizio, dataFine, Float.parseFloat(canone));
                return contratto;
            }

            System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
            confermaInput = scanner.next();

            if (confermaInput.equals("s")) {
                conferma = true;
            }
        }
        return null;
    }

    private void displayAggiungiInquilino() {
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

            System.out.println("Confermi i dati inseriti? (s/n)");
            String confermaInput = scanner.next();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Inquilino i = new Inquilino(cf, nome, cognome, dataNascita, cittàNascita, residenza, telefono, email);
                Contratto c = displayAggiungiContratto(i);
                if(c != null) {
                    controller.aggiungiInquilino(i);
                    controller.aggiungiContratto(c);
                    System.out.println("Inquilino e relativo contratto aggiunti con successo.");
                }
                return;
            }

            System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                conferma = true;
            }
        }
    }

}
