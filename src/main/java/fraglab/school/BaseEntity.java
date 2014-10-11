package fraglab.school;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore()
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore()
    private Date dateModified;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @PrePersist()
    void prePersist() {
        dateCreated = new Date();
    }

    @PreUpdate()
    void preUpdate() {
        dateModified = new Date();
    }

}
