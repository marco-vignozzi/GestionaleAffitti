package view;

import controller.Controller;
import model.Contratto;
import model.Immobile;
import model.Inquilino;

public class MenuContratti extends Menu {
    public MenuContratti(Controller controller) {
        super(controller);
    }
    public void display() {
        // TODO: finire di implementare
        boolean termina = false;

        System.out.println("Bentornato nell'app N° 1 di DESTE e VIGNOZ!");

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi contratto");
            System.out.println(" 2 - Mostra tabella contratti");
            System.out.println(" 3 - Modifica contratto");
            System.out.println(" 4 - Rimuovi contratto");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiContratto();
                    continue;
                case "2":
                    displayVisualizzaContratti();
                    continue;
                case "3":
                    displayModificaContratto();         // TODO: implementare
                    continue;
                case "4":
                    displayRimuoviContratto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito invalido come te e tua madre");
            }
        }
    }

    public void displayAggiungiContratto() {
        System.out.println("Per creare un contratto è necessario creare l'inquilino a cui è associato.");
        MenuInquilini menuInquilini = new MenuInquilini(this.controller);
        menuInquilini.displayAggiungiInquilino();
    }

    public Contratto displayAggiungiContratto(Inquilino inquilino) {
        boolean conferma = false;
        String cfInquilino = inquilino.getCf();

        while (!conferma) {
            controller.visualizzaImmobili();
            System.out.println("Inserisci i dati del contratto");
            System.out.print("ID dell'immobile come indicato nella tabella: ");
            String idImmobile = scanner.next();
            if(!controller.isImmobile(idImmobile)) {
                System.out.println("L'immobile selezionato non è presente nel database. Si desidera crearlo adesso? (S/n)");
                String confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    MenuImmobili menuImmobili = new MenuImmobili(this.controller);
                    Immobile immobile = menuImmobili.displayAggiungiImmobile();
                    if(immobile != null) {
                        idImmobile = Integer.toString(immobile.getId());
                    }
                    else {
                        return null;
                    }
                }
                else {
                    System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                    confermaInput = scanner.next();

                    if (confermaInput.equals("s")) {
                        conferma = true;
                    }
                }
            }
            System.out.print("Data di inizio contratto (formato: YYYY-MM-DD): ");
            String dataInizio = scanner.next();
            System.out.print("Data di fine contratto (formato: YYYY-MM-DD): ");
            String dataFine = scanner.next();
            System.out.print("Canone mensile: ");
            String canone = scanner.next();

            System.out.println("Confermi i dati inseriti? (s/n)");
            String confermaInput = scanner.next();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Contratto contratto = new Contratto(Integer.parseInt(idImmobile), cfInquilino, controller.getCfProprietario(), dataInizio, dataFine, Float.parseFloat(canone));
                return contratto;
            }

            System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
            confermaInput = scanner.next();

            if (confermaInput.equals("s")) {
                conferma = true;
            }
        }
        return null;
    }

    private void displayVisualizzaContratti() {
        controller.visualizzaContratti();
    }

    private void displayModificaContratto() {

    }

    private void displayRimuoviContratto() {
        String idContratto;
        String confermaInput;

        System.out.println("Inserire l'ID del contratto che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idContratto = scanner.next();
        if(controller.isContratto(idContratto)) {
            System.out.println("Rimuovere definitivamente il contratto con ID " + idContratto + "?");
            System.out.print("ATTENZIONE: verranno rimossi anche i dati dell'inquilino ad esso associato (S/n)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                controller.rimuoviContratto(idContratto);
                System.out.println("Rimosso contratto con ID " + idContratto + " e relativo inquilino.");
            }
        }
        else {
            System.out.println("Non esiste nessun contratto con l'ID selezionato.");
        }
    }

}
