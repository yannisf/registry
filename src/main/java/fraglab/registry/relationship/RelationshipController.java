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
    public void createOrUpdate(@RequestBody Relationship relationship, @PathVariable("childId") String childId, 
            @PathVariable("guardianId") String guardianId) throws NotIdentifiedException, NotFoundException {
        relationshipService.createOrUpdate(relationship, childId, guardianId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/child/{childId}/guardian/{guardianId}")
    public Relationship fetchForChildAndGuardian(@PathVariable("childId") String childId,
                                      @PathVariable("guardianId") String guardianId) throws NotFoundException {
        return relationshipService.fetchForChildAndGuardian(childId, guardianId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/child/{childId}")
    public List<Relationship> fetchAllForChild(@PathVariable("childId") String childId) {
        return relationshipService.fetchAllForChild(childId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        relationshipService.delete(id);
    }

}
