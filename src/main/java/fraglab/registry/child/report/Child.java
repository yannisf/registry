package fraglab.registry.child.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Child implements Serializable {

    private String name;
    private List<Guardian> guardians;
    private String remarks;

    public Child() {
    }

    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Guardian> getGuardians() {
        return guardians;
    }

    public void addGuardian(Guardian guardian) {
        if (this.guardians == null) {
            this.guardians = new ArrayList<>();
        }
        this.guardians.add(guardian);
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
