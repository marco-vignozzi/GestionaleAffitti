package view;

import controller.Controller;
import model.Immobile;
import model.ImmobileBuilder;


public class MenuImmobili extends Menu {

    public MenuImmobili(Controller controller) {
        super(controller);
    }

    public void display() {

        boolean termina = false;

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi immobile");
            System.out.println(" 2 - Mostra tabella immobili");
            System.out.println(" 3 - Modifica immobile");
            System.out.println(" 4 - Rimuovi immobile");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiImmobile();
                    continue;
                case "2":
                    controller.visualizzaImmobili();
                    continue;
                case "3":
                    displayModificaImmobile();         // TODO: implementare
                    continue;
                case "4":
                    displayRimuoviImmobile();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public Immobile displayAggiungiImmobile() {
        boolean termina = false;
        ImmobileBuilder builder = new ImmobileBuilder();

        while (!termina) {
            System.out.println("Inserire i dati dell'immobile");
            System.out.print("Comune: ");
            String comune = scanner.next();
            System.out.print("Indirizzo: ");
            String indirizzo = scanner.next();
            System.out.print("Numero Civico: ");
            String nCivico = scanner.next();
            scanner.nextLine();
            System.out.print("Subalterno: ");
            String subalterno = scanner.next();
            System.out.print("Affittato (S/n): ");
            String confermaInput = scanner.next();
            boolean affittato;

            if(confermaInput.equals("s") || confermaInput.equals("S")) {
                affittato = true;
            }
            else {
                affittato = false;
            }

            builder.comune(comune).indirizzo(indirizzo).nCivico(nCivico).subalterno(Integer.parseInt(subalterno)).affittato(affittato);

            System.out.println("Vuoi aggiungere i dati catastali? (altrimenti sarà possibile aggiungerli più tardi " +
                    "dal menu di modifica immobili)");
            System.out.print("(S/n) : ");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")){
                System.out.print("Foglio: ");
                String foglio = scanner.next();
                System.out.print("Particella: ");
                String particella = scanner.next();
                System.out.print("Categoria: ");
                String categoria = scanner.next();
                System.out.print("Classe: ");
                String classe = scanner.next();
                System.out.print("Superficie: ");
                String superficie = scanner.next();
                System.out.print("Rendita: ");
                String rendita = scanner.next();

                builder.foglio(Integer.parseInt(foglio)).particella(Integer.parseInt(particella)).categoria(categoria)
                        .classe(classe).superficie(Float.parseFloat(superficie)).rendita(Float.parseFloat(rendita));

            }

            System.out.println("Confermi i dati inseriti? (S/n)");
            confermaInput = scanner.next();

            Immobile immobile = builder.build();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.aggiungiImmobile(immobile);
                System.out.println("Immobile aggiunto con successo.");
                return immobile;
            }
            else {
                System.out.println("Riprovare?");
                confermaInput = scanner.next();
                if(!confermaInput.equals("s") || !confermaInput.equals("S")) {
                    termina = true;
                }
            }
        }
        return null;
    }

    private void displayModificaImmobile() {
    }

    private void displayRimuoviImmobile() {
        String idImmobile;
        String confermaInput;

        System.out.println("Inserire l'ID dell'immobile che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idImmobile = scanner.next();
        if(controller.isImmobile(idImmobile)) {
            System.out.println("Rimuovere definitivamente l'immobile con ID " + idImmobile + "?");
            System.out.print("ATTENZIONE: verranno rimossi anche eventuali inquilino e contratto ad esso associati (S/n) ");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.rimuoviImmobile(idImmobile);
                System.out.println("Rimosso immobile con ID " + idImmobile);
            }
        }
        else {
            System.out.println("Non esiste nessun immobile con l'ID selezionato.");
        }
    }

}
