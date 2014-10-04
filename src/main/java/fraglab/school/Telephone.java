package fraglab.school;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Telephone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String number;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        HOME, WORK, MOBILE, OTHER;
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type=" + type +
                '}';
    }
}
