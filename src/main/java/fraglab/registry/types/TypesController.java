package fraglab.registry.types;

import fraglab.registry.Telephone;
import fraglab.registry.child.Child;
import fraglab.registry.relationship.ChildGuardianRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/types")
public class TypesController {

    private static final Logger LOG = LoggerFactory.getLogger(TypesController.class);

    @RequestMapping(value = "/relationship", method = RequestMethod.GET)
    public ChildGuardianRelationship.Type[] getRelationshipTypes() {
        return ChildGuardianRelationship.Type.values();
    }


    @RequestMapping(value = "/telephone", method = RequestMethod.GET)
    public Telephone.Type[] getTelephoneTypes() {
        return Telephone.Type.values();
    }

    @RequestMapping(value = "/preschool", method = RequestMethod.GET)
    public Child.PreSchoolLevel[] getPreSchoolLevels() {
        return Child.PreSchoolLevel.values();
    }

}