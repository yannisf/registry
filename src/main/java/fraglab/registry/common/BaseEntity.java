package fraglab.registry.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    protected String id;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore()
    protected Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore()
    protected Date dateModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
