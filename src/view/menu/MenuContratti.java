package view.menu;

import controller.Controller;
import model.Contratto;
import model.ContrattoBuilder;
import model.Immobile;
import model.Inquilino;

public class MenuContratti extends Menu {
    private static String listaOpzioniModifica = " 1 - Data inizio (formato: YYYY-MM-DD)\n" +
            " 2 - Data fine (formato: YYYY-MM-DD)\n" +
            " 3 - Prossimo pagamento (formato: YYYY-MM-DD)\n" +
            " 4 - Canone\n" +
            " 5 - Sfratto\n" +
            " 6 - Proroga\n" +
            " x - Applica modifiche";

    public MenuContratti(Controller controller) {
        super(controller);
    }

    public void display() {

        boolean termina = false;

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
                    controller.mostraContratti();
                    continue;
                case "3":
                    displayModificaContratto();
                    continue;
                case "4":
                    displayRimuoviContratto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public void displayAggiungiContratto() {
        System.out.println("Per creare un contratto è necessario creare prima l'inquilino a cui è associato.");
        MenuInquilini menuInquilini = new MenuInquilini(this.controller);
        menuInquilini.displayAggiungiInquilino();
    }

    public Contratto displayAggiungiContratto(Inquilino inquilino) {
        boolean termina = false;
        String cfInquilino = inquilino.getCf();
        String confermaInput;
        System.out.println("Inserisci i dati del contratto");

        while (!termina) {
            controller.mostraImmobili();
            System.out.print("ID dell'immobile come indicato nella tabella: ");
            String idImmobile = scanner.next();

            if(!controller.isImmobile(idImmobile)) {
                System.out.println("L'immobile selezionato non è presente nel database. Si desidera crearlo adesso? (S/n)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    MenuImmobili menuImmobili = new MenuImmobili(this.controller);
                    Immobile immobile = menuImmobili.displayAggiungiImmobile();
                    if(immobile == null) {
                        return null;
                    }
                    idImmobile = Integer.toString(immobile.getID());
                }
                else {
                    System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                    confermaInput = scanner.next();

                    if (confermaInput.equals("s")) {
                        termina = true;
                        continue;
                    }
                    else {
                        continue;
                    }
                }
            }else if (controller.isAffittato(idImmobile)){
                System.out.println("L'immobile selezionato è già affittato.");
                System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    termina = true;
                    continue;
                }
                else {
                    continue;
                }
            }

            System.out.print("Data di inizio contratto (formato: YYYY-MM-DD): ");
            String dataInizio = scanner.next();
            System.out.print("Data di fine contratto (formato: YYYY-MM-DD): ");
            String dataFine = scanner.next();
            System.out.print("Data di pagamento (formato: DD): ");
            String dataPagamento = scanner.next();
            System.out.print("Canone mensile: ");
            String canone = scanner.next();
            System.out.print("Aggiungere possibilità di proroga? (S/n) ");
            confermaInput = scanner.next();

            boolean proroga;
            proroga = confermaInput.equals("s") || confermaInput.equals("S");

            System.out.println("Confermi i dati inseriti? (S/n) ");
            confermaInput = scanner.next();
            try {
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    ContrattoBuilder builder = new ContrattoBuilder();
                    builder.idImmobile(Integer.parseInt(idImmobile))
                            .cfInquilino(cfInquilino)
                            .dataInizio(dataInizio)
                            .dataFine(dataFine)
                            .prossimoPagamento(dataPagamento)
                            .canone(Float.parseFloat(canone))
                            .proroga(proroga);
                    Contratto contratto = builder.build();
                    return contratto;
                }
            }catch (NumberFormatException e) {
                System.out.println("Sono stati inseriti dei valori non validi");
            }

            System.out.println("Riprovare? (S/n)");
            confermaInput = scanner.next();

            if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                termina = true;
            }
        }
        return null;
    }

    private void displayModificaContratto() {
        controller.mostraContratti();
        String idContratto;
        String confermaInput;

        System.out.println("Inserire l'ID del contratto che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        idContratto = scanner.next();

        if(controller.isContratto(idContratto)) {
            boolean termina=false;
            String input;
            ContrattoBuilder builder = new ContrattoBuilder();

            while(!termina) {
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                try {
                    switch (input) {
                        case "1":
                            System.out.print("Inserire la nuova data di inizio contratto: ");
                            input = scanner.next();
                            builder.dataInizio(input);
                            continue;
                        case "2":
                            System.out.print("Inserire la nuova data di fine contratto: ");
                            input = scanner.next();
                            builder.dataFine(input);
                            continue;
                        case "3":
                            System.out.print("Inserire la nuova data di pagamento: ");
                            input = scanner.next();
                            builder.prossimoPagamento(input);
                            continue;
                        case "4":
                            System.out.print("Inserire nuovo canone mensile: ");
                            input = scanner.next();
                            builder.canone(Integer.parseInt(input));
                            continue;
                        case "5":
                            System.out.print("Inserire possibilità di sfratto (S/n): ");
                            input = scanner.next();
                            builder.sfratto(input.equals("s") || input.equals("S"));
                            continue;
                        case "6":
                            System.out.print("Inserire possibilità di proroga (S/n): ");
                            input = scanner.next();
                            builder.proroga(input.equals("s") || input.equals("S"));
                            continue;
                        case "x":
                            termina = true;
                            continue;
                        default:
                            System.out.println("Valore non valido");
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Valore inserito non valido");
                }
            }

            System.out.println("Applicare le modifiche al contratto con ID " + idContratto + "?");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Contratto contratto = builder.build();
                controller.modificaContratto(idContratto, contratto);
                System.out.println("Contratto modificato");
            }
        }
        else {
            System.out.println("Non esiste nessun contratto con l'ID selezionato.");
        }

    }

    private void displayRimuoviContratto() {
        controller.mostraContratti();
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
