package main;

import dao.DatabaseDAO;

public class Main {
    public static void main(String[] args) {
        DatabaseDAO databaseDAO = new DatabaseDAO();
        databaseDAO.connect();
    }
}