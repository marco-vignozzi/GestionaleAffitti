package view;

import java.util.Objects;
import java.util.Scanner;

import controller.Controller;
import model.Inquilino;
import model.Proprietario;


public class MenuFacade {
    public void display() {
//        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenuto nell'app numero 1 di deste e vigno!");
        boolean termina = false;

<<<<<<< HEAD
        while (!termina) {
            System.out.println("Digita uno dei seguenti numeri:");
=======
        while(!termina) {
            System.out.println("Digita uno dei seguenti numeri per scegliere l'operazione:");
>>>>>>> a5e0514 (Pre rebase)
            System.out.println(" 1 - Accedi");
            System.out.println(" 2 - Registra nuovo utente");
            System.out.println(" 0 - Esci");
            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAccesso();
                    continue;
                case "2":
                    displayRegistrazione();
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

        while (!termina) {
            System.out.println("Inserire email e password per accedere");

            System.out.print("Email: ");
            String email = scanner.next();

            System.out.print("Password: ");
            String password = scanner.next();

            if (controller.utenteValido(email, password)) {

                controller.setProprietario(email, password);

            } else {

                System.out.println("Email o password errate.");
                System.out.println("Riprovare? (Sì/no)");
                // se si sceglie qualcosa che inizia con 's' si riprova, altrimenti si esce
                // quindi scannerizzo l'input, lo rendo minuscolo e estraggo il carattere in prima posizione mettendolo in riprova
                scanner.next().toLowerCase().getChars(0, 1, riprova, 0);
                if (riprova[0] != 's') {
                    termina = true;
                }

            }
        }

    }

    public void displayRegistrazione() {
        boolean verificato = false;
        String email = "";
        while (!verificato) {
            System.out.print("Email: ");
            email = scanner.next();
            if (!controller.emailDisponibile(email)) {
                System.out.println("Email già associata a un account.");
            } else {
                verificato = true;
            }
        }
        System.out.print("Password: ");
        String pwd = scanner.next();
        System.out.print("Nome: ");
        String nome = scanner.next();
        System.out.print("Cognome: ");
        String cognome = scanner.next();
        System.out.print("Codice fiscale: ");
        String cf = scanner.next();

        Proprietario p = new Proprietario(email, pwd, nome, cognome, cf);
        controller.aggiungiUtente(p);
        // TODO: implementare dao.aggiungiProprietario(p);
    }


    private final Controller controller = new Controller();
    private final Scanner scanner = new Scanner(System.in);
    int input;

}
