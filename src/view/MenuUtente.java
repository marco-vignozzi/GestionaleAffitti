package view;

import controller.Controller;
import model.Inquilino;


public class MenuUtente extends Menu {


    public MenuUtente(Controller controller) {
        super(controller);
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
                    displaySollecitoMultiplo();
                    continue;
                case "2":
                    displaySollecitoSingolo();
                    continue;
                case "3":
                    displayPagamento();
                    continue;
                case "4":
                    displaySpesa();
                    continue;
                case "5":
                    controller.visualizzaResoconto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    private void displaySollecitoMultiplo() {
        controller.visualizzaInquilini();
        System.out.println("Verrà inviato un sollecito di pagamento agli inquilini con ID: ");
        Inquilino[] inquilini = controller.getInquiliniSollecito();
        for(int i=0; i< inquilini.length; i++) {
            if(i+1 == inquilini.length) {
                System.out.println(inquilini[i].getID());
            }else {
                System.out.print(inquilini[i].getID() + " ,");
            }
        }
        System.out.print("Continuare? (S/n) ");
        String confermaInput = scanner.next();
        if(confermaInput.equals("s") || confermaInput.equals("S")) {
            controller.inviaSollecito(inquilini);
        }
    }

    private void displaySollecitoSingolo() {
        controller.visualizzaInquilini();
        System.out.print("Selezionare l'ID dell'inquilino a cui si desidera inviare un sollecito di pagamento: ");
        String idInquilino = scanner.next();
        if(controller.isInquilino(idInquilino)) {
            Inquilino[] inquilino = new Inquilino[1];
            inquilino[0] = controller.getInquilino(idInquilino);
            controller.inviaSollecito(inquilino);
        }else {
            System.out.println("L'ID inserito non è associato a nessun inquilino.");
        }
    }

    private void displaySpesa() {
        controller.visualizzaInquilini();
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
        controller.visualizzaInquilini();
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
