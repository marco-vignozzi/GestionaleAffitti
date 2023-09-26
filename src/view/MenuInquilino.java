package view;

import controller.Controller;

import java.util.Scanner;

public class MenuInquilino {

    public void display(String nome, String cognome){
                // TODO: implementare
        boolean termina = false;

        while(!termina) {
            System.out.println("Bentornato " + nome + " " + cognome);
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Paga canone mensile");
            System.out.println(" 2 - Visualizza contratto di affitto");
            System.out.println(" 3 - Visualizza notifiche");
            System.out.println(" 4 - Chiudi programma");
            String input = scanner.next();

            switch (input) {
                case "1":
                    controller.pagaAffitto();
                    continue;
                case "2":
                    controller.visualizzaContratto();
                    continue;
                case "3":
                    controller.visualizzaNotifiche();
                    continue;
                case "4":
                    System.out.println("Arrivederci");
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
                    continue;
            }
        }
    }

    private final Controller controller = new Controller();
    private final Scanner scanner = new Scanner(System.in);
}
