package view;

import java.util.Scanner;
import controller.Controller;
import model.Proprietario;


public class MenuFacade {
    public void display() {
//        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenuto nell'app numero 1 di deste e vigno!");
        boolean termina = false;

        while(!termina) {
            System.out.println("Digita uno dei seguenti numeri:");
            System.out.println(" 1 - Accedi");
            System.out.println(" 2 - Registrati");
            System.out.println(" 3 - Visualizza immobili in affitto");
            System.out.println(" 0 - Esci");
            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAccesso();
                    continue;
                case "2":
                    displayRegistrazione();
                    continue;
                case "3":
                    controller.visualizzaImmobili();
                    continue;
                case "0":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public void displayAccesso() {

        boolean termina = false;
        char[] riprova = {'0'};            // inizializzo a 0, mi serve per metterci la scelta nell'else

        while(!termina) {
            System.out.println("Inserire email e password per accedere");

            System.out.print("Email: ");
            String email = scanner.next();

            System.out.print("Password: ");
            String password = scanner.next();

            if (controller.isInquilino(email, password)) {

                MenuInquilino menuInquilino = new MenuInquilino();
                menuInquilino.display(controller.getNome(email, password), controller.getCognome(email, password));

            } else if (controller.isProprietario(email, password)) {

                MenuProprietario menuProprietario = new MenuProprietario();
                menuProprietario.display(controller.getNome(email, password), controller.getCognome(email, password));

            } else {

                System.out.println("Email o password errate.");
                System.out.println("Riprovare? (Sì/no)");
            // se si sceglie qualcosa che inizia con 's' si riprova, altrimenti si esce
    // quindi scannerizzo l'input, lo rendo minuscolo e estraggo il carattere in prima posizione mettendolo in riprova
                scanner.next().toLowerCase().getChars(0, 1, riprova, 0);
                if(riprova[0] != 's') {
                    termina = true;
                }

            }
        }

    }

    public void displayRegistrazione() {
        System.out.println("E' arrivato il momento di scegliere da che parte stai.");
        boolean terminate = false;

        while(!terminate) {
            System.out.println("Sei un proprietario (1) o un servo della gleba (2)? ");
            System.out.println("Digita (0) per tornare al log-in.");
            input = scanner.nextInt();

// ho messo tutto l'inserimento dati da terminale nel metodo aggiungiUtente del controller, guarda che bello :)(:
            switch (input) {
                case 1:
                    controller.aggiungiUtente("proprietario");
                    continue;
                case 2:
                    controller.aggiungiUtente("inquilino");
                    continue;
                case 0:
                    terminate = true;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    private final Controller controller = new Controller();
    private final Scanner scanner = new Scanner(System.in);
    int input;

}
