package fraglab.registry.relationship;

import fraglab.web.NotFoundException;

import java.util.List;

public interface RelationshipService {

    void save(Relationship relationship);
    
    void save(Relationship relationship, String childId, String guardianId);
    
    Relationship find(String id) throws NotFoundException;
    
    void delete(String id) throws NotFoundException;

    List<Relationship> findAllForChild(String childId);

    Relationship findForChildAndGuardian(String childId, String guardianId);

}
