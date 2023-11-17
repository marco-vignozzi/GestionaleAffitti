package view.menu;

import controller.Controller;
import model.Inquilino;
import model.InquilinoBuilder;
import model.Proprietario;


import java.util.ArrayList;
import java.util.List;


public class MenuUtente extends Menu {
    private static String listaOpzioniModifica = " 1 - Nome\n" +
            " 2 - Cognome\n" +
            " 3 - Email\n" +
            " 4 - Password\n" +
            " x - Applica modifiche";



    public MenuUtente(Controller controller) {
        super(controller);

    }

    public void display() {
        return;             // solo per avere una signature coerente con la superclasse
    }

    public boolean display(int n) {

        boolean termina = false;

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Invia sollecito di pagamento a tutti ");
            System.out.println(" 2 - Invia sollecito di pagamento a un inquilino");
            System.out.println(" 3 - Aggiungi pagamento inquilino");
            System.out.println(" 4 - Aggiungi spesa inquilino");
            System.out.println(" 5 - Mostra tabella resoconto");
            System.out.println(" 6 - Modifica dati utente");
            System.out.println(" 7 - Elimina utente");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displaySollecitoAuto();
                    continue;
                case "2":
                    displaySollecitoManuale();
                    continue;
                case "3":
                    displayPagamento();
                    continue;
                case "4":
                    displaySpesa();
                    continue;
                case "5":
                    controller.mostraResoconto();
                    continue;
                case "6":
                    displayModifica();
                    continue;
                case "7":
                    if(displayRimuovi()) {
                        controller.reset();
                        return true;    // se ritorno true vuol dire che ho eliminato l'utente
                    }
                     continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
        return false;
    }

    private boolean displayRimuovi() {
        System.out.println("Desideri rimuovere definitivamente l'utente con codice fiscale " +
                controller.getProprietario().getCf() + " e tutti i dati ad esso associato?(S/n) ");
        String confermaInput = scanner.next();

        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            controller.rimuoviProprietario();
            System.out.println("Utente rimosso dal sistema");
            return true;
        }
        return false;
    }

    private void displayModifica() {
        String confermaInput;
        String input;
        boolean termina = false;
        Proprietario proprietario = controller.getProprietario();

        while(!termina) {

            System.out.println("Scegliere l'attributo che si desidera modificare:");
            System.out.println(listaOpzioniModifica);
            input = scanner.next();
            switch (input) {
                case "1":
                    System.out.print("Inserire il nuovo nome: ");
                    input = scanner.next();
                    proprietario.setNome(input);
                    continue;
                case "2":
                    System.out.print("Inserire il nuovo cognome: ");
                    input = scanner.next();
                    proprietario.setCognome(input);
                    continue;
                case "3":
                    System.out.print("Inserire il nuovo indirizzo email: ");
                    input = scanner.next();
                    proprietario.setEmail(input);
                    continue;
                case "4":
                    System.out.print("Inserire la nuova password: ");
                    input = scanner.next();
                    proprietario.setPassword(input);
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore non valido");
            }
        }

        System.out.println("Applicare le modifiche all'utente?");
        confermaInput=scanner.next();
        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            controller.modificaProprietario(proprietario);
            System.out.println("Utente modificato");
        }
        else {
            System.out.println("Operazione annullata");
        }
    }

    private void displaySollecitoAuto() {
        controller.mostraInquilini();
        System.out.println("Verrà inviato un sollecito di pagamento agli inquilini con ID: ");
        List<Inquilino> inquilini = controller.getInquiliniSollecito();
        for(int i=0; i< inquilini.size(); i++) {
            if(i+1 == inquilini.size()) {
                System.out.println(inquilini.get(i).getID());
            }else {
                System.out.print(inquilini.get(i).getID() + ", ");
            }
        }
        System.out.print("Continuare? (S/n) ");
        String confermaInput = scanner.next();
        if(confermaInput.equals("s") || confermaInput.equals("S")) {
            controller.inviaSollecito(inquilini);
        }
    }

    private void displaySollecitoManuale() {
        controller.mostraInquilini();
        List<Inquilino> inquilini = new ArrayList<>();
        boolean termina = false;

        while(!termina) {
            System.out.print("Selezionare l'ID dell'inquilino a cui si desidera inviare un sollecito di pagamento: ");
            String idInquilino = scanner.next();
            if (controller.isInquilino(idInquilino)) {
                inquilini.add(controller.getInquilino(idInquilino));
            } else {
                System.out.println("L'ID inserito non è associato a nessun inquilino.");
            }
            System.out.print("Selezionare altri inquilini? (S/n) ");
            String confermaInput = scanner.next();
            if(!confermaInput.equals("S") && !confermaInput.equals("s")) {
                termina = true;
                System.out.print("Verrà inviata una email di sollecito agli inquilini con ID ");
                for(int i=0; i< inquilini.size(); i++) {
                    if(i+1 == inquilini.size()) {
                        System.out.println(inquilini.get(i).getID());
                    }else {
                        System.out.print(inquilini.get(i).getID() + ", ");
                    }
                }
                System.out.print("Continuare ? (S/n) ");
                confermaInput = scanner.next();
                if(confermaInput.equals("S") || confermaInput.equals("s")) {
                    controller.inviaSollecito(inquilini);
                }
            }
        }
    }

    private void displaySpesa() {
        controller.mostraInquilini();
        System.out.print("Inserire l'ID dell'inquilino al quale addebitare la spesa (come indicato in tabella): ");
        String idInquilino = scanner.next();
        if(controller.isInquilino(idInquilino)) {
            System.out.print("Inserire la cifra da addebitare: ");
            String spesa = scanner.next();
            System.out.print("Confermi di voler aggiungere una spesa di " +
                    spesa + " euro a carico dell'inquilino con ID " + idInquilino + "? (S/n) ");
            String confermaInput = scanner.next();
            if(confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.aggiungiSpesa(idInquilino, spesa);
            }
        }else {
            System.out.println("L'ID selezionato non è associato a nessun inquilino");
        }

    }

    private void displayPagamento() {
        controller.mostraInquilini();
        System.out.print("Inserire l'ID dell'inquilino del quale si vuole aggiungere il pagamento (come indicato in tabella): ");
        String idInquilino = scanner.next();
        if(controller.isInquilino(idInquilino)) {
            System.out.print("Inserire la cifra versata: ");
            String pagamento = scanner.next();
            System.out.print("Confermi di voler aggiungere un pagamento di " +
                    pagamento + " euro a carico dell'inquilino con ID " + idInquilino + "? (S/n) ");
            String confermaInput = scanner.next();
            if(confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.aggiungiPagamento(idInquilino, pagamento);
            }
        }else {
            System.out.println("L'ID selezionato non è associato a nessun inquilino");
        }
    }

}
