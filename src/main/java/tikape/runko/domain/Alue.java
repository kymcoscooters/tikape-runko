package tikape.runko.domain;

public class Alue {
    private String nimi;
    private int maara;
    private String viimeisin;

    public Alue(String nimi, int maara, String viimeisin) {
        this.nimi = nimi;
        this.maara = maara;
        this.viimeisin = viimeisin;
    }

    public String getViimeisin() {
        return viimeisin;
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
