package view.menu;

import controller.Controller;
import model.Immobile;
import model.ImmobileBuilder;


public class MenuImmobili extends Menu {
    private static String listaOpzioniModifica = " 1 - Comune\n" +
            " 2 - Indirizzo\n" +
            " 3 - Numero civico\n" +
            " 4 - Foglio\n" +
            " 5 - Particella\n" +
            " 6 - Categoria\n" +
            " 7 - Classe\n" +
            " 8 - Superficie\n" +
            " 9 - Rendita\n" +
            " 10 - Subalterno\n" +
            " x - Applica modifiche";

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
                    controller.mostraImmobili();
                    continue;
                case "3":
                    displayModificaImmobile();
                    continue;
                case "4":
                    displayRimuoviImmobile();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public Immobile displayAggiungiImmobile() {
        boolean termina = false;
        ImmobileBuilder builder = new ImmobileBuilder();

        while (!termina) {
            System.out.println("Inserire i dati dell'immobile");
            scanner.nextLine();
            System.out.print("Comune: ");
            String comune = scanner.nextLine();
            System.out.print("Indirizzo: ");
            String indirizzo = scanner.nextLine();
            System.out.print("Numero Civico: ");
            String nCivico = scanner.nextLine();
            System.out.print("Subalterno: ");
            String subalterno = scanner.next();

            String confermaInput;

            try {
                builder.comune(comune).indirizzo(indirizzo).nCivico(nCivico).subalterno(Integer.parseInt(subalterno));

                System.out.println("Vuoi aggiungere i dati catastali? (altrimenti sarà possibile aggiungerli più tardi " +
                        "dal menu di modifica immobili)");
                System.out.print("(S/n) : ");
                confermaInput = scanner.next();
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
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

                System.out.println("Confermi i dati inseriti? (S/n) ");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    Immobile immobile = builder.build();
                    controller.aggiungiImmobile(immobile);
                    System.out.println("Immobile aggiunto con successo.");
                    return immobile;
                } else {
                    System.out.println("Riprovare? (S/n) ");
                    confermaInput = scanner.next();
                    if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                        termina = true;
                    }
                }
            }catch (NumberFormatException e) {
                System.out.println("Sono stati inseriti valori non validi. Riprovare? (S/n) ");
                confermaInput = scanner.next();
                if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                    termina = true;
                }
            }
        }
        return null;
    }

    private void displayModificaImmobile() {
        controller.mostraImmobili();
        String idImmobile;
        String confermaInput;

        System.out.println("Inserire l'ID dell'immobile che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        idImmobile = scanner.next();
        if(controller.isImmobile(idImmobile)) {
            boolean termina=false;
            String input;
            ImmobileBuilder builder = new ImmobileBuilder();
            while(!termina){
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                try {
                    switch (input) {
                        case "1":
                            System.out.print("Inserire il nuovo comune: ");
                            input = scanner.next();
                            builder.comune(input);
                            continue;
                        case "2":
                            System.out.print("Inserire il nuovo indirizzo: ");
                            input = scanner.next();
                            builder.indirizzo(input);
                            continue;
                        case "3":
                            System.out.print("Inserire il nuovo numero civico: ");
                            input = scanner.next();
                            builder.nCivico(input);
                            continue;
                        case "4":
                            System.out.print("Inserire il nuovo foglio: ");
                            input = scanner.next();
                            builder.foglio(Integer.parseInt(input));
                            continue;
                        case "5":
                            System.out.print("Inserire la nuova particella: ");
                            input = scanner.next();
                            builder.particella(Integer.parseInt(input));
                            continue;
                        case "6":
                            System.out.print("Inserire la nuova categoria: ");
                            input = scanner.next();
                            builder.categoria(input);
                            continue;
                        case "7":
                            System.out.print("Inserire la nuova classe: ");
                            input = scanner.next();
                            builder.classe(input);
                            continue;
                        case "8":
                            System.out.print("Inserire la nuova superficie (in metri quadri): ");
                            input = scanner.next();
                            builder.superficie(Float.parseFloat(input));
                            continue;
                        case "9":
                            System.out.print("Inserire la nuova rendita: ");
                            input = scanner.next();
                            builder.rendita(Float.parseFloat(input));
                            continue;
                        case "10":
                            System.out.print("Inserire il nuovo subalterno: ");
                            input = scanner.next();
                            builder.subalterno(Integer.parseInt(input));
                            continue;
                        case "x":
                            termina = true;
                            continue;
                        default:
                            System.out.println("Valore non valido");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valore inserito non valido");
                }

            }

            System.out.println("Applicare le modifiche all'immobile con ID " + idImmobile + "?");
            confermaInput=scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Immobile immobile= builder.build();
                controller.modificaImmobile(idImmobile, immobile);
                System.out.println("Immobile modificato modificato");
            }
        }else {
            System.out.println("Non esiste nessun immobile con l'ID selezionato.");
        }
    }

    private void displayRimuoviImmobile() {
        controller.mostraImmobili();
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

