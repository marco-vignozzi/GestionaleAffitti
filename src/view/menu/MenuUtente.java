package view.menu;

import controller.Controller;
import model.Inquilino;
import view.table.TabellaResoconto;

import java.util.ArrayList;
import java.util.List;


public class MenuUtente extends Menu {
    TabellaResoconto tabellaResoconto;

    public MenuUtente(Controller controller) {
        super(controller);
        tabellaResoconto = new TabellaResoconto();
    }

    public void display() {

        boolean termina = false;

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Invia sollecito di pagamento a tutti ");
            System.out.println(" 2 - Invia sollecito di pagamento a un inquilino");
            System.out.println(" 3 - Aggiungi pagamento inquilino");
            System.out.println(" 4 - Aggiungi spesa inquilino");
            System.out.println(" 5 - Mostra tabella resoconto");
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
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
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
