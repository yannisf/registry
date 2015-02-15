package fraglab.registry.child.report;

import java.io.Serializable;

public class Telephone implements Serializable {

    private String number;
    private String type;

    public Telephone() {
    }

    public Telephone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
