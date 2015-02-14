package fraglab.registry.relationship;

import fraglab.registry.formobject.RelationshipWithGuardianAndAddress;
import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class RelationshipController extends BaseRestController {

    @Autowired
    RelationshipService relationshipService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Relationship fetch(@PathVariable() String id) throws NotFoundException {
        return relationshipService.fetch(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.GET)
    public Relationship fetch(@PathVariable() String childId, @PathVariable() String guardianId)
            throws NotFoundException {
        return relationshipService.fetch(childId, guardianId);
    }

    @RequestMapping(value = "/child/{childId}/guardian", method = RequestMethod.GET)
    public List<Relationship> fetchRelationships(@PathVariable() String childId) throws NotFoundException {
        return relationshipService.fetchRelationships(childId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        relationshipService.delete(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}/address", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGuardianAndRelationship(@PathVariable() String childId, @PathVariable() String guardianId,
                                              @RequestBody RelationshipWithGuardianAndAddress relationshipWithGuardianAndAddress) {
        if (!guardianId.equals(relationshipWithGuardianAndAddress.getGuardian().getId())) {
            throw new AssertionError("Invalid data");
        }
        Relationship relationship = relationshipWithGuardianAndAddress.getRelationship();
        relationship.setChildId(childId);
        relationship.setGuardianId(guardianId);
        relationshipService.updateRelationshipWithGuardianAndAddress(relationshipWithGuardianAndAddress);
    }

}
