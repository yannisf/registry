package fraglab.school.guardian;

import fraglab.NotFoundException;
import fraglab.school.ControllerErrorWrapper;
import fraglab.school.Telephone;
import fraglab.school.relationship.ChildGuardianRelationship;
import fraglab.school.relationship.RelationshipDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardian")
public class GuardianController {

    private static final Logger LOG = LoggerFactory.getLogger(GuardianController.class);

    @Autowired
    GuardianService guardianService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Guardian guardian) {
        guardianService.create(guardian);
    }

    @RequestMapping(value = "/relationship", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RelationshipDto guardianRelationship) {
        Guardian guardian = guardianRelationship.getGuardian();
        ChildGuardianRelationship relationship = guardianRelationship.getRelationship();
        relationship.setChildId(guardianRelationship.getChild().getId());

        guardianService.establishRelationship(guardian, relationship);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Guardian guardian) throws NotFoundException {
        guardianService.update(id, guardian);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) throws NotFoundException {
        guardianService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Guardian fetch(@PathVariable Long id) throws NotFoundException {
        return guardianService.fetch(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Guardian> fetchAll() {
        return guardianService.fetchAll();
    }

    @RequestMapping(value = "/telephone/types", method = RequestMethod.GET)
    public Telephone.Type[] getRelationshipTypes() {
        return Telephone.Type.values();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    ControllerErrorWrapper handleNotFoundException(Exception e) {
        LOG.warn("Error handling ", e);
        return new ControllerErrorWrapper("Resource not found", e.getMessage());
    }

}
