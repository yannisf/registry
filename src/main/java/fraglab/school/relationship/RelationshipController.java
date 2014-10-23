package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class RelationshipController extends BaseRestController {

    @Autowired
    ChildGuardianService childGuardianService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ChildGuardianRelationship fetch(@PathVariable() String id) throws NotFoundException {
        return childGuardianService.fetch(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.GET)
    public RelationshipMetadata fetch(@PathVariable() String childId, @PathVariable() String guardianId)
            throws NotFoundException {
        return childGuardianService.fetch(childId, guardianId).getRelationshipMetadata();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) throws NotFoundException {
        childGuardianService.delete(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian", method = RequestMethod.GET)
    public List<RelationshipDto> fetchRelationships(@PathVariable() String childId) throws NotFoundException {
        return childGuardianService.fetchRelationships(childId);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGuardianAndRelationship(@PathVariable() String childId, @PathVariable() String guardianId,
                                              @RequestBody RelationshipDto relationshipDto) {
        childGuardianService.updateGuardianAndRelationship(childId, guardianId, relationshipDto);
    }

}
