package main;

import dao.DatabaseDAO;
import view.MenuFacade;

public class Main {
    public static void main(String[] args) {
        DatabaseDAO databaseDAO = new DatabaseDAO();
        databaseDAO.connect();
        MenuFacade menufacade = new MenuFacade();
        menufacade.display();
    }
}