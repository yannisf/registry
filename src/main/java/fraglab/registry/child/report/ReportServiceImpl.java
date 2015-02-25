package fraglab.registry.child.report;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.child.Relationship;
import fraglab.registry.common.Telephone;
import fraglab.registry.foundation.FoundationService;
import fraglab.registry.foundation.meta.GroupDataTransfer;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.guardian.GuardianService;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    FoundationService foundationService;

    @Autowired
    ChildService childService;

    @Autowired
    GuardianService guardianService;

    @Override
    public List<ReportChild> getReportChildrenForChildGroup(String childGroupId) throws NotFoundException {
        List<ReportChild> reportChildren = new ArrayList<>();
        List<Child> children = childService.fetchChildrenForGroup(childGroupId);
        for (Child child : children) {
            reportChildren.add(mapChild(child));
        }

        return reportChildren;
    }

    private ReportChild mapChild(Child child) throws NotFoundException {
        ReportChild reportChild = new ReportChild(child.getInformalFullName());
        reportChild.setNotes(child.getNotes());
        for (Relationship relationship : child.getRelationships()) {
            mapRelationship(reportChild, relationship);
        }

        return reportChild;
    }

    private void mapRelationship(ReportChild reportChild, Relationship relationship) throws NotFoundException {
        Guardian guardian = relationship.getGuardian();
        ReportGuardian reportGuardian = new ReportGuardian(guardian.getFullName(),
                relationship.getMetadata().getType(), relationship.getMetadata().getPickup());
        mapTelephones(guardian, reportGuardian);
        reportChild.addGuardian(reportGuardian);
    }

    private void mapTelephones(Guardian guardian, ReportGuardian reportGuardian) {
        for (Telephone telephone : guardian.getTelephones()) {
            reportGuardian.addTelephone(telephone.getNumber(), telephone.getType());
        }
    }

    @Override
    public GroupDataTransfer getSchoolDataForChildGroup(String childGroupId) {
        return foundationService.fetchSchoolData(childGroupId);
    }

}
