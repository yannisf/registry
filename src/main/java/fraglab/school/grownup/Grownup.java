package fraglab.school.grownup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fraglab.school.Person;
import fraglab.school.Telephone;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GROWN_UP")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Grownup extends Person implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telephone> telephones;

    private String profession;

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    public void addTelephone(Telephone telephone) {
        if (telephones == null) {
            telephones = new ArrayList<>();
        }
        telephones.add(telephone);
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

}
