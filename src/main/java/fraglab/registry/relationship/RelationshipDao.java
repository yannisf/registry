package fraglab.registry.relationship;

import fraglab.data.GenericDao;
import fraglab.web.NotFoundException;

import java.util.List;

public interface RelationshipDao extends GenericDao<Relationship, String> {

    List<Relationship> fetchAllForChild(String childId);

    Relationship fetchForChildAndGuardian(String childId, String guardianId) throws NotFoundException;

}