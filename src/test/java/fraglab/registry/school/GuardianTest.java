package fraglab.registry.school;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildService;
import fraglab.registry.relationship.Relationship;
import fraglab.registry.common.Telephone;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.guardian.GuardianService;
import fraglab.registry.relationship.RelationshipMetadata;
import fraglab.registry.relationship.RelationshipService;
import fraglab.registry.relationship.RelationshipType;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ContextConfiguration(locations = {"file:///home/yannis/development/school/src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class GuardianTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ChildService childService;
    
    @Autowired
    private GuardianService guardianService;
    
    @Autowired
    private RelationshipService relationshipService;
    
    private String childId = "3547e677-9f0f-4f48-9f0d-d0d8f0c404fb";
    
    private String guardianId;
    
    @Test
    @Transactional
    @Rollback(false)
    public void testAddGuardian() throws NotIdentifiedException, NotFoundException {
        Child child = childService.fetch(childId);
        Guardian guardian = new Guardian();
        guardian.setId(generateUuid());
        guardian.setFirstName("Μπαμπάς");
        guardian.setLastName("Κάποιου");
        Telephone telephone1 = new Telephone("2105847963", Telephone.Type.HOME);
        telephone1.setId(generateUuid());
        guardian.addTelephone(telephone1);
        Telephone telephone2 = new Telephone("6985847963", Telephone.Type.MOBILE);
        telephone2.setId(generateUuid());
        guardian.addTelephone(telephone2);
        guardian.setAddress(child.getAddress());
        guardianId = guardian.getId();
        guardianService.createOrUpdate(guardian, child.getAddress().getId());
    }

    @Test(dependsOnMethods = "testAddGuardian")
    @Transactional
    @Rollback(false)
    public void testAddRelationship() throws NotFoundException {
        Relationship relationship = new Relationship();
        relationship.setId(generateUuid());
        RelationshipMetadata metadata = new RelationshipMetadata();
        metadata.setPickup(true);
        metadata.setType(RelationshipType.FATHER);
        relationship.setMetadata(metadata);
        relationshipService.createOrUpdate(relationship, childId, guardianId);
    }

    @Test(dependsOnMethods = "testAddRelationship")
    public void testAssertions() throws NotFoundException {
        Child child = childService.fetch(childId);
        System.out.println(child);
    }

    @Test()
//    @Transactional
    public void testFetchForChild() {
        List<Relationship> relationships = relationshipService.fetchAllForChild(childId);
        System.out.println(relationships);
    }



    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

}
