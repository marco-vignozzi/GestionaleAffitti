package dao;

import java.sql.*;


public class DatabaseDAO {
    private Connection connection;

    public void connect() {
        System.out.println("Connessione al database...");
        try{
            //Verifico che i Driver ci siano nelle External Libraries
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Creo la connessione
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gestionale_affitti", "root","");
            System.out.println("Database connesso!");

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Errore di connessione al database: " + e.getMessage()); //e.printStackTrace();
        }
    }

    // ritorna true se l'email è presente, false altrimenti
    public boolean emailDisponibile(String email){
        if(connection == null){
            connect();
        }
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM proprietari JOIN inquilini WHERE email = '" + email + "'");  // join ON email?

            return !resultSet.next();  // ritorna true se non ci sono elementi nella tabella

        } catch(SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());     //e.printStackTrace();
        }
        return false;
    }



}