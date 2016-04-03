package fraglab.registry.relationship;

import fraglab.web.BaseRestController;
import fraglab.web.NotFoundException;
import fraglab.web.NotIdentifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationship")
public class RelationshipController extends BaseRestController {

    @Autowired
    private RelationshipService relationshipService;

    @RequestMapping(method = RequestMethod.PUT, value = "/child/{childId}/guardian/{guardianId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody Relationship relationship, @PathVariable("childId") String childId,
                     @PathVariable("guardianId") String guardianId) throws NotIdentifiedException {
        relationshipService.save(relationship, childId, guardianId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/child/{childId}/guardian/{guardianId}")
    public Relationship findForChildAndGuardian(@PathVariable("childId") String childId,
                                                @PathVariable("guardianId") String guardianId) throws NotFoundException {
        return relationshipService.findForChildAndGuardian(childId, guardianId).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/child/{childId}")
    public List<Relationship> findAllForChild(@PathVariable("childId") String childId) {
        return relationshipService.findAllForChild(childId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        relationshipService.delete(id);
    }

}
