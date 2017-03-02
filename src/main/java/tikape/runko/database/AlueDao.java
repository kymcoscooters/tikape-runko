package tikape.runko.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;

    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Opiskelija WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Alue o = new Alue(nimi, id);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Alue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Alue.nimi AS Alue, COUNT(*) AS Viestiketjuja FROM Alue,\n"
                + "Viestiketju WHERE Alue.id = Viestiketju.alue_id GROUP BY Alue.id;");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            String nimi = rs.getString("Alue");
            int maara = rs.getInt("Viestiketjuja");

            alueet.add(new Alue(nimi, maara));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    public int getAluemaara() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Alue.* FROM Alue;");

        ResultSet rs = stmt.executeQuery();

        
        int i = 0;
        while (rs.next()) {
            i++;
        }
        
        connection.close();
        return i;
    }

    

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void lisaaAlue(String nimi, String ketju, String lahettaja, String viesti) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue (nimi) VALUES (?);");
        stmt.setString(1, nimi);

        stmt.execute();

        connection.close();
        System.out.println(getAluemaara() + " findall size");
        lisaaViestiketju(ketju, getAluemaara(), lahettaja, viesti);
    }

    public int haeKetju(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE nimi = ?");
        stmt.setString(1, nimi);

        ResultSet rs = stmt.executeQuery();
        
        int id = rs.getInt("id");
        
        connection.close();

        return id;

    }
    
    public int haeAlueid (String alue) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE nimi = ?");
        stmt.setString(1, alue);
        
        ResultSet rs = stmt.executeQuery();
        
        int id = 0;
        
        while (rs.next()) {
            id = rs.getInt("id");
        }
        
        connection.close();
        
        return id;
    }
    public List<Viestiketju> haeKetjut(String alue) throws SQLException {
        int alueid = haeAlueid(alue);
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT nimi, COUNT(*) AS maara FROM Viestiketju, Viesti WHERE Alue_id = ? AND Viestiketju.id = Viesti.ketju_id GROUP BY Viestiketju.id;");
        stmt.setInt(1, alueid);
        
        ResultSet rs = stmt.executeQuery();
        
        List<Viestiketju> lista = new ArrayList<>();
        
        while(rs.next()) {
            String nimi = rs.getString("nimi");
            int maara = rs.getInt("maara");
            
            lista.add(new Viestiketju(maara, nimi));
        }
        
        connection.close();
        
        return lista;
    }
    
    public Alue haeAlue(String alue) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE nimi = ?");
        stmt.setString(1, alue);
        
        ResultSet rs = stmt.executeQuery();
        Alue a = null;
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            a = new Alue(nimi, id);
        }
        
        connection.close();
        
        return a;
    }
    public void lisaaViestiketju(String ketju, int alue, String lahettaja, String viesti) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viestiketju (alue_id, nimi) VALUES (?, ?);");
        stmt.setInt(1, alue);
        stmt.setString(2, ketju);

        stmt.execute();

        connection.close();

        lisaaViesti(lahettaja, viesti, haeKetju(ketju));
    }

    public void lisaaViesti(String lahettaja, String viesti, int ketju) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (ketju_id, aikaleima, lahettaja, sisalto) "
                + "VALUES (?, ?, ?, ?);");
        stmt.setInt(1, ketju);
        stmt.setDate(2, Date.valueOf(LocalDate.now()));
        stmt.setString(3, lahettaja);
        stmt.setString(4, viesti);

        stmt.execute();

        connection.close();
    }
    
    public List<Viesti> haeViestit(String ketju) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WEHERE ketju_id=?;");
        stmt.setInt(1, haeKetju(ketju));
        
        ResultSet rs = stmt.executeQuery();
        
        ArrayList<Viesti> lista = new ArrayList<>();
        
        while (rs.next()) {
            String lahettaja = rs.getString("lahettaja");
            String viesti = rs.getString("viesti");
            
            lista.add(new Viesti(lahettaja, viesti));
        }
        
        connection.close();
        
        return lista;
    }
    
    public Viestiketju haeKetjuPalauttaaKetjun(String ketju) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareCall("SELECT * FROM Viestiketju WHERE nimi=?;");
        stmt.setString(0, ketju);
        
        ResultSet rs = stmt.executeQuery();
        
        String nimi = rs.getString("nimi");
        int maara = rs.getInt("id");
        
        connection.close();
        
        return new Viestiketju(maara, nimi);
    }
}
