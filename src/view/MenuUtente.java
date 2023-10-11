package view;

import controller.Controller;


public class MenuUtente extends Menu {


    public MenuUtente(Controller controller) {
        super(controller);
    }

    public void display() {

        boolean termina = false;

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Invia sollecito pagamento");
            System.out.println(" 2 - Invia email a un inquilino");
            System.out.println(" 3 - Aggiungi pagamento inquilino");
            System.out.println(" 4 - Aggiungi spesa inquilino");
            System.out.println(" 5 - Mostra tabella resoconto");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
//                    controller.inviaSollecito();
                    continue;
                case "2":
//                    displayInviaEmail();
                    continue;
                case "3":
                    displayPagamento();
                    continue;
                case "4":
  //                  displaySpesa();
                    continue;
                case "5":
   //                 controller.visualizzaResoconto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
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
