package main;

import dao.DatabaseDAO;
import view.MenuFacade;

public class Main {
    public static void main(String[] args) {
        MenuFacade menufacade = new MenuFacade();
        menufacade.display();
    }
}