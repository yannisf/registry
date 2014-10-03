package fraglab.school.child;

import fraglab.NotFoundException;
import fraglab.school.ControllerErrorWrapper;
import fraglab.school.relationship.ChildGuardianRelationship;
import fraglab.school.relationship.RelationshipDto;
import fraglab.school.relationship.RelationshipMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child")
public class ChildController {

    private static final Logger LOG = LoggerFactory.getLogger(ChildController.class);

    @Autowired
    ChildService childService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Child child) {
        childService.create(child);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Child child) throws NotFoundException {
        childService.update(id, child);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        childService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Child fetch(@PathVariable Long id) throws NotFoundException {
        return childService.fetch(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Child> fetchAll() {
        return childService.fetchAll();
    }

    @RequestMapping(value = "/{childId}/guardian/{guardianId}", method = RequestMethod.GET)
    public ChildGuardianRelationship fetchRelationship(@PathVariable() Long childId, @PathVariable() Long guardianId) throws NotFoundException {
        return childService.fetchRelationship(childId, guardianId);
    }

    @RequestMapping(value = "/{childId}/guardian", method = RequestMethod.GET)
    public List<RelationshipDto> fetchRelationships(@PathVariable() Long childId) throws NotFoundException {
        return childService.fetchRelationships(childId);
    }

    @RequestMapping(value = "/{childId}/guardian/{guardianId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createRelationship(@PathVariable() Long childId, @PathVariable() Long guardianId,
                                   @RequestBody RelationshipMetadata relationshipMetadata) throws NotFoundException {
        childService.createRelationship(childId, guardianId, relationshipMetadata);
    }

    @RequestMapping(value = "/{childId}/guardian/{guardianId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRelationship(@PathVariable() Long childId, @PathVariable() Long guardianId) throws NotFoundException {
        childService.deleteRelationship(childId, guardianId);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    ControllerErrorWrapper handleNotFoundException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Resource not found", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    ControllerErrorWrapper handleException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Bad request", e.getMessage());
    }


}
