package fraglab.registry.relationship;

import java.util.List;

public interface RelationshipService {

    void createOrUpdate(Relationship relationship);
    
    void createOrUpdate(Relationship relationship, String childId, String guardianId);
    
    Relationship fetch(String id);
    
    void delete(String id);

    List<Relationship> fetchAllForChild(String childId);

    Relationship fetchForChildAndGuardian(String childId, String guardianId);
}
