package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");

        lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(32));");
        lista.add("CREATE TABLE Viestiketju (id integer PRIMARY KEY, alue_id integer, nimi varchar(64));");
        lista.add("CREATE TABLE Viesti (ketju_id integer, aikaleima datetime, lahettaja varchar(16), sisalto varchar(1024));");
        
//        lista.add("INSERT INTO Alue VALUES (1, 'ohjelmointi');");
//        lista.add("INSERT INTO Alue VALUES (2, 'autot');");
//        lista.add("INSERT INTO Alue VALUES (3, 'hepat');");
//        
//        lista.add("INSERT INTO Viestiketju VALUES (1, 2, 'fordi on paras');");
//        lista.add("INSERT INTO Viestiketju VALUES (2, 2, 'lamborghini jepa auto');");
//        lista.add("INSERT INTO Viestiketju VALUES (3, 1, 'java on paras kieli');");
//        lista.add("INSERT INTO Viestiketju VALUES (4, 1, 'keskustelua RUBYstä');");
//        lista.add("INSERT INTO Viestiketju VALUES (5, 3, 'shetlandsponny hc häst');");
        return lista;
    }
}
