package view;

import com.sun.jdi.request.InvalidRequestStateException;

import java.util.Scanner;

public class MenuFacade {
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
                case 2:
                    displayAccesso();
                case 3:
                    terminate = true;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public void displayAccesso(){
        System.out.println("Inserire mail e password per accedere");
        System.out.println("Mail: ");
        String utente = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();

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
                    break;
                case 2:
                    break;
                case 3:
                    terminate = true;
                default:
            }
        }
    }
}
