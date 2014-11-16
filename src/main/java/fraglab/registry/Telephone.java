package fraglab.registry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
public class Telephone extends BaseEntity implements Comparable<Telephone> {

    @NotNull
    @Column(nullable = false)
    private String number;

    @NotNull
    @Column(nullable = false)
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

    @Override
    public String toString() {
        return "Telephone{" +
                "id=" + getId() +
                ", number='" + number + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int compareTo(Telephone o) {
        return ((Integer) this.getType().ordinal()).compareTo(o.getType().ordinal());
    }

    public enum Type {
        MOBILE,
        WORK,
        HOME,
        OTHER;
    }
}
