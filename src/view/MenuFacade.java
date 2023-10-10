package view;

import controller.Controller;


public class MenuFacade extends Menu {

    public MenuFacade() {
        super(new Controller());
    }

    public void display() {

        MenuLogin menuLogin = new MenuLogin(controller);
        menuLogin.display();
        if(controller.getProprietario() == null) {
            return;  // se non setto il proprietario nel menuLogin vuol dire che voglio uscire
        }

        boolean termina = false;
        System.out.println("Bentornato nell'app N° 1 di DESTE e VIGNOZ!");

        while(!termina){
            System.out.println("Scegliere quale menu visualizzare: ");
            System.out.println(" 1 - Menu operazioni utente");          // TODO: scegliere nome più appropriato
            System.out.println(" 2 - Menu gestione inquilini");
            System.out.println(" 3 - Menu gestione immobili");
            System.out.println(" 4 - Menu gestione contratti");
            System.out.println(" x - Chiudi programma");

            String input = scanner.next();

            switch (input) {
                case "1":
                    MenuUtente menuUtente = new MenuUtente(controller);
                    menuUtente.display();
                    continue;
                case "2":
                    MenuInquilini menuInquilini = new MenuInquilini(controller);
                    menuInquilini.display();
                    continue;
                case "3":
                    MenuImmobili menuImmobili = new MenuImmobili(controller);
                    menuImmobili.display();
                    continue;
                case "4":
                    MenuContratti menuContratti = new MenuContratti(controller);
                    menuContratti.display();
                    continue;
                case "x":
                    termina = true;
                    controller.reset();
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

}
