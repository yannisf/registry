package fraglab.registry.child.report;

import fraglab.registry.child.ChildService;
import fraglab.registry.guardian.GuardianService;
import fraglab.registry.relationship.Relationship;
import fraglab.registry.relationship.RelationshipService;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ChildService childService;

    @Autowired
    GuardianService guardianService;

    @Autowired
    RelationshipService relationshipService;

    @Override
    public List<Child> getReportChildrenForChildGroup(String childGroupId) throws NotFoundException {
        List<Child> reportChildren = new ArrayList<>();
        List<fraglab.registry.child.Child> children = childService.fetchChildGroup(childGroupId);
        for (fraglab.registry.child.Child child : children) {
            Child reportChild = new Child(child.getName());
            reportChild.setRemarks(child.getNotes());
            List<Relationship> relationships = relationshipService.fetchRelationships(child.getId());
            for (Relationship relationship : relationships) {
                fraglab.registry.guardian.Guardian guardian = guardianService.fetch(relationship.getGuardianId());
                Guardian reportGuardian = new Guardian(guardian.getFirstName() + " " + guardian.getLastName(), relationship.getMetadata().getType(), relationship.getMetadata().getPickup());
                reportChild.addGuardian(reportGuardian);
            }
        }

        return reportChildren;
    }

}
