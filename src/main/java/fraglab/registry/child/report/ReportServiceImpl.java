package fraglab.registry.child.report;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.common.Telephone;
import fraglab.registry.group.GroupDataTransfer;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.guardian.GuardianService;
import fraglab.registry.overview.OverviewService;
import fraglab.registry.relationship.Relationship;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    OverviewService overviewService;

    @Autowired
    ChildService childService;

    @Autowired
    GuardianService guardianService;

    @Override
    public List<ReportChild> getReportChildrenForChildGroup(String groupId) throws NotFoundException {
        List<ReportChild> reportChildren = new ArrayList<>();
        List<String> ids = overviewService.findChildrenIdsForGroup(groupId);
        for (String id : ids) {
            Child child = childService.findWithRelationships(id).orElseThrow(NotFoundException::new);
            reportChildren.add(mapChild(child));
        }

        return reportChildren;
    }

    private ReportChild mapChild(Child child) throws NotFoundException {
        ReportChild reportChild = new ReportChild(child.getInformalFullName());
        reportChild.setNotes(child.getNotes());
        Collections.sort(child.getRelationships());
        for (Relationship relationship : child.getRelationships()) {
            mapRelationship(reportChild, relationship);
        }

        return reportChild;
    }

    private void mapRelationship(ReportChild reportChild, Relationship relationship) throws NotFoundException {
        Guardian guardian = relationship.getGuardian();
        ReportGuardian reportGuardian = new ReportGuardian(guardian.getFullName(),
                relationship.getMetadata().getType());
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
        return overviewService.findSchoolData(childGroupId);
    }

}
