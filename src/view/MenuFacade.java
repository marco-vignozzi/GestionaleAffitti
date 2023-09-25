package view;

import java.util.Scanner;
import controller.Controller;
import model.Proprietario;


public class MenuFacade {
    private Controller controller;
    private Scanner scanner = new Scanner(System.in);
    int input;

    public void display() {
//        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenuto nell'app numero 1 di deste e vigno!");
        boolean terminate = false;

        while(!terminate) {
            System.out.print("Digita 1 per registrarti, 2 per accedere o 3 per uscire: ");
            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    displayRegistrazione();
                    continue;
                case 2:
                    displayAccesso();
                    continue;
                case 3:
                    terminate = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public void displayAccesso() {
        System.out.println("Inserire mail e password per accedere");

        System.out.print("Mail: ");
        String utente = scanner.next();

        System.out.print("Password: ");
        String password = scanner.next();
    }

    public void displayRegistrazione() {
        System.out.println("E' arrivato il momento di scegliere da che parte stai.");
        boolean terminate = false;

        while(!terminate) {
            System.out.println("Sei un proprietario (1) o un servo della gleba (2)? ");
            System.out.println("Digita 3 per tornare al log-in.");
            input = scanner.nextInt();

            switch (input) {
                case 1:
                    System.out.print("Email: ");
                    String email = scanner.next();
                    if(!controller.emailDisponibile(email)) {
                        System.out.println("Email già associata a un account.");
                        continue;
                    }
                    System.out.print("Password: ");
                    String pwd = scanner.next();
                    System.out.print("Nome: ");
                    String nome = scanner.next();
                    System.out.print("Cognome: ");
                    String cognome = scanner.next();
                    System.out.print("Codice fiscale: ");
                    String cf = scanner.next();
                    System.out.print("Data di nascita: ");
                    String dataNascita = scanner.next();
                    System.out.print("Città di nascita: ");
                    String cittàNascita = scanner.next();
                    System.out.print("Residenza: ");
                    String residenza = scanner.next();
                    System.out.print("Telefono: ");
                    String tel = scanner.next();
                    Proprietario p = new Proprietario(email, pwd, nome, cognome, cf, dataNascita, cittàNascita, residenza, tel);
                    controller.aggiungiproprietario(p);
                    continue;
                case 2:
                    break;
                case 3:
                    terminate = true;
                default:
            }
        }
    }
}
