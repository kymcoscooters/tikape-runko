package tikape.runko.domain;

public class Viestiketju {

    private int viestiketju_id;
    private int alue_id;
    private String nimi;

    public Viestiketju(int viestiketju_id, int alue_id, String nimi) {
        this.viestiketju_id = viestiketju_id;
        this.alue_id = alue_id;
        this.nimi = nimi;
    }

    public int getViestiketju_id() {
        return viestiketju_id;
    }

    public void setViestiketju_id(int viestiketju_id) {
        this.viestiketju_id = viestiketju_id;
    }

    public int getAlue_id() {
        return alue_id;
    }

    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
