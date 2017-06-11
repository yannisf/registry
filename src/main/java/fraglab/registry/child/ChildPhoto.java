package fraglab.registry.child;

import fraglab.registry.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="CHILD_PHOTO")
public class ChildPhoto extends BaseEntity {

    @Lob
    @Column(name = "CONTENT")
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
