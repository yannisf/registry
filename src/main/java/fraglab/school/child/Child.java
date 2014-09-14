package fraglab.school.child;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fraglab.json.LocalDateDeserializer;
import fraglab.json.LocalDateSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Child implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String callName;

    private Date dateOfBirth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public Date getDateOfBirth() {
        return new Date(dateOfBirth.getTime());
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth.getTime());
    }

    public Child copy() {
        Child child = new Child();
        child.setFirstName(getFirstName());
        child.setLastName(getLastName());
        child.setCallName(getCallName());
        child.setDateOfBirth(new Date(dateOfBirth.getTime()));

        return child;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", callName='" + callName + '\'' +
                '}';
    }

}