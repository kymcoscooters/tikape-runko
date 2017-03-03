package tikape.runko.domain;

public class Sivu {
    private int seuraava;
    private int edellinen;

    public Sivu(int seuraava, int edellinen) {
        this.seuraava = seuraava;
        this.edellinen = edellinen;
    }

    public int getSeuraava() {
        return seuraava;
    }

    public int getEdellinen() {
        return edellinen;
    }
    
    
}
