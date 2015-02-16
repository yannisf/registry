package fraglab.registry.child.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportChild implements Serializable {

    private String name;
    private String notes;
    private List<ReportGuardian> guardians;

    public ReportChild() {
    }

    public ReportChild(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ReportGuardian> getGuardians() {
        return guardians;
    }

    public void addGuardian(ReportGuardian guardian) {
        if (this.guardians == null) {
            this.guardians = new ArrayList<>();
        }
        this.guardians.add(guardian);
    }

}
