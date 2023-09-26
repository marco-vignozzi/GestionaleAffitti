package view;

import controller.Controller;
import model.Proprietario;

import java.util.Scanner;

public class MenuProprietario {
    // nome e cognome servono per ora solo a mostrare i nomi nel benvenuto
    // TODO: più avanti probabilmente servirà tutta la struttura dati Proprietario, come facciamo?
    public void display(String nome, String cognome) {
        // TODO: finire di implementare
        boolean termina = false;

        while(!termina) {
            System.out.println("Bentornato " + nome + " " + cognome);
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi inquilino");
            System.out.println(" 2 - Aggiungi contratto");
            System.out.println(" 3 - Aggiungi immobile");
            System.out.println(" 5 - Visualizza inquilini");
            System.out.println(" 6 - Visualizza/modifica immobili");
            System.out.println(" 6 - Visualizza/modifica contratti");
            System.out.println(" 7 - Visualizza resoconto pagamenti");
            System.out.println(" 8 - Modifica immobile");
            System.out.println(" 0 - Chiudi programma");


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
            }
        }
    }

    Controller controller = new Controller();
    Scanner scanner = new Scanner(System.in);

}
