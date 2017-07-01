package fraglab.registry.child;

import fraglab.registry.child.Child;
import fraglab.registry.child.ChildJpaRepository;
import fraglab.registry.child.ChildServiceImpl;
import fraglab.registry.guardian.Guardian;
import fraglab.registry.relationship.Relationship;
import fraglab.registry.relationship.RelationshipMetadata;
import fraglab.registry.relationship.RelationshipType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChildServiceTest {

    @Mock
    private ChildJpaRepository childJpaRepository;

    @InjectMocks
    private ChildServiceImpl childService;

    @Test
    public void test() {
        String validGroupId = "validGroupId";

        List<Child> children = new ArrayList<>();
        Child child1 = createChild1();
        Child child2 = createChild2();
        Child child3 = createChild3();
        children.addAll(Arrays.asList(child1, child2, child3));

        when(childJpaRepository.findByGroupId(eq(validGroupId))).thenReturn(children);

        Map<String, List<String>> emailsForGroup = childService.findEmailsForGroup(validGroupId);
        assertThat(emailsForGroup, allOf(
                hasKey("Child1_First Child1_Last"),
                hasKey("Child2_First Child2_Last"))
        );


        System.out.println(emailsForGroup);
    }

    private Child createChild1() {
        Child child = new Child();
        child.setFirstName("Child1_First");
        child.setLastName("Child1_Last");

        Guardian guardian1 = new Guardian();
        guardian1.setFirstName("Guardian1_Child1_First");
        guardian1.setLastName("Guardian1_Child1_Last");

        Relationship relationship1 = new Relationship();
        relationship1.setGuardian(guardian1);
        relationship1.setMetadata(new RelationshipMetadata(RelationshipType.FATHER));

        child.addRelationship(relationship1);

        Guardian guardian2 = new Guardian();
        guardian2.setFirstName("Guardian2_Child1_First");
        guardian2.setLastName("Guardian2_Child1_Last");
        guardian2.setEmail("mother@child1");

        Relationship relationship2 = new Relationship();
        relationship2.setGuardian(guardian2);
        relationship2.setMetadata(new RelationshipMetadata(RelationshipType.MOTHER));

        child.addRelationship(relationship2);

        Guardian guardian3 = new Guardian();
        guardian3.setFirstName("Guardian3_Child1_First");
        guardian3.setLastName("Guardian3_Child1_Last");
        guardian3.setEmail("sister@child1");

        Relationship relationship3 = new Relationship();
        relationship3.setGuardian(guardian3);
        relationship3.setMetadata(new RelationshipMetadata(RelationshipType.SISTER));

        child.addRelationship(relationship3);

        return child;
    }

    private Child createChild2() {
        Child child = new Child();
        child.setFirstName("Child2_First");
        child.setLastName("Child2_Last");

        Guardian guardian1 = new Guardian();
        guardian1.setFirstName("Guardian1_Child2_First");
        guardian1.setLastName("Guardian1_Child2_Last");
        guardian1.setEmail("father@child2");

        Relationship relationship1 = new Relationship();
        relationship1.setGuardian(guardian1);
        relationship1.setMetadata(new RelationshipMetadata(RelationshipType.FATHER));

        child.addRelationship(relationship1);

        Guardian guardian2 = new Guardian();
        guardian2.setFirstName("Guardian2_Child2_First");
        guardian2.setLastName("Guardian2_Child2_Last");
        guardian2.setEmail("mother@child2");

        Relationship relationship2 = new Relationship();
        relationship2.setGuardian(guardian2);
        relationship2.setMetadata(new RelationshipMetadata(RelationshipType.MOTHER));

        child.addRelationship(relationship2);

        return child;
    }

    private Child createChild3() {
        Child child = new Child();
        child.setFirstName("Child3_First");
        child.setLastName("Child3_Last");

        Guardian guardian1 = new Guardian();
        guardian1.setFirstName("Guardian1_Child3_First");
        guardian1.setLastName("Guardian1_Child3_Last");

        Relationship relationship1 = new Relationship();
        relationship1.setGuardian(guardian1);
        relationship1.setMetadata(new RelationshipMetadata(RelationshipType.FATHER));

        child.addRelationship(relationship1);

        Guardian guardian2 = new Guardian();
        guardian2.setFirstName("Guardian2_Child3_First");
        guardian2.setLastName("Guardian2_Child3_Last");

        Relationship relationship2 = new Relationship();
        relationship2.setGuardian(guardian2);
        relationship2.setMetadata(new RelationshipMetadata(RelationshipType.MOTHER));

        child.addRelationship(relationship2);

        return child;
    }

}
