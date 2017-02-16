package tikape.runko.domain;

public class Alue {
    private String nimi;
    private int maara;

    public Alue(String nimi, int maara) {
        this.nimi = nimi;
        this.maara = maara;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getMaara() {
        return maara;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }
    
    
}
