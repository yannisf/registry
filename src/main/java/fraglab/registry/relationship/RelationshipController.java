package fraglab.registry.relationship;

import fraglab.registry.BaseRestController;
import fraglab.registry.formobject.RelationshipWithGuardianAndAddress;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class RelationshipController extends BaseRestController {

    @Autowired
    ChildGuardianRelationshipService childGuardianRelationshipService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ChildGuardianRelationship fetch(@PathVariable() String id) throws NotFoundException {
        return childGuardianRelationshipService.fetch(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.GET)
    public ChildGuardianRelationship fetch(@PathVariable() String childId, @PathVariable() String guardianId)
            throws NotFoundException {
        return childGuardianRelationshipService.fetch(childId, guardianId);
    }

    @RequestMapping(value = "/child/{childId}/guardian", method = RequestMethod.GET)
    public List<ChildGuardianRelationship> fetchRelationships(@PathVariable() String childId) throws NotFoundException {
        return childGuardianRelationshipService.fetchRelationships(childId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        childGuardianRelationshipService.delete(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}/address", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGuardianAndRelationship(@PathVariable() String childId, @PathVariable() String guardianId,
                                              @RequestBody RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress) {
        if (!guardianId.equals(relationshipWithGuardianAndAddress.getGuardian().getId())) {
            throw new AssertionError("Invalid data");
        }
        ChildGuardianRelationship relationship = relationshipWithGuardianAndAddress.getRelationship();
        relationship.setChildId(childId);
        relationship.setGuardianId(guardianId);
        childGuardianRelationshipService.updateRelationshipWithGuardianAndAddress(relationshipWithGuardianAndAddress);
    }

}
