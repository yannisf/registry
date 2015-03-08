package fraglab.registry.relationship;

import fraglab.web.NotFoundException;

import java.util.List;

public interface RelationshipService {

    void createOrUpdate(Relationship relationship);
    
    void createOrUpdate(Relationship relationship, String childId, String guardianId);
    
    Relationship fetch(String id) throws NotFoundException;
    
    void delete(String id) throws NotFoundException;

    List<Relationship> fetchAllForChild(String childId);

    Relationship fetchForChildAndGuardian(String childId, String guardianId);
}
