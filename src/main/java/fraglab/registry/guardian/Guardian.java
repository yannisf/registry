package fraglab.registry.guardian;

import fraglab.registry.common.Person;
import fraglab.registry.common.Telephone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("GUARDIAN")
public class Guardian extends Person {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("type")
    private List<Telephone> telephones;

    private String email;

    private String profession;

    public List<Telephone> getTelephones() {
        Collections.sort(telephones);
        return Collections.unmodifiableList(telephones);
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
