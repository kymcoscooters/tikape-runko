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
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

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

        lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(32));");
        lista.add("CREATE TABLE Viestiketju (id integer PRIMARY KEY, alue_id integer, nimi varchar(64));");
        lista.add("CREATE TABLE Viesti (ketju_id integer, aikaleima datetime, lahettaja varchar(16), sisalto varchar(1024));");
        
        return lista;
    }
    
    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        
        lista.add("CREATE TABLE Alue (id SERIAL PRIMARY KEY, nimi varchar(32));");
        lista.add("CREATE TABLE Viestiketju (id SERIAL PRIMARY KEY, alue_id integer, nimi varchar(64));");
        lista.add("CREATE TABLE Viesti (ketju_id integer, aikaleima timestamp, lahettaja varchar(16), sisalto varchar(1024));");
        
        return lista;
    }
}
