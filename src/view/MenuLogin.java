package view;

import controller.Controller;
import model.Proprietario;


public class MenuLogin extends Menu {

    public MenuLogin(Controller controller) {
        super(controller);
    }

    public void display() {

        System.out.println("Benvenuto nell'app di gestione immobili by DESTE e VIGNOZ!");
        boolean termina = false;

        while(!termina) {
            System.out.println("Digita uno dei seguenti numeri per scegliere l'operazione:");
            System.out.println(" 1 - Accedi ");
            System.out.println(" 2 - Registrati (nuovo utente)");
            System.out.println(" x - Esci");
            String input = scanner.next();

            switch (input) {
                case "1":
                    if(displayAccesso()) {
                        termina = true;
                    }
                    continue;
                case "2":
                    displayRegistrazione();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public boolean displayAccesso() {

        boolean termina = false;
        char[] riprova = {'0'};            // inizializzo a 0, mi serve per metterci la scelta nell'else

        while (!termina) {
            System.out.println("Inserire email e password per accedere");

            System.out.print("Email: ");
            String email = scanner.next();

            System.out.print("Password: ");
            String password = scanner.next();

            if (controller.isUtente(email, password)) {
                controller.setProprietario(email, password);
                return true;

            } else {
                System.out.println("Email o password errate.");
                System.out.println("Riprovare? (S/n)");
                // se si sceglie qualcosa che inizia con 's' si riprova, altrimenti si esce
                // quindi scannerizzo l'input, lo rendo minuscolo e estraggo il carattere in prima posizione mettendolo in riprova
                scanner.next().toLowerCase().getChars(0, 1, riprova, 0);
                if (riprova[0] != 's') {
                    termina = true;
                    return false;
                }
            }
        }
        return false;
    }

    public void displayRegistrazione() {

        boolean verificato = false;
        char[] riprova = {'0'};

        String email = "";
        while (!verificato) {
            System.out.print("Email: ");
            email = scanner.next();
            if (!controller.emailDisponibile(email)) {
                System.out.println("Email già associata a un account. Riprovare? (S/n)");
                // Ho aggiunto lo stesso giochino del riprova per evitare loop infiniti di email non disponibili
                scanner.next().toLowerCase().getChars(0, 1, riprova, 0);
                if (riprova[0] != 's') {
                    return;
                };

            } else {
                verificato = true;
            }
        }

        System.out.print("Password: ");
        String pwd = scanner.next();
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Codice fiscale: ");
        String cf = scanner.next();

        Proprietario p = new Proprietario(email, pwd, nome, cognome, cf);
        controller.aggiungiUtente(p);

        System.out.println("Registrazione avvenuta con successo");
    }

}
