package fraglab.school.relationship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relationship")
public class RelationshipController {

    private static final Logger LOG = LoggerFactory.getLogger(RelationshipController.class);

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ChildGuardianRelationship.Type[] getAllAffinities() {
        return ChildGuardianRelationship.Type.values();
    }

}
