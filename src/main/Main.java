package main;

import dao.DatabaseDAO;
import view.MenuFacade;
import view.MenuLogin;

public class Main {
    public static void main(String[] args) {
        MenuLogin menu = new MenuLogin();
        menu.display();
    }
}