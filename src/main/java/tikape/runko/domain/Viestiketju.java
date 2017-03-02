package tikape.runko.domain;

public class Viestiketju {

    private int maara;
    private String nimi;
    private String aikaleima;

    public Viestiketju(int maara, String nimi, String aikaleima) {
        this.maara = maara;
        this.nimi = nimi;
        this.aikaleima = aikaleima;
    }

    public String getAikaleima() {
        return aikaleima;
    }

    
    public int getMaara() {
        return maara;
    }

    public String getNimi() {
        return nimi;
    }
    

    

}
