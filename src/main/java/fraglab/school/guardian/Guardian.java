package fraglab.school.guardian;

import fraglab.school.Person;
import fraglab.school.Telephone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GUARDIAN")
public class Guardian extends Person {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Telephone> telephones;

    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
