package fraglab.registry.child;

import fraglab.registry.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="CHILD_PHOTO")
public class ChildPhoto extends BaseEntity {

    @Lob
    @Column(name = "CONTENT")
    private byte[] content;

    @Column(name="MD5")
    private String md5;

    @OneToOne
    @MapsId
    private Child child;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
