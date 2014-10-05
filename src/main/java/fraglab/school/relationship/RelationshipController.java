package fraglab.school.relationship;

import fraglab.NotFoundException;
import fraglab.school.BaseRestController;
import fraglab.school.guardian.Guardian;
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
    public ChildGuardianRelationship fetch(@PathVariable() Long id) throws NotFoundException {
        return childGuardianService.fetch(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.GET)
    public ChildGuardianRelationship fetch(@PathVariable() Long childId, @PathVariable() Long guardianId)
            throws NotFoundException {
        return childGuardianService.fetch(childId, guardianId);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable() Long childId, @PathVariable() Long guardianId,
                       @RequestBody RelationshipMetadata relationshipMetadata) throws NotFoundException {
        childGuardianService.create(childId, guardianId, relationshipMetadata);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable() Long id, @RequestBody RelationshipMetadata relationshipMetadata)
            throws NotFoundException {
        childGuardianService.update(id, relationshipMetadata);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        childGuardianService.delete(id);
    }

    @RequestMapping(value = "/child/{childId}/guardian", method = RequestMethod.GET)
    public List<GuardianRelationshipDto> fetchRelationships(@PathVariable() Long childId) throws NotFoundException {
        return childGuardianService.fetchRelationshipDtos(childId);
    }

    @RequestMapping(value = "/child/{childId}/guardian", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createGuardianAndRelationship(@PathVariable() Long childId, @RequestBody GuardianRelationshipDto guardianRelationship) {
        Guardian guardian = guardianRelationship.getGuardian();
        ChildGuardianRelationship relationship = guardianRelationship.getRelationship();
        relationship.setChildId(childId);

        childGuardianService.createGuardianAndRelationship(guardian, relationship);
    }

    @RequestMapping(value = "/child/{childId}/guardian/{guardianId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGuardianAndRelationship(@PathVariable() Long childId, @PathVariable() Long guardianId,
                                              @RequestBody GuardianRelationshipDto guardianRelationship) throws NotFoundException {
        childGuardianService.updateGuardianAndRelationship(childId, guardianId, guardianRelationship);
    }

}
