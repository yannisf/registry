package fraglab.registry.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fraglab.registry.address.Address;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person extends BaseEntity {

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Address address;

    private String nationality;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String notes;

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

    @JsonIgnore
    public String getFullName() {
        String firstName = getFirstName() != null ? getFirstName() : StringUtils.EMPTY;
        String lastName = getLastName() != null ? getLastName() : StringUtils.EMPTY;
        return StringUtils.stripToEmpty(firstName + StringUtils.SPACE + lastName);
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonIgnore
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public enum Gender {
        MALE, FEMALE, OTHER;
    }

}
