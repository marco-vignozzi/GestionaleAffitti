package view.menu;

import controller.Controller;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner scanner;
    protected static Controller controller;

    Menu(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public abstract void display();
}
