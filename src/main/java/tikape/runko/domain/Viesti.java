package tikape.runko.domain;

public class Viesti {
    private String lahettaja;
    private String viesti;

    public Viesti(String lahettaja, String viesti) {
        this.lahettaja = lahettaja;
        this.viesti = viesti;
    }

    public String getLahettaja() {
        return lahettaja;
    }

    public String getViesti() {
        return viesti;
    }
    
    
}
