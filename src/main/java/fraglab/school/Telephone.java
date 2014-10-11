package fraglab.school;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Telephone extends GeneratedIdBaseEntity {

    private String number;

    @Enumerated(EnumType.STRING)
    private Type type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        MOBILE,
        WORK,
        HOME,
        OTHER;
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "id=" + getId() +
                ", number='" + number + '\'' +
                ", type=" + type +
                '}';
    }
}
