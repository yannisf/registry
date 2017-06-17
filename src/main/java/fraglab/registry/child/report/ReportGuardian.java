package fraglab.registry.child.report;

import fraglab.registry.common.Telephone;
import fraglab.registry.relationship.RelationshipType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportGuardian implements Serializable {

    private String name;
    private RelationshipType relationship;
    private List<Telephone> telephones;

    public ReportGuardian() {
    }

    public ReportGuardian(String name, RelationshipType relationship) {
        this.name = name;
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationshipType getRelationship() {
        return relationship;
    }

    public void setRelationship(RelationshipType relationship) {
        this.relationship = relationship;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void addTelephone(String number, Telephone.Type type) {
        if (this.telephones == null) {
            this.telephones = new ArrayList<>();
        }
        this.telephones.add(new Telephone(number, type));
    }

}
